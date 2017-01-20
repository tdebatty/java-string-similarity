package info.debatty.java.stringsimilarity.testutil;

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class NullEmptyTests {

    public static void testDistance(NormalizedStringDistance instance) {
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(1.0, instance.distance("", "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", ""), 0.1);

        assertNullPointerExceptions(instance);
    }

    public static void testDistance(StringDistance instance) {
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(3.0, instance.distance("", "foo"), 0.1);
        assertEquals(3.0, instance.distance("foo", ""), 0.1);

        assertNullPointerExceptions(instance);
    }

    public static void testSimilarity(NormalizedStringSimilarity instance) {
        assertEquals(1.0, instance.similarity("", ""), 0.1);
        assertEquals(0.0, instance.similarity("", "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", ""), 0.1);

        try {
            instance.similarity(null, null);
            fail("A NullPointerException was not thrown.");
        } catch (NullPointerException ignored) {
        }

        try {
            instance.similarity(null, "");
            fail("A NullPointerException was not thrown.");
        } catch (NullPointerException ignored) {
        }

        try {
            instance.similarity("", null);
            fail("A NullPointerException was not thrown.");
        } catch (NullPointerException ignored) {
        }
    }

    public static void assertNullPointerExceptions(StringDistance instance) {
        try {
            instance.distance(null, null);
            fail("A NullPointerException was not thrown.");
        } catch (NullPointerException ignored) {
        }

        try {
            instance.distance(null, "");
            fail("A NullPointerException was not thrown.");
        } catch (NullPointerException ignored) {
        }

        try {
            instance.distance("", null);
            fail("A NullPointerException was not thrown.");
        } catch (NullPointerException ignored) {
        }
    }
}
