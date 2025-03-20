package dev.thesarfo.bounty.core;


/**
 * Enumeration of supported field types.
 */
public enum FieldType {
    // Basic types
    UUID,
    BOOLEAN,
    INTEGER,
    DECIMAL,
    STRING,
    TEXT,
    ENUM,

    // Date and time types
    DATE,
    TIME,
    TIMESTAMP,

    // Identity types
    FIRST_NAME,
    LAST_NAME,
    FULL_NAME,
    EMAIL,
    USERNAME,
    PASSWORD,

    // Address types
    STREET_ADDRESS,
    CITY,
    STATE,
    COUNTRY,
    ZIP_CODE,

    // Contact types
    PHONE_NUMBER,

    // Content types
    PARAGRAPH,
    SENTENCE,

    // Other types
    CUSTOM
}
