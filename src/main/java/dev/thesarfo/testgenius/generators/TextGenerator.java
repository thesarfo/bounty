package dev.thesarfo.testgenius.generators;

import dev.thesarfo.testgenius.constraints.Constraint;
import dev.thesarfo.testgenius.constraints.StringConstraint;

public class TextGenerator implements Generator {
    private static final String LOREM =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

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

            int length = 100;
            if (constraint instanceof StringConstraint) {
                StringConstraint strConstraint = (StringConstraint) constraint;
                if (strConstraint.getMinLength() != null) {
                    length = Math.max(length, strConstraint.getMinLength());
                }
                if (strConstraint.getMaxLength() != null) {
                    length = Math.min(length, strConstraint.getMaxLength());
                }
            }

            return generateLoremText(length);
        }

        // Default for null constraint
        return generateLoremText(100);
    }

    private String generateLoremText(int length) {
        if (length <= LOREM.length()) {
            return LOREM.substring(0, length);
        } else {
            StringBuilder sb = new StringBuilder();
            while (sb.length() < length) {
                sb.append(LOREM);
            }
            return sb.substring(0, length);
        }
    }
}