package dev.thesarfo.bounty.generators;


import dev.thesarfo.bounty.constraints.NumericConstraint;
import dev.thesarfo.bounty.constraints.StringConstraint;
import dev.thesarfo.bounty.core.FieldType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratorTest {

    @Test
    public void testIntegerGenerator() {
        IntegerGenerator generator = new IntegerGenerator();

        // Test with range constraints
        NumericConstraint constraint = new NumericConstraint();
        constraint.min(10).max(20);

        for (int i = 0; i < 100; i++) {
            Object result = generator.generate(constraint);
            assertInstanceOf(Integer.class, result);
            int value = (Integer) result;
            assertTrue(value >= 10 && value <= 20, "Value out of range: " + value);
        }

        // Test with positive constraint
        constraint = new NumericConstraint();
        constraint.positive();

        for (int i = 0; i < 100; i++) {
            Object result = generator.generate(constraint);
            assertTrue(result instanceof Integer);
            int value = (Integer) result;
            assertTrue(value > 0, "Value not positive: " + value);
        }
    }

    @Test
    public void testPersonGenerator() {
        PersonGenerator nameGenerator = new PersonGenerator(FieldType.FIRST_NAME);
        PersonGenerator emailGenerator = new PersonGenerator(FieldType.EMAIL);

        // Test name generation
        Object name = nameGenerator.generate(new StringConstraint());
        assertInstanceOf(String.class, name);
        assertFalse(((String) name).isEmpty());

        // Test email generation
        Object email = emailGenerator.generate(new StringConstraint());
        assertInstanceOf(String.class, email);
        String emailStr = (String) email;
        assertTrue(emailStr.contains("@"), "Not a valid email: " + emailStr);
    }

    @Test
    public void testUuidGenerator() {
        UuidGenerator generator = new UuidGenerator();

        Object result = generator.generate(new StringConstraint());
        assertInstanceOf(String.class, result);

        String uuid = (String) result;
        assertEquals(36, uuid.length());
        assertEquals(5, uuid.split("-").length);
    }
}
