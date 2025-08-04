import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_12 {

    // Merge a[lo...mid] and a[mid+1...hi]
    public static void mergeSublinear(Comparable[] a, int lo, int hi, int M) {
        int len = hi - lo + 1;
        int blks = len / M;

        // 1. Sort blocks internally and selection sort by block
        selectionSortBlk(a, lo, blks, M);

        // 2. Merge blocks into original array
        for (int b = 1; b < blks; b++) {
            int start = lo;             // start of sorted left
            int endL = lo + b * M - 1;  // end of sorted left
            int endR = endL + M;        // end of sorted right
            mergeBlk(a, start, endL, endR, M);
        }
        StdOut.printf("After merged: %s%n", Arrays.toString(a));
    }

    private static void mergeBlk(Comparable[] a, int start, int endL, int endR, int M) {
        // always copy the block of length into aux
        Comparable[] aux = new Comparable[M];
        for (int i = 0; i < M; i++) {
            aux[i] = a[endL + 1 + i];
        }

        // set 3 pointers
        int i = endL;       // index of prefix
        int j = M - 1;      // index of aux
        int k = endR;       // index of original array

        while (i >= start && j >= 0) {
            if (less(a[i], aux[j])) {
                a[k--] = aux[j--];
            } else {
                a[k--] = a[i--];
            }
        }
        // copy from aux (right block) if any
        while (j >= 0) {
            a[k--] = aux[j--];
        }
    }

    
    // Modified selection sort: use selection sort to sort the block, 
    // using the first element as key
    private static void selectionSortBlk(Comparable[] a, int lo, int blks, int M) {

        // 1. Sort each block interally
        // ex: sort [0...M-1], sort[M...2M-1], sort[2M...3M-1]...
        for (int b = 0; b < blks; b++) {
            int start = lo + b * M; // lo, lo + M, lo + 2M...
            Arrays.sort(a, start, start + M);
        }
        StdOut.printf("After sorted interally: %s%n", Arrays.toString(a));

        // 2. Selection sort by first element
        for (int b = 0; b < blks; b++) {
            int minB = b; // minB: the block with smallest first element
            for (int bb = b + 1; bb < blks; bb++) {
                int currStart = lo + minB * M;
                int nextStart = lo + bb * M;
                if (less(a[nextStart], a[currStart])) {
                    minB = bb;
                }
            }
            if (minB != b) {
                // swap entire block b and block minB
                for (int k = 0; k < M; k++) {
                    exch(a, lo + b * M + k, lo + minB * M + k);
                }
            }
        }
        StdOut.printf("After sorted by block: %s%n", Arrays.toString(a));
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    public static void main(String[] args) {
        Random r = new Random();
        int N = 20;
        int M = 2;

        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) {
            a[i] = r.nextInt(N);
        }
        mergeSublinear(a, 0, N - 1, M);
    }
}

// Goal: With O(NlogN) time, we would like to lower space complexity
// of mergesort from O(N) to O(max(M, N / M))

// 1. Divide to M blocks
// -- devide array of length N into N/M blocks
// -- each block has length M
// 2. Selection sort the block
// -- treat block M_i as element, use 1st element as key
// -- after sorting, the array is block sorted
// 3. Merge
// -- allocate a buffer of length M: buf[M]
// -- merge block 0 and 1, write to 2M positions of oirginal array
// -- merge these 2M with block 2, write to 3M positions of oirginal array
// -- ...

// Space
// 1. Buffer with length of M
// 2. Auxiliary block with length of N / M


// Example:
// a = { 5, 1, 9, 4, 7, 2, 8, 3 }, M = 2, N = 8
// 1. Divide into B blocks, sort within block
// => a = {[5, 1], [9, 4], [7, 2], [8, 3]}, B = 8/2 = 4
// => a = {[1, 5], [4, 9], [2, 7], [3, 8]}

// 2. Selection sort each block
// => a = {[1, 5], [2, 7], [3, 8], [4, 9]}

// 3. Merge ***
// a = {[1, 5], [2, 7], [3, 8], [4, 9]}
// Merge b0, b1: 
// pointer i in buf, buf = [1, 5]
// pointer j in right block, [2, 7]
// pointer k in a

// buf[i=0] = 1, a[j=2] = 2, 1 < 2, a[k=0] = 1, i++, k++
// buf[i=1] = 5, a[j=2] = 2, 2 < 5, a[k=1] = 2, j++, k++
// buf[i=1] = 5, a[j=3] = 7, 5 < 7, a[k=2] = 5, i++, k++
// buf[i=2] empty, a[j=3] = 7, a[k=3] = 7, j++, k++
// => a {[1, 2, 5, 7], [3, 8], [4, 9]}

// => a {[1, 2, 5, 7], [3, 8], [4, 9]}
// Merge b0, b2:
