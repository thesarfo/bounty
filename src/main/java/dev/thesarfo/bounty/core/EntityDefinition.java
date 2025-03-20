package dev.thesarfo.bounty.core;


import dev.thesarfo.bounty.constraints.Constraint;
import dev.thesarfo.bounty.constraints.NumericConstraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines the structure of an entity, including its fields and relationships.
 */
public class EntityDefinition {
    private final String name;
    private final List<FieldDefinition> fields = new ArrayList<>();
    private final List<RelationshipDefinition> relationships = new ArrayList<>();

    public EntityDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Adds a field to this entity definition.
     *
     * @param name The name of the field
     * @param type The data type of the field
     * @return This entity definition for chaining
     */
    public EntityDefinition withField(String name, FieldType type) {
        FieldDefinition field = new FieldDefinition(name, type);
        field.setConstraint(field.createConstraint());
        fields.add(field);
        return this;
    }

    /**
     * Adds a field with constraints to this entity definition.
     *
     * @param name The name of the field
     * @param type The data type of the field
     * @param constraintConfigurator A function to configure constraints
     * @return This entity definition for chaining
     */
    public EntityDefinition withField(String name, FieldType type,
                                      ConstraintConfigurator constraintConfigurator) {
        FieldDefinition field = new FieldDefinition(name, type);

        Constraint constraint;
        if (type == FieldType.INTEGER || type == FieldType.DECIMAL) {
            constraint = new NumericConstraint();
        } else {
            constraint = field.createConstraint();
        }

        constraintConfigurator.configure(constraint);
        field.setConstraint(constraint);
        fields.add(field);
        return this;
    }

    /**
     * Adds a relationship to another entity.
     *
     * @param name The name of the relationship
     * @param target The target entity definition
     * @param type The type of relationship
     * @return This entity definition for chaining
     */
    public EntityDefinition withRelationship(String name, EntityDefinition target,
                                             RelationType type) {
        relationships.add(new RelationshipDefinition(name, this, target, type));
        return this;
    }

    /**
     * Generates a single instance of this entity.
     *
     * @param dataSet The dataset being built
     * @return A map representing the generated entity
     */
    public Map<String, Object> generateInstance(DataSet dataSet) {
        Map<String, Object> instance = new HashMap<>();

        for (FieldDefinition field : fields) {
            instance.put(field.getName(), field.generateValue());
        }

        return instance;
    }

    /**
     * Establishes relationships for all instances of this entity.
     *
     * @param dataSet The dataset containing the entities
     */
    void establishRelationships(DataSet dataSet) {
        List<Map<String, Object>> entities = dataSet.getEntities(name);

        if (entities == null || entities.isEmpty()) {
            return;
        }

        for (RelationshipDefinition relationship : relationships) {
            relationship.establish(dataSet);
        }
    }

    /**
     * Functional interface for configuring constraints.
     */
    @FunctionalInterface
    public interface ConstraintConfigurator {
        void configure(Constraint constraint);
    }
}