public class _HeapSort {
    public static void sort(Comparable[] a) {
        int N = a.length;

        // Phase 1: Build a max-heap
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        // Phase 2: Sort-down phase
        while (N > 1) { 
            exch(a, 1, N--); // swap the max element to the end
            sink(a, 1, N);  // restore heap order
        }
    }

    private static void sink(Comparable[] a, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k; // left child
            if (j < n && less(a, j, j+1)) {
                j++; // the larger child
            }
            exch(a, k, j);
            k = j; // move down
        }
    }

    // Helper (the heap array is 1-based indexing!!!)
    private static boolean less(Comparable[] a, int i, int j) {
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = t;
    }


}