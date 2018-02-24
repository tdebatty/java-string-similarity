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
package info.debatty.java.stringsimilarity.interfaces;

/**
 * String distances that implement this interface are metrics.
 * This means:
 * - d(x, y) ≥ 0 (non-negativity, or separation axiom)
 * - d(x, y) = 0 if and only if x = y (identity, or coincidence axiom)
 * - d(x, y) = d(y, x) (symmetry)
 * - d(x, z) ≤ d(x, y) + d(y, z) (triangle inequality).
 *
 * @author Thibault Debatty
 */
public interface MetricStringDistance extends StringDistance {

    /**
     * Compute and return the metric distance.
     * @param s1
     * @param s2
     * @return
     */
    double distance(String s1, String s2);
}
