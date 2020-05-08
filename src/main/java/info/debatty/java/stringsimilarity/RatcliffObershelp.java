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
import java.util.*;

import net.jcip.annotations.Immutable;

/**
 * Ratcliff/Obershelp pattern recognition
 * The Ratcliff/Obershelp algorithm computes the similarity of two strings a
 * the doubled number of matching characters divided by the total number of
 * characters in the two strings. Matching characters are those in the longest
 * common subsequence plus, recursively, matching characters in the unmatched
 * region on either side of the longest common subsequence.
 * The Ratcliff/Obershelp distance is computed as 1 - Ratcliff/Obershelp similarity.
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
    public final double similarity(String source, String target) {
        if (source == null) {
            throw new NullPointerException("source must not be null");
        }

        if (target == null) {
            throw new NullPointerException("target must not be null");
        }

        if (source.equals(target)) {
            return 1;
        }

        List<String> matches; // = new ArrayList<>();
        matches = getMatchQueue(source, target);
        int sumOfMatches = 0;
        Iterator it;
        it = matches.iterator();

        // Display element by element using Iterator 
        while (it.hasNext()) {
            String element = it.next().toString();
            //System.out.println(element);
            sumOfMatches += element.length();
        }
        return 2.0d * sumOfMatches / (source.length() + target.length());
    }

    /**
     * Return 1 - similarity.
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return 1 - similarity
     * @throws NullPointerException if s1 or s2 is null.
     */
    public final double distance(final String s1, final String s2) {
        return 1.0 - similarity(s1, s2);
    }

    private static List<String> getMatchQueue(String source, String target) {
        List<String> list = new ArrayList<>();
        String match = frontMaxMatch(source, target);
        if (match.length() > 0) {
            String frontSource = source.substring(0, source.indexOf(match));
            String frontTarget = target.substring(0, target.indexOf(match));
            List<String> frontQueue = getMatchQueue(frontSource, frontTarget);

            String endSource = source.substring(source.indexOf(match) + match.length());
            String endTarget = target.substring(target.indexOf(match) + match.length());
            List<String> endQueue = getMatchQueue(endSource, endTarget);

            list.add(match);
            list.addAll(frontQueue);
            list.addAll(endQueue);
        }
        return list;
    }

    private static String frontMaxMatch(String firstString, String secondString) {
        int longest = 0;
        String longestSubstring = "";

        for (int i = 0; i < firstString.length(); ++i) {
            for (int j = i + 1; j <= firstString.length(); ++j) {
                String substring = firstString.substring(i, j);
                if (secondString.contains(substring) && substring.length() > longest) {
                    longest = substring.length();
                    longestSubstring = substring;
                }
            }
        }
        return longestSubstring;
    }
}