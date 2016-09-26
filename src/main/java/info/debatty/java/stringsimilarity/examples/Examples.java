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

import info.debatty.java.stringsimilarity.CharacterSubstitutionInterface;
import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Damerau;
import info.debatty.java.stringsimilarity.OptimalStringAlignment;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.QGram;
import info.debatty.java.stringsimilarity.SorensenDice;
import info.debatty.java.stringsimilarity.WeightedLevenshtein;

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
        System.out.println("\nLevenshtein");
        Levenshtein levenshtein = new Levenshtein();
        System.out.println(levenshtein.distance("My string", "My $tring"));
        System.out.println(levenshtein.distance("My string", "M string2"));
        System.out.println(levenshtein.distance("My string", "My $tring"));

        // Jaccard index
        // =============
        System.out.println("\nJaccard");
        Jaccard j2 = new Jaccard(2);
        // AB BC CD DE DF
        // 1  1  1  1  0
        // 1  1  1  0  1
        // => 3 / 5 = 0.6
        System.out.println(j2.similarity("ABCDE", "ABCDF"));

        // Jaro-Winkler
        // ============
        System.out.println("\nJaro-Winkler");
        JaroWinkler jw = new JaroWinkler();

        // substitution of s and t : 0.9740740656852722
        System.out.println(jw.similarity("My string", "My tsring"));

        // substitution of s and n : 0.8962963223457336
        System.out.println(jw.similarity("My string", "My ntrisg"));

        // Cosine
        // ======
        System.out.println("\nCosine");
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
        System.out.println("\nDamerau");
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


        // Optimal String Alignment
        // =======
        System.out.println("\nOptimal String Alignment");
        OptimalStringAlignment osa = new OptimalStringAlignment();

        //Will produce 3.0
        System.out.println(osa.distance("CA", "ABC"));


        // Longest Common Subsequence
        // ==========================
        System.out.println("\nLongest Common Subsequence");
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // Will produce 4.0
        System.out.println(lcs.distance("AGCAT", "GAC"));

        // Will produce 1.0
        System.out.println(lcs.distance("AGCAT", "AGCT"));

        // NGram
        // =====
        // produces 0.416666
        System.out.println("\nNGram");
        NGram twogram = new NGram(2);
        System.out.println(twogram.distance("ABCD", "ABTUIO"));

        // produces 0.97222
        String s1 = "Adobe CreativeSuite 5 Master Collection from cheap 4zp";
        String s2 = "Adobe CreativeSuite 5 Master Collection from cheap d1x";
        NGram ngram = new NGram(4);
        System.out.println(ngram.distance(s1, s2));

        // Normalized Levenshtein
        // ======================
        System.out.println("\nNormalized Levenshtein");
        NormalizedLevenshtein l = new NormalizedLevenshtein();

        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "M string2"));
        System.out.println(l.distance("My string", "abcd"));

        // QGram
        // =====
        System.out.println("\nQGram");
        QGram dig = new QGram(2);

        // AB BC CD CE
        // 1  1  1  0
        // 1  1  0  1
        // Total: 2
        System.out.println(dig.distance("ABCD", "ABCE"));

        System.out.println(dig.distance("", "QSDFGHJKLM"));

        System.out.println(dig.distance(
                "Best Deal Ever! Viagra50/100mg - $1.85 071",
                "Best Deal Ever! Viagra50/100mg - $1.85 7z3"));

        // Sorensen-Dice
        // =============
        System.out.println("\nSorensen-Dice");
        SorensenDice sd = new SorensenDice(2);

        // AB BC CD DE DF FG
        // 1  1  1  1  0  0
        // 1  1  1  0  1  1
        // => 2 x 3 / (4 + 5) = 6/9 = 0.6666
        System.out.println(sd.similarity("ABCDE", "ABCDFG"));

        // Weighted Levenshtein
        // ====================
        System.out.println("\nWeighted Levenshtein");
        WeightedLevenshtein wl = new WeightedLevenshtein(
                new CharacterSubstitutionInterface() {
                    public double cost(char c1, char c2) {

                        // The cost for substituting 't' and 'r' is considered
                        // smaller as these 2 are located next to each other
                        // on a keyboard
                        if (c1 == 't' && c2 == 'r') {
                            return 0.5;
                        }

                        // For most cases, the cost of substituting 2 characters
                        // is 1.0
                        return 1.0;
                    }
                });

        System.out.println(wl.distance("String1", "Srring2"));

        // K-Shingling
        System.out.println("\nK-Shingling");
        s1 = "my string,  \n  my song";
        s2 = "another string, from a song";
        Cosine cosine = new Cosine(4);
        System.out.println(cosine.getProfile(s1));
        System.out.println(cosine.getProfile(s2));

        cosine = new Cosine(2);
        System.out.println(cosine.getProfile("ABCAB"));

    }

}
