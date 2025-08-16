import java.util.Arrays;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_10 {

    // Return the index of parent (0-based)
    public static int parent(int k) {
        if (k == 0) throw new IllegalArgumentException("root (k=0) has no parent");
        return (k - 1) / 2;
    }

    // Return the index of left child (0-based)
    public static int leftChild(int k) {
        return 2 * k + 1;
    }

    // Return the index of right child (0-based)
    public static int rightChild(int k) {
        return 2 * k + 2;
    }


    // 0-based max-heap
    public static class MaxHeap0 <Key extends Comparable<Key>> {
        private Key[] pq;
        private int n;

        public MaxHeap0(int cap) {
            this.pq = (Key[]) new Comparable[cap]; // not cap + 1
            this.n = 0;
        }

        public boolean isEmpty() { return n == 0; }
        public int size() { return n; }

        public void insert(Key k) {
            if (n == pq.length) { resize(pq.length * 2); }
            pq[n] = k;
            swim(n);
            n++;
        }

        public Key delMax() {
            if (n == 0) throw new NoSuchElementException("PQ underflow");
            Key max = pq[0];
            exch(1, n-1);
            n--;
            sink(0);

            pq[n] = null;
            return max;
        }

        private void sink(int k) {
            while (leftChild(k) < n) {
                int j = leftChild(k);
                int r = rightChild(k);
                if (r < n && less(j, r)) {
                    j = r;
                }
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        private void swim(int k) {
            while (k > 0 && less(parent(k), k)) {
                exch(parent(k), k);
                k = parent(k);
            }
        }

        private boolean less(int i, int j) {
            return pq[i].compareTo(pq[j]) < 0;
        }

        private void exch(int i, int j) {
            Key temp = pq[i];
            pq[i] = pq[j];
            pq[j] = temp;
        }


        private void resize(int cap) {
            Key[] newpq = (Key[]) new Comparable[cap];
            for (int i = 0; i < pq.length; i++) {
                newpq[i] = pq[i]; 
            }
            pq = newpq;
        }

        public String arrayView() {
            return Arrays.toString(Arrays.copyOf(pq, n));
        }

        public Key at(int i) {
            return (i >= 0 && i < n) ? pq[i] : null;
        }
    }
    

    public static void main(String[] args) {
        String s = "ABCDEF";
        MaxHeap0<Character> heap = new MaxHeap0<>(s.length());

        for (char c : s.toCharArray()) {
            heap.insert(c);
        }

        StdOut.println("0-based max-heap built from: " + s);
        StdOut.println("Array view: " + heap.arrayView());
        StdOut.println();

        int n = heap.size();
        for (int k = 0; k < n; k++) {
            Character c = heap.at(k);
            String pStr, lStr, rStr;

            if (k == 0) pStr = "none";
            else {
                int p = parent(k);
                pStr = p + "('" + heap.at(p) + "')";
            }
            int li = leftChild(k), ri = rightChild(k);
            lStr  = (li < n) ? (li + "('" + heap.at(li) + "')") : "none";
            rStr = (ri < n) ? (ri + "('" + heap.at(ri) + "')") : "none";

            System.out.printf("k=%d('%s'): parent=%s, left=%s, right=%s%n",
                    k, c, pStr, lStr, rStr);
        }

    }
}

// L0: 1
// L1: 2 3
// L2: 4 5 6

// idx  0   1   2   3   4   5
// a[]  1   2   3   4   5   6

// parent of 4 (a[3]) is 2 (a[1])
// parent of 6 (a[5]) is 3 (a[2])
// parent(k) = (k - 1) / 2

// children of 2 (a[1]) is 4 (a[3]) and 5 (a[4])
// children of 1 (a[0]) is 2 (a[1]) and 3 (a[2])
// leftChild(k) = 2*k + 1
// rightChild(k) = 2*k + 2
