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

/**
 * Implements Cosine Similarity.
 * The strings are first transformed in vectors of occurences of k-shingles 
 * (sequences of k characters). In this n-dimensional space, the similarity
 * between the two strings is the cosine of their respective vectors.
 * @author Thibault Debatty
 */
public class Cosine implements StringSimilarityInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
    }
    
    private int k;
    
    public Cosine(int k) {
        this.k = k;
    }
    
    public Cosine() {
        this.k = 3;
    }

    /**
     * Computes the cosine similarity of s1 and s2.
     * The strings are first converted to vectors in the space of k-shingles. 
     * The cosine similarity is computed as V1 . V2 / (|V1| * |V2|)
     * @param s1
     * @param s2
     * @return Cosine similarity
     */
    public double similarity(String s1, String s2) {
        if (s1.equals(s2)) {
            return 1.0;
        }
        
        
        if (s1.equals("") || s2.equals("")) {
            return 0.0;
        }
        
        KShingling ks = new KShingling(this.k);
        ks.parse(s1);
        ks.parse(s2);
        
        int[] v1 = ks.profileOf(s1);
        int[] v2 = ks.profileOf(s2);
        
        return dotProduct(v1, v2) / (norm(v1) * norm(v2));
        
    }

    public double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }
    
    /**
     * Compute the norm L2 : sqrt(Sum_i( v_i^2))
     * @param v
     * @return L2 norm
     */
    protected static double norm(int[] v) {
        double agg = 0;
        
        for (int i = 0; i < v.length; i++) {
            agg += (v[i] * v[i]);
        }
        
        return Math.sqrt(agg);
    }
    
    protected static double dotProduct(int[] v1, int[] v2) {
        double agg = 0;
        
        for (int i = 0; i < v1.length; i++) {
            agg += (v1[i] * v2[i]);
        }
        
        return agg;
    }
    
}
