// To run this class, use the commands:
// cd Chapter_2-4
// javac -cp ../algs4.jar _MaxPQ.java _OrderedArrayMaxPQ.java _UnorderedArrayMaxPQ.java P_2_4_11.java
// java P_2_4_11

import java.util.Arrays;
import java.util.Random;


// |---------------------------------------|-----------|----------|
// | Implementation                        | Insert    | delMax   |
// |---------------------------------------|-----------|----------|
// | Unordered array                       | Θ(1)*     | Θ(n)     |
// | Ordered array                         | Θ(n)      | Θ(1)     |
// | Binary heap                           | Θ(logN)   | Θ(logN)  |
// |---------------------------------------|-----------|----------|

public class P_2_4_11 {
    public static void main(String[] args) {
        int N = 100_000; // N insertions
        int D = 5; // Delete
        long seed = 42L;

        System.out.printf("N=%d inserts, D=%d delMax, seed=%d%n", N, D, seed);

        int[] data = new int[N];
        Random rnd = new Random(seed);
        for (int i = 0; i < N; i++) data[i] = rnd.nextInt();

        runUnordered(data, D);
        runOrdered(data, D);
        runHeap(data, D);

    }

    private static void runUnordered(int[] data, int D) {
        _UnorderedArrayMaxPQ<Integer> pq = new _UnorderedArrayMaxPQ<>();
        long t0 = System.nanoTime();
        for (int x : data) pq.insert(x);
        int take = D;
        int[] out = new int[take];
        for (int i = 0; i < take; i++) out[i] = pq.delMax();
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("UnorderedArrayMaxPQ delMax -> " + Arrays.toString(out) + "  (" + ms + " ms)");
    }

    private static void runOrdered(int[] data, int D) {
        _OrderedArrayMaxPQ<Integer> pq = new _OrderedArrayMaxPQ<>();
        long t0 = System.nanoTime();
        for (int x : data) pq.insert(x);
        int take = D;
        int[] out = new int[take];
        for (int i = 0; i < take; i++) out[i] = pq.delMax();
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("OrderedArrayMaxPQ  delMax -> " + Arrays.toString(out) + "  (" + ms + " ms)");
    }

    private static void runHeap(int[] data, int D) {
        _MaxPQ<Integer> pq = new _MaxPQ<>(0);
        long t0 = System.nanoTime();
        for (int x : data) pq.insert(x);
        int take = D;
        int[] out = new int[take];
        for (int i = 0; i < take; i++) out[i] = pq.delMax();
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("BinaryHeap (_MaxPQ) delMax -> " + Arrays.toString(out) + "  (" + ms + " ms)");
    }
}

// With large amount of inserts and small amount of delete, time used: 
// UnorderedArrayMaxPQ < binaryHeap < OrderedArrayMaxPQ

// N=100000 inserts, D=5 delMax, seed=42
// UnorderedArrayMaxPQ delMax -> [2147370166, 2147366915, 2147234127, 2147178211, 2147170601]  (8 ms)
// OrderedArrayMaxPQ  delMax -> [2147370166, 2147366915, 2147234127, 2147178211, 2147170601]  (4951 ms)
// BinaryHeap (_MaxPQ) delMax -> [2147370166, 2147366915, 2147234127, 2147178211, 2147170601]  (9 ms)