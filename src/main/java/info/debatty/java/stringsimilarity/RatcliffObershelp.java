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
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import net.jcip.annotations.Immutable;

/**
 * Ratcliff/Obershelp pattern recognition
 * The Ratcliff/Obershelp algorithm computes the similarity of two strings a
 * the doubled number of matching characters divided by the total number of
 * characters in the two strings. Matching characters are those in the longest
 * common subsequence plus, recursively, matching characters in the unmatched
 * region on either side of the longest common subsequence.
 * The Ratcliff/Obershelp distance is computed as 1 - Ratcliff/Obershelp
 * similarity.
 *
 * @author Ligi https://github.com/dxpux (as a patch for fuzzystring)
 * Ported to java from .net by denmase
 */
@Immutable
public class RatcliffObershelp implements
        NormalizedStringSimilarity, NormalizedStringDistance {

    /**
     * Compute the Ratcliff-Obershelp similarity between strings.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return The RatcliffObershelp similarity in the range [0, 1]
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
    public final double similarity(final String s1, final String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 1.0d;
        }

        List<String> matches = getMatchList(s1, s2);
        int sum_of_matches = 0;

        for (String match : matches) {
            sum_of_matches += match.length();
        }

        return 2.0d * sum_of_matches / (s1.length() + s2.length());
    }

    /**
     * Return 1 - similarity.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return 1 - similarity
     * @throws NullPointerException if s1 or s2 is null.
     */
    @Override
    public final double distance(final String s1, final String s2) {
        return 1.0d - similarity(s1, s2);
    }

    private static List<String> getMatchList(final String s1, final String s2) {
        List<String> list = new ArrayList<String>();
        String match = frontMaxMatch(s1, s2);

        if (match.length() > 0) {
            String frontsource = s1.substring(0, s1.indexOf(match));
            String fronttarget = s2.substring(0, s2.indexOf(match));
            List<String> frontqueue = getMatchList(frontsource, fronttarget);

            String endsource = s1.substring(s1.indexOf(match) + match.length());
            String endtarget = s2.substring(s2.indexOf(match) + match.length());
            List<String> endqueue = getMatchList(endsource, endtarget);

            list.add(match);
            list.addAll(frontqueue);
            list.addAll(endqueue);
        }

        return list;
    }

    private static String frontMaxMatch(final String s1, final String s2) {
        int longest = 0;
        String longestsubstring = "";

        for (int i = 0; i < s1.length(); ++i) {
            for (int j = i + 1; j <= s1.length(); ++j) {
                String substring = s1.substring(i, j);
                if (s2.contains(substring) && substring.length() > longest) {
                    longest = substring.length();
                    longestsubstring = substring;
                }
            }
        }

        return longestsubstring;
    }
}
