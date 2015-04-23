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

/**
 *
 * @author tibo
 */
public abstract class SetBasedStringSimilarity implements StringSimilarityInterface {
    protected final int k;
    
    public SetBasedStringSimilarity(int k) {
        this.k = k;
    }
    
    public double similarity(String s1, String s2) {
        if (s1.equals(s2)) {
            return 1.0;
        }
        
        if (s1.equals("") || s2.equals("")) {
            return 0.0;
        }
        
        KShingling ks = new KShingling(this.k);
        return similarity(ks.getProfile(s1), ks.getProfile(s2));
    }
    
    public double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }
    
    public abstract double similarity(int[] profile1, int[] profile2);
}
