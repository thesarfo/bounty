package dev.thesarfo.bounty.constraints;

import java.time.LocalDateTime;

/**
 * Constraints for timestamp fields.
 */
public class TimestampConstraint extends Constraint {
    private LocalDateTime minTimestamp;
    private LocalDateTime maxTimestamp;
    private boolean past = false;
    private boolean future = false;

    /**
     * Restricts to timestamps in the past.
     *
     * @return This constraint for chaining
     */
    public TimestampConstraint past() {
        this.past = true;
        return this;
    }

    /**
     * Restricts to timestamps in the future.
     *
     * @return This constraint for chaining
     */
    public TimestampConstraint future() {
        this.future = true;
        return this;
    }

    /**
     * Sets the minimum timestamp.
     *
     * @param minTimestamp The minimum timestamp
     * @return This constraint for chaining
     */
    public TimestampConstraint min(LocalDateTime minTimestamp) {
        this.minTimestamp = minTimestamp;
        return this;
    }

    /**
     * Sets the maximum timestamp.
     *
     * @param maxTimestamp The maximum timestamp
     * @return This constraint for chaining
     */
    public TimestampConstraint max(LocalDateTime maxTimestamp) {
        this.maxTimestamp = maxTimestamp;
        return this;
    }

    public LocalDateTime getMinTimestamp() {
        return minTimestamp;
    }

    public LocalDateTime getMaxTimestamp() {
        return maxTimestamp;
    }

    public boolean isPast() {
        return past;
    }

    public boolean isFuture() {
        return future;
    }
}