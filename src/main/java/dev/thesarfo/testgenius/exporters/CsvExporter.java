package dev.thesarfo.testgenius.exporters;


import dev.thesarfo.testgenius.core.DataSet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Exporter for CSV format.
 */
public class CsvExporter implements DataExporter {
    @Override
    public void export(DataSet dataSet, File file) throws IOException {
        Map<String, List<Map<String, Object>>> allEntities = dataSet.getAllEntities();

        for (Map.Entry<String, List<Map<String, Object>>> entry : allEntities.entrySet()) {
            String entityName = entry.getKey();
            List<Map<String, Object>> entities = entry.getValue();

            if (entities.isEmpty()) {
                continue;
            }

            // Create a file for each entity type
            File entityFile;
            if (allEntities.size() == 1) {
                entityFile = file;
            } else {
                String fileName = file.getName();
                String extension = "";
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex > 0) {
                    extension = fileName.substring(dotIndex);
                    fileName = fileName.substring(0, dotIndex);
                }
                entityFile = new File(file.getParent(), fileName + "_" + entityName + extension);
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(entityFile))) {
                // Find all field names across all entities
                Set<String> fieldNames = new TreeSet<>();
                for (Map<String, Object> entity : entities) {
                    for (String fieldName : entity.keySet()) {
                        // Skip relationship objects
                        Object value = entity.get(fieldName);
                        if (!(value instanceof Map) && !(value instanceof List)) {
                            fieldNames.add(fieldName);
                        }
                    }
                }

                // Write header
                boolean first = true;
                for (String fieldName : fieldNames) {
                    if (!first) {
                        writer.print(",");
                    }
                    writer.print(escapeCsv(fieldName));
                    first = false;
                }
                writer.println();

                // Write data
                for (Map<String, Object> entity : entities) {
                    first = true;
                    for (String fieldName : fieldNames) {
                        if (!first) {
                            writer.print(",");
                        }

                        Object value = entity.get(fieldName);
                        if (value instanceof Map || value instanceof List) {
                            writer.print("");
                        } else {
                            writer.print(escapeCsv(value != null ? value.toString() : ""));
                        }

                        first = false;
                    }
                    writer.println();
                }
            }
        }
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        boolean needsQuoting = value.contains(",") || value.contains("\"") ||
                value.contains("\n") || value.contains("\r");

        if (needsQuoting) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        } else {
            return value;
        }
    }
}