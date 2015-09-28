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

import info.debatty.java.utils.SparseIntegerVector;

/**
 * Profile of a string, computed using shingling.
 * 
 * @author Thibault Debatty
 */
public class StringProfile {
    
    public static void main(String[] args) {
        KShingling ks = new KShingling(2);
        for (String ngram : ks.getProfile("ABCABC").getMostFrequentNGrams(2)) {
            System.out.println(ngram);
        }
        
        for (String ngram : ks.getProfile("A").getMostFrequentNGrams(2)) {
            System.out.println(ngram);
        }
        
        for (String ngram : ks.getProfile("This is a string...").getMostFrequentNGrams(2)) {
            System.out.println(ngram);
        }
    }
    
    private final SparseIntegerVector vector;
    private final KShingling ks;
    
    public StringProfile(SparseIntegerVector vector, KShingling ks) {
        this.vector = vector;
        this.ks = ks;
    }
    
    /**
     *
     * @param other
     * @return
     * @throws java.lang.Exception
     */
    public double cosineSimilarity(StringProfile other) throws Exception {
        if (this.ks != other.ks) {
            throw new Exception("Profiles were not created using the same kshingling object!");
        }
        
        return this.vector.cosineSimilarity(other.vector);
    }
    
    /**
     * 
     * @param other
     * @return
     * @throws Exception 
     */
    public double qgramDistance(StringProfile other) throws Exception {
        if (this.ks != other.ks) {
            throw new Exception("Profiles were not created using the same kshingling object!");
        }
        
        return this.vector.qgram(other.vector);
    }
    
    public SparseIntegerVector getSparseVector() {
        return this.vector;
    }
    
    public String[] getMostFrequentNGrams(int number) {
        String[] strings = new String[number];
        int[] frequencies = new int[number];
        
        int position_smallest_frequency = 0;
        
        for (int i = 0; i < vector.size(); i++) {
            int key = vector.getKey(i);
            int frequency = vector.getValue(i);
            String ngram = ks.getNGram(key);
            
            if (frequency > frequencies[position_smallest_frequency]) {
                // 1. replace the element with currently the smallest frequency
                strings[position_smallest_frequency] = ngram;
                frequencies[position_smallest_frequency] = frequency;
                
                // 2. loop over frequencies to find which one is now the lowest
                // frequency
                int smallest_frequency = Integer.MAX_VALUE;
                for (int j = 0; j < frequencies.length; j++) {
                    if (frequencies[j] < smallest_frequency) {
                        position_smallest_frequency = j;
                        smallest_frequency = frequencies[j];
                    }
                }
                
            }
            
        }
        
        return strings;

        
    }
}
