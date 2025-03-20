package dev.thesarfo.testgenius.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Defines a relationship between two entities.
 */
public class RelationshipDefinition {
    private final String name;
    private final EntityDefinition source;
    private final EntityDefinition target;
    private final RelationType type;

    public RelationshipDefinition(String name, EntityDefinition source,
                                  EntityDefinition target, RelationType type) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.type = type;
    }

    /**
     * Establishes relationships between entity instances in the dataset.
     *
     * @param dataSet The dataset containing the entities
     */
    public void establish(DataSet dataSet) {
        List<Map<String, Object>> sourceEntities = dataSet.getEntities(source.getName());
        List<Map<String, Object>> targetEntities = dataSet.getEntities(target.getName());

        if (sourceEntities.isEmpty() || targetEntities.isEmpty()) {
            return;
        }

        switch (type) {
            case ONE_TO_ONE:
                establishOneToOne(sourceEntities, targetEntities);
                break;
            case ONE_TO_MANY:
                establishOneToMany(sourceEntities, targetEntities);
                break;
            case MANY_TO_ONE:
                establishManyToOne(sourceEntities, targetEntities);
                break;
            case MANY_TO_MANY:
                establishManyToMany(sourceEntities, targetEntities);
                break;
        }
    }

    private void establishOneToOne(List<Map<String, Object>> sourceEntities,
                                   List<Map<String, Object>> targetEntities) {
        int minSize = Math.min(sourceEntities.size(), targetEntities.size());

        for (int i = 0; i < minSize; i++) {
            Map<String, Object> sourceEntity = sourceEntities.get(i);
            Map<String, Object> targetEntity = targetEntities.get(i);

            sourceEntity.put(name, targetEntity);
        }
    }

    private void establishOneToMany(List<Map<String, Object>> sourceEntities,
                                    List<Map<String, Object>> targetEntities) {
        int sourceSize = sourceEntities.size();

        for (int i = 0; i < targetEntities.size(); i++) {
            Map<String, Object> sourceEntity = sourceEntities.get(i % sourceSize);
            Map<String, Object> targetEntity = targetEntities.get(i);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> collection = (List<Map<String, Object>>)
                    sourceEntity.computeIfAbsent(name, k -> new ArrayList<>());

            collection.add(targetEntity);

            targetEntity.put(source.getName().toLowerCase(), sourceEntity);
        }
    }

    private void establishManyToOne(List<Map<String, Object>> sourceEntities,
                                    List<Map<String, Object>> targetEntities) {
        int targetSize = targetEntities.size();

        for (Map<String, Object> sourceEntity : sourceEntities) {
            int randomIndex = (int) (Math.random() * targetSize);
            Map<String, Object> targetEntity = targetEntities.get(randomIndex);

            sourceEntity.put(name, targetEntity);
        }
    }

    private void establishManyToMany(List<Map<String, Object>> sourceEntities,
                                     List<Map<String, Object>> targetEntities) {
        for (Map<String, Object> sourceEntity : sourceEntities) {
            int count = 1 + (int) (Math.random() * 5);
            count = Math.min(count, targetEntities.size());

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> collection = (List<Map<String, Object>>)
                    sourceEntity.computeIfAbsent(name, k -> new ArrayList<>());

            List<Integer> assignedIndices = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                int randomIndex;
                do {
                    randomIndex = (int) (Math.random() * targetEntities.size());
                } while (assignedIndices.contains(randomIndex));

                assignedIndices.add(randomIndex);
                collection.add(targetEntities.get(randomIndex));
            }
        }
    }
}