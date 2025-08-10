import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_4 {
    private static int cmp;

    public static void quickSort(Comparable[] a) {
        cmp = 0; // reset
        int N = a.length;
        sort(a, 0, N -1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable v, Comparable w) {
        cmp++;
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static Integer[] generateWorst(boolean[] pickMin) {
        int N = pickMin.length;
        Deque<Integer> nums = new LinkedList<>();
        for (int i = 1; i <= N; i++) nums.add(i);

        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = pickMin[i]? nums.removeFirst() : nums.removeLast();
        }
        return a;
    }

    public static int getCompareCount() {
        return cmp;
    }

    public static void main(String[] args) {
        int N = 10;
        boolean[][] patterns = {
            // 1. All mins
            {true,true,true,true,true,true,true,true,true,true},
            // 2. All maxs
            {false,false,false,false,false,false,false,false,false,false},
            // 3. min, max
            {true,false,true,false,true,false,true,false,true,false},
            // 4. max, min 
            {false,true,false,true,false,true,false,true,false,true},
            // 5. 2 min, 2 max
            {true,true,false,false,true,true,false,false,true,true},
            // 6. 2 max, 2 min
            {false,false,true,true,false,false,true,true,false,false}
        };


        StdOut.println("Worst-case arrays and compare counts (N=" + N + "):");
        for (int i = 0; i < patterns.length; i++) {
            Integer[] w = generateWorst(patterns[i]);
            StdOut.printf("Case %d before: %s%n", i+1, Arrays.toString(w));
            quickSort(w);
            StdOut.printf("Case %d after : %s%n", i+1, Arrays.toString(w));
            StdOut.printf("-> %d compares%n", getCompareCount());
        }

    }
}


// For quick-sort, the worst case is that the pivot always divides
// into subarrays of size 0 and size N - 1. To make such worst arrays,
// we always take min or max as pivot.
// The number of compares under the worst case is:
// N + (N - 1) + (N - 2) + ... + 2 + 1 = (N + 1) * N / 2