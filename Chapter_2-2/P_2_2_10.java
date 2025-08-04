import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_10 {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void sortTD(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        sort(a, aux, 0, N - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        mergeFast(a, aux, lo, mid, hi);
        
    }

    // Faster merge: copy left half in order and copy right half 
    // in decreasing order
    private static void mergeFast(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy left half
        for (int i = lo; i <= mid; i++) {
            aux[i] = a[i];
        }

        // copy right half in increasing order
        for (int j = mid + 1; j <= hi; j++) {
            // j - (mid + 1): steps that j moves forwards from mid + 1
            aux[hi + (mid + 1) - j] =  a[j];
        }

        int i = lo; // take from left (i++)
        int j = hi; // take from right (j--)
        for (int k = lo; k <= hi; k++) {
            if (less(aux[j], aux[i])) {
                a[k] = aux[j--];
            } else {
                a[k] = aux[i++];
            }
        }
        
    }
    public static void main(String[] args) {
        Integer[] a1 = {5,1,4,2,3,8,7,6,9,0,10,11,12,13,14,15};
        Integer[] a2 = a1.clone();

        Arrays.sort(a1);
        sortTD(a2);

        StdOut.println("Arrays sort: " + Arrays.toString(a1));
        StdOut.println("Merge-fast : " + Arrays.toString(a2));
    }
}

// Example:
// aux[lo...mid] ascending, aux[mid+1...hi] descending
// aux[mid] is max of left half, aux[mid + 1] is max of right half

// i scans lo -> mid:
// if i > mid, it means we have exhausted the left half, 
// now we have aux[i] = aux[mid + 1] = right max,
// comparing aux[j] and aux[i] allows us to take from right.
// and j will always stop at aux[mid+!]

// j scans hi -> mid + 1:
// if j < mid + 1, it means we have exhausted the right half
// now we have aux[j] = aux[mid] = left max
// comparing aux[j] and aux[i] allows us to take from left

// Aux[0...4] = [1, 3, 5, 4, 2], lo = 0, mid = 1, hi = 4
// k        aux[i]      aux[j]      compare             i       j       
// 0        1           2           1 < 2, a[0] = 1     1       4
// 1        3           2           2 < 3, a[1] = 2     1       3
// 2        3           4           3 < 4, a[2] = 3     2       3
// 3        5*          4           4 < 5, a[3] = 4     2       2
// 4        5*          5           5 = 5, a[4] = 5     3       2