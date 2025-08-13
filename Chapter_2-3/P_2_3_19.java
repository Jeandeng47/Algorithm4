import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_19 {
    private static final int CUTOFF = 12;
    // ========================= Public APIs =========================
    public static void quicksortBase(Comparable[] a) { sortBase(a, 0, a.length - 1); }
    public static void quicksortMed3(Comparable[] a) { sortMed3(a, 0, a.length - 1); }
    public static void quicksortMed5(Comparable[] a) { sortMed5(a, 0, a.length - 1); }

    // ========================= Classic QuickSort =========================
    public static void sortBase(Comparable[] a, int lo, int hi) {
        if (hi - lo + 1 <= CUTOFF) { insertion(a, lo, hi); return; }

        int j = partition(a, lo, hi);
        sortBase(a, lo, j-1);
        sortBase(a, j+1, hi);
    } 

    private static int partition(Comparable[] a, int lo, int hi) {
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

    // ========================= Median of 3 =========================
    public static void sortMed3(Comparable[] a, int lo, int hi) {
        if (hi - lo + 1 <= CUTOFF) { insertion(a, lo, hi); return; }

        // we would like the pivot to be the median of (a[lo], a[mid], a[hi])
        median3ToLo(a, lo, hi);

        // use pivot = a[lo] = median to split array
        int j = partition(a, lo, hi);
        sortMed3(a, lo, j - 1);
        sortMed3(a, j + 1, hi);
    } 

    private static void median3ToLo(Comparable[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        // ensure a[lo] <= a[hi]
        if (less(a[hi], a[lo])) exch(a, lo, hi);
        // ensure a[lo] <= a[mid]
        if (less(a[mid], a[lo])) exch(a, lo, mid);
        // ensure a[mid] <= a[hi]
        if (less(a[hi], a[mid])) exch(a, mid, hi);

        // put a[mid] to a[lo]
        exch(a, lo, mid);
    }

    // ========================= Median of 5 =========================

    // Idea: the median from a 5-sample is more closed to the real
    // median than median-of-3
    public static void sortMed5(Comparable[] a, int lo, int hi) {
        if (hi - lo + 1 <= CUTOFF) { insertion(a, lo, hi); return; }
        
        // 1. Pick 5 elements
        // [i1 ...... i2 ...... i3 ...... i4 ...... i5]
        //  lo       lo+q      lo+n/2    hi-q       hi
        int n = hi - lo + 1;
        int q = n / 4;
        int i1 = lo, i2 = lo + q, i3 = lo + n / 2, i4 = hi - q, i5 = hi;
        
        // 2. Get median of 5 elements (6 compares) and return its index
        int m = median5Cmp(a, i1, i2, i3, i4, i5); // 6 comparisons, no alloc
        exch(a, lo, m);
        
        // 3. Normal 2 way partition
        int j = partition(a, lo, hi);
        sortMed5(a, lo, j - 1);
        sortMed5(a, j + 1, hi);
    }

    private static int median5Cmp(Comparable[] a, int i1, int i2, int i3, int i4, int i5) {
        // 1. sort 2 pairs asc
        // p=min(a,b), P=max(a,b), q=min(c,d), Q=max(c,d)
        if (a[i2].compareTo(a[i1]) < 0) { int t=i1; i1=i2; i2=t; } // a[i1] <= a[i2]
        if (a[i4].compareTo(a[i3]) < 0) { int t=i3; i3=i4; i4=t; } // a[i3] <= a[i4]

        // 2. build bracket [m1,m2], m1=max(p,q), m2=min(P,Q)
        if (a[i3].compareTo(a[i1]) < 0) { int t=i1; i1=i3; i3=t; t=i2; i2=i4; i4=t; } // a[i1] <= a[i3], a[i2] <-> a[i4]
        if (a[i4].compareTo(a[i2]) < 0) { int t=i2; i2=i4; i4=t; } // a[i2] <= a[i4]
 
        // 3. If i5 is median, it could only fall between m1 & m2
        if (a[i5].compareTo(a[i2]) < 0) return i2;
        return (a[i3].compareTo(a[i5]) < 0) ? i3 : i5;
    }
   
    // ========================= Helpers =========================
    private static void insertion(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            Comparable v = a[i];
            int j = i - 1;
            while (j >= lo && less(v, a[j])) { a[j + 1] = a[j]; j--; }
            a[j + 1] = v;
        }
    }

    public static Integer[] randomArr(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = StdRandom.uniformInt(1_000_000);
        return a;
    }

    public static boolean isSorted(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int startN = 1_000, maxN = 1_000_000, trials = 5;

        StdOut.printf("%10s  %12s  %12s  %12s  %10s  %10s%n",
                "N", "base(ms)", "med3(ms)", "med5(ms)", "med3/base", "med5/base");
        for (int N = startN; N <= maxN; N *= 2) {
            long baseNs = 0, m3Ns = 0, m5Ns = 0;

            for (int t = 0; t < trials; t++) {
                Integer[] a = randomArr(N);
                Integer[] b = a.clone();
                Integer[] c = a.clone();

                long t0 = System.nanoTime();
                quicksortBase(a);
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                quicksortMed3(b);
                long t3 = System.nanoTime();

                long t4 = System.nanoTime();
                quicksortMed5(c);
                long t5 = System.nanoTime();

                if (!isSorted(a) || !isSorted(b) || !isSorted(c)) throw new AssertionError("not sorted");

                baseNs += (t1 - t0);
                m3Ns   += (t3 - t2);
                m5Ns   += (t5 - t4);
            }

            double baseMs = baseNs / 1e6 / trials;
            double m3Ms   = m3Ns   / 1e6 / trials;
            double m5Ms   = m5Ns   / 1e6 / trials;
            StdOut.printf("%10d  %12.3f  %12.3f  %12.3f  %10.2f  %10.2f%n",
                    N, baseMs, m3Ms, m5Ms, baseMs / m3Ms, baseMs / m5Ms);
        }

    }
    
}


    //     N      base(ms)      med3(ms)      med5(ms)   med3/base   med5/base
    //   1000         0.183         0.154         0.129        1.19        1.42
    //   2000         0.618         0.585         0.591        1.06        1.05
    //   4000         1.102         0.927         0.921        1.19        1.20
    //   8000         0.695         0.719         0.649        0.97        1.07
    //  16000         0.886         0.946         0.984        0.94        0.90
    //  32000         2.000         2.137         2.160        0.94        0.93
    //  64000         4.247         4.457         4.526        0.95        0.94
    // 128000        10.050        10.469        10.576        0.96        0.95
    // 256000        24.153        23.559        21.655        1.03        1.12
    // 512000        47.322        44.019        43.077        1.08        1.10



