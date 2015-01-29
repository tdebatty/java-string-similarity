
package info.debatty.stringsimilarity;

/**
 *
 * @author tibo
 */
public interface StringSimilarityInterface {
    /**
     * 
     * @param s1
     * @param s2
     * @return similarity between 0 (completely different) and 1 (s1 = s2)
     */
    public double similarity(String s1, String s2);
    
    /**
     * Generally, distance = 1 - similarity.
     * Some implementations can also provide a method distanceAbsolute
     * @param s1
     * @param s2
     * @return distance between 0 (s1 = s2) and 1 (completely different)
     */
    public double distance(String s1, String s2);
}
