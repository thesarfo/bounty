package dev.thesarfo.bounty.constraints;

import java.time.LocalTime;

/**
 * Constraints for time fields.
 */
public class TimeConstraint extends Constraint {
    private LocalTime minTime;
    private LocalTime maxTime;
    private boolean past = false;
    private boolean future = false;

    /**
     * Restricts to times in the past.
     *
     * @return This constraint for chaining
     */
    public TimeConstraint past() {
        this.past = true;
        return this;
    }

    /**
     * Restricts to times in the future.
     *
     * @return This constraint for chaining
     */
    public TimeConstraint future() {
        this.future = true;
        return this;
    }

    /**
     * Sets the minimum time.
     *
     * @param minTime The minimum time
     * @return This constraint for chaining
     */
    public TimeConstraint min(LocalTime minTime) {
        this.minTime = minTime;
        return this;
    }

    /**
     * Sets the maximum time.
     *
     * @param maxTime The maximum time
     * @return This constraint for chaining
     */
    public TimeConstraint max(LocalTime maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public LocalTime getMinTime() {
        return minTime;
    }

    public LocalTime getMaxTime() {
        return maxTime;
    }

    public boolean isPast() {
        return past;
    }

    public boolean isFuture() {
        return future;
    }
}