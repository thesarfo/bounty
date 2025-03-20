package dev.thesarfo.bounty.generators;

import dev.thesarfo.bounty.constraints.Constraint;

public class EnumGenerator implements Generator {
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

        // Cannot generate enum without allowed values
        throw new IllegalArgumentException("Enum generation requires allowed values or default value");
    }
}