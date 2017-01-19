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

import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import info.debatty.java.utils.NullEmptyUtil;
import net.jcip.annotations.Immutable;

/**
 * Implementation of Levenshtein that allows to define different weights for
 * different character substitutions.
 *
 * @author Thibault Debatty
 */
@Immutable
public class WeightedLevenshtein implements StringDistance {

    private final CharacterSubstitutionInterface charsub;

    /**
     * Instatiate with provided character substitution.
     * @param charsub
     */
    public WeightedLevenshtein(final CharacterSubstitutionInterface charsub) {
        this.charsub = charsub;
    }

    /**
     * Compute Levenshtein distance using provided weights for substitution.
     * @param s1
     * @param s2
     * @return
     */
    public final double distance(final String s1, final String s2) {
        Double nullEmptyDistance = NullEmptyUtil.lengthDistance(s1, s2);

        if (nullEmptyDistance != null) {
            return nullEmptyDistance;
        }

        if (s1.equals(s2)) {
            return 0;
        }

        // create two work vectors of integer distances
        double[] v0 = new double[s2.length() + 1];
        double[] v1 = new double[s2.length() + 1];
        double[] vtemp;

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s
        // the distance is just the number of characters to delete from t
        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < s1.length(); i++) {
            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            //   edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            // use formula to fill in the rest of the row
            for (int j = 0; j < s2.length(); j++) {
                double cost = 0;
                if (s1.charAt(i) != s2.charAt(j)) {
                    cost = charsub.cost(s1.charAt(i), s2.charAt(j));
                }
                v1[j + 1] = Math.min(
                        v1[j] + 1, // Cost of insertion
                        Math.min(
                                v0[j + 1] + 1, // Cost of remove
                                v0[j] + cost)); // Cost of substitution
            }

            // copy v1 (current row) to v0 (previous row) for next iteration
            //System.arraycopy(v1, 0, v0, 0, v0.length);
            // Flip references to current and previous row
            vtemp = v0;
            v0 = v1;
            v1 = vtemp;

        }

        return v0[s2.length()];
    }
}
