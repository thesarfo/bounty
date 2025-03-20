package dev.thesarfo.bounty.constraints;


import java.util.regex.Pattern;

/**
 * Constraints for string fields.
 */
public class StringConstraint extends Constraint {
    private Integer minLength;
    private Integer maxLength;
    private Pattern pattern;
    private String format;

    /**
     * Sets the minimum string length.
     *
     * @param minLength The minimum length
     * @return This constraint for chaining
     */
    public StringConstraint minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * Sets the maximum string length.
     *
     * @param maxLength The maximum length
     * @return This constraint for chaining
     */
    public StringConstraint maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Sets a regex pattern for the string.
     *
     * @param pattern The regex pattern
     * @return This constraint for chaining
     */
    public StringConstraint pattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
        return this;
    }

    /**
     * Sets a predefined format for the string.
     *
     * @param format The format name
     * @return This constraint for chaining
     */
    public StringConstraint format(String format) {
        this.format = format;
        return this;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getFormat() {
        return format;
    }
}