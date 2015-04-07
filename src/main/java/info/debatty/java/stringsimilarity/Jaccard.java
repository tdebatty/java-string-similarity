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
    
    private int k;
    
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
        ks.parse(s1);
        ks.parse(s2);
        
        boolean[] v1 = ks.booleanVectorOf(s1);
        boolean[] v2 = ks.booleanVectorOf(s2);
        
        int inter = 0;
        int union = 0;
        for (int i = 0; i < v1.length; i++) {
            if (v1[i] || v2[i]) {
                union++;
                
                if (v1[i] && v2[i]) {
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
