import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_7 {
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
        merge(a, lo, mid, hi);
    }

    private static Integer[] getRandArr(int size) {
        Integer[] a = new Integer[size];
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            a[i] = r.nextInt(100);
        }
        return a;
    }

    public static void main(String[] args) {
        
        StdOut.printf("%4s %10s %10s%n", "N", "C(N)", "C(N)-C(N-1)");
        StdOut.println("----------------------------------");

        int prev = 0;
        for (int N = 1; N <= 512; N *= 2) {
            Integer[] a = getRandArr(N);

            sortTD(a);
            int c = compares;
            int d = (N == 1)? c : c - prev;
            prev = c;

            System.out.printf("%4d %10d %10d%n", N, c, d);
        }

        
    }
}


//    N       C(N) C(N)-C(N-1)
// ----------------------------------
//    1          0          0
//    2          1          1
//    4          5          4
//    8         15         10
//   16         47         32
//   32        123         76
//   64        306        183
//  128        732        426
//  256       1725        993
//  512       3974       2249