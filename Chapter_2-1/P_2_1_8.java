import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_8 {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2]...
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }    
    
    public static void main(String[] args) {
        int[] sizes = {2000, 4000, 8000, 16000, 32000};
        int   trials = 5;
        Random rand  = new Random(42);

        double[] times = new double[sizes.length];
        for (int s = 0; s < sizes.length; s++) {
            long totalTime = 0;
            int N = sizes[s];

            // for each size, run 5 trials
            for (int t = 0; t < trials; t++) {
                Integer[] a = new Integer[N];
                for (int i = 0; i < N; i++) {
                    a[i] = rand.nextInt(3);  // 0, 1 or 2
                }
                long start = System.nanoTime();
                insertionSort(a);
                totalTime += System.nanoTime() - start;
            }

            double avgMs = totalTime / 1e6 / trials;
            times[s] = avgMs;
        }

        StdOut.printf("%-8s%15s%12s%n", "N", "Avg time (ms)", "Ratio");
        StdOut.println("------------------------------------------------");

        // Print each row
        for (int i = 0; i < sizes.length; i++) {
            double ratio = (i > 0)
            ? times[i] / times[i-1]
            : Double.NaN;  // or just 0 or "-"
            StdOut.printf(
                "%-8d%15.2f%12.2f%n",
                sizes[i], times[i], ratio
            );
        }
    }
}

// example output: 
// N         Avg time (ms)       Ratio
// ------------------------------------------------
// 2000               3.46         NaN
// 4000               5.28        1.52
// 8000              20.61        3.90
// 16000             76.71        3.72
// 32000            224.80        2.93

// The time complexity converges to O(N^2)