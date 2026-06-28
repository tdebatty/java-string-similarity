package info.debatty.java.stringsimilarity;

/**
 * Shared validation helpers for all similarity/distance methods.
 */


public class StringUtils {
    private StringUtils() {}  // utility class — no instantiation


    static void requireNonNull(String s1, String s2) {
        if (s1 == null) throw new NullPointerException("s1 must not be null");
        if (s2 == null) throw new NullPointerException("s2 must not be null");
    }

}
