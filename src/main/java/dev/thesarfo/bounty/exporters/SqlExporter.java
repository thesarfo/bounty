package dev.thesarfo.bounty.exporters;


import dev.thesarfo.bounty.core.DataSet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Exporter for SQL INSERT statements.
 */
public class SqlExporter implements DataExporter {
    @Override
    public void export(DataSet dataSet, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            Map<String, List<Map<String, Object>>> allEntities = dataSet.getAllEntities();

            for (Map.Entry<String, List<Map<String, Object>>> entry : allEntities.entrySet()) {
                String tableName = entry.getKey();
                List<Map<String, Object>> entities = entry.getValue();

                if (entities.isEmpty()) {
                    continue;
                }

                // Create table structure
                Map<String, Object> sampleEntity = entities.get(0);
                writer.println("-- Table structure for " + tableName);
                writer.println("CREATE TABLE IF NOT EXISTS " + tableName + " (");

                int i = 0;
                for (String columnName : sampleEntity.keySet()) {
                    // Skip relationship objects
                    if (sampleEntity.get(columnName) instanceof Map ||
                            sampleEntity.get(columnName) instanceof List) {
                        continue;
                    }

                    String type = getSqlType(sampleEntity.get(columnName));
                    writer.print("  " + columnName + " " + type);

                    if (i < sampleEntity.size() - 1) {
                        writer.println(",");
                    } else {
                        writer.println();
                    }
                    i++;
                }
                writer.println(");");
                writer.println();

                // Generate INSERT statements
                writer.println("-- Data for " + tableName);
                for (Map<String, Object> entity : entities) {
                    StringBuilder columns = new StringBuilder();
                    StringBuilder values = new StringBuilder();

                    boolean first = true;
                    for (Map.Entry<String, Object> field : entity.entrySet()) {
                        // Skip relationship objects
                        if (field.getValue() instanceof Map ||
                                field.getValue() instanceof List) {
                            continue;
                        }

                        if (!first) {
                            columns.append(", ");
                            values.append(", ");
                        }

                        columns.append(field.getKey());
                        values.append(formatSqlValue(field.getValue()));

                        first = false;
                    }

                    writer.println("INSERT INTO " + tableName +
                            " (" + columns + ") VALUES (" + values + ");");
                }

                writer.println();
            }
        }
    }

    private String getSqlType(Object value) {
        if (value == null) {
            return "VARCHAR(255)";
        }

        if (value instanceof Integer || value instanceof Long) {
            return "INTEGER";
        }

        if (value instanceof Float || value instanceof Double) {
            return "DECIMAL(10,2)";
        }

        if (value instanceof Boolean) {
            return "BOOLEAN";
        }

        if (value instanceof java.util.Date) {
            return "TIMESTAMP";
        }

        return "VARCHAR(255)";
    }

    private String formatSqlValue(Object value) {
        if (value == null) {
            return "NULL";
        }

        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }

        // Escape single quotes in strings
        return "'" + value.toString().replace("'", "''") + "'";
    }
}