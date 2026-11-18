/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.testutil.NullEmptyTests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Thibault Debatty
 */
public class JaroWinklerTest {


    /**
     * Test of similarity method, of class JaroWinkler.
     */
    @Test
    public final void testSimilarity() {
        System.out.println("similarity");
        JaroWinkler instance = new JaroWinkler();
        assertEquals(
                0.974074,
                instance.similarity("My string", "My tsring"),
                0.000001);

        assertEquals(
                0.896296,
                instance.similarity("My string", "My ntrisg"),
                0.000001);

        NullEmptyTests.testSimilarity(instance);
    }

    @Test
    public final void testDistance() {
        JaroWinkler instance = new JaroWinkler();
        NullEmptyTests.testDistance(instance);

        // TODO: regular (non-null/empty) distance tests
    }

    /**
     * The Winkler bonus is only applied when the Jaro similarity is above
     * the configured threshold.
     */
    @Test
    public final void testThreshold() {
        JaroWinkler instance = new JaroWinkler();
        // The Jaro similarity of these two strings is 2/3, below the
        // default threshold of 0.7, so the bonus is not applied.
        double expected = 2.0 / 3.0;
        assertEquals(expected, instance.similarity("abcd", "axby"), 0.00001);

        // Same result with an explicit threshold above 2/3.
        JaroWinkler instance80 = new JaroWinkler(0.8);
        assertEquals(expected, instance80.similarity("abcd", "axby"), 0.00001);

        // With a threshold below 2/3, the bonus is applied.
        JaroWinkler instance50 = new JaroWinkler(0.5);
        assertEquals(0.7, instance50.similarity("abcd", "axby"), 0.00001);
    }
}
