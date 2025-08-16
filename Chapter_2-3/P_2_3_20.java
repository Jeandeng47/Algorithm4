import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_20 {

    public static class Range {
        private int lo, hi;

        public Range(int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
        }

        public String toString() { 
            return "[" + lo + ".." + hi + "]"; 
        }
    }

    public static void quickSortNonRecursive(Comparable[] a) {
        int n = a.length;
        if (n <= 1) return;
        StdRandom.shuffle(a);

        Deque<Range> st = new ArrayDeque<>();
        st.add(new Range(0, n -  1));

        while (!st.isEmpty()) {
            StdOut.print("Stack: ");
            for (Range r:  st) {
                StdOut.print(r + " ");
            }
            StdOut.println();

            Range sub = st.removeLast();
            StdOut.printf("  POP %s%n", sub);
            int lo = sub.lo;
            int hi = sub.hi;
            if (lo >= hi) continue;

            int j = partition(a, lo, hi);
            // a[j] is put at correct place
            // sort a[lo...j-1] and a[j+1...hi]
            
            // compute sizes
            int lLo = lo, lHi = j - 1, leftSz  = lHi - lLo + 1;
            int rLo = j + 1, rHi = hi, rightSz = rHi - rLo + 1;

            // push larger first (smaller will be processed next → stack stays O(log N))
            if (leftSz >= rightSz) {
                if (leftSz  > 0) { 
                    st.addLast(new Range(lLo, lHi)); 
                    StdOut.printf("  PUSH big   %s%n", new Range(lLo, lHi)); }
                if (rightSz > 0) { 
                    st.addLast(new Range(rLo, rHi)); 
                    StdOut.printf("  PUSH small %s%n", new Range(rLo, rHi)); }
            } else {
                if (rightSz > 0) { 
                    st.addLast(new Range(rLo, rHi)); 
                    StdOut.printf("  PUSH big   %s%n", new Range(rLo, rHi)); }
                if (leftSz  > 0) { 
                    st.addLast(new Range(lLo, lHi)); 
                    StdOut.printf("  PUSH small %s%n", new Range(lLo, lHi)); }
            }
        }
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
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
        Integer[] a = {41, 12, 73, 24, 8, 55, 39, 68, 90, 15, 32, 60, 27};
        quickSortNonRecursive(a);  

        // print final array
        StdOut.print("FINAL: ");
        for (int v : a) StdOut.print(v + " ");
        StdOut.println();
    }
}


// Stack: [0..12]
//   POP [0..12]
//   PUSH big   [0..10]
//   PUSH small [12..12]
// Stack: [0..10] [12..12]
//   POP [12..12]
// Stack: [0..10]
//   POP [0..10]
//   PUSH big   [5..10]
//   PUSH small [0..3]
// Stack: [5..10] [0..3]
//   POP [0..3]
//   PUSH big   [0..2]
// Stack: [5..10] [0..2]
//   POP [0..2]
//   PUSH big   [1..2]
// Stack: [5..10] [1..2]
//   POP [1..2]
//   PUSH big   [2..2]
// Stack: [5..10] [2..2]
//   POP [2..2]
// Stack: [5..10]
//   POP [5..10]
//   PUSH big   [8..10]
//   PUSH small [5..6]
// Stack: [8..10] [5..6]
//   POP [5..6]
//   PUSH big   [5..5]
// Stack: [8..10] [5..5]
//   POP [5..5]
// Stack: [8..10]
//   POP [8..10]
//   PUSH big   [8..8]
//   PUSH small [10..10]
// Stack: [8..8] [10..10]
//   POP [10..10]
// Stack: [8..8]
//   POP [8..8]
// FINAL: 8 12 15 24 27 32 39 41 55 60 68 73 90