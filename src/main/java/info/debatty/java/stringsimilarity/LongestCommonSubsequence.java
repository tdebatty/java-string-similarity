package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * The longest common subsequence (LCS) problem consists in finding the 
 * longest subsequence common to two (or more) sequences. It differs from 
 * problems of finding common substrings: unlike substrings, subsequences are 
 * not required to occupy consecutive positions within the original sequences.
 * 
 * It is used by the diff utility, by Git for reconciling multiple changes, etc.
 * 
 * The LCS distance between Strings X (length n) and Y (length m) is
 * n + m - 2 |LCS(X, Y)|
 * min = 0
 * max = n + m
 * 
 * LCS distance is equivalent to Levenshtein distance, when only insertion and 
 * deletion is allowed (no substitution), or when the cost of the substitution 
 * is the double of the cost of an insertion or deletion.
 * 
 * ! This class currently implements the dynamic programming approach, which
 * has a space requirement O(m * n)!
 * 
 * @author Thibault Debatty
 */
public class LongestCommonSubsequence implements StringDistance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        
        // Will produce 4.0
        System.out.println(lcs.distance("AGCAT", "GAC"));
        
        // Will produce 1.0
        System.out.println(lcs.distance("AGCAT", "AGCT"));
    }
    
    
    /**
     * Return the LCS distance between strings s1 and s2, 
     * computed as |s1| + |s2| - 2 * |LCS(s1, s2)|
     * @param s1
     * @param s2
     * @return 
     */
    public double distance(String s1, String s2) {
        return s1.length() + s2.length() - 2 * length(s1, s2);
    }
    
    /**
     * Return the length of Longest Common Subsequence (LCS) between strings s1
     * and s2.
     * @param s1
     * @param s2
     * @return 
     */
    protected int length(String s1, String s2) {
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
        char[] X = s1.toCharArray();
        char[] Y = s2.toCharArray();
        
        int[][] C = new int[m+1][n+1];
        
        for (int i = 0; i <= m; i++) {
            C[i][0] = 0;
        }
        
        for (int j = 0; j <= n; j++) {
            C[0][j] = 0;
        }
        
        for (int i = 1; i <=m ; i++) {
            for (int j = 1; j <= n; j++) {
                if (X[i-1] == Y[j-1]) {
                    C[i][j] = C[i-1][j-1] + 1;
                    
                } else {
                    C[i][j] = Math.max(C[i][j-1], C[i-1][j]);
                }
            }
        }
        
        return C[m][n];
        
    }
    
}
