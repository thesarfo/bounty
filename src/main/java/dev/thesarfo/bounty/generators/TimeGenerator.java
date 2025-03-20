package dev.thesarfo.bounty.generators;

import dev.thesarfo.bounty.constraints.Constraint;
import dev.thesarfo.bounty.constraints.TimeConstraint;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class TimeGenerator implements Generator {
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

            LocalTime minTime = LocalTime.MIN;
            LocalTime maxTime = LocalTime.MAX.minusNanos(1);

            if (constraint instanceof TimeConstraint) {
                TimeConstraint timeConstraint = (TimeConstraint) constraint;

                if (timeConstraint.isPast()) {
                    maxTime = LocalTime.now().minusSeconds(1);
                } else if (timeConstraint.isFuture()) {
                    minTime = LocalTime.now().plusSeconds(1);
                }

                if (timeConstraint.getMinTime() != null) {
                    minTime = timeConstraint.getMinTime();
                }
                if (timeConstraint.getMaxTime() != null) {
                    maxTime = timeConstraint.getMaxTime();
                }
            }

            return generateRandomTime(minTime, maxTime);
        }

        // Default for null constraint
        return generateRandomTime(LocalTime.MIN, LocalTime.MAX.minusNanos(1));
    }

    private LocalTime generateRandomTime(LocalTime minTime, LocalTime maxTime) {
        int minSecond = minTime.toSecondOfDay();
        int maxSecond = maxTime.toSecondOfDay();
        int randomSecond = ThreadLocalRandom.current().nextInt(
                minSecond, maxSecond + 1);
        return LocalTime.ofSecondOfDay(randomSecond);
    }
}