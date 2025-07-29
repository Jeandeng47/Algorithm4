import edu.princeton.cs.algs4.StdOut;

public class P_2_2_9 {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void sortTD(Comparable[] a) {
        int N = a.length;
        // instead of a static array, use 
        Comparable[] aux = new Comparable[N];

        sort(a, aux, 0, N - 1);
    }

    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];   
        }
    }
    public static void main(String[] args) {
        Integer[] data1 = {5,2,9,1,5,6};
        Integer[] data2 = {3,4,1,7,2,8};

        // two sort could be safely called at the same time
        sortTD(data1);
        sortTD(data2);

        // Print results
        StdOut.println(java.util.Arrays.toString(data1));
        StdOut.println(java.util.Arrays.toString(data2)); 
    }
}
