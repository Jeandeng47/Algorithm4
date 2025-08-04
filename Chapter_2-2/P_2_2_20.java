import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_20 {

    // Implement a version of merge-sort that does not 
    // rearrange the array but return int[] perm, where
    // perm[i] is the ith smallest entry in the array
    public static int[] indirectMergeSort(Comparable[] a) {
        int N = a.length;
        // init the perm: a[i] is at i
        int[] perm = new int[N];
        for (int i = 0; i < N; i++) perm[i] = i;

        int[] aux = new int[N];
        sort(a, aux, perm, 0, N - 1);

        return perm;
    }

    public static void sort(Comparable[] a, int[] aux, int[] perm, int lo, int hi) {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, perm, lo, mid);
        sort(a, aux, perm, mid + 1, hi);
        merge(a, aux, perm, lo, mid, hi);
    } 

    private static void merge(Comparable[] a, int[] aux, int[] perm, 
    int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++) {
            // aux[k] = a[k], the original aux records actual elements
            // here we record index of elements
            aux[k] = perm[k];
        }

        int i = lo;         // pointer to walk left half
        int j = mid + 1;    // pointer to walk right half
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                perm[k] = aux[j++];
            } else if (j > hi) {
                perm[k] = aux[i++];
            } else if (a[aux[i]].compareTo(a[aux[j]]) <= 0) {
                // aux[i], aux[j]: index of element 
                perm[k] = aux[i++];
            } else {
                perm[k] = aux[j++];
            }
        }

        
    }
    
    public static void main(String[] args) {
        Integer[] a = { 50, 20, 40, 10, 30 };
        int[] perm = indirectMergeSort(a);

        StdOut.println("Original: " + Arrays.toString(a));
        StdOut.print  ("Sorted via perm: [");
        for (int k = 0; k < perm.length; k++) {
            StdOut.print(a[perm[k]] + (k + 1 < perm.length? ", ":""));
        }
        StdOut.println("]");

        StdOut.println("perm = " + Arrays.toString(perm));
    }
}


// Example input:
// a = [50, 20, 40, 10, 30] -> 
// perm before = [0, 1, 2, 3, 4]
// perm after = [3, 1, 4, 2, 0]

// Example output:
// Original: [50, 20, 40, 10, 30]
// Sorted via perm: [10, 20, 30, 40, 50]
// perm = [3, 1, 4, 2, 0]