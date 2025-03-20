package dev.thesarfo.testgenius.generators;


import dev.thesarfo.testgenius.constraints.Constraint;

import java.util.UUID;

/**
 * Generator for UUID values.
 */
public class UuidGenerator implements Generator {
    @Override
    public Object generate(Constraint constraint) {
        if(constraint != null){
            if (constraint.getDefaultValue() != null) {
                return constraint.getDefaultValue();
            }

            if (constraint.hasAllowedValues()) {
                int index = (int) (Math.random() * constraint.getAllowedValues().size());
                return constraint.getAllowedValues().get(index);
            }
        }

        return UUID.randomUUID();
    }
}
