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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

        NullEmptyTests.testSimilarity(instance);
    }

    @Test
    public final void testDistance() {
        SorensenDice instance = new SorensenDice();
        NullEmptyTests.testDistance(instance);
    }

    /**
     * If both strings are smaller than k, there are no shingles.
     */
    @Test
    public void testSmallStrings() {
        SorensenDice instance = new SorensenDice(3);
        assertEquals(0.0, instance.similarity("AB", "CD"), 0.0);
        assertEquals(1.0, instance.distance("AB", "CD"), 0.0);
        assertEquals(0.0, instance.similarity("", "AB"), 0.0);
        assertEquals(0.0, instance.similarity("AB", "ABCE"), 0.0);
        assertEquals(1.0, instance.similarity("AB", "AB"), 0.0);
    }
}
