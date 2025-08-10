import edu.princeton.cs.algs4.StdRandom;

public class _QuickSort {
    public static void quickSort(Comparable[] a) {
        StdRandom.shuffle(a); // shuffle to gurantee performance
        sort(a, 0, a.length - 1);
    }

    // Recursively perform partion + sort two halves
    private static void sort(Comparable[] a, int lo, int hi) {
        // base case
        if (lo >= hi) return;

        // recursive case:
        int j = partition(a, lo, hi); // sorting happens here
        sort(a, lo, j - 1);
        sort(a, j + 1 , hi);
    }

    // Return the index to split the array
    private static int partition(Comparable[] a, int lo, int hi) {
        // choose piviot
        Comparable v = a[lo];
        int i = lo;     // index of left part
        int j = hi + 1; // index of right part

        while (true) {
            // i scans from left, stop when a[i] >= v
            while (less(a[++i], v)) if (i == hi) break;
            // j scans from right, stop when a[j] <= v
            while (less(v, a[--j])) if (j == lo) break;

            // check if i and j cross each other
            if (i >= j) break;

            exch(a, i, j);
        }
        // put piviot (v = a[j]) into correct position
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
