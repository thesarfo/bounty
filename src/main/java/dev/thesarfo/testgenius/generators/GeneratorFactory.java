package dev.thesarfo.testgenius.generators;


import dev.thesarfo.testgenius.core.FieldType;

/**
 * Factory for creating appropriate generators based on field type.
 */
public class GeneratorFactory {
    /**
     * Creates a generator for the specified field type.
     *
     * @param type The field type
     * @return An appropriate generator
     */
    public static Generator createGenerator(FieldType type) {
        switch (type) {
            case UUID:
                return new UuidGenerator();
            case BOOLEAN:
                return new BooleanGenerator();
            case INTEGER:
                return new IntegerGenerator();
            case DECIMAL:
                return new DecimalGenerator();
            case STRING:
                return new StringGenerator();
            case TEXT:
                return new TextGenerator();
            case ENUM:
                return new EnumGenerator();
            case DATE:
                return new DateGenerator();
            case TIME:
                return new TimeGenerator();
            case TIMESTAMP:
                return new TimestampGenerator();
            case FIRST_NAME:
            case LAST_NAME:
            case FULL_NAME:
            case EMAIL:
            case USERNAME:
            case PASSWORD:
                return new PersonGenerator(type);
            case STREET_ADDRESS:
            case CITY:
            case STATE:
            case COUNTRY:
            case ZIP_CODE:
                return new AddressGenerator(type);
            case PHONE_NUMBER:
                return new PhoneGenerator();
            case PARAGRAPH:
            case SENTENCE:
                return new LoremGenerator(type);
            case CUSTOM:
            default:
                throw new IllegalArgumentException("Unsupported field type: " + type);
        }
    }
}