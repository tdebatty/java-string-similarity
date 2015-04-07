package info.debatty.java.stringsimilarity;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.Set;

/**
 * A k-shingling is a set of unique k-grams, used to measure the similarity of 
 * two documents.
 * 
 * Generally speaking, a k-gram is any sequence of k tokens. We use here the 
 * definition from  Leskovec, Rajaraman & Ullman (2014),
 * "Mining of Massive Datasets", Cambridge University Press:
 * Multiple subsequent spaces are replaced by a single space, and a k-gram is a 
 * sequence of k characters.
 * 
 * @author Thibault Debatty http://www.debatty.info
 */
public class KShingling extends HashSet<String> implements Serializable {
    

    public static void main(String[] args) {
        String s1 = "my string,  \n  my song";
        String s2 = "another string, from a song";
        KShingling ks = new KShingling(4);
        ks.parse(s1);
        ks.parse(s2);
        System.out.println(ks.toString());
        
        printArray(ks.booleanVectorOf(s1));
        printArray(ks.booleanVectorOf(s2));
        printArray(ks.profileOf(s1));
        
        ks.add("This should trigger an exception!");
    }
    
    public static int countOccurences(String substring, String str){
        return (str.length() - str.replace(substring, "").length()) / substring.length();
    }
    
    public static void printArray(boolean[] a) {
        
        System.out.print("[");
        for (boolean b : a) {
            System.out.print(b ? "1" : "0");
        }
        System.out.println("]");
    }
    
    public static void printArray(int[] a) {
        
        System.out.print("[");
        for (int i : a) {
            System.out.print("" + i + "\t");
        }
        System.out.println("]");
    }
    
    protected int k = 5;
    
    public KShingling() {
        super();
    }
    
    public KShingling(int k) {
        super();
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
     * Extract all k-singles from sting s and add them to the list of possible 
     * shingles
     * @param s
     * @return true
     */
    public boolean parse(String s) {
        s = spaceReg.matcher(s).replaceAll(" ");
        for (int i = 0; i < (s.length() - k + 1); i++) {
            this.add(s.substring(i, i+k));
        }
        return true;
    }
    
    /**
     * Add a k-shingle s to the list of possible shingles
     * @param s
     * @return 
     */
    @Override
    public boolean add(String s) {
        if (s.length() != k) {
            throw  new InvalidParameterException("This size of this String (" +
                    s.length() + ") is different from k (" + k + ")");
        }
        
        return super.add(s);
    }
    
    /**
     * Compute and return the boolean vector representation of string s.
     * E.g. if this set contains the shingles [AB, BC, CD, DE]
     * and s is ABCD
     * This will return [true, true, true, false]
     * @param s
     * @return 
     */
    public boolean[] booleanVectorOf(String s) {
        boolean[] r = new boolean[this.size()];
        
        int i = 0;
        for (String shingle : this) {
            r[i] = s.contains(shingle);            
            i++;
        }
        
        return r;
    }
    
    /**
     * Compute the boolean representation of string s, returned as a set of 
     * position integers.
     * E.g. if this set contains the shingles [AB, BC, CD, DE]
     * and s is ABCD
     * This will return (0, 1, 2)
     * @param s
     * @return 
     */
    public Set<Integer> integerSetOf(String s) {
        Set<Integer> set = new HashSet<Integer>();
        int i = 0;
        for (String shingle : this) {
            if (s.contains(shingle)) {
                set.add(i);
            }
            i++;
        }
        
        return set;
    }
    
    /**
     * Compute and return the profile of s, as defined by Ukkonen "Approximate
     * string-matching with q-grams and maximal matches".
     * https://www.cs.helsinki.fi/u/ukkonen/TCS92.pdf
     * The profile is the number of occurences of k-shingles, and is used to 
     * compute q-gram similarity.
     * E.g. if this set contains the shingles [AB, BC, CD, DE]
     * and s is ABCDAB
     * This will return [2, 1, 1, 0]
     * @param s
     * @return 
     */
    public int[] profileOf(String s) {
        int[] p = new int[this.size()];
        int i = 0;
        for (String shingle : this) {
            p[i] = countOccurences(shingle, s);
            i++;
        }
        
        return p;
    }
    
}
