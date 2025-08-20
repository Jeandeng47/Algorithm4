import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_19 {
    public static class MaxPQ <Key extends Comparable<Key>> {
        private Key[] pq; // 1-based
        private int n;
        
        // Default constructor
        public MaxPQ(int cap) {
            pq = (Key[]) new Comparable[cap + 1];
            n = 0;
        }

        // Max PQ that takes array of keys using heap construction
        public MaxPQ(Key[] keys) {
            n = keys.length;
            pq = (Key[]) new Comparable[n + 1];

            // copy into 1...N (off-by-1)
            for (int i = 0; i < n; i++) {
                pq[i + 1] = keys[i];
            }
            // bottom-up heapify
            for (int k = n / 2; k > 0; k--) {
                sink(k);
            }
        }

        public boolean isEmpty() { return n == 0; }
        public int size() { return n; }

        public Key max() { 
            if (isEmpty()) throw new NoSuchElementException(); 
            return pq[1]; 
        }

        public Key delMax() {
            if (isEmpty()) throw new NoSuchElementException(); 
            Key max = pq[1];
            exch(1, n--);
            pq[n + 1] = null;
            sink(1);
            return max;
        }

        public void insert(Key x) {
            if (n == pq.length - 1) resize(pq.length * 2);
            pq[++n] = x;
            swim(n);
        }

        private void swim(int k) {
            while (k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k / 2;
            }
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

        private void resize(int newCap) {
            Key[] t = (Key[]) new Comparable[newCap];
            for (int i = 1; i <= n; i++) t[i] = pq[i];
            pq = t;
        }
        private boolean less(int i, int j) { return pq[i].compareTo(pq[j]) < 0; }
        private void exch(int i, int j) { Key t = pq[i]; pq[i] = pq[j]; pq[j] = t; }

    }
    
    public static void main(String[] args) {
        Integer[] a = {7, 1, 9, 3, 5, 2, 8, 4, 6};
        MaxPQ<Integer> pq = new MaxPQ<>(a); // bottom-up heap construction O(N)

        StdOut.println("max = " + pq.max()); // should be 9
        while (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        StdOut.println();
        // 9 8 7 6 5 4 3 2 1
    }
}
