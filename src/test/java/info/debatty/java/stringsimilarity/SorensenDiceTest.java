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
public class SorensenDiceTest {

    /**
     * Test of similarity method, of class SorensenDice.
     */
    @Test
    public void testSimilarity() {
        System.out.println("similarity");
        SorensenDice instance = new SorensenDice(2);
        // AB BC CD DE DF FG
        // 1  1  1  1  0  0
        // 1  1  1  0  1  1
        // => 2 x 3 / (4 + 5) = 6/9 = 0.6666
        double result = instance.similarity("ABCDE", "ABCDFG");
        assertEquals(0.6666, result, 0.0001);
    }

    @Test
    public final void testEmptyStrings() {
        SorensenDice instance = new SorensenDice();
        assertEquals(1.0, instance.similarity("", ""), 0.1);
        assertEquals(0.0, instance.distance("", ""), 0.1);
        assertEquals(0.0, instance.similarity("", "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", ""), 0.1);
        assertEquals(1.0, instance.distance("", "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", ""), 0.1);
    }

    @Test
    public final void testNullStrings() {
        SorensenDice instance = new SorensenDice();
        assertEquals(1.0, instance.similarity(null, null), 0.1);
        assertEquals(0.0, instance.distance(null, null), 0.1);
        assertEquals(0.0, instance.similarity(null, "foo"), 0.1);
        assertEquals(0.0, instance.similarity("foo", null), 0.1);
        assertEquals(1.0, instance.distance(null, "foo"), 0.1);
        assertEquals(1.0, instance.distance("foo", null), 0.1);
    }
}
