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

package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;

/**
 * This distance is computed as levenshtein distance divided by the length of 
 * the longest string. The resulting value is always in the interval [0.0 1.0] 
 * but it is not a metric anymore!
 * The similarity is computed as 1 - normalized distance.
 * @author Thibault Debatty
 */
public class NormalizedLevenshtein implements NormalizedStringDistance, NormalizedStringSimilarity {


    public static void main(String[] args) {
        NormalizedLevenshtein l = new NormalizedLevenshtein();
        
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "M string2"));
        System.out.println(l.distance("My string", "abcd"));
    }
    
    private final Levenshtein l = new Levenshtein();

    public double distance(String s1, String s2) {
        return l.distance(s1, s2) / Math.max(s1.length(), s2.length());
    }

    public double similarity(String s1, String s2) {
        return 1.0 - distance(s1, s2);
    }
    
}
