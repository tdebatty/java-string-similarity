package info.debatty.java.stringsimilarity.testutil;

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

import static org.junit.Assert.assertEquals;

public final class NullEmptyTests {

    public static void testDistance(NormalizedStringDistance instance) {
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(1.0, instance.distance("", "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", ""), 0.1);
        assertEquals(0.0, instance.distance(null, null), 0.1);
        assertEquals(0.0, instance.distance(null, ""), 0.1);
        assertEquals(0.0, instance.distance("", null), 0.1);
        assertEquals(1.0, instance.distance(null, "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", null), 0.1);
    }

    public static void testDistance(StringDistance instance) {
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(3.0, instance.distance("", "foo"), 0.1);
        assertEquals(3.0, instance.distance("foo", ""), 0.1);
        assertEquals(0.0, instance.distance(null, null), 0.1);
        assertEquals(0.0, instance.distance(null, ""), 0.1);
        assertEquals(0.0, instance.distance("", null), 0.1);
        assertEquals(3.0, instance.distance(null, "foo"), 0.1);
        assertEquals(3.0, instance.distance("foo", null), 0.1);
    }

    public static void testSimilarity(NormalizedStringSimilarity instance) {
        assertEquals(1.0, instance.similarity("", ""), 0.1);
        assertEquals(0.0, instance.similarity("", "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", ""), 0.1);
        assertEquals(1.0, instance.similarity(null, null), 0.1);
        assertEquals(1.0, instance.similarity(null, ""), 0.1);
        assertEquals(1.0, instance.similarity("", null), 0.1);
        assertEquals(0.0, instance.similarity(null, "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", null), 0.1);
    }
}
