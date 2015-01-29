package info.debatty.stringsimilarity;

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
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.similarity("My string", "My $tring"));
    }

    public static int Distance(String s1, String s2) {
        Levenshtein l = new Levenshtein();
        return l.distanceAbsolute(s1, s2);
    }

    @Override
    public double distance(String s1, String s2) {
        return ((double) distanceAbsolute(s1, s2)) / Math.max(s1.length(), s2.length());
        
    }
    
    @Override
    public double similarity(String s1, String s2) {
        return 1.0 - distance(s1, s2);
    }
    
    /**
     * The Levenshtein distance, or edit distance, between two words is the 
     * minimum number of single-character edits (i.e. insertions, deletions or 
     * substitutions) required to change one word into the other. 
     * 
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     * 
     * It is always at least the difference of the sizes of the two strings.
     * It is at most the length of the longer string.
     * It is zero if and only if the strings are equal.
     * If the strings are the same size, the Hamming distance is an upper bound 
     * on the Levenshtein distance.
     * The Levenshtein distance between two strings is no greater than the sum 
     * of their Levenshtein distances from a third string (triangle inequality).
     * 
     * @param s0
     * @param s1
     * @return 
     */
    public int distanceAbsolute(String s0, String s1) {
        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

	// dynamicaly computing the array of distances
        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {

            // initial cost of skipping prefix in String s1
            newcost[0] = j - 1;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {

                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(
                        Math.min(cost_insert, cost_delete),
                        cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }
}
