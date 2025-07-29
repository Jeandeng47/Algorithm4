public class _MergeSortTD {

    private static Comparable[] aux; // the auxiliary array

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

     public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo; // pointer for left array
        int j = mid + 1; // pointer for right array
        
        // copy elements to aux array
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // compare element from left & right array, 
        // put back to original array
        for (int k = lo; k <= hi; k++) {
            if (i > mid) { // no element at left, take from right
                a[k] = aux[j++]; 
            } else if (j > hi) { // no element at right, take from left
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) { // right < left, take from right
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void sortTD(Comparable[] a) {
        aux = new Comparable[a.length];
        sortTDHelper(a, 0, a.length - 1);
    }

    public static void sortTDHelper(Comparable[] a, int lo, int hi) {
        // base case
        if (lo >= hi) return;
        
        // recursive case
        int mid = lo + (hi - lo) / 2;
        sortTDHelper(a, lo, mid);
        sortTDHelper(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }
}
