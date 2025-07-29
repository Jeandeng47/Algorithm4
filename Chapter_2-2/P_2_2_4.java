import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_4 {
    private static Comparable[] aux;

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static boolean isSorted(Comparable[] a, int lo, int hi) {
        // asec: a[i] > a[i-1]
        if (lo >= hi) return true;   
        for (int k = lo + 1; k <= hi; k++) {
            if (less(a[k], a[k-1])) {
                return false;
            }
        }
        return true;
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int N = a.length;
        aux = new Comparable[N];

        // copy into aux
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                        a[k] = aux[i++];
        }
    }
    public static void main(String[] args) {
        Random rand = new Random(42);
        int trials = 10;
        int failures = 0;

        for (int t = 0; t < trials; t++) {
            StdOut.printf("Trail %d%n", t + 1);

            // random len between 2 - 10
            int N = 2 + rand.nextInt(9);
            Integer[] a = new Integer[N];
            for (int i = 0; i < N; i++) a[i] = rand.nextInt(100);

            // random split 
            int lo = 0;
            int mid = rand.nextInt(N - 1); // ensure mid + 1 <= N - 1
            int hi = N - 1;

            // Store original halves [in, ex)
            Integer[] l = Arrays.copyOfRange(a, lo, mid+1);
            Integer[] r = Arrays.copyOfRange(a, mid+1, hi+1);
            StdOut.println("l: " + Arrays.toString(l)); 
            StdOut.println("r: " + Arrays.toString(r)); 

            // merge sort a
            merge(a, lo, mid, hi);
            StdOut.println("a: " + Arrays.toString(a)); 
            StdOut.println();

            // isSorted(a, lo, hi) [lo, hi]
            boolean halvesSorted = isSorted(l, 0, l.length - 1) && isSorted(r, 0, r.length - 1);
            boolean mergedSorted = isSorted(a, 0, a.length - 1);

            // If the hypothesis holds (merged array is sorted iff 2 subarrays are sorted),
            // halvesSorted (true) == mergeSorted (true)
            if (halvesSorted != mergedSorted) {
                failures++;
                StdOut.println("Counterexample!");
                StdOut.println("Original l : " + Arrays.toString(l) +
                                   "  sorted? " + isSorted(l,0,l.length-1));
                StdOut.println("Original r: " + Arrays.toString(r) +
                                   "  sorted? " + isSorted(r,0,r.length-1));
                StdOut.println("After merge   : " + Arrays.toString(a) +
                                   "  mergedSorted? " + mergedSorted);
                break;
            }
        }

        if (failures == 0) {
            StdOut.println("No counterexamples found in " + trials +
                               " trials.  Empirically, mergeSorted <=> both halves sorted.");
        }

    }
}
