package dev.thesarfo.bounty.generators;


import dev.thesarfo.bounty.constraints.Constraint;

/**
 * Interface for value generators.
 */
public interface Generator {
    /**
     * Generates a value according to the given constraints.
     *
     * @param constraint The constraints to apply
     * @return A generated value
     */
    Object generate(Constraint constraint);
}
