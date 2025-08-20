import edu.princeton.cs.algs4.StdOut;

public class P_2_4_15 {
    public static boolean isMinHeap(Comparable[] a) {
        for (int c = 1; c < a.length; c++) {
            int p = (c - 1) / 2; // 0-based
            if (a[p].compareTo(a[c]) > 0) {
                return false;
            }
        }
        return true;
    }

    // Return the 1st violating child index
    public static int firstViolation(Comparable[] a) {
        for (int c = 1; c < a.length; c++) {
            int p = (c - 1) / 2; // 0-based
            if (a[p].compareTo(a[c]) > 0) {
                return c;
            }
        }
        return -1;
    }

    public static boolean isMinHeapRecur(Comparable[] pq) {
        int n = pq.length;
        return isMinHeapOrdered(pq, 0, n);
    } 

    private static boolean isMinHeapOrdered(Comparable[] pq, int k, int n) {
        // 0-based indexing, n is exclusive
        if (k >= n) return true;
        int left = 2 * k + 1;
        int right = 2 * k + 2;
        if (left < n && less(pq, left, k)) return false;
        if (right < n && less(pq, right, k)) return false;
        return isMinHeapOrdered(pq, left, n) && isMinHeapOrdered(pq, right, n);

    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    public static void main(String[] args) {
        Integer[] ok  = {1, 2, 3, 4, 5, 6};
        Integer[] bad = {1, 5, 2, 7, 0, 6};

        StdOut.println(isMinHeap(ok));           // true
        StdOut.println(isMinHeap(bad));          // false
        StdOut.println(firstViolation(bad));     // 4 (since 0 < parent 5)

        StdOut.println(isMinHeapRecur(ok));           // true
        StdOut.println(isMinHeapRecur(bad));          // false
    }
}
