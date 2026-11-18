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

package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.testutil.NullEmptyTests;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thibault Debatty
 */
public class NGramTest {

    /**
     * Test of distance method, of class NGram.
     */
    @Test
    public void testDistance() {
        System.out.println("distance");
        String s0 = "ABABABAB";
        String s1 = "ABCABCABCABC";
        String s2 = "POIULKJH";
        NGram ngram = new NGram();
        System.out.println(ngram.distance(s0, s1));
        System.out.println(ngram.distance(s0, s2));
        Assertions.assertTrue(ngram.distance(s0, s1) < ngram.distance(s0, s2));

        assertEquals(0.0, ngram.distance("SIJK", "SIJK"), 0.0);
        assertEquals(0.0, ngram.distance("S", "S"), 0.0);

        NullEmptyTests.testDistance(ngram);
    }

    /**
     * When one of the strings is shorter than n, the distance falls back
     * to a character-by-character comparison.
     */
    @Test
    public void testShortStrings() {
        System.out.println("short strings");
        NGram ngram = new NGram(3);
        assertEquals(1.0, ngram.distance("a", "b"), 0.0);
        assertEquals(1.0, ngram.distance("ab", "cd"), 0.0);
        assertEquals(0.5, ngram.distance("ab", "ac"), 0.0);
        // Also cover the "tl < n" side of the condition (s1 is long enough,
        // s2 is not): the fallback only compares the common prefix.
        assertEquals(0.0, ngram.distance("abc", "ab"), 0.0);
    }
}
