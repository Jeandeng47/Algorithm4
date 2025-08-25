import java.util.Random;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_3_1_13 {
    static final int PUTS = 1_000;
    static final int GETS = 1_000_000;
    static final int DOMAIN = 50_000; // larger key set

    public static void build(boolean[] ops, int[] keys) {
        int n = ops.length;
        for (int i = 0; i < PUTS; i++) ops[i] = true;
        for (int i = PUTS; i < n; i++) ops[i] = false;

        // shuffle
        Random rand = new Random(42);
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            boolean tmp = ops[i];
            ops[i] = ops[j];
            ops[j] = tmp;
        }

        // random keys
        for (int i = 0; i < n; i++) keys[i] = rand.nextInt(DOMAIN);
    }

    public static long runSeq(boolean[] ops, int[] keys) {
        SequentialSearchST<Integer, Integer> st = new SequentialSearchST<>();
        long t0 = System.nanoTime();
        for (int i = 0; i < ops.length; i++) {
            int k = keys[i];
            if (ops[i]) { 
                Integer v = st.get(k);
                st.put(k, v == null? 1 : v + 1); // O(N) or O(1)
            } else {
                st.get(k);  // O(N)
            }
        }
        return System.nanoTime() - t0;
    }

    public static long runBinary(boolean[] ops, int[] keys) {
        BinarySearchST<Integer,Integer> st = new BinarySearchST<>();
        long t0 = System.nanoTime();
        for (int i = 0; i < ops.length; i++) {
            int k = keys[i];
            if (ops[i]) {
                Integer v = st.get(k);                 // O(log N)
                st.put(k, v == null ? 1 : v + 1);      // O(N) shift on misses
            } else {
                st.get(k);                              // O(log N)
            }
        }
        return System.nanoTime() - t0;
    }

    public static void main(String[] args) {
        

        int n = PUTS + GETS;
        boolean[] ops = new boolean[n];
        int[] keys = new int[n];
        build(ops, keys);

        long seqNs = runSeq(ops, keys);
        long binNs = runBinary(ops, keys);

        StdOut.printf("Workload: %,d puts, %,d gets (randomly intermixed), domain=%d%n", PUTS, GETS, DOMAIN);
        StdOut.printf("SequentialSearchST time: %.2f ms%n", seqNs / 1e6);
        StdOut.printf("BinarySearchST     time: %.2f ms%n", binNs / 1e6);

    }
}

// Workload: 1,000 puts, 1,000,000 gets (randomly intermixed), domain=50000
// SequentialSearchST time: 1424.38 ms
// BinarySearchST     time: 65.95 ms


// With GETS = 10^6, PUTS = 10^3
// BinarySearchST wins because gets are O(log N) vs O(N) for SequentialSearchST
