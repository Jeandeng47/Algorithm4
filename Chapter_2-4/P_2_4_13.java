import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class P_2_4_13 {
    public static class MaxPQ<Key extends Comparable<Key>> {
        private Key[] pq;
        private int n;

        @SuppressWarnings("unchecked")
        public MaxPQ(int capacity) {
            pq = (Key[]) new Comparable[capacity + 1]; // 1-based indexing
            n = 0;
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public void insert(Key v) {
            if (n == pq.length - 1) {
                resize(2 * pq.length);
            }
            pq[++n] = v;
            swim(n);
        }

        public Key max() {
            return pq[1]; 
        }

        public Key delMax() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            Key max = pq[1];
            exch(1, n--); 
            sink(1);

            pq[n + 1] = null;

            if (n > 0 && n == (pq.length - 1) / 4) {
                resize(pq.length / 2); 
            }
            return max;
            
        }

        // Heap order is broken if the child is greater than the parent
        // so we need to swim the child up
        private void swim(int k) {
            while (k > 1 && less(k/2, k)) {
                exch(k, k/2);
                k = k / 2; 
            }
        }

        // Heap order is broken if the parent is less than the child
        // we need to sink the parent down
        private void sink(int k) {
            while (2 * k <= n) {    // check left child
                int j = 2 * k;      // left child
                if (j < n && less(j, j + 1)) j++;
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        public Key delMaxNew() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            Key max = pq[1];
            exch(1, n--); 
            sinkNew(1);

            pq[n + 1] = null;

            if (n > 0 && n == (pq.length - 1) / 4) {
                resize(pq.length / 2); 
            }
            return max;
            
        }

        // Condition (j < n): is there a right child? if j > n;
        // there is no right child j = 2*k + 1
        // To remove (j < n): we could directly check the existence
        // of right child, if right exists, left child must exist

        private void sinkNew(int k) {
            // both children exist (right child exists iff 2*k + 1 <= n)
            while (2 * k + 1 <= n) {
                int left = 2 * k;
                int right = left + 1;
                int j = less(left, right)? right : left;
                if (!less(k, j)) return; // if no violation
                exch(k, j);
                k = j;
            }

            // At last check of while loop, the check 2 * k + 1 <= n fails
            // but without checking 2 * k == n, we may miss one exchange

            if (2 * k == n && less(k, 2 * k)) {
                exch(k, 2 * k);
            }
        }

        private void exch(int i, int j) {
            Key temp = pq[i];
            pq[i] = pq[j];
            pq[j] = temp;
        }

        private boolean less(int i, int j) {
            return pq[i].compareTo(pq[j]) < 0;
        }
        

        @SuppressWarnings("unchecked")
        private void resize(int capacity) {
            Key[] newPq = (Key[]) new Comparable[capacity + 1];
            for (int i = 1; i <= n; i++) {
                newPq[i] = pq[i];
            }
            pq = newPq;
        }

        public String arrayView() {
            StringBuilder sb = new StringBuilder("[ ");
            for (int i = 1; i <= n; i++) {
                sb.append(pq[i]);
                if (i < n) sb.append(' ');
            }
            return sb.append(" ]").toString();
        }
    }

    public static void main(String[] args) {
        MaxPQ<Integer> h1 = new MaxPQ<>(5);
        MaxPQ<Integer> h2 = new MaxPQ<>(5);

        int[] vals = { 5, 1, 9, 3, 7, 8, 2, 6, 4, 10 };
        for (int v : vals) h1.insert(v);
        for (int v : vals) h2.insert(v);
        

        StdOut.println("Heap1 after inserts: " + h1.arrayView());
        StdOut.println("Heap2 after inserts: " + h1.arrayView());

        StdOut.print("delMax order (original sink): ");
        while (!h1.isEmpty()) {
            StdOut.print(h1.delMax() + " ");
        }
        StdOut.println();

        StdOut.print("delMax2 order (new sink): ");
        while (!h2.isEmpty()) {
            StdOut.print(h2.delMax() + " ");
        }
        StdOut.println();
    }

}
