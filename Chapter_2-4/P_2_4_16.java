import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_16 {
    private static int cmp;

    // heap sort (0-based)
    public static int heapSort(Comparable[] a) {
        cmp = 0;
        int N = a.length;

        // 1. build max heap
        for (int k = N / 2 - 1; k >= 0; k--) {
            sink(a, k, N);
        }

        // 2. sort down
        for (int end = N - 1; end > 0; end--) {
            exch(a, 0, end);      // max to end
            sink(a, 0, end);      // heap size = end
        }

        return cmp;
    }

    private static void sink(Comparable[] a, int k, int N) {
        while (2 * k + 1 < N) {
            int j = 2 * k + 1;
            if (j + 1 < N && less(a, j, j + 1)) {
                j++;
            }
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        cmp++;
        return a[i].compareTo(a[j]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i-1].compareTo(a[i]) > 0) return false;
        }
        return true;
    }


    // Array builders (0-based)

    private static Integer[] buildAscArr(int N) {
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) a[i] = i+1;  // 1..N
        return a;
    }
    private static Integer[] buildDescArr(int N) {
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) a[i] = N - i; // N..1
        return a;
    }

    private static void runCase(String name, Integer[] a0) {
        Comparable[] a = Arrays.copyOf(a0, a0.length);
        int c = heapSort(a);
        System.out.printf("%-10s -> comparisons = %d, sorted? %s%n",
                name, c, isSorted(a));
    }

    public static void main(String[] args) {
        int N = 32;

        runCase("Asc [1..N]",       buildAscArr(N));
        runCase("Desc [N..1]",      buildDescArr(N));
    }
}


// Asc [1..N] -> comparisons = 231, sorted? true
// Desc [N..1] -> comparisons = 202, sorted? true