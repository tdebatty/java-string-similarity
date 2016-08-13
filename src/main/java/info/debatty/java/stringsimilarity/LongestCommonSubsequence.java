package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;
import net.jcip.annotations.Immutable;

/**
 * The longest common subsequence (LCS) problem consists in finding the longest
 * subsequence common to two (or more) sequences. It differs from problems of
 * finding common substrings: unlike substrings, subsequences are not required
 * to occupy consecutive positions within the original sequences.
 *
 * It is used by the diff utility, by Git for reconciling multiple changes, etc.
 *
 * The LCS distance between Strings X (length n) and Y (length m) is n + m - 2
 * |LCS(X, Y)| min = 0 max = n + m
 *
 * LCS distance is equivalent to Levenshtein distance, when only insertion and
 * deletion is allowed (no substitution), or when the cost of the substitution
 * is the double of the cost of an insertion or deletion.
 *
 * ! This class currently implements the dynamic programming approach, which has
 * a space requirement O(m * n)!
 *
 * @author Thibault Debatty
 */
@Immutable
public class LongestCommonSubsequence implements StringDistance {

    /**
     * Return the LCS distance between strings s1 and s2, computed as |s1| +
     * |s2| - 2 * |LCS(s1, s2)|.
     *
     * @param s1
     * @param s2
     * @return the LCS distance between strings s1 and s2, computed as |s1| +
     * |s2| - 2 * |LCS(s1, s2)|
     */
    public final double distance(final String s1, final String s2) {
        return s1.length() + s2.length() - 2 * length(s1, s2);
    }

    /**
     * Return the length of Longest Common Subsequence (LCS) between strings s1
     * and s2.
     *
     * @param s1
     * @param s2
     * @return the length of LCS(s1, s2)
     */
    public final int length(final String s1, final String s2) {
        /* function LCSLength(X[1..m], Y[1..n])
         C = array(0..m, 0..n)

         for i := 0..m
         C[i,0] = 0

         for j := 0..n
         C[0,j] = 0

         for i := 1..m
         for j := 1..n
         if X[i] = Y[j]
         C[i,j] := C[i-1,j-1] + 1
         else
         C[i,j] := max(C[i,j-1], C[i-1,j])
         return C[m,n]
         */
        int m = s1.length();
        int n = s2.length();
        char[] x = s1.toCharArray();
        char[] y = s2.toCharArray();

        int[][] c = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            c[i][0] = 0;
        }

        for (int j = 0; j <= n; j++) {
            c[0][j] = 0;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x[i - 1] == y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;

                } else {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }

        return c[m][n];
    }
}
