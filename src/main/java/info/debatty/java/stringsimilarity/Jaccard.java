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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Thibault Debatty
 */
public class Jaccard implements StringSimilarityInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jaccard j2 = new Jaccard(2);
        
        // AB BC CD DE DF
        // 1  1  1  1  0
        // 1  1  1  0  1
        // => 3 / 5 = 0.6
        System.out.println(j2.similarity("ABCDE", "ABCDF"));
    }
    
    private final int k;
    
    /**
     * The strings are first transformed into sets of k-shingles (sequences of k
     * characters), then Jaccard index is computed as |A inter B| / |A union B|.
     * The default value of k is 3.
     * 
     * @param k 
     */
    public Jaccard(int k) {
        this.k = k;
    }
    
    public Jaccard() {
        this.k = 3;
    }

    public double similarity(String s1, String s2) {
        KShingling ks = new KShingling(this.k);
        return similarity(ks.getProfile(s1), ks.getProfile(s2));
    }
    
    /**
     * Compute and return the Jaccard index similarity between two string profiles.
     * 
     * E.g:
     * ks = new KShingling(4)
     * profile1 = ks.getProfile("My String")
     * profile2 = ks.getProfile("My other string")
     * similarity = jaccard.similarity(profile1, profile2)
     * 
     * @param profile1
     * @param profile2
     * @return 
     */
    public double similarity(HashMap<String,Integer> profile1,
        HashMap<String,Integer> profile2) {
        Set<String> set1 = profile1.keySet();
        Set<String> set2 = profile2.keySet();
        
        Set union = new HashSet();
        union.addAll(set1);
        union.addAll(set2);
        
        Set inter = new HashSet(set1);
        inter.retainAll(set2);
        
        return (double) inter.size() / union.size();
    }

    public double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }
    
}
