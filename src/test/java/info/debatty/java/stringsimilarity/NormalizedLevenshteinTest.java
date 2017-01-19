package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.testutil.NullEmptyTests;
import org.junit.Test;

import static org.junit.Assert.*;

public class NormalizedLevenshteinTest {
    @Test
    public final void testDistance() {
        NormalizedLevenshtein instance = new NormalizedLevenshtein();
        NullEmptyTests.testDistance(instance);

        // TODO: regular (non-null/empty) distance tests
    }

    @Test
    public final void testSimilarity() {
        NormalizedLevenshtein instance = new NormalizedLevenshtein();
        NullEmptyTests.testSimilarity(instance);

        // TODO: regular (non-null/empty) similarity tests
    }
}