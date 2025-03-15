package dev.thesarfo.testgenius.generators;

import dev.thesarfo.testgenius.constraints.Constraint;
import dev.thesarfo.testgenius.constraints.DateConstraint;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class DateGenerator implements Generator {
    private static final LocalDate DEFAULT_MIN = LocalDate.of(1970, 1, 1);
    private static final LocalDate DEFAULT_MAX = LocalDate.of(2030, 12, 31);

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

            LocalDate minDate = DEFAULT_MIN;
            LocalDate maxDate = DEFAULT_MAX;

            if (constraint instanceof DateConstraint) {
                DateConstraint dateConstraint = (DateConstraint) constraint;

                if (dateConstraint.isPast()) {
                    maxDate = LocalDate.now().minusDays(1);
                } else if (dateConstraint.isFuture()) {
                    minDate = LocalDate.now().plusDays(1);
                }

                if (dateConstraint.getMin() instanceof LocalDate) {
                    minDate = (LocalDate) dateConstraint.getMin();
                }
                if (dateConstraint.getMax() instanceof LocalDate) {
                    maxDate = (LocalDate) dateConstraint.getMax();
                }
            }

            return generateRandomDate(minDate, maxDate);
        }

        // Default for null constraint
        return generateRandomDate(DEFAULT_MIN, DEFAULT_MAX);
    }

    private LocalDate generateRandomDate(LocalDate minDate, LocalDate maxDate) {
        long daysBetween = ChronoUnit.DAYS.between(minDate, maxDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        return minDate.plusDays(randomDays);
    }
}