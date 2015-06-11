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

import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;

/**
 * 
 * @author Thibault Debatty
 */
public class Jaccard extends ShingleBased implements 
        MetricStringDistance, NormalizedStringDistance, NormalizedStringSimilarity {

    public static void main(String[] args) {
        Jaccard j2 = new Jaccard(2);
        
        // AB BC CD DE DF
        // 1  1  1  1  0
        // 1  1  1  0  1
        // => 3 / 5 = 0.6
        System.out.println(j2.similarity("ABCDE", "ABCDF"));
        
    }
    
    /**
     * The strings are first transformed into sets of k-shingles (sequences of k
     * characters), then Jaccard index is computed as |A inter B| / |A union B|.
     * The default value of k is 3.
     * 
     * @param k 
     */
    public Jaccard(int k) {
        super(k);
    }
    
    /**
     * 
     */
    public Jaccard() {
        super();
    }

    
    public double similarity(String s1, String s2) {
        KShingling ks = new KShingling(k);
        int[] profile1 = ks.getArrayProfile(s1);
        int[] profile2 = ks.getArrayProfile(s2);
    
        int length = Math.max(profile1.length, profile2.length);
        profile1 = java.util.Arrays.copyOf(profile1, length);
        profile2 = java.util.Arrays.copyOf(profile2, length);
        
        int inter = 0;
        int union = 0;
        
        for (int i = 0; i < length; i++) {
            if (profile1[i] > 0 || profile2[i] > 0) {
                union++;
                
                if (profile1[i] > 0 && profile2[i] > 0) {
                    inter++;
                }
            }
        }
    
        return (double) inter / union;
    }
    

    public double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }
}
