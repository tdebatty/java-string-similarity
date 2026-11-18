/*
 * The MIT License
 *
 * Copyright 2016 Thibault Debatty.
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

package info.debatty.java.stringsimilarity.experimental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thibault Debatty
 */
public class Sift4Test {

    /**
     * Test of distance method, of class Sift4.
     */
    @Test
    public void testDistance() {
        System.out.println("SIFT4 distance");
        String s1 = "This is the first string";
        String s2 = "And this is another string";
        Sift4 sift4 = new Sift4();
        sift4.setMaxOffset(5);
        double expResult =  11.0;
        double result = sift4.distance(s1, s2);
        assertEquals(expResult, result, 0.0);

        sift4.setMaxOffset(10);
        assertEquals(
                12.0,
                sift4.distance(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        "Amet Lorm ispum dolor sit amet, consetetur adixxxpiscing elit."),
                0.0);
    }

    /**
     * Unlike most other distances, Sift4 accepts null and empty strings
     * and returns the length of the other string.
     */
    @Test
    public void testNullAndEmpty() {
        Sift4 sift4 = new Sift4();
        assertEquals(0, sift4.distance(null, null));
        assertEquals(0, sift4.distance("", ""));
        assertEquals(3, sift4.distance(null, "foo"));
        assertEquals(3, sift4.distance("foo", null));
        assertEquals(3, sift4.distance("", "foo"));
        assertEquals(3, sift4.distance("foo", ""));
    }

    /**
     * A transposition costs 1 (not 2 as two separate edits would).
     */
    @Test
    public void testTranspositions() {
        Sift4 sift4 = new Sift4();
        assertEquals(1, sift4.distance("ab", "ba"));
        assertEquals(1, sift4.distance("abc", "acb"));
    }

    /**
     * Reference values from the Sift4 test suite.
     */
    @Test
    public void testReferenceVectors() {
        Sift4 sift4 = new Sift4();
        assertEquals(1, sift4.distance("abcd", "abc"));
        assertEquals(1, sift4.distance("1321", "1212"));
        assertEquals(1, sift4.distance("1213", "1132"));
        assertEquals(2, sift4.distance("121212", "123123"));
        assertEquals(2, sift4.distance("MyString", "My $tring"));
    }

    /**
     * Strings of different lengths, in both directions.
     */
    @Test
    public void testAsymmetricLengths() {
        Sift4 sift4 = new Sift4();
        assertEquals(1, sift4.distance("abc", "abcd"));
        assertEquals(2, sift4.distance("ab", "abcd"));
        assertEquals(2, sift4.distance("abcd", "ab"));
        assertEquals(4, sift4.distance("a", "abcde"));
        assertEquals(4, sift4.distance("abcde", "a"));
        assertEquals(3, sift4.distance("ab", "abcde"));
        assertEquals(3, sift4.distance("abcde", "ab"));
        assertEquals(2, sift4.distance("abc", "abcde"));
        assertEquals(2, sift4.distance("abcde", "abc"));
        assertEquals(1, sift4.distance("abx", "abc"));
        assertEquals(1, sift4.distance("abc", "abx"));
        // When s1 is longer and the mismatched character has a follower,
        // the forward search in s2 can hit the end of s2 before a match.
        assertEquals(2, sift4.distance("abcd", "abx"));
        assertEquals(2, sift4.distance("abx", "abcd"));
    }

}
