package dev.thesarfo.bounty.constraints;


import java.math.BigDecimal;

/**
 * Constraints for numeric fields.
 */
public class NumericConstraint extends Constraint {
    private Number min;
    private Number max;
    private boolean positive = false;
    private boolean negative = false;

    /**
     * Sets the minimum value.
     *
     * @param min The minimum value
     * @return This constraint for chaining
     */
    public NumericConstraint min(Number min) {
        this.min = min;
        return this;
    }

    /**
     * Sets the maximum value.
     *
     * @param max The maximum value
     * @return This constraint for chaining
     */
    public NumericConstraint max(Number max) {
        this.max = max;
        return this;
    }

    /**
     * Restricts to positive values.
     *
     * @return This constraint for chaining
     */
    public NumericConstraint positive() {
        this.positive = true;
        return this;
    }

    /**
     * Restricts to negative values.
     *
     * @return This constraint for chaining
     */
    public NumericConstraint negative() {
        this.negative = true;
        return this;
    }

    /**
     * Sets the minimum decimal value.
     *
     * @param min The minimum value as a string
     * @return This constraint for chaining
     */
    public NumericConstraint min(String min) {
        this.min = new BigDecimal(min);
        return this;
    }

    /**
     * Sets the maximum decimal value.
     *
     * @param max The maximum value as a string
     * @return This constraint for chaining
     */
    public NumericConstraint max(String max) {
        this.max = new BigDecimal(max);
        return this;
    }

    public Number getMin() {
        return min;
    }

    public Number getMax() {
        return max;
    }

    public boolean isPositive() {
        return positive;
    }

    public boolean isNegative() {
        return negative;
    }
}