package dev.thesarfo.bounty.constraints;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for field constraints.
 */
public class Constraint {
    private boolean unique = false;
    private boolean nullable = false;
    private Object defaultValue = null;
    private final List<Object> allowedValues = new ArrayList<>();

    /**
     * Sets this field to require unique values.
     *
     * @return This constraint for chaining
     */
    public Constraint unique() {
        this.unique = true;
        return this;
    }

    /**
     * Sets this field to allow null values.
     *
     * @return This constraint for chaining
     */
    public Constraint nullable() {
        this.nullable = true;
        return this;
    }

    /**
     * Sets a default value for this field.
     *
     * @param value The default value
     * @return This constraint for chaining
     */
    public Constraint defaultValue(Object value) {
        this.defaultValue = value;
        return this;
    }

    /**
     * Restricts this field to a set of allowed values.
     *
     * @param values The allowed values
     * @return This constraint for chaining
     */
    public Constraint values(Object... values) {
        allowedValues.addAll(Arrays.asList(values));
        return this;
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public List<Object> getAllowedValues() {
        return allowedValues;
    }

    public boolean hasAllowedValues() {
        return !allowedValues.isEmpty();
    }
}