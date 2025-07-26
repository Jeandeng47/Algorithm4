public class _SelectionSort {
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
}
