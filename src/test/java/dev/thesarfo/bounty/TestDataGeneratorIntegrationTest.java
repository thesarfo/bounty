package dev.thesarfo.bounty;


import dev.thesarfo.bounty.constraints.NumericConstraint;
import dev.thesarfo.bounty.core.DataSet;
import dev.thesarfo.bounty.core.EntityDefinition;
import dev.thesarfo.bounty.core.FieldType;
import dev.thesarfo.bounty.core.RelationType;
import dev.thesarfo.bounty.core.TestDataGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDataGeneratorIntegrationTest {

    @Test
    public void testFullGeneration() throws IOException {
        TestDataGenerator generator = new TestDataGenerator();

        // Define Person
        EntityDefinition person = generator.defineEntity("Person")
                .withField("id", FieldType.UUID)
                .withField("firstName", FieldType.FIRST_NAME)
                .withField("lastName", FieldType.LAST_NAME)
                .withField("email", FieldType.EMAIL)
                .withField("age", FieldType.INTEGER,
                        constraint -> ((NumericConstraint) constraint).min(18).max(80));

        // Define Order
        EntityDefinition order = generator.defineEntity("Order")
                .withField("id", FieldType.UUID)
                .withField("amount", FieldType.DECIMAL,
                        constraint -> ((NumericConstraint) constraint).min("10.00").max("5000.00"))
                .withField("status", FieldType.ENUM,
                        constraint -> constraint.values("NEW", "PROCESSING", "SHIPPED", "DELIVERED"))
                .withRelationship("customer", person, RelationType.MANY_TO_ONE);

        // Generate data
        DataSet dataSet = generator.generate()
                .entities(person, 50)
                .entities(order, 200)
                .build();

        // Verify data
        List<Map<String, Object>> people = dataSet.getEntities("Person");
        List<Map<String, Object>> orders = dataSet.getEntities("Order");

        assertEquals(50, people.size());
        assertEquals(200, orders.size());

        // Export to JSON
        File tempFile = File.createTempFile("test-data", ".json");
        dataSet.exportToJson(tempFile);

        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }
}