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

import org.junit.Test;
import static org.junit.Assert.*;

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
    }

    @Test
    public final void testEmptyStrings() {
        JaroWinkler instance = new JaroWinkler();
        assertEquals(1.0, instance.similarity("", ""), 0.1);
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(0.0, instance.similarity("", "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", ""), 0.1);
        assertEquals(1.0, instance.distance("", "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", ""), 0.1);
    }

    @Test
    public final void testNullStrings() {
        JaroWinkler instance = new JaroWinkler();
        assertEquals(1.0, instance.similarity(null, null), 0.1);
        assertEquals(0.0, instance.distance(null, null), 0.1);
        assertEquals(0.0, instance.similarity(null, "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", null), 0.1);
        assertEquals(1.0, instance.distance(null, "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", null), 0.1);
    }
}
