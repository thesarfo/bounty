# Bounty

Bounty helps you create realistic test data for your Java applications. Instead of manually crafting test data or using
random values, Bounty lets you define data models and generates values that look like real-world data.

## Features

- Define entities with various field types (names, emails, dates, etc.)
- Apply constraints to ensure data validity
- Create relationships between entities
- Export data to JSON, SQL, or CSV
- Integrate with frameworks like Spring Boot, Micronaut, Quarkus, etc.

## Get Started

### Maven

```xml

<dependency>
    <groupId>io.github.thesarfo</groupId>
    <artifactId>bounty</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.thesarfo:bounty:1.0.1'
```

## Quick Start

```java
// Create a generator
TestDataGenerator generator = new TestDataGenerator();

// Define an entity
EntityDefinition person = generator.defineEntity("Person")
        .withField("firstName", FieldType.FIRST_NAME)
        .withField("lastName", FieldType.LAST_NAME)
        .withField("email", FieldType.EMAIL)
        .withField("age", FieldType.INTEGER, c ->
                ((NumericConstraint) c).min(18).max(80));

// Generate 100 people
DataSet dataSet = generator.generate()
        .entities(person, 100)
        .build();

// Export to JSON
dataSet.

exportToJson(new File("people.json"));
```

## Documentation

For complete documentation including:

- Field Types
- Constraints
- Relationships
- Data Export
- Integration Examples
- Advanced Usage

Visit the [full documentation website](https://thesarfo.github.io/projects/bounty/).