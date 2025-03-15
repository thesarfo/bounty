package dev.thesarfo.testgenius.generators;

import dev.thesarfo.testgenius.constraints.Constraint;
import dev.thesarfo.testgenius.constraints.StringConstraint;
import java.util.Random;

public class StringGenerator implements Generator {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

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

            int length = 10;
            if (constraint instanceof StringConstraint) {
                StringConstraint strConstraint = (StringConstraint) constraint;
                if (strConstraint.getMinLength() != null && strConstraint.getMaxLength() != null) {
                    length = strConstraint.getMinLength() +
                            RANDOM.nextInt(strConstraint.getMaxLength() - strConstraint.getMinLength() + 1);
                } else if (strConstraint.getMinLength() != null) {
                    length = strConstraint.getMinLength();
                } else if (strConstraint.getMaxLength() != null) {
                    length = strConstraint.getMaxLength();
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
            }
            return sb.toString();
        }

        // Default behavior for null constraint
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}