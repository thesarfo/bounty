package dev.thesarfo.bounty.constraints;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Constraints for date and time fields.
 */
public class DateConstraint extends Constraint {
    private Object min;
    private Object max;
    private boolean past = false;
    private boolean future = false;

    /**
     * Restricts to dates in the past.
     *
     * @return This constraint for chaining
     */
    public DateConstraint past() {
        this.past = true;
        return this;
    }

    /**
     * Restricts to dates in the future.
     *
     * @return This constraint for chaining
     */
    public DateConstraint future() {
        this.future = true;
        return this;
    }

    /**
     * Sets the minimum date or time.
     *
     * @param min The minimum value
     * @return This constraint for chaining
     */
    public DateConstraint min(Object min) {
        if (!(min instanceof LocalDate) && !(min instanceof LocalTime) &&
                !(min instanceof LocalDateTime)) {
            throw new IllegalArgumentException(
                    "Min must be LocalDate, LocalTime, or LocalDateTime");
        }
        this.min = min;
        return this;
    }

    /**
     * Sets the maximum date or time.
     *
     * @param max The maximum value
     * @return This constraint for chaining
     */
    public DateConstraint max(Object max) {
        if (!(max instanceof LocalDate) && !(max instanceof LocalTime) &&
                !(max instanceof LocalDateTime)) {
            throw new IllegalArgumentException(
                    "Max must be LocalDate, LocalTime, or LocalDateTime");
        }
        this.max = max;
        return this;
    }

    public Object getMin() {
        return min;
    }

    public Object getMax() {
        return max;
    }

    public boolean isPast() {
        return past;
    }

    public boolean isFuture() {
        return future;
    }
}