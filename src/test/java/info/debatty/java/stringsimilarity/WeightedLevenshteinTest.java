package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.testutil.NullEmptyTests;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeightedLevenshteinTest {
    @Test
    public void testDistance() {
        WeightedLevenshtein instance = new WeightedLevenshtein(new CharacterSubstitutionInterface() {
            public double cost(char c1, char c2) {
                // The cost for substituting 't' and 'r' is considered
                // smaller as these 2 are located next to each other
                // on a keyboard
                if (c1 == 't' && c2 == 'r') {
                    return 0.5;
                }

                // For most cases, the cost of substituting 2 characters
                // is 1.0
                return 1.0;
            }
        });

        assertEquals(0.0, instance.distance("String1", "String1"), 0.1);
        assertEquals(0.5, instance.distance("String1", "Srring1"), 0.1);
        assertEquals(1.5, instance.distance("String1", "Srring2"), 0.1);

        // One insert or delete.
        assertEquals(1.0, instance.distance("Strng", "String"), 0.1);
        assertEquals(1.0, instance.distance("String", "Strng"), 0.1);

        NullEmptyTests.testDistance(instance);
    }

    @Test
    public void testDistanceCharacterInsDelInterface() {
        WeightedLevenshtein instance = new WeightedLevenshtein(
                new CharacterSubstitutionInterface() {
            public double cost(char c1, char c2) {
                if (c1 == 't' && c2 == 'r') {
                    return 0.5;
                }
                return 1.0;
            }
        },
        new CharacterInsDelInterface() {
            public double deletionCost(char c) {
                if (c == 'i') {
                    return 0.8;
                }
                return 1.0;
            }

            public double insertionCost(char c) {
                if (c == 'i') {
                    return 0.5;
                }
                return 1.0;
            }
        });

        // Same as testDistance above.
        assertEquals(0.0, instance.distance("String1", "String1"), 0.1);
        assertEquals(0.5, instance.distance("String1", "Srring1"), 0.1);
        assertEquals(1.5, instance.distance("String1", "Srring2"), 0.1);

        // Cost of insert of 'i' is less than normal, so these scores are
        // different than testDistance above.  Note that the cost of delete
        // has been set differently than the cost of insert, so the distance
        // call is not symmetric in its arguments if an 'i' has changed.
        assertEquals(0.5, instance.distance("Strng", "String"), 0.1);
        assertEquals(0.8, instance.distance("String", "Strng"), 0.1);
        assertEquals(1.0, instance.distance("Strig", "String"), 0.1);
        assertEquals(1.0, instance.distance("String", "Strig"), 0.1);

        NullEmptyTests.testDistance(instance);
    }
}