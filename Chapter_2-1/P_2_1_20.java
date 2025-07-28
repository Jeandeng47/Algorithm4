import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_20 {
    private static int[] hs = {40, 13, 4, 1};
    private static int compares;

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void shellSort(Comparable[] a) {
        int N = a.length;
        compares = 0; // reset

        for (int h : hs) {
            for (int i = h; i < N; i++) {
                // swap a[j] with a[j-h], a[j-2h], ...
                for (int j = i; j >= h; j -= h) {
                    compares++;
                    if (less(a[j], a[j-h])) {
                        exch(a, j, j - h);
                    } else {
                        break;
                    }
                    
                }
            }
        }
    }

    public static long sortCount(Comparable[] a) {
        shellSort(a);
        return compares;
    }

    private static void shuffle(Integer[] a, Random rnd) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Integer tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int RANDOM_TRIALS = 200;
        Random RND = new Random(42);

        // Best case: ascending 1..N
        Integer[] asc = new Integer[N];
        for (int i = 0; i < N; i++) asc[i] = i + 1;
        long cAsc = sortCount(asc);
        StdOut.printf("Ascending compares: %d%n", cAsc);

        // Worst case: descending N..1
        Integer[] desc = new Integer[N];
        for (int i = 0; i < N; i++) desc[i] = N - i;
        long cDesc = sortCount(desc);
        StdOut.printf("Descending compares: %d%n", cDesc);

        // Random case: average over RANDOM_TRIALS permutations
        long sum = 0;
        Integer[] rndArr = new Integer[N];
        for (int t = 0; t < RANDOM_TRIALS; t++) {
            // fill 1..N
            for (int i = 0; i < N; i++) rndArr[i] = i + 1;
            shuffle(rndArr, RND);
            sum += sortCount(rndArr);
        }
        double avg = (double) sum / RANDOM_TRIALS;
        StdOut.printf("Random-case average compares (%d trials): %.2f%n",
                      RANDOM_TRIALS, avg);
     
    }
}
