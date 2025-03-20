package dev.thesarfo;

import dev.thesarfo.bounty.constraints.NumericConstraint;
import dev.thesarfo.bounty.core.DataSet;
import dev.thesarfo.bounty.core.EntityDefinition;
import dev.thesarfo.bounty.core.FieldType;
import dev.thesarfo.bounty.core.RelationType;
import dev.thesarfo.bounty.core.TestDataGenerator;

import java.io.File;
import java.io.IOException;

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

        EntityDefinition user = generator.defineEntity("User")
                .withField("id", FieldType.UUID)
                .withField("username", FieldType.USERNAME);

        EntityDefinition profile = generator.defineEntity("Profile")
                .withField("id", FieldType.UUID)
                .withField("bio", FieldType.TEXT);

        // Each user has one profile
        user.withRelationship("profile", profile, RelationType.ONE_TO_ONE);

        // Generate 10 people
        DataSet dataSet = generator.generate()
                .entities(person, 10)
                .build();

        DataSet userSet = generator.generate()
                .entities(user, 5)
                .entities(profile, 5)
                .build();


//        // Get the generated data
//        List<Map<String, Object>> people = dataSet.getEntities("Person");
//
//        // Print the data
//        for (Map<String, Object> p : people) {
//            System.out.println(p);
//        }

        // Export to JSON
        dataSet.exportToJson(new File("people.json"));
        dataSet.exportToSql(new File("people.sql"));
        dataSet.exportToCsv(new File("people.csv"));
        userSet.exportToJson(new File("users.json"));
    }
}
