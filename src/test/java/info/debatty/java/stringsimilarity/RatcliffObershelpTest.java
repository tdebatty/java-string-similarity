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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Agung Nugroho
 */
public class RatcliffObershelpTest {


    /**
     * Test of similarity method, of class RatcliffObershelp.
     */
    @Test
    public final void testSimilarity() {
        System.out.println("similarity");
        RatcliffObershelp instance = new RatcliffObershelp();
		
		// test data from other algorithms
		// "My string" vs "My tsring"
		// Substrings:
		// "ring" ==> 4, "My s" ==> 3, "s" ==> 1
		// Ratcliff-Obershelp = 2*(sum of substrings)/(length of s1 + length of s2)
		//                    = 2*(4 + 3 + 1) / (9 + 9)
		//                    = 16/18
		//                    = 0.888888
        assertEquals(
                0.888888,
                instance.similarity("My string", "My tsring"),
                0.000001);
				
		// test data from other algorithms
		// "My string" vs "My tsring"
		// Substrings:
		// "My " ==> 3, "tri" ==> 3, "g" ==> 1
		// Ratcliff-Obershelp = 2*(sum of substrings)/(length of s1 + length of s2)
		//                    = 2*(3 + 3 + 1) / (9 + 9)
		//                    = 14/18
		//                    = 0.777778
        assertEquals(
                0.777778,
                instance.similarity("My string", "My ntrisg"),
                0.000001);

        // test data from essay by Ilya Ilyankou
        // "Comparison of Jaro-Winkler and Ratcliff/Obershelp algorithms
        // in spell check"
        // https://ilyankou.files.wordpress.com/2015/06/ib-extended-essay.pdf
        // p13, expected result is 0.857
        assertEquals(
                0.857,
                instance.similarity("MATEMATICA", "MATHEMATICS"),
                0.001);

        // test data from stringmetric
        // https://github.com/rockymadden/stringmetric
        // expected output is 0.7368421052631579
        assertEquals(
                0.736842,
                instance.similarity("aleksander", "alexandre"),
                0.000001);

        // test data from stringmetric
        // https://github.com/rockymadden/stringmetric
        // expected output is 0.6666666666666666
        assertEquals(
                0.666666,
                instance.similarity("pennsylvania", "pencilvaneya"),
                0.000001);

        // test data from wikipedia
        // https://en.wikipedia.org/wiki/Gestalt_Pattern_Matching
        // expected output is 14/18 = 0.7777777777777778‬
        assertEquals(
                0.777778,
                instance.similarity("WIKIMEDIA", "WIKIMANIA"),
                0.000001);

        // test data from wikipedia
        // https://en.wikipedia.org/wiki/Gestalt_Pattern_Matching
        // expected output is 24/40 = 0.65
        assertEquals(
                0.6,
                instance.similarity("GESTALT PATTERN MATCHING", "GESTALT PRACTICE"),
                0.000001);
        
        NullEmptyTests.testSimilarity(instance);
    }

    @Test
    public final void testDistance() {
        RatcliffObershelp instance = new RatcliffObershelp();
        NullEmptyTests.testDistance(instance);

        // TODO: regular (non-null/empty) distance tests
    }
}
