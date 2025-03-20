package dev.thesarfo.bounty.generators;


import dev.thesarfo.bounty.constraints.Constraint;
import dev.thesarfo.bounty.constraints.NumericConstraint;

/**
 * Generator for integer values.
 */
public class IntegerGenerator implements Generator {
    @Override
    public Object generate(Constraint constraint) {
        if (constraint.getDefaultValue() != null) {
            return constraint.getDefaultValue();
        }

        if (constraint.hasAllowedValues()) {
            int index = (int) (Math.random() * constraint.getAllowedValues().size());
            return constraint.getAllowedValues().get(index);
        }

        if (constraint instanceof NumericConstraint) {
            NumericConstraint numConstraint = (NumericConstraint) constraint;
            int min = Integer.MIN_VALUE / 2;
            int max = Integer.MAX_VALUE / 2;

            if (numConstraint.isPositive()) {
                min = 1;
            } else if (numConstraint.isNegative()) {
                max = -1;
            }

            if (numConstraint.getMin() != null) {
                min = numConstraint.getMin().intValue();
            }

            if (numConstraint.getMax() != null) {
                max = numConstraint.getMax().intValue();
            }

            return min + (int) (Math.random() * ((max - min) + 1));
        }

        // Default range if no constraints
        return (int) (Math.random() * 1000);
    }
}