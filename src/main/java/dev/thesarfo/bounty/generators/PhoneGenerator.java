package dev.thesarfo.bounty.generators;

import com.github.javafaker.Faker;
import dev.thesarfo.bounty.constraints.Constraint;

public class PhoneGenerator implements Generator {
    private final Faker faker = new Faker();

    @Override
    public Object generate(Constraint constraint) {
        if (constraint.getDefaultValue() != null) {
            return constraint.getDefaultValue();
        }

        if (constraint.hasAllowedValues()) {
            int index = (int) (Math.random() * constraint.getAllowedValues().size());
            return constraint.getAllowedValues().get(index);
        }

        return faker.phoneNumber().phoneNumber();
    }
}