package info.debatty.java.stringsimilarity;

import java.security.InvalidParameterException;
import java.util.HashMap;
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

    protected int k = 5;
    
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
     */
    public KShingling() {
        
    }
    
    public KShingling(int k) {
        this.setK(k);
    }
    
    public int getK() {
        return k;
    }
    
    /**
     * Set the size of k-grams.
     * Default value is 5 (recommended for emails).
     * A good rule of thumb is to imagine that there are only 20 characters 
     * and estimate the number of k-shingles as 20^k. For large documents, 
     * such as research articles, choice k = 9 is considered safe.
     * @param k 
     */
    public final void setK(int k) {
        if (k <= 0) {
            throw new InvalidParameterException("k should be positive!");
        }
        
        this.k = k;
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
    public HashMap<String, Integer> getProfile(String s) {
        HashMap<String, Integer> r = new HashMap<String, Integer>();
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
        return r;
    }
}
