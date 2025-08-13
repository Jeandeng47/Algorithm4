import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_22 {
    public static void quickSortFast(Integer[] a) {
        int N = a.length;
        // StdRandom.shuffle(a);
        sort(a, 0, N - 1, 0);
    }

    private static void sort(Integer[] a, int lo, int hi, int depth) {
        if (lo >= hi) return;
        log(depth, "CALL sort(lo=%d, hi=%d) sub=%s", lo, hi, slice(a, lo, hi));
        int[] mid = partition(a, lo, hi, depth);
        StdOut.println();
        log(depth, "AFTER partition sub=%s  <v:[%d..%d]  ==v:[%d..%d]  >v:[%d..%d]",
                slice(a, lo, hi), lo, mid[0], mid[0] + 1, mid[1] - 1, mid[1], hi);
        sort(a, lo, mid[0], depth + 1);  // < v
        sort(a, mid[1], hi, depth + 1);  // > v
    }
    

    private static int[] partition(Integer[] a, int lo, int hi, int depth) {
        int i = lo, j = hi + 1;
        int p = lo, q = hi + 1;
        Integer v = a[lo];

        log(depth, "PART lo=%d hi=%d  pivot v=a[%d]=%s", lo, hi, lo, v);
        printState(a, lo, hi, i, j, p, q, depth, "start");

        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            log(depth, "scanL stop: i=%d %s", i, (i <= hi ? "(" + a[i] + ")" : "(hit hi)"));

            while (less(v, a[--j])) if (j == lo) break;
            log(depth, "scanR stop: j=%d %s", j, (j >= lo ? "(" + a[j] + ")" : "(hit lo)"));

            // pointers cross (tie case)
            if (i == j && eq(a[i], v)) {
                exch(a, ++p, i);
                log(depth, "tie & eq: exch(++p=%d, i=%d)", p, i);
                printState(a, lo, hi, i, j, p, q, depth, "after tie-eq move");
            }

            if (i >= j) {
                log(depth, "break: i=%d >= j=%d", i, j);
                break;
            }
            log(depth, "swap  a[%d]=%s ↔ a[%d]=%s", i, a[i], j, a[j]);
            exch(a, i, j);
            printState(a, lo, hi, i, j, p, q, depth, "after swap(i,j)");

            // park equals to the ends
            if (eq(a[i], v)) {
                exch(a, ++p, i);
                log(depth, "park-eq-left: exch(++p=%d, i=%d)", p, i);
                printState(a, lo, hi, i, j, p, q, depth, "after park-left");
            }
            if (eq(a[j], v)) {
                exch(a, --q, j);
                log(depth, "park-eq-right: exch(--q=%d, j=%d)", q, j);
                printState(a, lo, hi, i, j, p, q, depth, "after park-right");
            }
            StdOut.println();
        }
        // At the end of the while loop, we have
        // [ =v |    <v      |       |   >v  | =v ]
        // lo   p            j       i       q    hi

        // We could gather equlas to the center (like Dijsktra)
        // Move =v from a[lo..p-1] to a[j--]
        // Move =v from a[q+1..hi] to a[i++]

        // [     <v      |    =v         |   >v   ]
        // lo            j               i        hi

        i = j + 1;
        log(depth, "gather equals: i=j+1=%d", i);

        for (int k = lo; k <= p; k++) {
            exch(a, k, j--);
            log(depth, "gather-left  exch(k=%d, j+1) → j=%d", k, j + 1);
            printState(a, lo, hi, i, j, p, q, depth, "after gather-left");
        }
        for (int k = hi; k >= q; k--) {
            exch(a, k, i++);
            log(depth, "gather-right exch(k=%d, i-1) → i=%d", k, i - 1);
            printState(a, lo, hi, i, j, p, q, depth, "after gather-right");
        }

        // result: [lo..j] < v, [j+1..i-1] == v, [i..hi] > v
        log(depth, "DONE: <v=[%d..%d], ==v=[%d..%d], >v=[%d..%d]", lo, j, j + 1, i - 1, i, hi);
        return new int[]{ j, i };
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;    // optimization when reference equal
        return v.compareTo(w) < 0;
    }

    private static boolean eq(Comparable v, Comparable w) {
        if (v == w) return true;    // optimization when reference equal
        return v.compareTo(w) == 0;
    }

    // Trace helpers
    private static void printState(Comparable[] a, int lo, int hi, int i, int j, int p, int q,
                                   int depth, String note) {
        log(depth, "  a[lo..hi]=%s", slice(a, lo, hi));
        log(depth, "  lo=%d hi=%d | i=%d j=%d | p=%d q=%d  -- %s",
                lo, hi, i, j, p, q, note);
    }

    private static String slice(Comparable[] a, int lo, int hi) {
        StringBuilder sb = new StringBuilder("[");
        for (int k = lo; k <= hi; k++) {
            if (k > lo) sb.append(' ');
            sb.append(a[k]);
        }
        sb.append(']');
        return sb.toString();
    }

    private static void log(int depth, String fmt, Object... args) {
        char[] ind = new char[depth * 2];
        Arrays.fill(ind, ' ');
        StdOut.print(new String(ind));
        StdOut.printf(fmt, args);
        StdOut.println();
    }

    public static void main(String[] args) {
        // Integer[] a = {5,3,5,2,5,7,3,5,1,5,5,4,3,2,5,6,5,3};
        Integer[] a = {5,3,3,2,5,5,5,3};
        quickSortFast(a);  

        // print final array
        StdOut.print("FINAL: ");
        for (int v : a) StdOut.print(v + " ");
        StdOut.println();
    }
}


