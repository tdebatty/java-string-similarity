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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Sparse vector of double, implemented using two arrays
 * @author Thibault Debatty
 */
public class SparseDoubleVector implements Serializable {
    
    protected int[] keys;
    protected double[] values;
    protected int size = 0;
    
    private double norm = -1.0;
    private int total_size = 1;
    
    /**
     * Math.sqrt(gamma)
     * Set when using dimsum sampling
     * and used to compute cosine similarity after dimsum sampling
     */
    private double sq_gamma = Double.MAX_VALUE;

    public SparseDoubleVector(int size) {
        keys = new int[size];
        values = new double[size];
    }
    
    public SparseDoubleVector() {
        this(20);
    }
    
    public SparseDoubleVector(HashMap<Integer, Double> hashmap) {
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
    public SparseDoubleVector(double[] array) {
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                size++;
            }
        }
        
        keys = new int[size];
        values = new double[size];
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
    public double dotProduct(SparseDoubleVector other) {
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
    
    public double dotProduct(double[] other) {
        double agg = 0;
        for (int i = 0; i < keys.length; i++) {
            agg += other[keys[i]] * values[i];
        }
        return agg;
    }
    
    
    /**
     * Computes and return the Jaccard index with other SparseVector.
     * |A inter B| / |A union B|
     * It is actually computed as |A inter B| / (|A| +|B| - | A inter B|)
     * using a single loop over A and B
     * @param other
     * @return 
     */
    public double jaccard(SparseDoubleVector other) {
        int intersection = this.intersection(other);
        return (double) intersection / (this.size + other.size - intersection);
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public int union(SparseDoubleVector other) {
        return this.size + other.size - this.intersection(other);
    }
    
    /**
     * Return the number of non-zero values these two vectors have in common, 
     * |A inter B|.
     * E.g: A = {0 1 2 3} and B = {1 2 3 0}
     * have non-zero values at positions 1 and 2, hence A.intersection(B) = 2
     * @param other
     * @return 
     */
    public int intersection(SparseDoubleVector other) {
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
    public double qgram(SparseDoubleVector other) {
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
     * Return the number of non-zero elements in this vector.
     * @return 
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Compute and return the L2 norm of the vector
     * @return 
     */
    public double norm() {
        if (norm >= 0) {
            return norm;
        }
        
        double agg = 0;
        for (int i = 0; i < values.length; i++) {
            agg += values[i] * values[i];
        }
        norm = Math.sqrt(agg);
        return norm;
    }
    
    /**
     * Return the cosine similarity between the vectors.
     * Similarity = cos(theta) = A . B / (|A|.|B|)
     * @param other
     * @return 
     */
    
    public double cosineSimilarity(SparseDoubleVector other) {
        
        //double den = this.norm() * other.norm();
        
        //double den =
        //            Math.min(this.sq_gamma * this.norm() / Math.sqrt(total_size), this.norm()) *
        //            Math.min(other.sq_gamma * other.norm() / Math.sqrt(total_size), other.norm());
        
        
        // Original DIMSUM:
        double den =
                    Math.min(this.sq_gamma, this.norm()) *
                    Math.min(other.sq_gamma, other.norm());
        
        double agg = 0;
        int i = 0;
        int j = 0;
        while (i < this.keys.length  && j < other.keys.length) {
            int k1 = this.keys[i];
            int k2 = other.keys[j];

            if (k1 == k2) {
                agg += this.values[i] * other.values[j];// / den;
                i++;
                j++;

            } else if (k1 < k2) {
                i++;
            } else {
                j++;
            }
        }
        return agg/den;
    }
    
    public void sampleDIMSUM(double threshold, int count, int size) {
        this.total_size = size; // Will be used to compute cosine similarity...
        double gamma = 10 * Math.log(count) / threshold;
        this.sq_gamma = Math.sqrt(gamma);
        
        this.norm();
        
        // Original dimsum: 
        double probability =  sq_gamma / this.norm();
        
        //
        //double probability =  sq_gamma / Math.sqrt(size);
        
        if (probability >= 1.0) {
            return;
        }
        
        // This is extremely inefficient :-/
        Random r = new Random();
        ArrayList<Integer> new_keys = new ArrayList<Integer>();
        ArrayList<Double> new_values = new ArrayList<Double>();
        
        for (int i = 0; i < keys.length; i++) {
            
            if (r.nextDouble() < probability) {
                new_keys.add(keys[i]);
                new_values.add(values[i]);
            }
        }
        
        
        this.keys = new int[new_keys.size()];
        this.values = new double[new_values.size()];
        this.size = new_keys.size();
        for (int i = 0; i < keys.length; i++) {
            this.keys[i] = new_keys.get(i);
            this.values[i] = new_values.get(i);
        }
    }
    
    /**
     * Return the array corresponding to this sparse vector.
     * @param size
     * @return 
     */
    public double[] toArray(int size) {
        
        double[] array = new double[size];
        for (int i = 0; i < keys.length; i++) {
            array[keys[i]] = values[i];
        }
        return array;
    }
}
