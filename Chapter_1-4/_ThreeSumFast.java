import java.util.Arrays;

import edu.princeton.cs.algs4.BinarySearch;

// Faster: Use binary search to bring time complexity to O(N^2 * logN)
public class _ThreeSumFast {
    public static int twoSumCountFast(int[] a) {
        Arrays.sort(a); // O(NlogN)
        int N = a.length;
        int count = 0;
        
        // N^2 * O(logN) = O(N^2logN)
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; i < N; j++) {
                // index k > i: we find an index k where
                // (i, j, k) statisfies a[j] + a[i] + a[k] == 0
                if (BinarySearch.indexOf(a, -a[i]-a[j]) > i) {
                    count++;
                }
            }
        }
        return count;
    }
}
