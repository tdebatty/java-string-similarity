package info.debatty.java.stringsimilarity;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
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
        
        for (boolean b : ks.booleanVectorOf(s1)) {
            System.out.print(b ? "1" : "0");
        }
        System.out.print("\n");
        
        for (boolean b : ks.booleanVectorOf(s2)) {
            System.out.print(b ? "1" : "0");
        }
        System.out.print("\n");
        
        ks.add("This should trigger an exception!");
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
    
    public boolean parse(String s) {
        s = s.replaceAll("\\s+", " ");
        for (int i = 0; i < (s.length() - k + 1); i++) {
            this.add(s.substring(i, i+k));
        }
        return true;
    }
    
    @Override
    public boolean add(String s) {
        if (s.length() != k) {
            throw  new InvalidParameterException("This size of this String (" +
                    s.length() + ") is different from k (" + k + ")");
        }
        
        return super.add(s);
    }
    
    public boolean[] booleanVectorOf(String s) {
        boolean[] r = new boolean[this.size()];
        
        int i = 0;
        for (String shingle : this) {
            r[i] = s.contains(shingle);            
            i++;
        }
        
        return r;
    }
    
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
    
}
