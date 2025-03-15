package dev.thesarfo.testgenius.core;


import java.util.HashMap;
import java.util.Map;

/**
 * Main entry point for the test data generation library.
 * This class provides methods to define entities and their relationships,
 * and to generate datasets according to those definitions.
 */
public class TestDataGenerator {
    private final Map<String, EntityDefinition> entityDefinitions = new HashMap<>();

    /**
     * Defines a new entity with the given name.
     *
     * @param name The name of the entity
     * @return A builder for defining the entity's fields and relationships
     */
    public EntityDefinition defineEntity(String name) {
        EntityDefinition entityDefinition = new EntityDefinition(name);
        entityDefinitions.put(name, entityDefinition);
        return entityDefinition;
    }

    /**
     * Starts the process of generating test data.
     *
     * @return A builder for configuring the data generation process
     */
    public DataSetBuilder generate() {
        return new DataSetBuilder(this);
    }

    /**
     * Inner builder class for configuring and executing data generation.
     */
    public class DataSetBuilder {
        private final TestDataGenerator generator;
        private final Map<String, Integer> entityCounts = new HashMap<>();

        private DataSetBuilder(TestDataGenerator generator) {
            this.generator = generator;
        }

        /**
         * Specifies how many instances of a particular entity to generate.
         *
         * @param entity The entity definition
         * @param count The number of instances to generate
         * @return This builder for chaining
         */
        public DataSetBuilder entities(EntityDefinition entity, int count) {
            entityCounts.put(entity.getName(), count);
            return this;
        }

        /**
         * Builds the dataset according to the configuration.
         *
         * @return The generated dataset
         */
        public DataSet build() {
            DataSet dataSet = new DataSet();

            // First pass: Create entities without relationships
            for (Map.Entry<String, Integer> entry : entityCounts.entrySet()) {
                String entityName = entry.getKey();
                int count = entry.getValue();
                EntityDefinition definition = entityDefinitions.get(entityName);

                for (int i = 0; i < count; i++) {
                    dataSet.addEntity(entityName, definition.generateInstance(dataSet));
                }
            }

            // Second pass: Establish relationships
            for (String entityName : entityCounts.keySet()) {
                EntityDefinition definition = entityDefinitions.get(entityName);
                definition.establishRelationships(dataSet);
            }

            return dataSet;
        }
    }
}