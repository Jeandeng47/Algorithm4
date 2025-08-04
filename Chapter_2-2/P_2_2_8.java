import edu.princeton.cs.algs4.StdOut;

public class P_2_2_8 {
    private static Comparable[] aux;
    private static int compares;

    private static boolean less(Comparable v, Comparable w) {
        compares++; // increase cmp
        return v.compareTo(w) < 0;
    }

    public static void sortTD(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];

        compares = 0; // reset
        sort(a, 0, N - 1);
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
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

    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        // speed up modification: skip merge
        // if a[mid] < a[mid + 1]
        if (less(a[mid], a[mid + 1])) return;
        merge(a, lo, mid, hi); // merge is most expensive operation
    }

    public static void main(String[] args) {
        StdOut.printf("%4s %10s%n", "N", "C(N)");
        StdOut.println("----------------------");
        for (int N = 1; N <= 512; N *= 2) {
            // prepare a sorted array [0,1,2,...,N-1]
            Integer[] a = new Integer[N];
            for (int i = 0; i < N; i++) a[i] = i;
            // sort and count
            sortTD(a);
            StdOut.printf("%4d %10d%n", N, compares);
        }
    }
}

//    N       C(N)
// ----------------------
//    1          0
//    2          1
//    4          3
//    8          7
//   16         15
//   32         31
//   64         63
//  128        127
//  256        255
//  512        511