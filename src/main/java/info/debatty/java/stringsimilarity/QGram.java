package info.debatty.java.stringsimilarity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Thibault Debatty
 */
public class QGram implements StringSimilarityInterface {
    
    public static void main(String[] args) {
        QGram dig = new QGram(2);
        
        // AB BC CD CE
        // 1  1  1  0
        // 1  1  0  1
        // Total: 2
        System.out.println(dig.absoluteDistance("ABCD", "ABCE"));
        
        // 2 / (3 + 3) = 0.33333
        System.out.println(dig.distance("ABCD", "ABCE"));
        
        System.out.println(dig.similarity("", "QSDFGHJKLM"));
        
        System.out.println(dig.similarity(
                "High Qua1ityMedications   Discount On All Reorders = Best Deal Ever! Viagra50/100mg - $1.85 071",
                "High Qua1ityMedications   Discount On All Reorders = Best Deal Ever! Viagra50/100mg - $1.85 7z3"));
    }
    
    private final int k;
    
    
    /**
     * Q-gram similarity and distance.
     * Defined by Ukkonen in "Approximate string-matching with q-grams and maximal
     * matches", http://www.sciencedirect.com/science/article/pii/0304397592901434
     * The distance between two strings is defined as the L1 norm of the difference 
     * of their profiles (the number of occurences of each k-shingle).
     * Q-gram distance is a lower bound on Levenshtein distance, but can be computed
     * in O(|A| + |B|), where Levenshtein requires O(|A|.|B|)
     * 
     * @param n 
     */
    public QGram(int n) {
        this.k = n;
    }
    
    public QGram() {
        this.k = 3;
    }
    
    @Override
    public double similarity(String s1, String s2) {
        return 1.0 - distance(s1, s2);
    }

    @Override
    public double distance(String s1, String s2) {
        return dist(s1, s2, false);
    }
    
    public int absoluteDistance(String s1, String s2) {
        return (int) dist(s1, s2, true);
    }
    
    protected double dist(String s1, String s2, boolean abs) {
        if (s1.length() < k || s2.length() < k) {
            return 1;
        }
        
        KShingling sh = new KShingling(k);
        HashMap<String, Integer> profile1 = sh.getProfile(s1);
        HashMap<String, Integer> profile2 = sh.getProfile(s2);
        
        Set<String> union = new HashSet<String>();
        union.addAll(profile1.keySet());
        union.addAll(profile2.keySet());
        
        int d = 0;
        for (String key : union) {
            int v1 = profile1.containsKey(key) ? profile1.get(key) : 0;
            int v2 = profile2.containsKey(key) ? profile2.get(key) : 0;
            d += Math.abs(v1 - v2);
        }
        
        if (abs) {
            return d;
        }
        
        int sum = 0;
        for (Entry<String, Integer> e : profile1.entrySet()) {
            sum += e.getValue();
        }
        for (Entry<String, Integer> e : profile2.entrySet()) {
            sum += e.getValue();
        }
        
        return (double) d / sum;
    }
    
}
