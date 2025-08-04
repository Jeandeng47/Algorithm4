import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_21 {

    // Check if String x is the smallest among three String
    private static boolean minStr(String s, String cmp1, String cmp2) {
        return s.compareTo(cmp1) <= 0 && s.compareTo(cmp2) <= 0; 
    }
    public static String findCommon(String[] a, String[] b, String[] c) {
        // Sort the name lists in place: 
        // O(alog(a)) + O(blog(b)) + O(clog(c)) = O((a+b+c)log(a+b+c))
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);

        // Use three pointers to compare names in sorted lists: O(a+b+c)
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length && k < c.length) {
            String as = a[i];
            String bs = b[j];
            String cs = c[k];

            // if as == bs == cs
            if (as.equals(bs) && bs.equals(cs)) {
                return as;
            }

            // at each step, if three not equal
            // advance the pointer of the smallest string

            if (minStr(as, bs, cs))          i++;    // min = as
            else if (minStr(bs, as, cs))     j++;    // min = bs
            else                             k++;    // min = cs
        }

        // if any one list exhuasted, no common found
        return null;
    } 
    public static void main(String[] args) {
        String[] A = { "beta", "epsilon", "alpha", "gamma"};
        String[] B = { "delta", "beta", "eta"};
        String[] C = { "beta", "theta", "iota", "alpha"};

        String common = findCommon(A, B, C);
        if (common != null) {
            StdOut.println("First common name: " + common); // First common name: beta
        } else {
            StdOut.println("No common name found");
        }
    }
}


// Example: 
// String[] A = { "beta",    "epsilon", "alpha",  "gamma" };
// String[] B = { "delta",   "beta",    "eta"            };
// String[] C = { "beta",    "theta",   "iota",   "alpha" };

// Sorted
// A → ["alpha", "beta",    "epsilon", "gamma"]
// B → ["beta",  "delta",   "eta"           ]
// C → ["alpha", "beta",    "iota",    "theta"]

// i    j    k       a[i]        b[j]        c[k]
// 0    0    0       alpha       beta        alpha      -> i++
// 1    0    0       beta        beta        alpha      -> k++
// 1    0    1       beta        beta        beta       
