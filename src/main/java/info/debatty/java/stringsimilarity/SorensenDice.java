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

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.jcip.annotations.Immutable;

/**
 * Similar to Jaccard index, but this time the similarity is computed as 2 * |V1
 * inter V2| / (|V1| + |V2|). Distance is computed as 1 - cosine similarity.
 *
 * @author Thibault Debatty
 */
@Immutable
public class SorensenDice extends ShingleBased implements
        NormalizedStringDistance, NormalizedStringSimilarity {

    /**
     * Sorensen-Dice coefficient, aka Sørensen index, Dice's coefficient or
     * Czekanowski's binary (non-quantitative) index.
     *
     * The strings are first converted to boolean sets of k-shingles (sequences
     * of k characters), then the similarity is computed as 2 * |A inter B| /
     * (|A| + |B|). Attention: Sorensen-Dice distance (and similarity) does not
     * satisfy triangle inequality.
     *
     * @param k
     */
    public SorensenDice(final int k) {
        super(k);
    }

    /**
     * Sorensen-Dice coefficient, aka Sørensen index, Dice's coefficient or
     * Czekanowski's binary (non-quantitative) index.
     *
     * The strings are first converted to boolean sets of k-shingles (sequences
     * of k characters), then the similarity is computed as 2 * |A inter B| /
     * (|A| + |B|). Attention: Sorensen-Dice distance (and similarity) does not
     * satisfy triangle inequality. Default k is 3.
     */
    public SorensenDice() {
        super();
    }

    /**
     * Similarity is computed as 2 * |A inter B| / (|A| + |B|).
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return The computed Sorensen-Dice similarity.
     * @throws NullPointerException if s1 or s2 is null.
     */
    public final double similarity(final String s1, final String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 1;
        }

        Map<String, Integer> profile1 = getProfile(s1);
        Map<String, Integer> profile2 = getProfile(s2);

        Set<String> union = new HashSet<String>();
        union.addAll(profile1.keySet());
        union.addAll(profile2.keySet());

        int inter = 0;

        for (String key : union) {
            if (profile1.containsKey(key) && profile2.containsKey(key)) {
                inter++;
            }
        }

        return 2.0 * inter / (profile1.size() + profile2.size());
    }

    /**
     * Returns 1 - similarity.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return 1.0 - the computed similarity
     * @throws NullPointerException if s1 or s2 is null.
     */
    public final double distance(final String s1, final String s2) {
        return 1 - similarity(s1, s2);
    }
}
