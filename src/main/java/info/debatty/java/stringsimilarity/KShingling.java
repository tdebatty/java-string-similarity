package info.debatty.java.stringsimilarity;

import info.debatty.java.utils.SparseBooleanVector;
import info.debatty.java.utils.SparseIntegerVector;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    
    protected int k;
    private final HashMap<String, Integer> shingles = new HashMap<String, Integer>();
    
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
     * The KShingling object will store the dictionary of n-grams:
     * {AB BC CA}
     * and the profile will be
     * [2  1  1]
     * 
     * Attention: the space requirement of a single profile can be as large as
     * 20^k x 4Bytes (sizeof(int))
     * Computation cost is O(n)
     * @param s
     * @return the profile of this string as an array of integers
     */
    protected int[] getArrayProfile(String s) {
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
    }

    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
    
    /**
     * Compute and returns the profile of string s
     * The profiles of different strings can be used to compute cosine similarity
     * or qgram distance.
     * 
     * @param s
     * @return the profile of this string, as a StringProfile object
     */
    public StringProfile getProfile(String s) {
        
        HashMap<Integer, Integer> hash_profile = getHashProfile(s);
        
        // Convert hashmap to sparsearray
        return new StringProfile(new SparseIntegerVector(hash_profile), this);
    }
    
    public StringSet getSet(String s) {
        HashMap<Integer, Integer> hash_profile = getHashProfile(s);
        
        // Convert hashmap to sparsearray
        return new StringSet(new SparseBooleanVector(hash_profile), this);
    }
    
    /**
     * Return the number of different n-grams (k-shingles) found by this 
     * k-shingling instance.
     * @return the total number of different n-grams found by this k-shingling instance
     */
    public int getDimension() {
        return this.shingles.size();
    }

    private HashMap<Integer, Integer> getHashProfile(String s) {
        HashMap<Integer, Integer> hash_profile = new HashMap<Integer, Integer>(s.length());
        
        s = spaceReg.matcher(s).replaceAll(" ");
        String shingle;
        for (int i = 0; i < (s.length() - k + 1); i++) {
            shingle = s.substring(i, i+k);
            int position;
            
            if (shingles.containsKey(shingle)) {
                position = shingles.get(shingle);
                
            } else {
                position = shingles.size();
                shingles.put(shingle, shingles.size());
                
            }
            
            if (hash_profile.containsKey(position)) {
                hash_profile.put(position, hash_profile.get(position) + 1);
                
            } else {
                hash_profile.put(position, 1);
            }
        }
        
        return hash_profile;
    }

    String getNGram(int key) {
        for (Map.Entry<String, Integer> entry : shingles.entrySet()) {
            if (entry.getValue().equals(key)) {
                return entry.getKey();
            }
        }
        
        throw new InvalidParameterException("No ngram coresponds to key " + key);
    }
}
