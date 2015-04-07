package info.debatty.java.stringsimilarity;

/**
 * Q-gram similarity and distance.
 * Defined by Ukkonen in "Approximate string-matching with q-grams and maximal
 * matches", http://www.sciencedirect.com/science/article/pii/0304397592901434
 * The distance between two strings is defined as the L1 norm of the difference 
 * of their profiles (the number of occurences of each k-shingle).
 * Q-gram distance is a lower bound on Levenshtein distance, but can be computed
 * in O(|A| + |B|), where Levenshtein requires O(|A|.|B|)
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
        
        System.out.println(dig.similarity(
                "High Qua1ityMedications   Discount On All Reorders = Best Deal Ever! Viagra50/100mg - $1.85 071",
                "High Qua1ityMedications   Discount On All Reorders = Best Deal Ever! Viagra50/100mg - $1.85 7z3"));
    }
    
    private int n;

    public QGram(int n) {
        this.n = n;
    }
    
    public QGram() {
        this.n = 3;
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
        if (s1.length() < n || s2.length() < n) {
            return 0;
        }
        
        KShingling sh = new KShingling(n);
        sh.parse(s1);
        sh.parse(s2);
        
        int[] p1 = sh.profileOf(s1);
        int[] p2 = sh.profileOf(s2);
               
        
        int d = 0;
        for (int i = 0; i < p1.length; i++) {
            d += Math.abs(p1[i] - p2[i]);
        }
        
        if (abs) {
            return d;
        }
        
        int sum = 0;
        for (int i : p1) {
            sum += i;
        }
        for (int i : p2) {
            sum += i;
        }
        
        return (double) d / sum;
    }
    
}
