package info.debatty.java.stringsimilarity;


/**
 * As an adjunct to CharacterSubstitutionInterface, this interface
 * allows you to specify the cost of deletion or insertion of a
 * character.
 */
public interface CharacterInsDelInterface {
    /**
     * @param c The character being deleted.
     * @return The cost to be allocated to deleting the given character,
     * in the range [0, 1].
     */
    double deletionCost(char c);

    /**
     * @param c The character being inserted.
     * @return The cost to be allocated to inserting the given character,
     * in the range [0, 1].
     */
    double insertionCost(char c);
}
