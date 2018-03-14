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

import net.jcip.annotations.Immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Abstract class for string similarities that rely on set operations (like
 * cosine similarity or jaccard index).
 *
 * k-shingling is the operation of transforming a string (or text document) into
 * a set of n-grams, which can be used to measure the similarity between two
 * strings or documents.
 *
 * Generally speaking, a k-gram is any sequence of k tokens. We use here the
 * definition from Leskovec, Rajaraman &amp; Ullman (2014), "Mining of Massive
 * Datasets", Cambridge University Press: Multiple subsequent spaces are
 * replaced by a single space, and a k-gram is a sequence of k characters.
 *
 * Default value of k is 3. A good rule of thumb is to imagine that there are
 * only 20 characters and estimate the number of k-shingles as 20^k. For small
 * documents like e-mails, k = 5 is a recommended value. For large documents,
 * such as research articles, k = 9 is considered a safe choice.
 *
 * @author Thibault Debatty
 */
@Immutable
public abstract class ShingleBased {

    private static final int DEFAULT_K = 3;

    private final int k;

    /**
     * Pattern for finding multiple following spaces.
     */
    private static final Pattern SPACE_REG = Pattern.compile("\\s+");

    /**
     *
     * @param k
     * @throws IllegalArgumentException if k is &lt;= 0
     */
    public ShingleBased(final int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k should be positive!");
        }
        this.k = k;
    }

    /**
     *
     */
    ShingleBased() {
        this(DEFAULT_K);
    }

    /**
     * Return k, the length of k-shingles (aka n-grams).
     *
     * @return The length of k-shingles.
     */
    public final int getK() {
        return k;
    }

    /**
     * Compute and return the profile of s, as defined by Ukkonen "Approximate
     * string-matching with q-grams and maximal matches".
     * https://www.cs.helsinki.fi/u/ukkonen/TCS92.pdf The profile is the number
     * of occurrences of k-shingles, and is used to compute q-gram similarity,
     * Jaccard index, etc. Pay attention: the memory requirement of the profile
     * can be up to k * size of the string
     *
     * @param string
     * @return the profile of this string, as an unmodifiable Map
     */
    public final Map<String, Integer> getProfile(final String string) {
        HashMap<String, Integer> shingles = new HashMap<String, Integer>();

        String string_no_space = SPACE_REG.matcher(string).replaceAll(" ");
        for (int i = 0; i < (string_no_space.length() - k + 1); i++) {
            String shingle = string_no_space.substring(i, i + k);
            Integer old = shingles.get(shingle);
            if (old != null) {
                shingles.put(shingle, old + 1);
            } else {
                shingles.put(shingle, 1);
            }
        }

        return Collections.unmodifiableMap(shingles);
    }
}
