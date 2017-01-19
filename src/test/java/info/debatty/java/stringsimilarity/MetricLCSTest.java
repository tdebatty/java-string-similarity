package info.debatty.java.stringsimilarity;

import org.junit.Test;

import static org.junit.Assert.*;

public class MetricLCSTest {
    @Test
    public final void testEmptyStrings() {
        MetricLCS instance = new MetricLCS();
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(1.0, instance.distance("", "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", ""), 0.1);
    }

    @Test
    public final void testNullStrings() {
        MetricLCS instance = new MetricLCS();
        assertEquals(0.0, instance.distance(null, null), 0.1);
        assertEquals(1.0, instance.distance(null, "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", null), 0.1);
    }
}