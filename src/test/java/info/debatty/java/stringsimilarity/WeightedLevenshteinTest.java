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

        NullEmptyTests.testDistance(instance);
    }
}