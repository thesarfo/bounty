package dev.thesarfo.testgenius.generators;

import dev.thesarfo.testgenius.constraints.Constraint;
import dev.thesarfo.testgenius.constraints.StringConstraint;
import dev.thesarfo.testgenius.core.FieldType;
import com.github.javafaker.Faker;

public class LoremGenerator implements Generator {
    private final FieldType type;
    private final Faker faker = new Faker();

    public LoremGenerator(FieldType type) {
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

        int count = 3;
        if (constraint instanceof StringConstraint) {
            StringConstraint sc = (StringConstraint) constraint;
            if (sc.getMinLength() != null && sc.getMinLength() > 100) {
                count = sc.getMinLength() / 100;
            }
        }

        switch (type) {
            case PARAGRAPH:
                return faker.lorem().paragraphs(count).toString();
            case SENTENCE:
            default:
                return faker.lorem().sentences(count).toString();
        }
    }
}