package info.debatty.java.stringsimilarity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * k-shingling is the operation of transforming a string (or text document) into
 * a set of n-grams, which can be used to measure the similarity between two 
 * strings or documents.
 * 
 * Generally speaking, a n-gram is any sequence of k tokens. We use here the 
 * definition from  Leskovec, Rajaraman & Ullman (2014),
 * "Mining of Massive Datasets", Cambridge University Press:
 * Multiple subsequent spaces are replaced by a single space, and a k-gram is a 
 * sequence of k characters.
 * 
 * @author Thibault Debatty http://www.debatty.info
 */
public class KShingling {
    
    public static void main(String[] args) {
        String s1 = "my string,  \n  my song";
        String s2 = "another string, from a song";
        KShingling ks = new KShingling(4);
        System.out.println(ks.getProfile(s1));
        System.out.println(ks.getProfile(s2));
        
        ks = new KShingling(2);
        System.out.println(ks.getProfile("ABCAB"));
    }

    protected int k;
    private HashMap<String, Integer> shingles = new HashMap<String, Integer>();
    
    /**
     * k-shingling is the operation of transforming a string (or text document) into
     * a set of n-grams, which can be used to measure the similarity between two 
     * strings or documents.
     * 
     * Generally speaking, a k-gram is any sequence of k tokens. We use here the 
     * definition from  Leskovec, Rajaraman & Ullman (2014),
     * "Mining of Massive Datasets", Cambridge University Press:
     * Multiple subsequent spaces are replaced by a single space, and a k-gram is a 
     * sequence of k characters. 
     * 
     * Default value of k is 5 (recommended for emails).
     * A good rule of thumb is to imagine that there are only 20 characters 
     * and estimate the number of k-shingles as 20^k. For large documents, 
     * such as research articles, k = 9 is considered a safe choice.
     */
    public KShingling() {
        k = 5;
    }
    
    public KShingling(int k) {
        if (k <= 0) {
            throw new InvalidParameterException("k should be positive!");
        }
        
        this.k = k;
    }
    
    public int getK() {
        return k;
    }
    
    /**
     * Pattern for finding multiple following spaces
     */
    private static final Pattern spaceReg = Pattern.compile("\\s+");
    
    /**
     * Compute and return the profile of s, as defined by Ukkonen "Approximate
     * string-matching with q-grams and maximal matches".
     * https://www.cs.helsinki.fi/u/ukkonen/TCS92.pdf
     * The profile is the number of occurrences of k-shingles, and is used to 
     * compute q-gram similarity, Jaccard index, etc.
     * E.g. if s = ABCAB and k =2
     * This will return {AB=2, BC=1, CA=1}
     * 
     * Attention: the space requirement of a single profile can be as large as
     * k * n (where n is the size of the string)
     * Computation cost is O(n)
     * @param s
     * @return 
     */
    public int[] getProfile(String s) {
        ArrayList<Integer> r = new ArrayList<Integer>(shingles.size());
        for (int i = 0; i < shingles.size(); i++) {
            r.add(0);
        }
        
        s = spaceReg.matcher(s).replaceAll(" ");
        String shingle;
        for (int i = 0; i < (s.length() - k + 1); i++) {
            shingle = s.substring(i, i+k);
            int position;
            
            if (shingles.containsKey(shingle)) {
                position = shingles.get(shingle);
                r.set(position, r.get(position) + 1);
                
            } else {
                shingles.put(shingle, shingles.size());
                r.add(1);
            }
            
        }
        
        return convertIntegers(r);
        
        /*
        HashMap<String, Integer> r = new HashMap<String, Integer>(s.length() / 2);
        s = spaceReg.matcher(s).replaceAll(" ");
        String kgram;
        for (int i = 0; i < (s.length() - k + 1); i++) {
            kgram = s.substring(i, i+k);
            //this.add();
            
            if (r.containsKey(kgram)) {
                r.put(
                        kgram,
                        r.get(kgram) + 1);
            } else {
                r.put(kgram, 1);
            }
        }
        return r;*/
    }
 
    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
}
