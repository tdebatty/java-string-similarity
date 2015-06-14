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
     * Test of cosineSimilarity method, of class SparseIntegerVector.
     */
    @Test
    public void testCosineSimilarity() {
        System.out.println("cosineSimilarity");
        SparseIntegerVector other = new SparseIntegerVector(new int[]{0, 1, 2, 3});
        SparseIntegerVector instance = new SparseIntegerVector(new int[]{1, 2, 0, 0});
        double expResult = instance.dotProduct(other) / (instance.norm() * other.norm());
        double result = instance.cosineSimilarity(other);
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
}
