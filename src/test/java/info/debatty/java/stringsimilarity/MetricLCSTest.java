package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.testutil.NullEmptyTests;
import org.junit.Test;

import static org.junit.Assert.*;

public class MetricLCSTest {
    @Test
    public final void testDistance() {
        MetricLCS instance = new MetricLCS();
        NullEmptyTests.testDistance(instance);

        // TODO: regular (non-null/empty) distance tests
    }
}