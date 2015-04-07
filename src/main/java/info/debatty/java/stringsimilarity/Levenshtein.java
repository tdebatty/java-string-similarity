package info.debatty.java.stringsimilarity;

/**
 * The Levenshtein distance between two words is the minimum number of 
 * single-character edits (insertions, deletions or substitutions) required to 
 * change one word into the other.
 * 
 * @author Thibault Debatty
 */
public class Levenshtein implements StringSimilarityInterface {
    
    public static void main (String[] args) {
        Levenshtein l = new Levenshtein();
        
        System.out.println(l.distanceAbsolute("My string", "My $tring"));
        System.out.println(l.distanceAbsolute("My string", "M string2"));
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.similarity("My string", "My $tring"));
    }

    public static int Distance(String s1, String s2) {
        Levenshtein l = new Levenshtein();
        return l.distanceAbsolute(s1, s2);
    }

    @Override
    public double distance(String s1, String s2) {
        return (double) distanceAbsolute(s1, s2) / Math.max(s1.length(), s2.length());
        
    }
    
    @Override
    public double similarity(String s1, String s2) {
        return 1.0 - distance(s1, s2);
    }
    
    /**
     * The Levenshtein distance, or edit distance, between two words is the 
     * minimum number of single-character edits (insertions, deletions or 
     * substitutions) required to change one word into the other. 
     * 
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     * 
     * It is always at least the difference of the sizes of the two strings.
     * It is at most the length of the longer string.
     * It is zero if and only if the strings are equal.
     * If the strings are the same size, the Hamming distance is an upper bound 
     * on the Levenshtein distance.
     * The Levenshtein distance verifies the triangle inequality (the distance 
     * between two strings is no greater than the sum Levenshtein distances from
     * a third string).
     * 
     * Implementation uses dynamic programming (Wagner–Fischer algorithm), with
     * only 2 rows of data. The space requirement is thus O(m) and the algorithm
     * runs in O(mn).
     * 
     * @param s1
     * @param s2
     * @return 
     */
    public int distanceAbsolute(String s1, String s2) {
        if (s1.equals(s2)){
            return 0;
        }
        
        if (s1.length() == 0) {
            return s2.length();
        }
        
        if (s2.length() == 0) {
            return s1.length();
        }

        // create two work vectors of integer distances
        int[] v0 = new int[s2.length() + 1];
        int[] v1 = new int[s2.length() + 1];
        int[] vtemp;

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s
        // the distance is just the number of characters to delete from t
        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }
        
        for (int i = 0; i < s1.length(); i++) {
            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            //   edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            // use formula to fill in the rest of the row
            for (int j = 0; j < s2.length(); j++) {
                int cost = (s1.charAt(i) == s2.charAt(j)) ? 0 : 1;
                v1[j + 1] = Math.min(
                        v1[j] + 1,              // Cost of insertion
                        Math.min(
                                v0[j + 1] + 1,  // Cost of remove
                                v0[j] + cost)); // Cost of substitution
            }
            
            // copy v1 (current row) to v0 (previous row) for next iteration
            //System.arraycopy(v1, 0, v0, 0, v0.length);
            
            // Flip references to current and previous row
            vtemp = v0;
            v0 = v1;
            v1 = vtemp;
                
        }

        return v0[s2.length()];
    }
}
