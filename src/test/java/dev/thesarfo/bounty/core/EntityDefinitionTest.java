package dev.thesarfo.bounty.core;


import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityDefinitionTest {

    @Test
    public void testEntityDefinition() {
        EntityDefinition person = new EntityDefinition("Person");
        person.withField("id", FieldType.UUID)
                .withField("firstName", FieldType.FIRST_NAME)
                .withField("lastName", FieldType.LAST_NAME)
                .withField("age", FieldType.INTEGER,
                        constraint -> ((dev.thesarfo.bounty.constraints.NumericConstraint) constraint)
                                .min(18).max(80));

        assertEquals("Person", person.getName());

        DataSet dataSet = new DataSet();
        Map<String, Object> instance = person.generateInstance(dataSet);

        assertNotNull(instance);
        assertTrue(instance.containsKey("id"));
        assertTrue(instance.containsKey("firstName"));
        assertTrue(instance.containsKey("lastName"));
        assertTrue(instance.containsKey("age"));

        Object age = instance.get("age");
        assertTrue(age instanceof Integer);
        int ageValue = (Integer) age;
        assertTrue(ageValue >= 18 && ageValue <= 80);
    }

    @Test
    public void testRelationships() {
        EntityDefinition person = new EntityDefinition("Person");
        person.withField("id", FieldType.UUID)
                .withField("name", FieldType.FULL_NAME);

        EntityDefinition order = new EntityDefinition("Order");
        order.withField("id", FieldType.UUID)
                .withField("amount", FieldType.DECIMAL,
                        constraint -> ((dev.thesarfo.bounty.constraints.NumericConstraint) constraint)
                                .min("10.00").max("100.00"))
                .withRelationship("customer", person, RelationType.MANY_TO_ONE);

        // Create a dataset with these entities
        TestDataGenerator generator = new TestDataGenerator();
        DataSet dataSet = generator.generate()
                .entities(person, 5)
                .entities(order, 10)
                .build();

        // Check results
        assertNotNull(dataSet);
        assertEquals(5, dataSet.getEntities("Person").size());
        assertEquals(10, dataSet.getEntities("Order").size());

        // Check relationships
        for (Map<String, Object> orderEntity : dataSet.getEntities("Order")) {
            assertTrue(orderEntity.containsKey("customer"));
            Object customer = orderEntity.get("customer");
            assertInstanceOf(Map.class, customer);
            Map<String, Object> customerMap = (Map<String, Object>) customer;
            assertTrue(customerMap.containsKey("id"));
            assertTrue(customerMap.containsKey("name"));
        }
    }
}