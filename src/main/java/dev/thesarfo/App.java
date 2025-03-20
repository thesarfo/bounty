package dev.thesarfo;

import dev.thesarfo.testgenius.constraints.NumericConstraint;
import dev.thesarfo.testgenius.core.DataSet;
import dev.thesarfo.testgenius.core.EntityDefinition;
import dev.thesarfo.testgenius.core.FieldType;
import dev.thesarfo.testgenius.core.TestDataGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * TEST GENIUS!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        TestDataGenerator generator = new TestDataGenerator();

        EntityDefinition person = generator.defineEntity("Person")
                .withField("id", FieldType.UUID)
                .withField("firstName", FieldType.FIRST_NAME)
                .withField("lastName", FieldType.LAST_NAME)
                .withField("email", FieldType.EMAIL)
                .withField("age", FieldType.INTEGER, constraint -> {
                    NumericConstraint numConstraint = (NumericConstraint) constraint;
                    numConstraint.min(18).max(80);
                });

        // Generate 10 people
        DataSet dataSet = generator.generate()
                .entities(person, 10)
                .build();

        // Get the generated data
        List<Map<String, Object>> people = dataSet.getEntities("Person");

        // Print the data
        for (Map<String, Object> p : people) {
            System.out.println(p);
        }

        // Export to JSON
        dataSet.exportToJson(new File("people.json"));
    }
}
