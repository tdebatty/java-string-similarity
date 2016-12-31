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

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import net.jcip.annotations.Immutable;

/**
 * This distance is computed as levenshtein distance divided by the length of
 * the longest string. The resulting value is always in the interval [0.0 1.0]
 * but it is not a metric anymore! The similarity is computed as 1 - normalized
 * distance.
 *
 * @author Thibault Debatty
 */
@Immutable
public class Normalized implements
        NormalizedStringDistance, NormalizedStringSimilarity {

    private final StringDistance algorithm;

    public Normalized(StringDistance algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Compute distance as Levenshtein(s1, s2) / max(|s1|, |s2|).
     * @param s1
     * @param s2
     * @return
     */
    public final double distance(final String s1, final String s2) {
    int mLen = Math.max(s1.length(), s2.length());
    if (mLen == 0) {
        return 0;
    }
        return algorithm.distance(s1, s2) / mLen;
    }

    /**
     * Return 1 - distance.
     * @param s1
     * @param s2
     * @return
     */
    public final double similarity(final String s1, final String s2) {
        return 1.0 - distance(s1, s2);
    }


}
