package dev.thesarfo.bounty.generators;

import dev.thesarfo.bounty.constraints.Constraint;

/**
 * Generator for boolean values.
 */
public class BooleanGenerator implements Generator {
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
        }

        // Generate random boolean
        return Math.random() < 0.5;
    }
}