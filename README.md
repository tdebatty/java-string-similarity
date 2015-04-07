#java-string-similarity

A library implementing different string similarity algorithms.

Currently implemeted:
- Levenshtein edit distance;
- Damerau-Levenshtein distance;
- Jaro-Winkler similarity;
- Longest Common Subsequence edit distance;
- Q-Gram (Ukkonen);
- n-Gram distance (Kondrak);
- Jaccard index;
- Sorensen-Dice coefficient;
- Cosine similarity.

## Download
Using maven:
```
<dependency>
    <groupId>info.debatty</groupId>
    <artifactId>java-string-similarity</artifactId>
    <version>RELEASE</version>
</dependency>
```

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

## Weighted Levenshtein
An implementation of Levenshtein that allows to define different weights for different character substitutions.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {

    public static void main(String[] args) {
        WeightedLevenshtein wl = new WeightedLevenshtein(
      
                new CharacterSubstitutionInterface() {
                    public double cost(char c1, char c2) {
                  
                        // t and r are next to each other,
                        // let's assign a lower cost to substitution
                        if (c1 == 't' && c2 == 'r') {
                            return 0.5;
                        }
                        
                        return 1.0;
                    }
        });
        System.out.println(wl.distanceAbsolute("String1", "Srring2"));
    }
```

## Damerau-Levenshtein
Similar to Levenshtein, Damerau-Levenshtein distance is the minimum number of operations needed to transform one string into the other, where an operation is defined as an insertion, deletion, or substitution of a single character, or a **transposition of two adjacent characters**.

This is not to be confused with the optimal string alignment distance, which is an extension where no substring can be edited more than once.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {


    public static void main(String[] args) {
        Damerau d = new Damerau();
        
        // One substitution
        System.out.println(d.absoluteDistance("ABCDEF", "ABDCEF"));

        // Substitution of 2 characters that are far from each other
        // => 1 deletion + 1 insertion
        System.out.println(d.absoluteDistance("ABCDEF", "BCDAEF"));

        // distance and similarity allways produce a result between 0 and 1
        System.out.println(d.distance("ABCDEF", "GHABCDE"));
    }
}
```

Will produce:

```
1
2
0.23076923076923078
```



## Jaro-Winkler
Jaro-Winkler is a string edit distance that was developed in the area of record linkage (duplicate detection) (Winkler, 1990). The Jaro–Winkler distance metric is designed and best suited for short strings such as person names, and to detect typos.

It is (roughly) a variation of Levenshtein distance, where the substitution of 2 close characters is considered less important then the substitution of 2 characters that a far from each other.

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

## Q-Gram

A-gram similarity and distance, as defined by Ukkonen in "Approximate string-matching with q-grams and maximal matches"
http://www.sciencedirect.com/science/article/pii/0304397592901434

The distance between two strings is defined as the L1 norm of the difference of their profiles (the number of occurences of each k-shingle). Q-gram distance is a lower bound on Levenshtein distance, but can be computed in O(|A| + |B|), where Levenshtein requires O(|A|.|B|)

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    
    public static void main(String[] args) {
        QGram dig = new QGram(2);

        // AB BC CD CE
        // 1  1  1  0
        // 1  1  0  1
        // Total: 2
        System.out.println(dig.absoluteDistance("ABCD", "ABCE"));

        // 2 / (3 + 3) = 0.33333
        System.out.println(dig.distance("ABCD", "ABCE"));
    }
}
```

## N-Gram similarity (Kondrak)

N-Gram Similarity as defined by Kondrak, "N-Gram Similarity and Distance", String Processing and Information Retrieval, Lecture Notes in Computer Science Volume 3772, 2005, pp 115-126.

http://webdocs.cs.ualberta.ca/~kondrak/papers/spire05.pdf

The algorithm uses affixing with special character '\n' two increase the weight of first characters. The normalization is achieved by dividing the total similarity score the original length of the longer word.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {

    public static void main(String[] args) {
        NGram twogram = new NGram(2);

        // Should be 0.41666
        System.out.println(twogram.distance("ABCD", "ABTUIO"));
    }
}
```



