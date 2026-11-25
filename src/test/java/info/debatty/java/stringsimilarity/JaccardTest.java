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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Thibault Debatty
 */
public class JaccardTest {

    /**
     * Test of similarity method, of class Jaccard.
     */
    @Test
    public void testSimilarity() {
        System.out.println("similarity");
        Jaccard instance = new Jaccard(2);
        
        // AB BC CD DE DF
        // 1  1  1  1  0
        // 1  1  1  0  1
        // => 3 / 5 = 0.6
        double result = instance.similarity("ABCDE", "ABCDF");
        assertEquals(0.6, result, 0.0);

        NullEmptyTests.testSimilarity(instance);
    }

    /**
     * Test of distance method, of class Jaccard.
     */
    @Test
    public void testDistance() {
        System.out.println("distance");
        String s1 = "";
        String s2 = "";
        Jaccard instance = new Jaccard(2);
        double expResult = 0.4;
        double result = instance.distance("ABCDE", "ABCDF");
        assertEquals(expResult, result, 0.0);

        NullEmptyTests.testDistance(instance);
    }

    /**
     * If both strings are smaller than k, there are no shingles.
     */
    @Test
    public void testSmallStrings() {
        Jaccard instance = new Jaccard(3);
        assertEquals(0.0, instance.similarity("AB", "CD"), 0.0);
        assertEquals(1.0, instance.distance("AB", "CD"), 0.0);
        assertEquals(0.0, instance.similarity("", "AB"), 0.0);
        assertEquals(0.0, instance.similarity("AB", "ABCE"), 0.0);
        assertEquals(1.0, instance.similarity("AB", "AB"), 0.0);
    }

    /**
     * Default constructor (shingle size 3).
     */
    @Test
    public void testDefaultK() {
        Jaccard instance = new Jaccard();
        // ABCD: {ABC, BCD}; ABCE: {ABC, BCE}
        // => |intersection| / |union| = 1 / 3
        assertEquals(1.0 / 3.0, instance.similarity("ABCD", "ABCE"), 0.00001);
    }

    /**
     * The shingle size must be positive.
     */
    @Test
    public void testInvalidK() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Jaccard(0));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Jaccard(-1));
    }
}
