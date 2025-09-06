import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_11 {
    // BST node for verification
    private static class Node {
        private int key;
        private Node left, right;
        public Node(int key) { this.key = key; }
    }

    private static Node insert(Node x, int k) {
        if (x == null) return new Node(k);
        if (k < x.key) x.left = insert(x.left, k);
        else           x.right = insert(x.right, k);
        return x;
    }

    public static int height(Node x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    // Use bit-mask to generate 2^(N-1) possible worst BSTs
    // (as insertion sequence)
    public static int[] getWorstBST(int N, Random rand) {
        int[] seq = new int[N];
        int lo = 1, hi = N;

        // first pick: either lo or hi
        boolean pickHi = rand.nextBoolean();
        seq[0] = pickHi ? hi-- : lo++;

        for (int i = 1; i < N; i++) {
            if      (lo > hi)            throw new AssertionError("exhausted");
            else if (lo == hi)           seq[i] = lo++;               // only one left
            else if (rand.nextBoolean())  seq[i] = hi--;               // pick max
            else                         seq[i] = lo++;               // pick min
        }
        return seq;
    }

    public static int count(int N) {
        if (N <= 0) return 0;
        return 1 << (N-1);
    }

    public static void main(String[] args) {
        int[] Ns = {2, 3, 4, 5};
        for (int N : Ns) {
            Random rand = new Random(42);
            int count = count(N);
            StdOut.printf("N=%d  count=2^(N-1) = %d%n", N, count);

            // Random sampling from the generated path, confirm
            // the BST height given by the insertion sequence is N
            for (int t = 0; t < Math.min(5, count); t++) {
                int[] seq = getWorstBST(N, rand);
                Node root = null; // dummy root
                for (int x : seq) { root = insert(root, x); }

                int h = height(root);
                StdOut.printf("Sample order height = %d (expect = %d)%n", h, N);
            }
        }
    }
}

// 1. To keep BST as a chain, after picking an arbitrary element,
// the next elements must be either min or max. Therefore, for
// the rest N - 1 nodes, each has 2 choices, there are total
// possibilities of 2^(N-1).
// 2. Use bitmask to represent our choice: 0 = pick next min,
// 1 = pick next max
// Example: N = 3, keys = {1,2,3}, there should be 2^2 = 4 ways
// to construct a worst BST
// At first we pick 2, lo = 1, hi = 3

// (1) CODE 00: 
// -- insert lo = 1, now lo = 0;
// -- could only pick hi = 3
// insert: [2, 1, 3], height = 3

// (2) CODE 01:
// -- insert lo = 1, now lo = 0;
// -- could only pick hi = 3
// insert: [2, 1, 3], height = 3

// (3) CODE 11:
// -- insert hi = 3, now hi = 4
// -- could only pick lo = 1
// insert: [2, 3, 1], height = 3

// (4) CODE 10:
// -- insert hi = 3, now hi = 4
// -- could only pick min 
// insert [2, 3, 1], height = 3


// N=2  count=2^(N-1) = 2
// Sample order height = 2 (expect = 2)
// Sample order height = 2 (expect = 2)
// N=3  count=2^(N-1) = 4
// Sample order height = 3 (expect = 3)
// Sample order height = 3 (expect = 3)
// Sample order height = 3 (expect = 3)
// Sample order height = 3 (expect = 3)
// N=4  count=2^(N-1) = 8
// Sample order height = 4 (expect = 4)
// Sample order height = 4 (expect = 4)
// Sample order height = 4 (expect = 4)
// Sample order height = 4 (expect = 4)
// Sample order height = 4 (expect = 4)
// N=5  count=2^(N-1) = 16
// Sample order height = 5 (expect = 5)
// Sample order height = 5 (expect = 5)
// Sample order height = 5 (expect = 5)
// Sample order height = 5 (expect = 5)
// Sample order height = 5 (expect = 5)