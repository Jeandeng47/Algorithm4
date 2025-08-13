import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_23 {
    private static int INSERTION_CUTOFF = 8;
    private static int MEDIAN_CUTOFF = 40;

    public static void quickSort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 1) return;

        // use insertion sort for small subarray
        if (n <= INSERTION_CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        } 

        // choose a good pivot, place it at a[lo]
        pivotToLo(a, lo, hi);

        // Recursive part:
        // Fast 3-way partition
        int[] mid = partitionBM(a, lo, hi); // return {ltHi, gtLo}
        sort(a, lo, mid[0]);
        sort(a, mid[1], hi);
    }

    private static int[] partitionBM(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        int p = lo, q = hi + 1;
        Comparable v = a[lo];

        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;

            if (i == j && eq(a[i], v)) exch(a, ++p, i);
            if (i >= j) break;
            exch(a, i, j);

            if (eq(a[i], v)) exch(a, ++p, i);
            if (eq(a[j], v)) exch(a, --q, j);
        }

        i = j + 1;
        for (int k = lo; k <= p; k++) exch(a, k, j--);
        for (int k = hi; k >= q; k--) exch(a, k, i++);

        return new int[]{j, i};
    }

    private static void pivotToLo(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= MEDIAN_CUTOFF) {
            median3(a, lo, hi);
        } else {
            // only use this when subarray is large
            tukeyNither(a, lo, hi);
        }
    }

    // Median-of-3: put the median of (lo, mid, hi) into a[lo]
    private static void median3(Comparable[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        if (less(a[mid], a[lo])) exch(a, lo, mid);  // a[lo] <= a[mid]
        if (less(a[hi], a[lo])) exch(a, lo, hi);    // a[lo] <= a[hi]
        if (less(a[hi], a[mid])) exch(a, mid, hi);  // a[mid] <= a[hi]
        exch(a, lo, mid);
    }

    // Tukey ninther: median of {lo, lo+eps, lo+2eps}, {mid-eps, mid, mid+eps}, 
    // {hi-2eps, hi-eps, hi}, then swap that median-of-medians into a[lo]
    private static void tukeyNither(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        int e = n / 8;
        int mid = lo + (hi - lo) / 2;

        // 3 median candidates
        int m1 = median3Index(a, lo, lo + e, lo + 2 * e);
        int m2 = median3Index(a, mid - e, mid, mid + e);
        int m3 = median3Index(a, hi - 2 * e, hi - e, hi);

        // median of median
        int mm = median3Index(a, m1, m2, m3);
        exch(a, lo, mm);
    }


    // Return the index of the median among a[i], a[j], a[k]
    private static int median3Index(Comparable[] a, int i, int j, int k) {
        Comparable ai = a[i], aj = a[j], ak = a[k];
        if (less(ai, aj)) { // ai < aj
            if (less(aj, ak)) return j;     // ai < aj < ak
            return less(ai, ak) ? k : i;    // ai < ak <= aj ? k : i
        } else {             // aj <= ai
            if (less(ai, ak)) return i;     // aj <= ai < ak
            return less(aj, ak) ? k : j;    // aj < ak <= ai ? k : j
        }
    }

    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;    // optimization when reference equal
        return v.compareTo(w) < 0;
    }

    private static boolean eq(Comparable v, Comparable w) {
        if (v == w) return true;    // optimization when reference equal
        return v.compareTo(w) == 0;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) if (less(a[i], a[i-1])) return false;
        return true;
    }
    

    public static void main(String[] args) {
        Integer[] a = new Integer[50];
        for (int i = 0; i < a.length; i++) a[i] = StdRandom.uniformInt(1000);
        // sprinkle lots of duplicates to show 3-way goodness
        for (int i = 0; i < a.length; i += 7) a[i] = 500;

        System.out.println("Before: " + Arrays.toString(a));
        quickSort(a);
        System.out.println("After : " + Arrays.toString(a));
        System.out.println("sorted? " + isSorted(a));
    }
    
}
