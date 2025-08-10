import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_5 {
    private static int cmp;
    private static int exch;

    // Modify quick-sort to handle array with only 2 distinct elements
    public static void quickSort2Distinct(Comparable[] a, boolean trace) {
        int N = a.length;
        sort(a, 0, N - 1,trace);
    }

    // Use 3-way partition
    private static void sort(Comparable[] a, int lo, int hi, boolean trace) {
        // base case!
        if (lo >= hi) return;

        // recursive case
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        Comparable v = a[lo];
        int step = 0;

        if (trace) {
            StdOut.println("step  lt  i  gt  action        array");
            StdOut.printf("%-5d%-4d%-4d%-4d%-13s%s%n",
                    step, lt, i, gt, "start", Arrays.toString(a));
        }

        while (i <= gt) {
            int cmp = cmp(a[i], v);
            if (cmp < 0) {
                exch(a, lt++, i++);
                if (trace) log(++step, lt, i, gt, "exch lt,i(<)", a);
            }
            else if (cmp > 0) {
                exch(a, i, gt--);
                if (trace) log(++step, lt, i, gt, "exch i,gt(>)", a);
            }
            else {
                i++;
                if (trace) log(++step, lt, i, gt, "== pivot", a);
            }
        }

        // No need for recursive calls for 2 keys
        // With 2 distinct keys, left blocks all < v, right block all > v
        // The array is already sorted
    }


    // Helper functions

    private static int cmp(Comparable x, Comparable y) { cmp++; return x.compareTo(y); }

    private static void log(int step, int lt, int i, int gt, String action, Object[] a) {
        StdOut.printf("%-5d%-4d%-4d%-4d%-13s%s%n", step, lt, i, gt, action, Arrays.toString(a));
    }

    private static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        exch++;

        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void runCase(String name, Integer[] a, boolean trace) {
        Integer[] b = a.clone();
        StdOut.printf("%s before: %s%n", name, Arrays.toString(b));
        resetCounters();
        quickSort2Distinct(b, trace);
        StdOut.printf("%s after : %s%n", name, Arrays.toString(b));
        StdOut.printf(" -> compares=%d, exchanges=%d, sorted=%b%n%n",
                cmp, exch, isSorted(b));
    }

    private static void resetCounters() { cmp = 0; exch = 0; }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i-1].compareTo(a[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    private static Integer[] randTwoKeyArray(int N, int k1, int k2, double pk1) {
        Random rnd = new Random(42);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = (rnd.nextDouble() < pk1) ? k1 : k2;
        }
        return a;
    }
    
    
    public static void main(String[] args) {
        Integer[][] cases = new Integer[][]{
            // 0/1 cases
            {0,1,1,0,1,0,1,0,0,1},               // alternating-ish
            {1,1,1,1,1,1,1,1,1,1},               // all same (already sorted)
            {0,0,0,0,1,1,1,1,1,1},               // already sorted
            {1,1,1,1,1,1,1,1,0,0},               // almost reversed
            randTwoKeyArray(20, 0, 1, 0.5)       // random
        };

        // Print a full TRACE 
        runCase("Case 1 (trace)", cases[0], true);

        for (int k = 1; k < cases.length; k++) {
            runCase("Case " + (k+1), cases[k], false);
        }
    }
}

// Case 1 (trace) before: [0, 1, 1, 0, 1, 0, 1, 0, 0, 1]
// step  lt  i  gt  action        array
// 0    0   1   9   start        [0, 1, 1, 0, 1, 0, 1, 0, 0, 1]
// 1    0   1   8   exch i,gt(>) [0, 1, 1, 0, 1, 0, 1, 0, 0, 1]
// 2    0   1   7   exch i,gt(>) [0, 0, 1, 0, 1, 0, 1, 0, 1, 1]
// 3    0   2   7   == pivot     [0, 0, 1, 0, 1, 0, 1, 0, 1, 1]
// 4    0   2   6   exch i,gt(>) [0, 0, 0, 0, 1, 0, 1, 1, 1, 1]
// 5    0   3   6   == pivot     [0, 0, 0, 0, 1, 0, 1, 1, 1, 1]
// 6    0   4   6   == pivot     [0, 0, 0, 0, 1, 0, 1, 1, 1, 1]
// 7    0   4   5   exch i,gt(>) [0, 0, 0, 0, 1, 0, 1, 1, 1, 1]
// 8    0   4   4   exch i,gt(>) [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]
// 9    0   5   4   == pivot     [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]
// Case 1 (trace) after : [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]
//  -> compares=9, exchanges=5, sorted=true

// Case 2 before: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
// Case 2 after : [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
//  -> compares=9, exchanges=0, sorted=true

// Case 3 before: [0, 0, 0, 0, 1, 1, 1, 1, 1, 1]
// Case 3 after : [0, 0, 0, 0, 1, 1, 1, 1, 1, 1]
//  -> compares=9, exchanges=5, sorted=true

// Case 4 before: [1, 1, 1, 1, 1, 1, 1, 1, 0, 0]
// Case 4 after : [0, 0, 1, 1, 1, 1, 1, 1, 1, 1]
//  -> compares=9, exchanges=2, sorted=true

// Case 5 before: [1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1]
// Case 5 after : [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
//  -> compares=19, exchanges=10, sorted=true
