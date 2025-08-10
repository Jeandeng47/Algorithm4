import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_6 {
    private static int cmp;

    public static int quickSort(Comparable[] a) {
        cmp = 0; // reset
        int N = a.length;

        StdRandom.shuffle(a); 
        sort(a, 0, N - 1);
        return cmp;
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
            // we find a a[i] > v or a[j] < v
            exch(a, i, j);
        }
        // final exchange
        exch(a, lo, j);

        return j;
    }

    private static boolean less(Comparable v, Comparable w) {
        cmp++;
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }



    private static Integer[] getArr(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        return a;
    }

    private static void runExperiment(int N, int trials, long seed) {
        StdRandom.setSeed(seed);                    // reproducible shuffles
        int sum = 0;
        Integer[] a = getArr(N);

        for (int t = 0; t < trials; t++) {
            Integer[] copy = a.clone();             // quickSortCount will shuffle internally
            int c = quickSort(copy);
            sum += c;
        }

        double mean = (double) sum / trials;
        double approx = 2.0 * N * Math.log(N);

        StdOut.printf("N=%-6d trials=%-6d seed=%d%n", N, trials, seed);
        StdOut.printf("Empirical Cn = %.2f  2NlgN = %.2f%n", mean, approx);
    }


    public static void main(String[] args) {
        long seed = 42L;

        runExperiment(100, 100, seed);
        runExperiment(1000, 100, seed + 1);
        runExperiment(10000, 100, seed + 2);
    }
}


// N=100    trials=100    seed=42
// Empirical Cn = 766.05  2NlgN = 921.03
// N=1000   trials=100    seed=43
// Empirical Cn = 12253.48  2NlgN = 13815.51
// N=10000  trials=100    seed=44
// Empirical Cn = 167551.90  2NlgN = 184206.81