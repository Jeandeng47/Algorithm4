import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_15 {
    private static void quickMatch(Comparable[] nuts, Comparable[] bolts, int lo, int hi) {
        if (lo >= hi) return;
        
        // choose a bolt as pivot
        Comparable pbolt = bolts[lo];
        int p = partition(nuts, pbolt, lo, hi); // put nuts[p] at correct position with pivot blot

        Comparable pnut = nuts[p]; // put bolts[p] at correct position with same pivot
        partition(bolts, pnut, lo, hi);

        // nut[p] & bolts[p] are at place
        quickMatch(nuts, bolts, lo, p - 1);
        quickMatch(nuts, bolts, p + 1, hi);
    }

    // do this while (i < j && j < a.length)
    // -- if a[j] > pivot, j++
    // -- if a[j] < pivot, i++, exch(a, i, j)
    // -- if a[j] == pivot, swap pivot to end (N-1)
    // while end

    // i = the position of pivot
    // j = hi = N - 1
    // exch(a, i, hi) -> put pivot into final position
    private static int partition(Comparable[] a, Comparable p, int lo, int hi) {
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (less(a[j], p)) {
                exch(a, i, j);
                i++;
            } else if (equal(a[j], p)) {
                exch(a, j, hi);
                j--; // re-check
            }
        }
        exch(a, i, hi);
        return i;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static boolean equal(Comparable a, Comparable b) {
        return a.compareTo(b) == 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        Character[] nuts = {'A', 'C', 'D', 'F', 'E', 'B'};
        Character[] bolts = {'B', 'D', 'E', 'C', 'A', 'F'};

        int N = nuts.length;
        quickMatch(nuts, bolts, 0, N - 1);

        StdOut.println("Matched nuts and bolts: ");
        StdOut.println("Nuts: " + Arrays.toString(nuts));
        StdOut.println("Bolts: " + Arrays.toString(bolts));
    }    
}

// Example of partition()

// idx  0   1   2   3   4   5
// n[]  A   C   D   F   E   B   
// b[]  B   D   E   C   A   F

// b = b[0] = B, use b to partiton n[]
// i   j   cmp     action
// 0   0   A < B   i++, exch(0, 0) -> n[] = [A, C, D, F, E, B]
// 1   1   C > B   do nothing
// 1   2   D > B   do nothing
// 1   3   F > B   do nothing
// 1   4   E > B   do nothing
// 1   5   B == B  pivot already at the end

// return i = 1 -> piviot B should be placed at pos 1
// final swap: exch(p, j) -> n[] = [A, B, D, F, E, C] => B is at final position

// P = 1
// left: sort(0, 0) -> return
// right: sort(2, 5)

// n = n[p] = B, use n to partition b[]
// i   j   cmp     action
// 0   0   B == B  swap to end, swap to end -> b[] = [F, D, E, C, A, B]
// 0   1   D > B   do nothing
// 0   2   E > B   do nothing
// 0   3   C > B   do nothing
// 0   4   A < B   i++, exch(0, 4) -> b[] = [A, D, E, C, F, B]
// 1   5   F > B   do nothing

// return i = 1 -> piviot B should be placed at pos 1
// final swap: exch(p, j) -> b[] = [A, B, E, C, F, D] => B is at final position
// left: sort(0, 0) -> return
// rightL sort(2, 5)

