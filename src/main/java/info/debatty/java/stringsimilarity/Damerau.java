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

import java.util.HashMap;

/**
 * Implementation of Damerau-Levenshtein distance, computed as the 
 * minimum number of operations needed to transform one string into the other, 
 * where an operation is defined as an insertion, deletion, or substitution of a
 * single character, or a transposition of two adjacent characters.
 * 
 * This is not to be confused with the optimal string alignment distance, which 
 * is an extension where no substring can be edited more than once.
 * 
 * @author Thibault Debatty
 */
public class Damerau implements StringSimilarityInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Damerau d = new Damerau();
        System.out.println(d.absoluteDistance("ABCDEF", "ABDCEF"));
        System.out.println(d.absoluteDistance("ABCDEF", "BACDFE"));
        System.out.println(d.absoluteDistance("ABCDEF", "ABCDE"));
        System.out.println(d.absoluteDistance("ABCDEF", "BCDEF"));
        System.out.println(d.absoluteDistance("ABCDEF", "ABCGDEF"));
        System.out.println(d.absoluteDistance("ABCDEF", "BCDAEF"));
        
        System.out.println(d.distance("ABCDEF", "GHABCDE"));
    }

    public int absoluteDistance(String s1, String s2) {

        // INFinite distance is the max possible distance
        int INF = s1.length() + s2.length();
        
        // Create and initialize the character array indices
        HashMap<Character, Integer> DA = new HashMap<Character, Integer>();
        
        for (int d = 0; d < s1.length(); d++) {
            if (!DA.containsKey(s1.charAt(d))) {
                DA.put(s1.charAt(d), 0);
            }
        }
        
        for (int d = 0; d < s2.length(); d++) {
            if (!DA.containsKey(s2.charAt(d))) {
                DA.put(s2.charAt(d), 0);
            }
        }
        
        // Create the distance matrix H[0 .. s1.length+1][0 .. s2.length+1]
        int[][] H = new int[s1.length() + 2][s2.length() + 2];
        
        // initialize the left and top edges of H
        for (int i = 0; i <= s1.length(); i++) {
            H[i + 1][0] = INF;
            H[i + 1][1] = i;
        }
        
        for (int j = 0; j <= s2.length(); j++) {
            H[0][j + 1] = INF;
            H[1][j + 1] = j;
            
        }
        

        // fill in the distance matrix H
        // look at each character in s1
        for (int i = 1; i <= s1.length(); i++) {
            int DB = 0;
            
            // look at each character in b
            for (int j = 1; j <= s2.length(); j++) {
                int i1 = DA.get(s2.charAt(j - 1));
                int j1 = DB;
                
                int cost = 1;
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    cost = 0;
                    DB = j;
                }
                
                H[i + 1][j + 1] = min(
                        H[i][j] + cost,     // substitution
                        H[i + 1][j] + 1,    // insertion
                        H[i][j + 1] + 1,    // deletion
                        H[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
            }
            
            DA.put(s1.charAt(i - 1), i);
        }
        
        return H[s1.length() + 1][s2.length() + 1];
    }

    public double similarity(String s1, String s2) {
        return 1.0 - distance(s1, s2);
    }

    public double distance(String s1, String s2) {
        return (double) absoluteDistance(s1, s2) / (s1.length() + s2.length());
    }
    
    protected static int min(int a, int b, int c, int d) {
        return Math.min(a, Math.min(b, Math.min(c, d)));
    }

}
