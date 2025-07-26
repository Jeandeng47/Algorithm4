public class _ShellSort {
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(Comparable[] a) {
        // check ascending order
        for (int i = 0; i < a.length; i++) {
            // if a[i] is less than a[i-1], a reverse pair
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    } 

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        // h : 1, 4, 13, 40, 121, 364, 1093, ...
        while (h < N / 3) {
            h = 3 * h + 1;
        }
        
        while (h >= 1) {
            // outter loop: iterate through a[h]...a[N-1]
            for (int i = h; i < N; i++) {
                // inner loop: compare a[i] with a[i - h], a[i - 2h]...
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            
            h = h / 3;
        }
    }
}
