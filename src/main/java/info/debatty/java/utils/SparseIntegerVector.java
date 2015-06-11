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

package info.debatty.java.utils;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Sparse vector of int, implemented using two arrays
 * @author Thibault Debatty
 */
public class SparseIntegerVector {
    
    protected int[] keys;
    protected int[] values;
    protected int size = 0;

    public SparseIntegerVector(int size) {
        keys = new int[size];
        values = new int[size];
    }
    
    public SparseIntegerVector() {
        this(20);
    }
    
    public SparseIntegerVector(HashMap<Integer, Integer> hashmap) {
        this(hashmap.size());
        SortedSet<Integer> sorted_keys = new TreeSet<Integer>(hashmap.keySet());
        for (int key : sorted_keys) {
            keys[size] = key;
            values[size] = hashmap.get(key);
            size++;
        }
    }

    /**
     * 
     * @param array 
     */
    public SparseIntegerVector(int[] array) {
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                size++;
            }
        }
        
        keys = new int[size];
        values = new int[size];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                keys[j] = i;
                values[j] = array[i];
                j++;
            }
        }
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public double dotProduct(SparseIntegerVector other) {
        double agg = 0;
        int i = 0;
        int j = 0;
        while (i < this.keys.length  && j < other.keys.length) {
            int k1 = this.keys[i];
            int k2 = other.keys[j];

            if (k1 == k2) {
                agg += this.values[i] * other.values[j];
                i++;
                j++;

            } else if (k1 < k2) {
                i++;
            } else {
                j++;
            }
        }
        return agg;
    }
    
    /**
     * 
     * @return 
     */
    public double norm() {
        double agg = 0;
        for (int i = 0; i < values.length; i++) {
            agg += values[i] * values[i];
        }
        return Math.sqrt(agg);
    }
    
    /**
     * Computes and return the Jaccard index with other SparseVector.
     * |A inter B| / |A union B|
     * It is actually computed as |A inter B| / (|A| +|B| - | A inter B|)
     * using a single loop over A and B
     * @param other
     * @return 
     */
    public double jaccard(SparseIntegerVector other) {
        int intersection = this.intersection(other);
        return (double) intersection / (this.size + other.size - intersection);
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public int union(SparseIntegerVector other) {
        return this.size + other.size - this.intersection(other);
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public int intersection(SparseIntegerVector other) {
        int agg = 0;
        int i = 0;
        int j = 0;
        while (i < this.keys.length  && j < other.keys.length) {
            int k1 = this.keys[i];
            int k2 = other.keys[j];

            if (k1 == k2) {
                agg++;
                i++;
                j++;

            } else if (k1 < k2) {
                i++;
                
            } else {
                j++;
            }
        }
        return agg;
    }
    
    @Override
    public String toString() {
        String r = "";
        for (int i = 0; i < size; i++) {
            r += keys[i] + ":" + values[i] + " ";
        }
        
        return r;
    }

    /**
     * Compute and return the qgram similarity with other vector.
     * Sum(|a_i - b_i|)
     * @param other
     * @return 
     */
    public double qgram(SparseIntegerVector other) {
        double agg = 0;
        int i = 0, j = 0;
        int k1, k2;
        
        while (i < this.keys.length  && j < other.keys.length) {
            k1 = this.keys[i];
            k2 = other.keys[j];

            if (k1 == k2) {
                agg += Math.abs(this.values[i] - other.values[j]);
                i++;
                j++;

            } else if (k1 < k2) {
                agg += Math.abs(this.values[i]);
                i++;
                
            } else {
                agg += Math.abs(other.values[j]);
                j++;
            }
        }
        
        // Maybe one of the two vectors was not completely walked...
        while (i < this.keys.length) {
            agg += Math.abs(this.values[i]);
            i++;
        }
        
        while (j < other.keys.length) {
            agg += Math.abs(other.values[j]);
            j++;
        }
        return agg;
    }

    /**
     * Return the number of (non-zero) elements in this vector.
     * @return 
     */
    public int size() {
        return this.size;
    }
}
