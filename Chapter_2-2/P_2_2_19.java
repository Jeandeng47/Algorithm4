import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_19 {

    // Return the number of inversions in an array by simulating inversion sort
    public static int insertionSortInv(Comparable[] a) {
        int N = a.length;
        int inv = 0;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && (a[j].compareTo(a[j-1]) <= 0); j--) {
                // count inversions and fix
                inv++;
                swap(a, j, j - 1);
            }
        }
        return inv;
    }

    // Return the number of inversions in an array by simulating merge sort
    // In divide-and-conquer approach, inversion (i, j) with i < j
    // and (a[i] > a[j]) is either
    // 1. In the left half -> count when divide
    // 2. In the right half -> count when divide
    // 3. At the intersection of left and right half -> count when merge
    public static int mergeSortInv(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        return sortCount(a, aux, 0, N - 1);
    }

    // Recursively count the inversions and sort array
    public static int sortCount(Comparable[] a, Comparable[] aux, int lo, int hi) {
        // base case
        if (lo >= hi) return 0; 

        // recursive case:
        int mid = lo + (hi - lo) / 2;
        int count = 0;
        count += sortCount(a, aux, lo, mid);
        count += sortCount(a, aux, mid + 1, hi);
        count += mergeCount(a, aux, lo, mid, hi);

        return count;
    }


    // Merge the two sorted subarray and count inversion at the intersection
    public static int mergeCount(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++) aux[k] = a[k];

        int inv = 0;
        int i = lo;
        int j = mid + 1;

        
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)            a[k] = aux[j++];
            else if (j > hi)             a[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) <= 0)   a[k] = aux[i++];
            else {
                a[k] = aux[j++];
                // all remaining aux[i..mid] are > aux[j-1]
                // left : 2 4 6, right 1 3 5
                // if a[j] < a[i], all elements in left form inversions with this
                // right element
                // ex: 2 > 1, inv: (2, 1) (4, 1), (6, 1)
                inv += (mid - i + 1);
            }
        }
        return inv;
    }

    private static void swap(Comparable[] a, int i , int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


    public static void main(String[] args) {
        int N = 10000;
        Integer[] original = new Integer[N];
        for (int i = 0; i < N; i++) original[i] = i;
        
        // shuffle
        Random rnd = new Random(42);
        for (int i = N - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1); // range [0, i]
            swap(original, i, j);
        }

        // copy for each sort
        Integer[] a1 = Arrays.copyOf(original, N);
        Integer[] a2 = Arrays.copyOf(original, N);

        // mergesort-based count
        long t0 = System.currentTimeMillis();
        int invMS = mergeSortInv(a1);
        long t1 = System.currentTimeMillis();

        // inversionsort-based count
        int invIS = insertionSortInv(a2);
        long t2 = System.currentTimeMillis();

        StdOut.println("N = " + N);
        StdOut.printf("Mergesort count   = %d  (time = %d ms)%n",
                          invMS, (t1 - t0));
        StdOut.printf("Insertion count   = %d  (time = %d ms)%n",
                          invIS, (t2 - t1));
        StdOut.println("Counts match? " + (invMS == invMS));
        
    }
}




// Example output:
// N = 10000
// Mergesort count   = 25135423  (time = 2 ms)
// Insertion count   = 25135423  (time = 63 ms)
// Counts match? true