// Fast 3-way partitiooning (Bentley and McIlroy)

// During [ =v |    <v      |       |   >v  | =v ]
//        lo   p            i       j       q    hi

// After  [     <v      |    =v         |   >v   ]
//        lo            j               i        hi
//        ltLo          ltHi            gtLo     gtHi

// Maintain indices p, q, i, j
// 1. a[lo..p-1], a[q+1..hi]: equal to a[lo]
// 2. a[p..i-1]: less than a[lo]
// 3. a[j+1..q]: greater than a[lo]
// 4. Add to the inner partitioning loop code to 
// swap a[i] with a[p] (and increment p) if it is equal to v
//  and to swap a[j] with a[q] (and decrement q) if it is equal 
// to v before the usual comparisons of a[i] and a[j] with v. 




// CALL sort(lo=0, hi=7) sub=[5 3 3 2 5 6 5 3]
// PART lo=0 hi=7  pivot v=a[0]=5
//   a[lo..hi]=[5 3 3 2 5 6 5 3]
//   lo=0 hi=7 | i=0 j=8 | p=0 q=8  -- start
// scanL stop: i=4 (5)
// scanR stop: j=7 (3)
// swap  a[4]=5 ↔ a[7]=3
//   a[lo..hi]=[5 3 3 2 3 6 5 5]
//   lo=0 hi=7 | i=4 j=7 | p=0 q=8  -- after swap(i,j)
// park-eq-right: exch(--q=7, j=7)
//   a[lo..hi]=[5 3 3 2 3 6 5 5]
//   lo=0 hi=7 | i=4 j=7 | p=0 q=7  -- after park-right

// scanL stop: i=5 (6)
// scanR stop: j=6 (5)
// swap  a[5]=6 ↔ a[6]=5
//   a[lo..hi]=[5 3 3 2 3 5 6 5]
//   lo=0 hi=7 | i=5 j=6 | p=0 q=7  -- after swap(i,j)
// park-eq-left: exch(++p=1, i=5)
//   a[lo..hi]=[5 5 3 2 3 3 6 5]
//   lo=0 hi=7 | i=5 j=6 | p=1 q=7  -- after park-left

// scanL stop: i=6 (6)
// scanR stop: j=5 (3)
// break: i=6 >= j=5
// gather equals: i=j+1=6
// gather-left  exch(k=0, j+1) → j=5
//   a[lo..hi]=[3 5 3 2 3 5 6 5]
//   lo=0 hi=7 | i=6 j=4 | p=1 q=7  -- after gather-left
// gather-left  exch(k=1, j+1) → j=4
//   a[lo..hi]=[3 3 3 2 5 5 6 5]
//   lo=0 hi=7 | i=6 j=3 | p=1 q=7  -- after gather-left
// gather-right exch(k=7, i-1) → i=6
//   a[lo..hi]=[3 3 3 2 5 5 5 6]
//   lo=0 hi=7 | i=7 j=3 | p=1 q=7  -- after gather-right
// DONE: <v=[0..3], ==v=[4..6], >v=[7..7]

// AFTER partition sub=[3 3 3 2 5 5 5 6]  <v:[0..3]  ==v:[4..6]  >v:[7..7]
//   CALL sort(lo=0, hi=3) sub=[3 3 3 2]
//   PART lo=0 hi=3  pivot v=a[0]=3
//     a[lo..hi]=[3 3 3 2]
//     lo=0 hi=3 | i=0 j=4 | p=0 q=4  -- start
//   scanL stop: i=1 (3)
//   scanR stop: j=3 (2)
//   swap  a[1]=3 ↔ a[3]=2
//     a[lo..hi]=[3 2 3 3]
//     lo=0 hi=3 | i=1 j=3 | p=0 q=4  -- after swap(i,j)
//   park-eq-right: exch(--q=3, j=3)
//     a[lo..hi]=[3 2 3 3]
//     lo=0 hi=3 | i=1 j=3 | p=0 q=3  -- after park-right

//   scanL stop: i=2 (3)
//   scanR stop: j=2 (3)
//   tie & eq: exch(++p=1, i=2)
//     a[lo..hi]=[3 3 2 3]
//     lo=0 hi=3 | i=2 j=2 | p=1 q=3  -- after tie-eq move
//   break: i=2 >= j=2
//   gather equals: i=j+1=3
//   gather-left  exch(k=0, j+1) → j=2
//     a[lo..hi]=[2 3 3 3]
//     lo=0 hi=3 | i=3 j=1 | p=1 q=3  -- after gather-left
//   gather-left  exch(k=1, j+1) → j=1
//     a[lo..hi]=[2 3 3 3]
//     lo=0 hi=3 | i=3 j=0 | p=1 q=3  -- after gather-left
//   gather-right exch(k=3, i-1) → i=3
//     a[lo..hi]=[2 3 3 3]
//     lo=0 hi=3 | i=4 j=0 | p=1 q=3  -- after gather-right
//   DONE: <v=[0..0], ==v=[1..3], >v=[4..3]

//   AFTER partition sub=[2 3 3 3]  <v:[0..0]  ==v:[1..3]  >v:[4..3]
// FINAL: 2 3 3 3 5 5 5 6