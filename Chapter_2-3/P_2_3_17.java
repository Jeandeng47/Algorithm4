import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_17 {
    public static void quickSort(Comparable[] a) {
        int N = a.length;

        StdRandom.shuffle(a);
        
        // put max at the end of array
        int max = 0;
        for (int i = 1; i < N; i++) {
            if (less(a[max], a[i])) {
                max = i; // record index
            }
        }
        exch(a, max, N - 1);
        
        sort(a, 0, N - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        while (true) {

            // Left check: v == a[lo] is sentinel, a[j] never equals to 
            // v (a[lo]) s.t. j never reaches lo, if (j == lo) is redundant

            // Right check: to remove (i == hi), we should set a guard value
            // at hi s.t. i never reaches hi. Therefore we should guarantee
            // that a[hi] should hold the max value

            while (less(a[++i], v)) { }       
            while (less(v, a[--j])) { }
            
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    

    public static void main(String[] args) {
        Integer[] a = {5, 3, 9, 1, 7, 2, 8, 4, 6};
        quickSort(a);
        StdOut.println(Arrays.toString(a));
        
    }
}
