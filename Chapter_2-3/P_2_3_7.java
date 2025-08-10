import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_7 {

    private static int cnt0, cnt1, cnt2;

    public static void quickSort(Comparable[] a) {
        resetCounters();
        StdRandom.shuffle(a);         
        sort(a, 0, a.length - 1);
    }
    
    private static void sort(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        
        // count small subarrays
        if (n <= 0) {cnt0++; return;} // hi + 1 <= lo
        if (n == 1) {cnt1++; return;} // hi == lo
        if (n == 2) {cnt2++;} // fall through

        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
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
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    private static Integer[] getArr(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        return a;
    }

    public static void runExperiment(int N, int trials, long seed) {
        StdRandom.setSeed(seed);
        double sum0 = 0, sum1 = 0, sum2 = 0;

        Integer[] base = getArr(N);
        for (int t = 0; t < trials; t++) {
            Integer[] a = base.clone();
            quickSort(a);
            sum0 += cnt0;
            sum1 += cnt1;
            sum2 += cnt2;
        }

        double emp0 = sum0 / trials;
        double emp1 = sum1 / trials;
        double emp2 = sum2 / trials;

        // exact expectations
        double ex0 = (N >= 2) ? (N + 1) / 3.0 : (N == 1 ? 0.0 : 1.0);
        double ex1 = (N >= 2) ? (N + 1) / 3.0 : (N == 1 ? 1.0 : 0.0);
        double ex2 = (N >= 2) ? (N + 1) / 6.0 : 0.0;

        StdOut.printf("N=%-6d trials=%-6d%n", N, trials);
        StdOut.printf("  size 0: empirical=%.2f  exact=%.2f  diff=%+.2f%n", emp0, ex0, emp0 - ex0);
        StdOut.printf("  size 1: empirical=%.2f  exact=%.2f  diff=%+.2f%n", emp1, ex1, emp1 - ex1);
        StdOut.printf("  size 2: empirical=%.2f  exact=%.2f  diff=%+.2f%n%n", emp2, ex2, emp2 - ex2);
    }

    private static void resetCounters() {
        cnt0 = 0;
        cnt1 = 0;
        cnt2 = 0;
    }

    public static void main(String[] args) {
        int trials = 1000;
        long seed = 42L;

        runExperiment(100, trials, seed);
        runExperiment(1000, trials, seed);
        runExperiment(10000, trials, seed);
    }
}

// N=100    trials=1000  
//   size 0: empirical=33.57  exact=33.67  diff=-0.09
//   size 1: empirical=33.71  exact=33.67  diff=+0.05
//   size 2: empirical=16.83  exact=16.83  diff=-0.00

// N=1000   trials=1000  
//   size 0: empirical=333.13  exact=333.67  diff=-0.53
//   size 1: empirical=333.93  exact=333.67  diff=+0.27
//   size 2: empirical=166.89  exact=166.83  diff=+0.06

// N=10000  trials=1000  
//   size 0: empirical=3335.48  exact=3333.67  diff=+1.81
//   size 1: empirical=3332.76  exact=3333.67  diff=-0.91
//   size 2: empirical=1668.06  exact=1666.83  diff=+1.23
