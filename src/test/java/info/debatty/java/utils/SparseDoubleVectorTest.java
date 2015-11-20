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

package info.debatty.java.utils;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thibault Debatty
 */
public class SparseDoubleVectorTest {
    
    public SparseDoubleVectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of dotProduct method, of class SparseDoubleVector.
     */
    @Test
    public void testDotProduct_SparseDoubleVector() {
        System.out.println("dotProduct");
        SparseDoubleVector other = new SparseDoubleVector(new double[]{1.0, 2.0, 3.0, 4.5});
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{1.5, 0, 2.0, 1.0});
        double expResult = 12.0;
        double result = instance.dotProduct(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of dotProduct method, of class SparseDoubleVector.
     */
    @Test
    public void testDotProduct_doubleArr() {
        System.out.println("dotProduct");
        double[] other = new double[]{1.0, 2.0, 3.0, 4.5};
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{1.5, 0, 2.0, 1.0});
        double expResult = 12.0;
        double result = instance.dotProduct(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of jaccard method, of class SparseDoubleVector.
     */
    @Test
    public void testJaccard() {
        System.out.println("jaccard");
        SparseDoubleVector other = new SparseDoubleVector(new double[]{1, 0, 5, 7});
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{1, 2, 0, 7});
        double expResult = 0.5;
        double result = instance.jaccard(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of union method, of class SparseDoubleVector.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        SparseDoubleVector other = new SparseDoubleVector(new double[]{1, 0, 5, 0});
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{2, 2, 4, 0});
        int expResult = 3;
        int result = instance.union(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersection method, of class SparseDoubleVector.
     */
    @Test
    public void testIntersection() {
        System.out.println("intersection");
        SparseDoubleVector other = new SparseDoubleVector(new double[]{1, 0, 5, 7});
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{2, 2, 4, 0});
        int expResult = 2;
        int result = instance.intersection(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class SparseDoubleVector.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{2, 2, 0, 4});
        String expResult = "0:2.0 1:2.0 3:4.0 ";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of qgram method, of class SparseDoubleVector.
     */
    @Test
    public void testQgram() {
        System.out.println("qgram");
        SparseDoubleVector other = new SparseDoubleVector(new double[]{1.0, 2.0, 3.0, 4.5});
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{1.5, 0, 2.0, 1.0});
        double expResult = 7.0;
        double result = instance.qgram(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of size method, of class SparseDoubleVector.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{2, 2, 0, 4});
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of norm method, of class SparseDoubleVector.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{1.5, 0, 2.0, 1.0});
        double expResult = 2.692582404;
        double result = instance.norm();
        assertEquals(expResult, result, 0.00001);
    }

    /**
     * Test of cosineSimilarity method, of class SparseDoubleVector.
     */
    @Test
    public void testCosineSimilarity() {
        System.out.println("cosineSimilarity");
        SparseDoubleVector other = new SparseDoubleVector(new double[]{1.0, 2.0, 3.0, 4.5});
        SparseDoubleVector instance = new SparseDoubleVector(new double[]{1.5, 0, 2.0, 1.0});
        double expResult = 0.761521124;
        double result = instance.cosineSimilarity(other);
        assertEquals(expResult, result, 0.000001);
    }

    /**
     * Test of toArray method, of class SparseDoubleVector.
     */
    @Test
    public void testToArray() {
        System.out.println("toArray");
        int size = 4;
        HashMap<Integer, Double> values = new HashMap<Integer, Double>();
        values.put(0, 1.5);
        values.put(3, 2.5);
        SparseDoubleVector instance = new SparseDoubleVector(values);
        double[] expResult = new double[]{1.5, 0, 0, 2.5};
        double[] result = instance.toArray(size);
        assertArrayEquals(expResult, result, 0.0);
    }
    
}
