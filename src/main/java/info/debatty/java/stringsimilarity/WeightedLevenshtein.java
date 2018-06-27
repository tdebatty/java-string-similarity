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
    private final CharacterInsDelInterface charchange;

    /**
     * Instantiate with provided character substitution.
     * @param charsub The strategy to determine character substitution weights.
     */
    public WeightedLevenshtein(final CharacterSubstitutionInterface charsub) {
        this(charsub, null);
    }

    /**
     * Instantiate with provided character substitution, insertion, and
     * deletion weights.
     * @param charsub The strategy to determine character substitution weights.
     * @param charchange The strategy to determine character insertion /
     *                   deletion weights.
     */
    public WeightedLevenshtein(final CharacterSubstitutionInterface charsub,
                               final CharacterInsDelInterface charchange) {
        this.charsub = charsub;
        this.charchange = charchange;
    }

    /**
     * Equivalent to distance(s1, s2, Double.MAX_VALUE).
     */
    public final double distance(final String s1, final String s2) {
        return distance(s1, s2, Double.MAX_VALUE);
    }

    /**
     * Compute Levenshtein distance using provided weights for substitution.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @param limit The maximum result to compute before stopping. This
     *              means that the calculation can terminate early if you
     *              only care about strings with a certain similarity.
     *              Set this to Double.MAX_VALUE if you want to run the
     *              calculation to completion in every case.
     * @return The computed weighted Levenshtein distance.
     * @throws NullPointerException if s1 or s2 is null.
     */
    public final double distance(final String s1, final String s2,
                                 final double limit) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 0;
        }

        if (s1.length() == 0) {
            return s2.length();
        }

        if (s2.length() == 0) {
            return s1.length();
        }

        // create two work vectors of floating point (i.e. weighted) distances
        double[] v0 = new double[s2.length() + 1];
        double[] v1 = new double[s2.length() + 1];
        double[] vtemp;

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s1
        // the distance is the cost of inserting each character of s2
        v0[0] = 0;
        for (int i = 1; i < v0.length; i++) {
            v0[i] = v0[i - 1] + insertionCost(s2.charAt(i - 1));
        }

        for (int i = 0; i < s1.length(); i++) {
            char s1i = s1.charAt(i);
            double deletion_cost = deletionCost(s1i);

            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            // Edit distance is the cost of deleting characters from s1
            // to match empty t.
            v1[0] = v0[0] + deletion_cost;

            double minv1 = v1[0];

            // use formula to fill in the rest of the row
            for (int j = 0; j < s2.length(); j++) {
                char s2j = s2.charAt(j);
                double cost = 0;
                if (s1i != s2j) {
                    cost = charsub.cost(s1i, s2j);
                }
                double insertion_cost = insertionCost(s2j);
                v1[j + 1] = Math.min(
                        v1[j] + insertion_cost, // Cost of insertion
                        Math.min(
                                v0[j + 1] + deletion_cost, // Cost of deletion
                                v0[j] + cost)); // Cost of substitution

                minv1 = Math.min(minv1, v1[j + 1]);
            }

            if (minv1 >= limit) {
                return limit;
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


    private double insertionCost(final char c) {
        if (charchange == null) {
            return 1.0;
        } else {
            return charchange.insertionCost(c);
        }
    }

    private double deletionCost(final char c) {
        if (charchange == null) {
            return 1.0;
        } else {
            return charchange.deletionCost(c);
        }
    }
}
