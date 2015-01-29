package info.debatty.stringsimilarity;

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
 * @author tibo
 */
public class LongestCommonSubsequence implements StringSimilarityInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        
        System.out.println(lcs.length("AGCAT", "GAC"));
        System.out.println(lcs.distanceAbsolute("AGCAT", "GAC"));
        System.out.println(lcs.distance("AGCAT", "GAC"));
    }
    
    public static int Distance(String s1, String s2) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        return lcs.distanceAbsolute(s1, s2);
    }

    @Override
    public double similarity(String s1, String s2) {
        return 1.0 - distance(s1, s2);
    }

    @Override
    public double distance(String s1, String s2) {
        return ((double) distanceAbsolute(s1, s2)) / (s1.length() + s2.length());
    }
    
    
    public int distanceAbsolute(String s1, String s2) {
        return s1.length() + s2.length() - 2 * length(s1, s2);
    }
    
    public int length(String s1, String s2) {
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
