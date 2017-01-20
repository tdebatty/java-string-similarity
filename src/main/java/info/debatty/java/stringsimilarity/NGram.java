package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import net.jcip.annotations.Immutable;

/**
 * N-Gram Similarity as defined by Kondrak, "N-Gram Similarity and Distance",
 * String Processing and Information Retrieval, Lecture Notes in Computer
 * Science Volume 3772, 2005, pp 115-126.
 *
 * The algorithm uses affixing with special character '\n' to increase the
 * weight of first characters. The normalization is achieved by dividing the
 * total similarity score the original length of the longest word.
 *
 * http://webdocs.cs.ualberta.ca/~kondrak/papers/spire05.pdf
 */
@Immutable
public class NGram implements NormalizedStringDistance {

    private static final int DEFAULT_N = 2;
    private final int n;

    /**
     * Instantiate with given value for n-gram length.
     * @param n
     */
    public NGram(final int n) {
        this.n = n;
    }

    /**
     * Instantiate with default value for n-gram length (2).
     */
    public NGram() {
        this.n = DEFAULT_N;
    }

    /**
     * Compute n-gram distance.
     * @param s0 The first string to compare.
     * @param s1 The second string to compare.
     * @return The computed n-gram distance in the range [0, 1]
     * @throws NullPointerException if s0 or s1 is null.
     */
    public final double distance(final String s0, final String s1) {
        if (s0 == null) {
            throw new NullPointerException("s0 must not be null");
        }

        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s0.equals(s1)) {
            return 0;
        }

        final char special = '\n';
        final int sl = s0.length();
        final int tl = s1.length();

        if (sl == 0 || tl == 0) {
            return 1;
        }

        int cost = 0;
        if (sl < n || tl < n) {
            for (int i = 0, ni = Math.min(sl, tl); i < ni; i++) {
                if (s0.charAt(i) == s1.charAt(i)) {
                    cost++;
                }
            }
            return (float) cost / Math.max(sl, tl);
        }

        char[] sa = new char[sl + n - 1];
        float[] p; //'previous' cost array, horizontally
        float[] d; // cost array, horizontally
        float[] d2; //placeholder to assist in swapping p and d

        //construct sa with prefix
        for (int i = 0; i < sa.length; i++) {
            if (i < n - 1) {
                sa[i] = special; //add prefix
            } else {
                sa[i] = s0.charAt(i - n + 1);
            }
        }
        p = new float[sl + 1];
        d = new float[sl + 1];

        // indexes into strings s and t
        int i; // iterates through source
        int j; // iterates through target

        char[] t_j = new char[n]; // jth n-gram of t

        for (i = 0; i <= sl; i++) {
            p[i] = i;
        }

        for (j = 1; j <= tl; j++) {
            //construct t_j n-gram
            if (j < n) {
                for (int ti = 0; ti < n - j; ti++) {
                    t_j[ti] = special; //add prefix
                }
                for (int ti = n - j; ti < n; ti++) {
                    t_j[ti] = s1.charAt(ti - (n - j));
                }
            } else {
                t_j = s1.substring(j - n, j).toCharArray();
            }
            d[0] = j;
            for (i = 1; i <= sl; i++) {
                cost = 0;
                int tn = n;
                //compare sa to t_j
                for (int ni = 0; ni < n; ni++) {
                    if (sa[i - 1 + ni] != t_j[ni]) {
                        cost++;
                    } else if (sa[i - 1 + ni] == special) {
                        //discount matches on prefix
                        tn--;
                    }
                }
                float ec = (float) cost / tn;
                // minimum of cell to the left+1, to the top+1,
                // diagonally left and up +cost
                d[i] = Math.min(
                        Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + ec);
            }
            // copy current distance counts to 'previous row' distance counts
            d2 = p;
            p = d;
            d = d2;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[sl] / Math.max(tl, sl);
    }
}
