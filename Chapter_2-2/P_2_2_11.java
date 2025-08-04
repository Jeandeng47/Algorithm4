import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_11 {

    private final static int CUTOFF = 7;

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void insertionSort(Comparable[] a, int lo, int hi, int depth) {
        StdOut.printf("%s insertionSort(dst)[%d..%d]: %s\n",
                      indent(depth), lo, hi, Arrays.toString(a));

        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j-1]); j--) {
                exch(a, j, j -1);
            }
        }

        StdOut.printf("%s after insertion: %s\n", indent(depth), Arrays.toString(a));
    }

    private static void merge(Comparable[] src, Comparable[] dst, int lo, int mid, int hi, int depth) {
        
       StdOut.printf("%s merge[%d..%d]: src=%s dst=%s\n", indent(depth), lo, hi,
                      Arrays.toString(src), Arrays.toString(dst));
        
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)               dst[k] = src[j++];
            else if (j > hi)                dst[k] = src[i++];
            else if (less(src[j], src[i])) dst[k] = src[j++];
            else                            dst[k] = src[i++];
        }

        StdOut.printf("%s after merge, dst: %s\n", indent(depth), Arrays.toString(dst));
    }

    public static void sortTD(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        // Copy a into aux, sort alternatively by reading from
        // src and writing to dst
        StdOut.println("Initial a: " + Arrays.toString(a));
        StdOut.println("Initial aux: " + Arrays.toString(aux));
        StdOut.println();

        System.arraycopy(a, 0, aux, 0, a.length);
        sort(aux, a, 0, N - 1, 0);
    }

    private static String indent(int depth) {
        char[] c = new char[depth * 2];
        Arrays.fill(c, '-');
        return new String(c);
    }

    public static void sort(Comparable[] src, Comparable[] dst, int lo, int hi, int depth) {

        String ind = indent(depth);
        StdOut.printf("%s sort[%d..%d]: src=%s dst=%s\n", ind, lo, hi,
                      Arrays.toString(src), Arrays.toString(dst));

        // Improv 1: insertion sort
        if (hi - lo <= CUTOFF) {
            insertionSort(dst, lo, hi, depth + 1);
            return;
        }

        // Improv 3: swap the role of src and dst
        int mid = lo + (hi - lo) / 2;
        sort(dst, src, lo, mid, depth + 1);
        sort(dst, src, mid + 1, hi, depth + 1);

        // Improv 2: if sorted, skip merge
        if (!less(src[mid+1], src[mid])) {
            StdOut.printf("%s skip merge [%d..%d], already ordered\n", ind, lo, hi);
            System.arraycopy(src, lo, dst, lo, hi - lo + 1);
            return;
        }

        merge(src, dst, lo, mid, hi, depth);  // merge from src to dst
    }
    public static void main(String[] args) {
        Random r = new Random();
        int N = 20;
        Integer[] a1 = new Integer[N];
        for (int i = 0; i < N; i++) {
            a1[i] = r.nextInt(N);
        }
        Integer[] a2 = a1.clone();

        Arrays.sort(a1);
        sortTD(a2);

        StdOut.println("Arrays sort.   : " + Arrays.toString(a1));
        StdOut.println("Merge improved : " + Arrays.toString(a2));
    }   
}


// 1. Improvement 1: Use insertion sort for small subarrays
// 2. Improvement 2: Skip merge() for already sorted array (a[mid] <= a[mid+1])
// 3. Improvement 3: Eliminate the copy to the auxiliary array



// Example intput:
// a = [8, 3, 5, 1], CUTOFF =1

// Initial a: [8, 3, 5, 1]
// Initial aux: [null, null, null, null]

 
// CALL at depth0: sort(src=aux, dst=a, lo=0, hi=3)
//  sort[0..3]: src=[8, 3, 5, 1] dst=[8, 3, 5, 1]

// -- LEFT at depth1: sort(src=a, dst=aux, lo=0, hi=1)
// -- sort[0..1]: src=[8, 3, 5, 1] dst=[8, 3, 5, 1]
// ---- insertionSort(dst)[0..1]: [8, 3, 5, 1]
// ---- after insertion: [3, 8, 5, 1]csa

// -- RIGHT at depth1: sort(src=a, dst=aux, lo=2, hi=3)
// -- sort[2..3]: src=[8, 3, 5, 1] dst=[3, 8, 5, 1]
// ---- insertionSort(dst)[2..3]: [3, 8, 5, 1]
// ---- after insertion: [3, 8, 1, 5]

// MERGE at depth0: merge(src=aux, dst=a, lo=0, hi=3)
//  merge[0..3]: src=[3, 8, 1, 5] dst=[8, 3, 5, 1]
//  after merge, dst: [1, 3, 5, 8]

// Arrays sort.   : [1, 3, 5, 8]
// Merge improved : [1, 3, 5, 8]

// Observations:
// 1. When we come to the base case, we use insertion sort
// to sort the dst array
// 2. When we return from the base case, we switch the role
// of dst[] and src[] when recursively sorting. The effect is
// that the sorted dst array become the src array (input) for 
// merge(), such that merge could write two sorted array to dst
// array correctly 
