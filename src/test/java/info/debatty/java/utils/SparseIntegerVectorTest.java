/*
 * The MIT License
 *
 * Copyright 2015 tibo.
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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tibo
 */
public class SparseIntegerVectorTest {
    
    public SparseIntegerVectorTest() {
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
     * Test of dotProduct method, of class SparseIntegerVector.
     */
    @Test
    public void testDotProduct_SparseIntegerVector() {
        System.out.println("dotProduct");
        SparseIntegerVector other = new SparseIntegerVector(new int[]{0, 2, 0, 1});
        SparseIntegerVector instance = new SparseIntegerVector(new int[]{1, 2, 1, 0});
        double expResult = 4.0;
        double result = instance.dotProduct(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of dotProduct method, of class SparseIntegerVector.
     */
    @Test
    public void testDotProduct_doubleArr() {
        System.out.println("dotProduct");
        double[] other = new double[]{0, 1.5, 2.0, 3.0};
        SparseIntegerVector instance = new SparseIntegerVector(new int[]{1, 2, 0, 0});
        double expResult = 3.0;
        double result = instance.dotProduct(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of norm method, of class SparseIntegerVector.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        SparseIntegerVector instance = new SparseIntegerVector(new int[]{0, 0, 2});
        double expResult = 2.0;
        double result = instance.norm();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of jaccard method, of class SparseIntegerVector.
     */
    @Test
    public void testJaccard() {
        System.out.println("jaccard");
        SparseIntegerVector other = null;
        SparseIntegerVector instance = new SparseIntegerVector();
        double expResult = 0.0;
        double result = instance.jaccard(other);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of union method, of class SparseIntegerVector.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        SparseIntegerVector other = null;
        SparseIntegerVector instance = new SparseIntegerVector();
        int expResult = 0;
        int result = instance.union(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of intersection method, of class SparseIntegerVector.
     */
    @Test
    public void testIntersection() {
        System.out.println("intersection");
        SparseIntegerVector other = null;
        SparseIntegerVector instance = new SparseIntegerVector();
        int expResult = 0;
        int result = instance.intersection(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SparseIntegerVector.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SparseIntegerVector instance = new SparseIntegerVector();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of qgram method, of class SparseIntegerVector.
     */
    @Test
    public void testQgram() {
        System.out.println("qgram");
        SparseIntegerVector other = null;
        SparseIntegerVector instance = new SparseIntegerVector();
        double expResult = 0.0;
        double result = instance.qgram(other);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class SparseIntegerVector.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        SparseIntegerVector instance = new SparseIntegerVector();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
