import java.util.Arrays;

public class P_2_1_6 {
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

    public static void seletcionSort(Comparable[] a) {
        int N = a.length;
        
        // compare a[i] and a[i+1]...a[N-1]
        for (int i = 0; i < N; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                // if a[j] is smaller, update min
                if (less(a[j], a[minIdx])) {
                    minIdx = j;
                }
            }
            // always exchange a[i] with a[min]
                exch(a, i, minIdx);
        }
    }

    public static void main(String[] args) {
        int N = 10000;
        int trials = 5;

        long seletcionT = 0;
        long insertionT = 0;

        for (int t = 0; t < trials; t++) {
            Integer[] a = new Integer[N];
            Arrays.fill(a, 1);
            Integer[] b = a.clone();

            long start = System.nanoTime();
            seletcionSort(a);
            seletcionT += System.nanoTime() - start;

            start = System.nanoTime();
            insertionSort(b);
            insertionT += System.nanoTime() - start;
        }

        double selMs = seletcionT / 1e6 / trials;
        double insMs = insertionT / 1e6 / trials;

        System.out.printf("Selection sort avg: %.1f ms%n", selMs);
        System.out.printf("Insertion sort avg: %.1f ms%n", insMs);
    }

}


// For array with identical keys, insertion sort is much faster.
// 1. For insertion sort, it only does N - 1 comparisons and 0 swaps.
// 2. For selection sort, it always do ~N^2 comparisons and N - 1 swaps.

// Output: N = 10000, trials = 5
// Selection sort avg: 26.1 ms
// Insertion sort avg: 0.2 ms