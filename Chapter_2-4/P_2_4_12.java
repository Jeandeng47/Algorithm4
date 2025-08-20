// To run this class, use the commands:
// cd Chapter_2-4
// javac -cp ../algs4.jar _MaxPQ.java _OrderedArrayMaxPQ.java _UnorderedArrayMaxPQ.java P_2_4_12.java
// java P_2_4_12


// |---------------------------------------|-----------|----------|----------|
// | Implementation                        | Insert    | Max      | delMax   |
// |---------------------------------------|-----------|----------|----------|
// | Unordered array                       | Θ(1)*     | Θ(n)     | Θ(n)     |
// | Ordered array (max at end)            | Θ(n)      | Θ(1)     | Θ(1)     |
// | Binary heap                           | Θ(logN)   | Θ(logN)  | Θ(n)     |
// |---------------------------------------|-----------|----------|----------|

import java.util.Arrays;
import java.util.Random;

public class P_2_4_12 {
    public static void main(String[] args) {
        int N    = 2_000;
        int F    = 500_000;
        int D    = 10;
        long SEED= 42L;

        System.out.printf("Workload: N=%d inserts, F=%d findMax, D=%d delMax, seed=%d%n", N, F, D, SEED);

        // same random data for each PQ
        int[] data = new int[N];
        Random rnd = new Random(SEED);
        for (int i = 0; i < N; i++) data[i] = rnd.nextInt();

        runUnordered(data, F, D);
        runOrdered(data, F, D);
        runHeap(data, F, D);
    }

    private static void runUnordered(int[] data, int F, int D) {
        _UnorderedArrayMaxPQ<Integer> pq = new _UnorderedArrayMaxPQ<>();
        long t0 = System.nanoTime();
        for (int x : data) pq.insert(x);
        long t1 = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < F; i++) sum += pq.max();   // requires max()
        long t2 = System.nanoTime();
        for (int i = 0; i < D; i++) pq.delMax();
        long t3 = System.nanoTime();

        System.out.printf("UnorderedArrayMaxPQ  | insert: %6.2f ms | findMax: %7.2f ms | delMax: %5.2f ms | checksum=%d%n",
                ms(t0,t1), ms(t1,t2), ms(t2,t3), sum);
    }

    private static void runOrdered(int[] data, int F, int D) {
        _OrderedArrayMaxPQ<Integer> pq = new _OrderedArrayMaxPQ<>();
        long t0 = System.nanoTime();
        for (int x : data) pq.insert(x);
        long t1 = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < F; i++) sum += pq.max();   // O(1): last element
        long t2 = System.nanoTime();
        for (int i = 0; i < D; i++) pq.delMax();       // O(1)
        long t3 = System.nanoTime();

        System.out.printf("OrderedArrayMaxPQ    | insert: %6.2f ms | findMax: %7.2f ms | delMax: %5.2f ms | checksum=%d%n",
                ms(t0,t1), ms(t1,t2), ms(t2,t3), sum);
    }

    private static void runHeap(int[] data, int F, int D) {
        // If your heap prefers initial capacity, pass data.length instead of 0
        _MaxPQ<Integer> pq = new _MaxPQ<>(0);
        long t0 = System.nanoTime();
        for (int x : data) pq.insert(x);
        long t1 = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < F; i++) sum += pq.max();   // O(1): root
        long t2 = System.nanoTime();
        for (int i = 0; i < D; i++) pq.delMax();
        long t3 = System.nanoTime();

        System.out.printf("BinaryHeap (_MaxPQ)  | insert: %6.2f ms | findMax: %7.2f ms | delMax: %5.2f ms | checksum=%d%n",
                ms(t0,t1), ms(t1,t2), ms(t2,t3), sum);
    }

    private static double ms(long a, long b) { return (b - a) / 1e6; }
}

// With enormous findMax operation and small insertions and deletion,
// choose order array

// Workload: N=2000 inserts, F=100000 findMax, D=10 delMax, seed=42
// UnorderedArrayMaxPQ  | insert:   0.21 ms | findMax:  133.00 ms | delMax:  0.38 ms | checksum=214643388800000
// OrderedArrayMaxPQ    | insert:   5.78 ms | findMax:    2.11 ms | delMax:  0.01 ms | checksum=214643388800000
// BinaryHeap (_MaxPQ)  | insert:   0.29 ms | findMax:    1.96 ms | delMax:  0.01 ms | checksum=214643388800000