package dev.thesarfo.bounty.generators;


import com.github.javafaker.Faker;
import dev.thesarfo.bounty.constraints.Constraint;
import dev.thesarfo.bounty.constraints.StringConstraint;
import dev.thesarfo.bounty.core.FieldType;

/**
 * Generator for person-related data like names and emails.
 */
public class PersonGenerator implements Generator {
    private final FieldType type;
    private final Faker faker = new Faker();

    public PersonGenerator(FieldType type) {
        this.type = type;
    }

    @Override
    public Object generate(Constraint constraint) {
        if (constraint.getDefaultValue() != null) {
            return constraint.getDefaultValue();
        }

        if (constraint.hasAllowedValues()) {
            int index = (int) (Math.random() * constraint.getAllowedValues().size());
            return constraint.getAllowedValues().get(index);
        }

        switch (type) {
            case FIRST_NAME:
                return faker.name().firstName();
            case LAST_NAME:
                return faker.name().lastName();
            case FULL_NAME:
                return faker.name().fullName();
            case EMAIL:
                return faker.internet().emailAddress();
            case USERNAME:
                return faker.name().username();
            case PASSWORD:
                StringConstraint sc = (StringConstraint) constraint;
                int length = 10;
                if (sc.getMinLength() != null && sc.getMaxLength() != null) {
                    length = sc.getMinLength() +
                            (int) (Math.random() * (sc.getMaxLength() - sc.getMinLength() + 1));
                } else if (sc.getMinLength() != null) {
                    length = sc.getMinLength();
                } else if (sc.getMaxLength() != null) {
                    length = sc.getMaxLength();
                }
                return faker.internet().password(
                        Math.max(length, 6),
                        Math.max(length + 4, 10),
                        true, true, true);
            default:
                throw new IllegalArgumentException("Unsupported person field type: " + type);
        }
    }
}