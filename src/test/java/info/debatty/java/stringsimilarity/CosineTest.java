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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import info.debatty.java.stringsimilarity.testutil.NullEmptyTests;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thibault Debatty
 */
public class CosineTest {

    /**
     * Test of similarity method, of class Cosine.
     */
    @Test
    public final void testSimilarity() {
        System.out.println("similarity");
        Cosine instance = new Cosine();
        double result = instance.similarity("ABC", "ABCE");
        assertEquals(0.71, result, 0.01);

        NullEmptyTests.testSimilarity(instance);
    }

    /**
     * If one of the strings is smaller than k, the similarity should be 0.
     */
    @Test
    public final void testSmallString() {
        System.out.println("test small string");
        Cosine instance = new Cosine(3);
        double result = instance.similarity("AB", "ABCE");
        assertEquals(0.0, result, 0.00001);
    }

    @Test
    public final void testLargeString() throws IOException {

        System.out.println("Test with large strings");
        Cosine cos = new Cosine();

        // read from 2 text files
        String string1 = readResourceFile("71816-2.txt");
        String string2 = readResourceFile("11328-1.txt");
        double similarity = cos.similarity(string1, string2);

        assertEquals(0.8115, similarity, 0.001);
    }

    @Test
    public final void testDistance() {
        Cosine instance = new Cosine();

        double result = instance.distance("ABC", "ABCE");
        assertEquals(0.29, result, 0.01);

        NullEmptyTests.testDistance(instance);
    }

    @Test
    public final void testDistanceSmallString() {
        System.out.println("test small string");
        Cosine instance = new Cosine(3);
        double result = instance.distance("AB", "ABCE");
        assertEquals(1, result, 0.00001);
    }

    @Test
    public final void testDistanceLargeString() throws IOException {

        System.out.println("Test with large strings");
        Cosine cos = new Cosine();

        // read from 2 text files
        String string1 = readResourceFile("71816-2.txt");
        String string2 = readResourceFile("11328-1.txt");
        double similarity = cos.distance(string1, string2);

        assertEquals(0.1885, similarity, 0.001);
    }

    private static String readResourceFile(String file) throws IOException {

        InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(file);

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder string_builder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        String line = null;

        while (( line = reader.readLine() ) != null ) {
            string_builder.append(line);
            string_builder.append(ls);
        }

        string_builder.deleteCharAt(string_builder.length() - 1);
        return string_builder.toString();
    }
}
