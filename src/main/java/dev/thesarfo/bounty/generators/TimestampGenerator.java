package dev.thesarfo.bounty.generators;

import dev.thesarfo.bounty.constraints.Constraint;
import dev.thesarfo.bounty.constraints.TimestampConstraint;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class TimestampGenerator implements Generator {
    private static final LocalDateTime DEFAULT_MIN = LocalDateTime.of(1970, 1, 1, 0, 0);
    private static final LocalDateTime DEFAULT_MAX = LocalDateTime.of(2030, 12, 31, 23, 59, 59);

    @Override
    public Object generate(Constraint constraint) {
        if (constraint != null) {
            if (constraint.getDefaultValue() != null) {
                return constraint.getDefaultValue();
            }

            if (constraint.hasAllowedValues()) {
                int index = (int) (Math.random() * constraint.getAllowedValues().size());
                return constraint.getAllowedValues().get(index);
            }

            LocalDateTime minDateTime = DEFAULT_MIN;
            LocalDateTime maxDateTime = DEFAULT_MAX;

            if (constraint instanceof TimestampConstraint) {
                TimestampConstraint tsConstraint = (TimestampConstraint) constraint;

                if (tsConstraint.isPast()) {
                    maxDateTime = LocalDateTime.now().minusSeconds(1);
                } else if (tsConstraint.isFuture()) {
                    minDateTime = LocalDateTime.now().plusSeconds(1);
                }

                if (tsConstraint.getMinTimestamp() != null) {
                    minDateTime = tsConstraint.getMinTimestamp();
                }
                if (tsConstraint.getMaxTimestamp() != null) {
                    maxDateTime = tsConstraint.getMaxTimestamp();
                }
            }

            return generateRandomTimestamp(minDateTime, maxDateTime);
        }

        // Default for null constraint
        return generateRandomTimestamp(DEFAULT_MIN, DEFAULT_MAX);
    }

    private LocalDateTime generateRandomTimestamp(LocalDateTime min, LocalDateTime max) {
        long minEpochSecond = min.toEpochSecond(ZoneOffset.UTC);
        long maxEpochSecond = max.toEpochSecond(ZoneOffset.UTC);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(
                minEpochSecond, maxEpochSecond + 1);
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }
}