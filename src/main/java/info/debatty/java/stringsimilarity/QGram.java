package info.debatty.java.stringsimilarity;

/**
 * QGram similarity is the relative number of n-grams both strings have in 
 * common. It is thus the Jaccard index between strings, considered as sets 
 * of n-grams. The computed similarity and distance are relative value (between 
 * 0 and 1).
 * 
 * @author Thibault Debatty
 */
public class QGram implements StringSimilarityInterface {
    
    public static void main(String[] args) {
        QGram dig = new QGram(2);
        
        // Should be 2: CD and CE
        System.out.println(dig.absoluteDistance("ABCD", "ABCE"));
        
        // Should be 0.5 (2 / 4)
        System.out.println(dig.distance("ABCD", "ABCE"));
        
        // AB BC CD DE BX XB CE
        // 2 / 7
        System.out.println(dig.similarity("ABCDE", "ABXBCE"));
        
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
        if (s1.length() < n || s2.length() < n) {
            return 0;
        }
        
        KShingling sh = new KShingling(n);
        sh.parse(s1);
        sh.parse(s2);
        
        boolean[] b1 = sh.booleanVectorOf(s1);
        boolean[] b2 = sh.booleanVectorOf(s2);
        
        int d = 0;
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                d++;
            }
        }
        
        return ((double) d) / sh.size();
    }
    
    public int absoluteDistance(String s1, String s2) {
        KShingling sh = new KShingling(n);
        sh.parse(s1);
        sh.parse(s2);
        
        boolean[] b1 = sh.booleanVectorOf(s1);
        boolean[] b2 = sh.booleanVectorOf(s2);
        
        int d = 0;
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                d++;
            }
        }
        
        return d;
    }
    
}
