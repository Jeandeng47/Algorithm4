import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_10 {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static int[] makeIncrements(int N) {
        int cnt = 0;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
            cnt++;
        }

        int[] inc = new int[cnt + 1];
        for (int i = 0; i <= cnt; i++) {
            inc[i] = h;
            h = (h - 1) / 3;
        }
        return inc;
    }

    public static void shellSortInsertion(Comparable[] a) {
        int N = a.length;
        int[] hs = makeIncrements(N);
        for (int h : hs) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);  
                }
            }
        }
    }


    // Shell sort using selection sort:
    // Example: a = [ 8, 5, 3, 7, 1, 6, 4, 2 ], N = 8, when h = 3

    // start = 0: subsequence [a[0], a[3], a[6]] = [8, 7, 4]
    // sort -> [4, 7, 8], a = [ 4, 5, 3, 7, 1, 6, 8, 2 ]

    // start = 1: subsequence [a[1], a[4], a[7]] = [5, 1, 2]
    // sort -> [1, 2, 5], a = [ 4, 1, 3, 7, 2, 6, 8, 5 ]

    // start = 2: subsequence [a[2], a[5]] = [3, 6]
    // sort -> [3, 6], a = [ 4, 1, 3, 7, 2, 6, 8, 5 ]

    public static void shellSortSelection(Comparable[] a) {
        int N = a.length;
        int[] hs = makeIncrements(N);
        for (int h : hs) {
            for (int start = 0; start < h; start++) {
                // for each h, do selection sort on each of the h subsequences
                // a[s], a[s+h], a[s+2h]...
                for (int i = start; i < N; i += h) {
                    int minIdx = i;
                    for (int j = i + h; j < N; j +=h) {
                        if (less(a[j], a[minIdx])) {
                            minIdx = j;
                        }
                    }
                    exch(a, i, minIdx);
                }
            }
        }

    }

     public static void main(String[] args) {

        int N = 10000;     
        int trials = 5;        
        Random rand = new Random(42);
        
        long insTotal = 0, selTotal = 0;

        for (int t = 0; t < trials; t++) {
            // generate one random array
            Integer[] base = new Integer[N];
            for (int i = 0; i < N; i++) {
                base[i] = rand.nextInt();
            }
            Integer[] a = base.clone();
            Integer[] b = base.clone();

            long start = System.nanoTime();
            shellSortInsertion(a);
            insTotal += System.nanoTime() - start;

            start = System.nanoTime();
            shellSortSelection(b);
            selTotal += System.nanoTime() - start;
        }

        double insMs = insTotal / 1e6 / trials;
        double selMs = selTotal / 1e6 / trials;

        StdOut.printf("Shell+insertion avg: %.1f ms%n", insMs);
        StdOut.printf("Shell+selection avg: %.1f ms%n", selMs);
    }
}

// Use insertion sort could use the partially sorted h-subsequences, 
// reducing the number of data movements

// output:
// Shell+insertion avg: 1.9 ms
// Shell+selection avg: 70.9 ms