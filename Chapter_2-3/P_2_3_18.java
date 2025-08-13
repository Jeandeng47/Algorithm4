import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_18 {
    public static void quicksortBase(Comparable[] a) {
        sortBase(a, 0, a.length - 1);
    }

    public static void quicksortMed3(Comparable[] a) {
        sortMed3(a, 0, a.length - 1);
    }

    public static void sortBase(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;

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

    public static void sortMed3(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;

        // we would like the pivot to be the median 
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
    

    // Return the median index of (a[lo], a[mid], a[hi])
    private static int median(Comparable[] a, int i, int j, int k) {
        int m;
        if (less(a[i], a[j])) {  // ai < aj
            if (less(a[j], a[k])) { 
                // ai < aj < ak
                m = j;
            } else {
                // ai < aj, aj > ak
                // -- ai < ak < aj => k
                // -- ak < ai < ak => i
                m = (less(a[i], a[k])? k : i);
            }

        } else { // aj < ai
            if (less(a[k], a[j])) {
                // ak < aj < ai
                m = j;
            } else {
                // aj < ai, aj < ak
                // -- aj < ai < ak => i
                // -- aj < ak < ai => k
                m = (less(a[i], a[k])? i : k);
            }
        }
        return m;
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
        int startN = 1_000;
        int maxN = 1_000_000;
        int trials = 5;  

        StdOut.printf("%10s  %12s  %12s  %8s%n", "N", "base(ms)", "med3(ms)", "speedup");
        for (int N = startN; N <= maxN; N *= 2) {
            long baseNs = 0, med3Ns = 0;

            for (int t = 0; t < trials; t++) {
                Integer[] a = randomArr(N);
                Integer[] b = a.clone();

                long t0 = System.nanoTime();
                quicksortBase(a);
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                quicksortMed3(b);
                long t3 = System.nanoTime();

                if (!isSorted(a) || !isSorted(b)) throw new AssertionError("not sorted");
                baseNs += (t1 - t0);
                med3Ns += (t3 - t2);
            }

            double baseMs = baseNs / 1e6 / trials;
            double med3Ms = med3Ns / 1e6 / trials;
            double speedup = baseMs / med3Ms;
            StdOut.printf("%10d  %12.3f  %12.3f  %8.2fX%n", N, baseMs, med3Ms, speedup);
        }
    }
}

    //      N      base(ms)      med3(ms)   speedup
    //   1000         0.191         0.118      1.61X
    //   2000         0.703         0.669      1.05X
    //   4000         1.548         1.506      1.03X
    //   8000         0.908         0.571      1.59X
    //  16000         0.951         1.073      0.89X
    //  32000         2.151         2.247      0.96X
    //  64000         4.683         4.999      0.94X
    // 128000         9.652        10.314      0.94X
    // 256000        23.420        24.347      0.96X
    // 512000        45.959        45.642      1.01X