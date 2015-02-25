package info.debatty.java.stringsimilarity;

import java.util.Arrays;

/**
 *
 * @author tibo
 */
public class JaroWinkler implements StringSimilarityInterface {
    
    
    public static void main(String[] args) {
        JaroWinkler jw = new JaroWinkler();
        
        System.out.println(jw.distance("My string", "My $tring"));
        System.out.println(jw.similarity("My string", "My $tring"));
    }
    
    /**
     * Jaro-Winkler is string edit distance that was developed in the area of 
     * record linkage (duplicate detection) (Winkler, 1990). 
     * 
     * The Jaro–Winkler distance metric is designed and best suited for short 
     * strings such as person names, and to detect typos.
     * 
     * http://en.wikipedia.org/wiki/Jaro-Winkler_distance
     * 
     * @param s0
     * @param s1
     * @return 
     */
    public static double Similarity(String s0, String s1) {
        JaroWinkler jw = new JaroWinkler();
        return jw.similarity(s0, s1);
    }

    private double threshold = 0.7;
    
    public JaroWinkler() {
        
    }
    
    public JaroWinkler(double threshold) {
        this.setThreshold(threshold);
    }

    @Override
    public double similarity(String s1, String s2) {
        int[] mtp = matches(s1, s2);
        float m = mtp[0];
        if (m == 0) {
            return 0f;
        }
        float j = ((m / s1.length() + m / s2.length() + (m - mtp[1]) / m)) / 3;
        float jw = j < getThreshold() ? j : j + Math.min(0.1f, 1f / mtp[3]) * mtp[2]
                * (1 - j);
        return jw;
    }
    
    @Override
    public double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }

    /**
     * Sets the threshold used to determine when Winkler bonus should be used.
     * Set to a negative value to get the Jaro distance.
     * Default value is 0.7
     *
     * @param threshold the new value of the threshold
     */
    public final void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    /**
     * Returns the current value of the threshold used for adding the Winkler
     * bonus. The default value is 0.7.
     *
     * @return the current value of the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    private int[] matches(String s1, String s2) {
        String max, min;
        if (s1.length() > s2.length()) {
            max = s1;
            min = s2;
        } else {
            max = s2;
            min = s1;
        }
        int range = Math.max(max.length() / 2 - 1, 0);
        int[] matchIndexes = new int[min.length()];
        Arrays.fill(matchIndexes, -1);
        boolean[] matchFlags = new boolean[max.length()];
        int matches = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            char c1 = min.charAt(mi);
            for (int xi = Math.max(mi - range, 0),
                    xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
                if (!matchFlags[xi] && c1 == max.charAt(xi)) {
                    matchIndexes[mi] = xi;
                    matchFlags[xi] = true;
                    matches++;
                    break;
                }
            }
        }
        char[] ms1 = new char[matches];
        char[] ms2 = new char[matches];
        for (int i = 0, si = 0; i < min.length(); i++) {
            if (matchIndexes[i] != -1) {
                ms1[si] = min.charAt(i);
                si++;
            }
        }
        for (int i = 0, si = 0; i < max.length(); i++) {
            if (matchFlags[i]) {
                ms2[si] = max.charAt(i);
                si++;
            }
        }
        int transpositions = 0;
        for (int mi = 0; mi < ms1.length; mi++) {
            if (ms1[mi] != ms2[mi]) {
                transpositions++;
            }
        }
        int prefix = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            if (s1.charAt(mi) == s2.charAt(mi)) {
                prefix++;
            } else {
                break;
            }
        }
        return new int[]{matches, transpositions / 2, prefix, max.length()};
    }
}
