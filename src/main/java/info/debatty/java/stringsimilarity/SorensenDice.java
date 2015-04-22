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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Thibault Debatty
 */
public class SorensenDice implements StringSimilarityInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SorensenDice sd = new SorensenDice(2);
        
        // AB BC CD DE DF FG
        // 1  1  1  1  0  0
        // 1  1  1  0  1  1
        // => 2 x 3 / (4 + 5) = 6/9 = 0.6666
        System.out.println(sd.similarity("ABCDE", "ABCDFG"));
    }

    private final int k;
    
    /**
     * Sorensen-Dice coefficient, aka Sørensen index, Dice's coefficient or 
     * Czekanowski's binary (non-quantitative) index.
     * 
     * The strings are first converted to boolean sets of k-shingles (strings of k
     * characters), then the similarity is computed as 2 * |A inter B| / (|A| + |B|).
     * Attention: Sorensen-Dice distance (and similarity) does not satisfy 
     * triangle inequality.
     * 
     * @param k 
     */
    public SorensenDice(int k) {
        this.k = k;
    }
    
    public SorensenDice() {
        this.k = 3;
    }
    
    /**
     * Compute Sorensen-Dice coefficient 2 * |A inter B| / (|A| + |B|).
     * @param s1
     * @param s2
     * @return 
     */
    public double similarity(String s1, String s2) {
        KShingling ks = new KShingling(this.k);
        Set<String> set1 = ks.getProfile(s1).keySet();
        Set<String> set2 = ks.getProfile(s2).keySet();
        
        Set inter = new HashSet(set1);
        inter.retainAll(set2);
        
        return 2.0 * inter.size() / (set1.size() + set2.size());
    }

    public double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }
    
}
