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

import java.util.ArrayList;

/**
 *
 * @author tibo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<StringSimilarityInterface> similarities = new ArrayList<StringSimilarityInterface>();
        similarities.add(new Cosine(4));
        similarities.add(new Damerau());
        similarities.add(new Jaccard(4));
        similarities.add(new JaroWinkler());
        similarities.add(new Levenshtein());
        similarities.add(new LongestCommonSubsequence());
        similarities.add(new NGram(4));
        similarities.add(new QGram(4));
        similarities.add(new SorensenDice(4));
        
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        // Twice the same
        pairs.add(new Pair("ABCDEF", "ABCDEF"));
        
        // adjacent letters switch
        pairs.add(new Pair("ABCDEFGHIJ", "ABDCEFGHIJ"));
        
        // adjacent letters switch, with mixed cases
        pairs.add(new Pair("abcdefghij", "ABDCEFGHIJ"));
        
        // close letters switch
        pairs.add(new Pair("ABCDEFGHIJ", "ABFDECGHIJ"));
        
        // 2 blocks switch
        pairs.add(new Pair("ABCDEFVWXYZ", "VWXYZABCDEF"));
        
        // block switch in a long sequence
        pairs.add(new Pair(
                "Lorem ipsum dolor sit amet, MY BLOCK IS HERE consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, MY BLOCK IS HERE sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        
        // "Noise" : Small inserts
        pairs.add(new Pair(
                "Lorem Xipsum Xdolor Xsit Xamet, Xconsectetur Xadipiscing Xelit, Xsed Xdo Xeiusmod Xtempor Xincididunt Xut Xlabore Xet Xdolore Xmagna Xaliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
        
        // Semantic similar
        pairs.add(new Pair(
                "Mon automobile est en panne",
                "Ma voiture est cassée"
        ));
        
        // multiple blocks switch
        pairs.add(new Pair("AZERPOIUQSDFMLKJ", "QSDFMLKJAZERPOIU"));
        
        // completely different...
        pairs.add(new Pair("AZERTYUIOP", "QSDFGHJKLM"));
        
        // One empty
        pairs.add(new Pair("", "QSDFGHJKLM"));
        
        // All reverse
        pairs.add(new Pair("AZERTYUIOP", "POIUYTREZA"));
        
        
        for(Pair pair: pairs) {
            System.out.println("\n" + pair._1 + " <> " + pair._2);
            for (StringSimilarityInterface similarity: similarities) {
                System.out.printf("%-25s : %f\n",
                        similarity.getClass().getSimpleName(),
                        similarity.similarity(pair._1, pair._2));
            }
        }
    }
    
}

class Pair {
    public String _1 = "";
    public String _2 = "";
    
    public Pair(String s1, String s2) {
        _1 = s1;
        _2 = s2;
    }
}