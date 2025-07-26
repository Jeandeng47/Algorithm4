public class _InsertionSort {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(Comparable[] a) {
        // check ascending order
        for (int i = 1; i < a.length; i++) {
            // if a[i] is less than a[i-1], a reverse pair
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    } 

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // insert a[i] among a[i-1], a[i-2]...
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }    
}
