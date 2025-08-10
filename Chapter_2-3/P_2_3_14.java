import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_14 {
    private static int totalCmp;
    private static boolean[][] visited; // record whether we visit (i, j)

    public static int quickSort(Integer[] a) {
        totalCmp = 0; // reset

        int N = a.length;
        StdRandom.shuffle(a);
        sort(a, 0, N - 1);

        return totalCmp;
    }

    private static void sort(Integer[] a, int lo, int hi) {
        if (lo >= hi) return;
        
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Integer[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Integer v = a[lo];
        while (true) {
            while (lessTrack(a[++i], v)) if (i == hi) break;
            while (lessTrack(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    // Compare a and b, record visit
    private static boolean lessTrack(Integer a, Integer b) {
        totalCmp++;
        // 0-based index
        int i = Math.min(a, b) - 1;
        int j = Math.max(a, b) - 1;
        if (visited != null) {
            visited[i][j] = true;
        }
        return a.compareTo(b) < 0;
    }

    private static void exch(Integer[] a, int i, int j) {
        Integer t = a[i]; a[i] = a[j]; a[j] = t;
    }

    private static Integer[] identity(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i + 1; // values are the ranks
        return a;
    }

    private static void run(int N, int trials, long seed) {
        StdRandom.setSeed(seed);

        // counts[i][j] = #compares of (i, j) across all trials
        int[][] counts = new int[N][N];
        int sumCmp = 0;

        Integer[] base = identity(N);
        for (int t = 0; t < trials; t++) {
            visited = new boolean[N][N];

            Integer[] a = base.clone();
            int c = quickSort(a);
            sumCmp += c;

            // add #cmp of this run into counts matrix
             for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (visited[i][j]) counts[i][j] += 1;
                }
            }
        }

        // Pr(ith and jth compared) = 2 / (j - i + 1) for 1 <= i < j <= N,  d = j - i
        StdOut.printf("N=%2d, trials=%d\n", N, trials);
        StdOut.println(" d   empirical P     2/(d+1)      abs diff");
        for (int d = 1; d <= Math.min(N - 1, 12); d++) {
            int pairsAtD = N - d; // number of (i, j)
            int cmpAtD = 0;
            for (int i = 0; i < pairsAtD; i++) {
                cmpAtD += counts[i][i + d]; // d = j - i
            }
            double e = cmpAtD / (double) (trials * pairsAtD);
            double p = 2.0 / (d + 1.0);
            StdOut.printf("%2d   %10.6f     %10.6f     %.6f\n", d, e, p, e - p);
        }
        StdOut.println();



        double theoryPairsSum = 0.0;
        for (int d = 1; d <= N - 1; d++) {
            theoryPairsSum += (N - d) * (2.0 / (d + 1.0));
        }
        double approx2NlnN = 2.0 * N * Math.log(N);

        double empiricalMean = sumCmp / (double) trials;

        StdOut.printf("Empirical mean compares: %.3f\n", empiricalMean);
        StdOut.printf("Theory (2/(d+1)): %.3f\n", theoryPairsSum);
        StdOut.printf("Approx 2N ln N:                  %.3f\n", approx2NlnN);

    }

    public static void main(String[] args) {
        int N = 100;         
        int trials = 1000;   
        long seed = 42L;

        run(N, trials, seed);
    }
}

// N=100, trials=1000
//  d   empirical P     2/(d+1)      abs diff
//  1     1.000000       1.000000     0.000000
//  2     0.666194       0.666667     -0.000473
//  3     0.499536       0.500000     -0.000464
//  4     0.399292       0.400000     -0.000708
//  5     0.333400       0.333333     0.000067
//  6     0.285979       0.285714     0.000264
//  7     0.249624       0.250000     -0.000376
//  8     0.221783       0.222222     -0.000440
//  9     0.199857       0.200000     -0.000143
// 10     0.181500       0.181818     -0.000318
// 11     0.166449       0.166667     -0.000217
// 12     0.153818       0.153846     -0.000028

// Empirical mean compares: 762.865
// Theory (2/(d+1)): 647.850
// Exact  E[C_N]:   647.850
// Approx 2N ln N:                  921.034
// Empirical - Exact:                115.015