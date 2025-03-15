package dev.thesarfo.testgenius.generators;

import dev.thesarfo.testgenius.constraints.Constraint;
import dev.thesarfo.testgenius.constraints.NumericConstraint;

public class DecimalGenerator implements Generator {
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

            if (constraint instanceof NumericConstraint) {
                NumericConstraint numConstraint = (NumericConstraint) constraint;
                double min = Double.MIN_VALUE / 2;
                double max = Double.MAX_VALUE / 2;

                if (numConstraint.isPositive()) {
                    min = 0.00001;
                } else if (numConstraint.isNegative()) {
                    max = -0.00001;
                }

                if (numConstraint.getMin() != null) {
                    min = numConstraint.getMin().doubleValue();
                }

                if (numConstraint.getMax() != null) {
                    max = numConstraint.getMax().doubleValue();
                }

                return min + (Math.random() * (max - min));
            }
        }

        // Default range if no constraints
        return Math.random() * 1000;
    }
}