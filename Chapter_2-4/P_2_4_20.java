import java.util.NoSuchElementException;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_20 {
    private static int cmp, exch;
    public static class MaxPQ<Key extends Comparable<Key>> {
        private Key[] pq;
        private int n;

        public MaxPQ(Key[] keys) {
            n = keys.length;
            pq = (Key[]) new Comparable[n + 1];
            for (int i = 0; i < n; i++) pq[i + 1] = keys[i];  
            for (int k = n / 2; k >= 1; k--) sink(k);        
        }
        
        public MaxPQ(int cap) {
            pq = (Key[]) new Comparable[cap + 1];
            n = 0;
        }

        public boolean isEmpty() { return n == 0; }
        public int size() { return n; }

        public void insert(Key v) {
            if (n == pq.length) resize(pq.length * 2);
            pq[++n] = v;
            swim(n);
        }

        public Key max() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue empty");
            return pq[1]; 
        }

        public Key delMax() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue empty");
            Key max = pq[1];
            exch(1, n--);
            sink(1);

            pq[n + 1] = null; // Avoid loitering

            if (n > 0 && n == (pq.length - 1) / 4) {
                resize(pq.length / 2); 
            }
            return max;
        }

        private void sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && less(j, j + 1)) j++;
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        private void swim(int k) {
            while (k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k / 2;
            }
        }

        private void exch(int i, int j) {
            exch++;
            Key temp = pq[i];
            pq[i] = pq[j];
            pq[j] = temp;
        }

        private boolean less(int i, int j) {
            cmp++;
            return pq[i].compareTo(pq[j]) < 0;
        }

        private void resize(int cap) {
            Key[] newpq = (Key[]) new Comparable[cap + 1];
            for (int i = 1; i <= n; i++) {
                newpq[i] = pq[i];
            }
            pq = newpq;
        }
    }

    public static Integer[] oneBased(int[] arr) {
        Integer[] b = new Integer[arr.length + 1];
        for (int i = 0; i < arr.length; i++) b[i + 1] = arr[i];
        return b;
    }
    private static void resetCounters() { cmp = 0; exch = 0; }

    public static void runCase(String name, int[] data) {
        resetCounters();

        // 0-based -> one based
        Integer[] keys = new Integer[data.length];
        for (int i = 0; i < data.length; i++) keys[i] = data[i];

        MaxPQ<Integer> pq = new MaxPQ<>(keys);

        long N = data.length;
        System.out.printf("%-16s N=%d  compares=%d  exchanges=%d  (2N=%d, N=%d)%n",
                name, N, cmp, exch, 2*N, N);
    }

    public static void main(String[] args) {
        int[] Ns = {15, 31, 63, 127, 255};

        Random r = new Random(42);

        for (int N : Ns) {
            // Random data
            int[] rnd = new int[N];
            for (int i = 0; i < N; i++) rnd[i] = r.nextInt(1_000_000);
            runCase("random", rnd);

            // Ascending data
            int[] asc = new int[N];
            for (int i = 0; i < N; i++) asc[i] = i;
            runCase("ascending", asc);

            // Descending data
            int[] desc = new int[N];
            for (int i = 0; i < N; i++) desc[i] = N - i;
            runCase("descending", desc);

            StdOut.println();
        }
    }
}
