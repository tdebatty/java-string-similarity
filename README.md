#java-string-similarity
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-string-similarity/badge.svg)](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-string-similarity) [![Build Status](https://travis-ci.org/tdebatty/java-string-similarity.svg?branch=master)](https://travis-ci.org/tdebatty/java-string-similarity) [![API Documentation](http://api123.web-d.be/api123-head.svg)](http://api123.web-d.be/api/java-string-similarity/head/index.html)

A library implementing different string similarity and distance algorithms.

Currently implemented:
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

Or check the [releases](https://github.com/tdebatty/java-string-similarity/releases).

## Interfaces
Although the topic might seem simple, a lot of different algorithms exist to measure text similarity or distance. Therefore the library defines some interfaces to categorize them.

### (Normalized) similarity and distance

- StringSimilarity : Implementing algorithms define a similarity between strings (0 means strings are completely different).
- NormalizedStringSimilarity : Implementing algorithms define a similarity between 0.0 and 1.0, like Jaro-Winkler for example.
- StringDistance : Implementing algorithms define a distance between strings (0 means strings are identical), like Levenshtein for example. The maximum distance value depends on the algorithm.
- NormalizedStringDistance : This interface extends StringDistance. For implementing classes, the computed distance value is between 0.0 and 1.0. NormalizedLevenshtein is an example of NormalizedLevenshtein.

Generally, algorithms that implement NormalizedStringSimilarity also implement NormalizedStringDistance, and similarity = 1 - distance. But there are a few exceptions, like N-Gram similarity and distance (Kondrak)...

### Metric distances
The MetricStringDistance interface : A few of the distances are actually metric distances, which means that verify the triangle inequality d(x, y) <= d(x,z) + d(z,y). For example, Levenshtein is a metric distance, but NormalizedLevenshtein is not.

[Read Javadoc for details](http://api123.web-d.be/api/java-string-similarity/head/index.html)

## Shingles (n-gram) based similarity and distance
A few algorithms work by converting strings into sets of n-grams (sequences of n characters, also sometimes called k-shingles). The similarity or distance between the strings is then the similarity or distance between the sets.

Some ot them, like jaccard, consider strings as sets of shingles, and don't consider the number of occurences of each shingle. Others, like cosine similarity, work using what is sometimes called the profile of the strings, which takes into account the number of occurences of each shingle.

For these algorithms, another use case is possible when dealing with large datasets:
1. compute the set or profile representation of all the strings
2. compute the similarity between sets or profiles

## Summary

The main characteristics of each implemented algorithm are presented below. The "cost" column givs an estimation of the computational cost to compute te similarity between two strings of length m and n respectively.

|  									|  						| Normalized? 	| Metric?	| Type    | Cost |
|--------							|-------				|-------------	|----------	| ------  | ---- |
| Levenshtein 						|distance 				| No 			| Yes 		|         | O(m.n) |
| Normalized Levenshtein 			|distance<br>similarity	| Yes 			| No 		| 	      | O(m.n) |
| Weighted Levenshtein 				|distance 				| No 			| No 		| 	      | O(m.n) |
| Damerau-Levenshtein 				|distance 				| No 			| No 		| 	      | O(m.n) |
| Jaro-Winkler 						|similarity<br>distance	| Yes  			| No 		| 	      | O(m.n) |
| Longest Common Subsequence 		|distance 				| No 			| No 		| 	      | O(m.n)* |
| Metric Longest Common Subsequence |distance   			| Yes 			| Yes  		| 	      | O(m.n)* |
| N-Gram (Kondrak)		 			|distance				| Yes  			| No 		| 	      | O(m.n) |
| Q-Gram 							|distance  			 	| No  			| No 		| Profile | O(m+n) |
| Cosine 							|similarity<br>distance | Yes  			| No  		| Profile | O(m+n) |
| Jaccard 							|similarity<br>distance | Yes  			| Yes  		| Set	  | O(m+n) |
| Sorensen-Dice 					|similarity<br>distance | Yes 			| No 		| Set	  | O(m+n) |

\* In "Length of Maximal Common Subsequences", K.S. Larsen proposed an algorithm that computes the length of LCS in time O(log(m).log(n)). But the algorithm has a memory requirement O(m.n²) and was thus not implemented here.

## Levenshtein
The Levenshtein distance between two words is the minimum number of single-character edits (insertions, deletions or substitutions) required to change one word into the other.

It is a metric string distance. This implementation uses dynamic programming (Wagner–Fischer algorithm), with only 2 rows of data. The space requirement is thus O(m) and the algorithm runs in O(m.n).

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    
    public static void main (String[] args) {
        Levenshtein l = new Levenshtein();

        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
    }
}
```

## Normalized Levenshtein
This distance is computed as levenshtein distance divided by the length of the longest string. The resulting value is always in the interval [0.0 1.0] but it is not a metric anymore!

The similarity is computed as 1 - normalized distance.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    
    public static void main (String[] args) {
        NormalizedLevenshtein l = new NormalizedLevenshtein();

        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
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
                        
                        // The cost for substituting 't' and 'r' is considered
                        // smaller as these 2 are located next to each other
                        // on a keyboard
                        if (c1 == 't' && c2 == 'r') {
                            return 0.5;
                        }
                        
                        // For most cases, the cost of substituting 2 characters
                        // is 1.0
                        return 1.0;
                    }
        });
        
        System.out.println(wl.distance("String1", "Srring2"));
    }
```

## Damerau-Levenshtein
Similar to Levenshtein, Damerau-Levenshtein distance is the minimum number of operations needed to transform one string into the other, where an operation is defined as an insertion, deletion, or substitution of a single character, or a **transposition of two adjacent characters**.

This is not to be confused with the optimal string alignment distance, which is an extension where no substring can be edited more than once.

Also, Damerau-Levenshting does not respect triangle inequality, and is thus not a metric distance.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {


    public static void main(String[] args) {
        Damerau d = new Damerau();
        
        // 1 substitution
        System.out.println(d.distance("ABCDEF", "ABDCEF"));
        
        // 2 substitutions
        System.out.println(d.distance("ABCDEF", "BACDFE"));
        
        // 1 deletion
        System.out.println(d.distance("ABCDEF", "ABCDE"));
        System.out.println(d.distance("ABCDEF", "BCDEF"));
        System.out.println(d.distance("ABCDEF", "ABCGDEF"));
        
        // All different
        System.out.println(d.distance("ABCDEF", "POIU"));
    }
}
```

Will produce:

```
1.0
2.0
1.0
1.0
1.0
6.0
```



## Jaro-Winkler
Jaro-Winkler is a string edit distance that was developed in the area of record linkage (duplicate detection) (Winkler, 1990). The Jaro–Winkler distance metric is designed and best suited for short strings such as person names, and to detect typos.

Jaro-Winkler computes the similarity between 2 strings, and the returned value lies in the interval [0.0, 1.0].
It is (roughly) a variation of Damerau-Levenshtein, where the substitution of 2 close characters is considered less important then the substitution of 2 characters that a far from each other.

The distance is computed as 1 - Jaro-Winkler similarity.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {


    public static void main(String[] args) {
        JaroWinkler jw = new JaroWinkler();
        
        // substitution of s and t
        System.out.println(jw.similarity("My string", "My tsring"));
        
        // substitution of s and n
        System.out.println(jw.similarity("My string", "My ntrisg"));
    }
}
```

will produce:

```
0.9740740656852722
0.8962963223457336
```

## Longest Common Subsequence

The longest common subsequence (LCS) problem consists in finding the longest subsequence common to two (or more) sequences. It differs from problems of finding common substrings: unlike substrings, subsequences are not required to occupy consecutive positions within the original sequences.

It is used by the diff utility, by Git for reconciling multiple changes, etc.

The LCS distance between strings X (of length n) and Y (of length m) is n + m - 2 |LCS(X, Y)|
min = 0
max = n + m

LCS distance is equivalent to Levenshtein distance when only insertion and deletion is allowed (no substitution), or when the cost of the substitution is the double of the cost of an insertion or deletion.

This class implements the dynamic programming approach, which has a space requirement O(m.n), and computation cost O(m.n).

In "Length of Maximal Common Subsequences", K.S. Larsen proposed an algorithm that computes the length of LCS in time O(log(m).log(n)). But the algorithm has a memory requirement O(m.n²) and was thus not implemented here.

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // Will produce 4.0
        System.out.println(lcs.distance("AGCAT", "GAC"));
        
        // Will produce 1.0
        System.out.println(lcs.distance("AGCAT", "AGCT"));
    }
}
```

## Metric LCS (Longest Common Subsequence)
Distance metric based on Longest Common Subsequence, from the notes "An LCS-based string metric" by Daniel Bakkelund.
http://heim.ifi.uio.no/~danielry/StringMetric.pdf

The distance is computed as 1 - |LCS(s1, s2)| / max(|s1|, |s2|)
```java
public class MyApp {

        public static void main(String[] args) {

        info.debatty.java.stringsimilarity.MetricLCS lcs = 
                new info.debatty.java.stringsimilarity.MetricLCS();

        String s1 = "ABCDEFG";   
        String s2 = "ABCDEFHJKL";
        // LCS: ABCDEF => length = 6
        // longest = s2 => length = 10
        // => 1 - 6/10 = 0.4
        System.out.println(lcs.distance(s1, s2));

        // LCS: ABDF => length = 4
        // longest = ABDEF => length = 5
        // => 1 - 4 / 5 = 0.2
        System.out.println(lcs.distance("ABDEF", "ABDIF"));
    }
}
```

## N-Gram distance (Kondrak)

Normalized N-Gram distance as defined by Kondrak, "N-Gram Similarity and Distance", String Processing and Information Retrieval, Lecture Notes in Computer Science Volume 3772, 2005, pp 115-126.

http://webdocs.cs.ualberta.ca/~kondrak/papers/spire05.pdf

The algorithm uses affixing with special character '\n' to increase the weight of first characters. The normalization is achieved by dividing the total similarity score the original length of the longest word.

In the paper, Kondrak also defines a similarity measure, which is not implemented (yet).

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {

    public static void main(String[] args) {
        
        // produces 0.416666
        NGram twogram = new NGram(2);
        System.out.println(twogram.distance("ABCD", "ABTUIO"));
        
        // produces 0.97222
        String s1 = "Adobe CreativeSuite 5 Master Collection from cheap 4zp";
        String s2 = "Adobe CreativeSuite 5 Master Collection from cheap d1x";
        NGram ngram = new NGram(4);
        System.out.println(ngram.distance(s1, s2));
    }
}
```

## Shingle (n-gram) based algorithms
A few algorithms work by converting strings into sets of n-grams (sequences of n characters, also sometimes called k-shingles). The similarity or distance between the strings is then the similarity or distance between the sets.

The cost for computing these similarities and distances is mainly domnitated by k-shingling (converting the strings into sequences of k characters). Therefore there are typically two use cases for these algorithms:

Directly compute the distance between strings:

```java
import info.debatty.java.stringsimilarity.*;

public class MyApp {
    
    public static void main(String[] args) {
        QGram dig = new QGram(2);
        
        // AB BC CD CE
        // 1  1  1  0
        // 1  1  0  1
        // Total: 2

        System.out.println(dig.distance("ABCD", "ABCE"));
    }
}
```

Or, for large datasets, pre-compute the profile or set representation of all strings. The similarity can then be computed between profiles or sets:

```java
import info.debatty.java.stringsimilarity.KShingling;
import info.debatty.java.stringsimilarity.StringProfile;


public class PrecomputedCosine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String s1 = "My first string";
        String s2 = "My other string...";
        
        // Let's work with sequences of 2 characters...
        KShingling ks = new KShingling(2);
        
        // For cosine similarity I need the profile of strings
        StringProfile profile1 = ks.getProfile(s1);
        StringProfile profile2 = ks.getProfile(s2);
        
        // Prints 0.516185
        System.out.println(profile1.cosineSimilarity(profile2));
        
    }
    
}
```

Pay attention, this only works if the same KShingling object is used to parse all input strings !


### Q-Gram
Q-gram distance, as defined by Ukkonen in "Approximate string-matching with q-grams and maximal matches"
http://www.sciencedirect.com/science/article/pii/0304397592901434

The distance between two strings is defined as the L1 norm of the difference of their profiles (the number of occurences of each n-gram): SUM( |V1_i - V2_i| ). Q-gram distance is a lower bound on Levenshtein distance, but can be computed in O(m + n), where Levenshtein requires O(m.n)


### Cosine similarity
The similarity between the two strings is the cosine of the angle between these two vectors representation, and is computed as V1 . V2 / (|V1| * |V2|)

Distance is computed as 1 - cosine similarity.

### Jaccard index
Like Q-Gram distance, the input strings are first converted into sets of n-grams (sequences of n characters, also called k-shingles), but this time the cardinality of each n-gram is not taken into account. Each input string is simply a set of n-grams. The Jaccard index is then computed as |V1 inter V2| / |V1 union V2|.

Distance is computed as 1 - cosine similarity.
Jaccard index is a metric distance.

### Sorensen-Dice coefficient
Similar to Jaccard index, but this time the similarity is computed as 2 * |V1 inter V2| / (|V1| + |V2|).

Distance is computed as 1 - cosine similarity.
