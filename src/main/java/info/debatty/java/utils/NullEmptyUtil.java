package info.debatty.java.utils;

/**
 * Utility functions for handling null or empty values.
 */
public final class NullEmptyUtil {
    /**
     * Returns a normalized similarity value if either strings are empty or null.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return Returns a value if either strings are empty or null, or null if both strings have a value.
     */
    public static Double normalizedSimilarity(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return 1d;
        } else if (s1 == null || s2 == null) {
            return 0d;
        } else if (s1.equals("") && s2.equals("")) {
            return 1d;
        } else if (s1.equals("") || s2.equals("")) {
            return 0d;
        }

        return null;
    }

    /**
     * Returns a normalized distance value if either strings are empty or null.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return Returns a value if either strings are empty or null, or null if both strings have a value.
     */
    public static Double normalizedDistance(String s1, String s2) {
        Double similarity = normalizedSimilarity(s1, s2);

        return similarity == null ? null : 1d - similarity;
    }

    /**
     * Returns a non-normalized (i.e. metric) distance value if either strings are empty or null.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return Returns a value if either strings are empty or null, or null if both strings have a value.
     */
    public static Double lengthDistance(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return 0d;
        } else if (s1 == null || s1.equals("")) {
            return (double) s2.length();
        } else if (s2 == null || s2.equals("")) {
            return (double) s1.length();
        }

        return null;
    }
}
