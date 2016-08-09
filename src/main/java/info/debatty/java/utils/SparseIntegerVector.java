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
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Sparse vector of int, implemented using two arrays.
 * @author Thibault Debatty
 */
public class SparseIntegerVector implements Serializable {

    private int[] keys;
    private int[] values;
    private int size = 0;

    private static final int DEFAULT_SIZE = 20;

    /**
     * Sparse vector of int, implemented using two arrays.
     * @param size number of non zero elements in the vector
     */
    public SparseIntegerVector(final int size) {
        keys = new int[size];
        values = new int[size];
    }

    /**
     * Sparse vector of int, implemented using two arrays.
     * Default size is 20.
     */
    public SparseIntegerVector() {
        this(DEFAULT_SIZE);
    }

    /**
     * Sparse vector of int, implemented using two arrays.
     * @param hashmap
     */
    public SparseIntegerVector(final HashMap<Integer, Integer> hashmap) {
        this(hashmap.size());
        SortedSet<Integer> sorted_keys = new TreeSet<Integer>(hashmap.keySet());
        for (int key : sorted_keys) {
            keys[size] = key;
            values[size] = hashmap.get(key);
            size++;
        }
    }

    /**
     * Sparse vector of int, implemented using two arrays.
     * @param array
     */
    public SparseIntegerVector(final int[] array) {

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
     * Compute and return the cosine similarity (cosine of angle between both
     * vectors).
     * @param other
     * @return
     */
    public final double cosineSimilarity(final SparseIntegerVector other) {
        double den = this.norm() * other.norm();
        double agg = 0;
        int i = 0;
        int j = 0;
        while (i < this.keys.length  && j < other.keys.length) {
            int k1 = this.keys[i];
            int k2 = other.keys[j];

            if (k1 == k2) {
                agg += 1.0 * this.values[i] * other.values[j] / den;
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
     * Compute and return the dot product.
     * @param other
     * @return
     */
    public final double dotProduct(final SparseIntegerVector other) {
        double agg = 0;
        int i = 0;
        int j = 0;
        while (i < this.keys.length  && j < other.keys.length) {
            int k1 = this.keys[i];
            int k2 = other.keys[j];

            if (k1 == k2) {
                agg += 1.0 * this.values[i] * other.values[j];
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
     * Compute and return the dot product.
     * @param other
     * @return
     */
    public final double dotProduct(final double[] other) {
        double agg = 0;
        for (int i = 0; i < keys.length; i++) {
            agg += 1.0 * other[keys[i]] * values[i];
        }
        return agg;
    }

    /**
     * Compute and return the L2 norm of the vector.
     * @return
     */
    public final double norm() {
        double agg = 0;
        for (int i = 0; i < values.length; i++) {
            agg += 1.0 * values[i] * values[i];
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
    public final double jaccard(final SparseIntegerVector other) {
        int intersection = this.intersection(other);
        return (double) intersection / (this.size + other.size - intersection);
    }

    /**
     * Compute the size of the union of these two vectors.
     * @param other
     * @return
     */
    public final int union(final SparseIntegerVector other) {
        return this.size + other.size - this.intersection(other);
    }

    /**
     * Compute the number of values that are present in both vectors (used to
     * compute jaccard index).
     * @param other
     * @return
     */
    public final int intersection(final SparseIntegerVector other) {
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
    public final String toString() {
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
    public final double qgram(final SparseIntegerVector other) {
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
    public final int size() {
        return this.size;
    }

    /**
     * Get the key at position i.
     * @param i
     * @return
     */
    public final int getKey(final int i) {
        return this.keys[i];
    }

    /**
     * Get the value of position i.
     * @param i
     * @return
     */
    public final int getValue(final int i) {
        return this.values[i];
    }
}
