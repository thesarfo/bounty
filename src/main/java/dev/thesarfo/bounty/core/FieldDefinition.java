package dev.thesarfo.bounty.core;

import dev.thesarfo.bounty.constraints.Constraint;
import dev.thesarfo.bounty.constraints.DateConstraint;
import dev.thesarfo.bounty.constraints.NumericConstraint;
import dev.thesarfo.bounty.constraints.StringConstraint;
import dev.thesarfo.bounty.generators.Generator;
import dev.thesarfo.bounty.generators.GeneratorFactory;

/**
 * Defines a field within an entity, including its type and constraints.
 */
public class FieldDefinition {
    private final String name;
    private final FieldType type;
    private Constraint constraint;
    private Generator generator;

    public FieldDefinition(String name, FieldType type) {
        this.name = name;
        this.type = type;
        this.generator = GeneratorFactory.createGenerator(type);
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    /**
     * Creates an appropriate constraint based on the field type.
     *
     * @return A constraint appropriate for this field type
     */
    public Constraint createConstraint() {
        switch (type) {
            case INTEGER:
            case DECIMAL:
                constraint = new NumericConstraint();
                break;
            case STRING:
            case TEXT:
            case EMAIL:
            case USERNAME:
            case PASSWORD:
                constraint = new StringConstraint();
                break;
            case DATE:
            case TIME:
            case TIMESTAMP:
                constraint = new DateConstraint();
                break;
            default:
                constraint = new Constraint();
        }
        return constraint;
    }

    /**
     * Generates a value for this field based on its type and constraints.
     *
     * @return A generated value
     */
    public Object generateValue() {
        return generator.generate(constraint);
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }
}
