#java-string-similarity

A library implementing different string similarity algorithms.

Currently implemeted:
- Levenshtein edit distance;
- Jaro-Winkler similarity;
- Longest Common Subsequence edit distance;
- n-Gram distance.

## Download
See [releases](https://github.com/tdebatty/java-string-similarity/releases).

## Levenshtein
The Levenshtein distance between two words is the minimum number of single-character edits (insertions, deletions or substitutions) required to change one word into the other.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    
    public static void main (String[] args) {
        Levenshtein l = new Levenshtein();
        
        System.out.println(l.distanceAbsolute("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.similarity("My string", "My $tring"));
    }
}
```

## Jaro-Winkler
Jaro-Winkler is a string edit distance that was developed in the area of record linkage (duplicate detection) (Winkler, 1990). The Jaro–Winkler distance metric is designed and best suited for short strings such as person names, and to detect typos.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    
    
    public static void main(String[] args) {
        JaroWinkler jw = new JaroWinkler();
        
        System.out.println(jw.distance("My string", "My $tring"));
        System.out.println(jw.similarity("My string", "My $tring"));
    }
}
```


## Longest Common Subsequence

The longest common subsequence (LCS) problem consists in finding the longest subsequence common to two (or more) sequences. It differs from problems of finding common substrings: unlike substrings, subsequences are not required to occupy consecutive positions within the original sequences.

It is used by the diff utility, by Git for reconciling multiple changes, etc.

The LCS distance between Strings X (length n) and Y (length m) is n + m - 2 |LCS(X, Y)|
min = 0
max = n + m

LCS distance is equivalent to Levenshtein distance, when only insertion and deletion is allowed (no substitution), or when the cost of the substitution is the double of the cost of an insertion or deletion.

This class currently implements the dynamic programming approach, which has a space requirement O(m * n)

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        
        System.out.println(lcs.length("AGCAT", "GAC"));
        System.out.println(lcs.distanceAbsolute("AGCAT", "GAC"));
        System.out.println(lcs.distance("AGCAT", "GAC"));
    }
}
```



