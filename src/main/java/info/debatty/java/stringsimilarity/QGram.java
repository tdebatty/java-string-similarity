package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * Q-gram distance, as defined by Ukkonen in "Approximate string-matching with
 * q-grams and maximal matches". The distance between two strings is defined as
 * the L1 norm of the difference of their profiles (the number of occurences of
 * each n-gram): SUM( |V1_i - V2_i| ). Q-gram distance is a lower bound on
 * Levenshtein distance, but can be computed in O(m + n), where Levenshtein
 * requires O(m.n).
 *
 * @author Thibault Debatty
 */
public class QGram extends ShingleBased implements StringDistance {

    /**
     * Q-gram similarity and distance. Defined by Ukkonen in "Approximate
     * string-matching with q-grams and maximal matches",
     * http://www.sciencedirect.com/science/article/pii/0304397592901434 The
     * distance between two strings is defined as the L1 norm of the difference
     * of their profiles (the number of occurence of each k-shingle). Q-gram
     * distance is a lower bound on Levenshtein distance, but can be computed in
     * O(|A| + |B|), where Levenshtein requires O(|A|.|B|)
     *
     * @param n
     */
    public QGram(int n) {
        super(n);
    }

    public QGram() {
        super();
    }

    public double distance(String s1, String s2) {
        KShingling ks = new KShingling(k);
        int[] profile1 = ks.getArrayProfile(s1);
        int[] profile2 = ks.getArrayProfile(s2);
        int length = Math.max(profile1.length, profile2.length);

        profile1 = java.util.Arrays.copyOf(profile1, length);
        profile2 = java.util.Arrays.copyOf(profile2, length);

        int d = 0;
        for (int i = 0; i < length; i++) {
            d += Math.abs(profile1[i] - profile2[i]);
        }

        return d;
    }
}
