package info.debatty.java.stringsimilarity;

import org.junit.Test;

import static org.junit.Assert.*;

public class NormalizedLevenshteinTest {
    @Test
    public final void testEmptyStrings() {
        NormalizedLevenshtein instance = new NormalizedLevenshtein();
        assertEquals(1.0, instance.similarity("", ""), 0.1);
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(0.0, instance.similarity("", "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", ""), 0.1);
        assertEquals(1.0, instance.distance("", "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", ""), 0.1);
    }

    @Test
    public final void testNullStrings() {
        NormalizedLevenshtein instance = new NormalizedLevenshtein();
        assertEquals(1.0, instance.similarity(null, null), 0.1);
        assertEquals(0.0, instance.distance(null, null), 0.1);
        assertEquals(0.0, instance.similarity(null, "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", null), 0.1);
        assertEquals(1.0, instance.distance(null, "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", null), 0.1);
    }
}