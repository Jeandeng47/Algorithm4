import java.util.Arrays;

import edu.princeton.cs.algs4.BinarySearch;

public class _TwoSum {
    public static int twoSumCountFast(int[] a) {
        Arrays.sort(a); // O(NlogN)
        int N = a.length;
        int count = 0;
        
        // N * O(logN) = O(NlogN)
        for (int i = 0; i < N; i++) {
            // index > i: we find an index j where
            // (i, j) statisfies a[j] + a[i] == 0
            if (BinarySearch.indexOf(a, -a[i]) > i) {
                count++;
            }
        }
        return count;
    }

    public static int twoSumCountBF(int[] a) {
        int N = a.length;
        int count = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (a[i] + a[j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
