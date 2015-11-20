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

package info.debatty.java.stringsimilarity.examples;

import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;

/**
 *
 * @author Thibault Debatty
 */
public class Examples {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Levenshtein
        // ===========
        Levenshtein levenshtein = new Levenshtein();
        System.out.println(levenshtein.distance("My string", "My $tring"));
        System.out.println(levenshtein.distance("My string", "M string2"));
        System.out.println(levenshtein.distance("My string", "My $tring"));
        
        
        // Jaccard index
        // =============
        Jaccard j2 = new Jaccard(2);
        // AB BC CD DE DF
        // 1  1  1  1  0
        // 1  1  1  0  1
        // => 3 / 5 = 0.6
        System.out.println(j2.similarity("ABCDE", "ABCDF"));
        
        
        // Jaro-Winkler
        // ============
        JaroWinkler jw = new JaroWinkler();
        
        // substitution of s and t : 0.9740740656852722
        System.out.println(jw.similarity("My string", "My tsring"));
        
        // substitution of s and n : 0.8962963223457336
        System.out.println(jw.similarity("My string", "My ntrisg"));
        
        
        // Cosine
        // ======
        Cosine cos = new Cosine(3);
        
        // ABC BCE
        // 1  0
        // 1  1
        // angle = 45°
        // => similarity = .71
        
        System.out.println(cos.similarity("ABC", "ABCE"));
        
        cos = new Cosine(2);
        // AB BA
        // 2  1
        // 1  1
        // similarity = .95
        System.out.println(cos.similarity("ABAB", "BAB"));
        
        // Damerau
        // =======
        Damerau damerau = new Damerau();
        
        // 1 substitution
        System.out.println(damerau.distance("ABCDEF", "ABDCEF"));
        
        // 2 substitutions
        System.out.println(damerau.distance("ABCDEF", "BACDFE"));
        
        // 1 deletion
        System.out.println(damerau.distance("ABCDEF", "ABCDE"));
        System.out.println(damerau.distance("ABCDEF", "BCDEF"));
        
        System.out.println(damerau.distance("ABCDEF", "ABCGDEF"));
        
        // All different
        System.out.println(damerau.distance("ABCDEF", "POIU"));
        
        // Longest Common Subsequence
        // ==========================
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        
        // Will produce 4.0
        System.out.println(lcs.distance("AGCAT", "GAC"));
        
        // Will produce 1.0
        System.out.println(lcs.distance("AGCAT", "AGCT"));
    }
    
}
