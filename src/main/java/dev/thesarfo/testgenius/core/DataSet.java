package dev.thesarfo.testgenius.core;


import dev.thesarfo.testgenius.exporters.CsvExporter;
import dev.thesarfo.testgenius.exporters.DataExporter;
import dev.thesarfo.testgenius.exporters.JsonExporter;
import dev.thesarfo.testgenius.exporters.SqlExporter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a generated dataset containing entity instances.
 */
public class DataSet {
    private final Map<String, List<Map<String, Object>>> entities = new HashMap<>();

    /**
     * Adds an entity instance to the dataset.
     *
     * @param entityName The entity type name
     * @param entity The entity instance data
     */
    public void addEntity(String entityName, Map<String, Object> entity) {
        entities.computeIfAbsent(entityName, k -> new ArrayList<>()).add(entity);
    }

    /**
     * Gets all entity instances of a specific type.
     *
     * @param entityName The entity type name
     * @return A list of entity instances
     */
    public List<Map<String, Object>> getEntities(String entityName) {
        return entities.getOrDefault(entityName, new ArrayList<>());
    }

    /**
     * Gets a random entity of a specific type.
     *
     * @param entityName The entity type name
     * @return A random entity instance or null if none exist
     */
    public Map<String, Object> getRandomEntity(String entityName) {
        List<Map<String, Object>> entityList = getEntities(entityName);
        if (entityList.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * entityList.size());
        return entityList.get(randomIndex);
    }

    /**
     * Exports the dataset to a JSON file.
     *
     * @param file The destination file in JSON Format
     * @throws IOException If an I/O error occurs
     */
    public void exportToJson(File file) throws IOException {
        DataExporter exporter = new JsonExporter();
        exporter.export(this, file);
    }

    /**
     * Exports the dataset to a SQL file.
     *
     * @param file The destination file in SQL Format
     * @throws IOException If an I/O error occurs
     */
    public void exportToSql(File file) throws IOException {
        DataExporter exporter = new SqlExporter();
        exporter.export(this, file);
    }

    /**
     * Exports the dataset to a CSV file.
     *
     * @param file The destination file in CSV Format
     * @throws IOException If an I/O error occurs
     */
    public void exportToCsv(File file) throws IOException {
        DataExporter exporter = new CsvExporter();
        exporter.export(this, file);
    }

    /**
     * Gets all entity types in this dataset.
     *
     * @return A map of entity types to their instances
     */
    public Map<String, List<Map<String, Object>>> getAllEntities() {
        return entities;
    }
}