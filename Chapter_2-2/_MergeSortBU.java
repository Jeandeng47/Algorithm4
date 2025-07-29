public class _MergeSortBU {
    
    private static Comparable[] aux;

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++]; 
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void sortBU(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];

        // size = 1, 2, 4, 8, ....
        for (int size = 1; size < N; size = 2 * size) {
            for (int lo = 0; lo < N - size; lo += 2 * size) {
                merge(a, lo, lo+size-1, Math.min(lo+2*size-1, N -1));
            }
        }
    }
}
