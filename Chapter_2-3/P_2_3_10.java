import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_10 {
    private static long compares;

    private static long quickSortCount(Comparable[] a) {
        compares = 0;
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        return compares;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
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
        compares++;
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // Experiment
    private static Integer[] getArray(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        return a;
    }

    // Compute the expected comparions of quick-sort
    private static double harmonicApprox(int n) {
        // Euler–Maclaurin: H_n ≈ ln n + γ + 1/(2n) - 1/(12n^2)
        final double gamma = 0.5772156649015329;
        double N = n;
        return Math.log(N) + gamma + 1.0/(2*N) - 1.0/(12*N*N);
    }

    public static void main(String[] args) {
        int N = 1_000_000; // N = 10^6
        int TRIALS = 25;
        long seed = 42L;
        StdRandom.setSeed(seed);

        long sum = 0;
        double sumSq = 0.0;

        Integer[] base = getArray(N);
        
        // for each trials, run quicksort and 
        // record # compares
        for (int t = 0; t < TRIALS; t++) {
            Integer[] a = base.clone();
            long c = quickSortCount(a);
            sum += c;
            sumSq += (double) c * c;
        }

        double mean = (double) sum / TRIALS;
        double var  = sumSq / TRIALS - mean * mean; // Var(x) = E(x^2) - E(x)^2
        double sd   = Math.sqrt(Math.max(0.0, var));

        double Hn   = harmonicApprox(N);
        double muExactApprox = 2.0 * (N + 1) * Hn - 4.0 * N;  // ≈ E[C_N]

        // Chebyshev (two-sided) using sample sd:
        // k = (T - u) / std = (T - E[Cn]) / std
        // Pr(Cn >= T) <= 1/k^2
        double threshold = 0.1 * (double) N * (double) N;     // 0.1 N^2 = 10^11
        double k = (threshold - muExactApprox) / sd; 
        double cheby = 1.0 / (k * k);

        StdOut.printf("N=%d, trials=%d%n", N, TRIALS);
        StdOut.printf("Empirical mean=%.0f, sd=%.0f%n", mean, sd);
        StdOut.printf("Approx E[C_N]=%.0f (via 2(N+1)H_N-4N) %n", muExactApprox);
        StdOut.printf("Threshold T=0.1*N^2=%.0f%n", threshold);
        StdOut.printf("Chebyshev bound (two-sided): <= %.3e%n", cheby);
       
    }
}

// N=1000000, trials=25
// Empirical mean=26169073, sd=738480
// Approx E[C_N]=24785482 (via 2(N+1)H_N-4N) 
// Threshold T=0.1*N^2=100000000000
// Chebyshev bound (two-sided): <= 5.456e-11

// According to the resulting Chebyshev bound, the probability
// that number of compares exceed 0.1*N^2 is very very small.
// It's safe to use quick-sort for large random input.
