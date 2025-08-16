import edu.princeton.cs.algs4.StdOut;

public class P_2_4_5 {
    public static class MaxHeap<Key extends Comparable<Key>> {
        private Key[] pq;
        private int n;

        public MaxHeap(int cap) {
            this.pq = (Key[]) new Comparable[cap + 1]; // 1-based indexing
            this.n = 0;
        }

        public boolean isEmpty() {return n== 0;}

        public int size() {return n;}

        public void insert(Key key) {
            if (n == pq.length - 1) {
                resize(pq.length * 2);
            }
            pq[++n] = key;
            swim(n); // restore heap order
        }

        private void swim(int k) {
            while (k > 1 && less(k/2, k)) {
                exch(k, k / 2);
                k = k / 2;
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

        private void resize(int cap) {
            Key[] newpq = (Key[]) new Object[cap + 1];
            for (int i = 1; i <= n; i++) {
                newpq[i] = pq[i];
            }
            pq = newpq;
        }

         public void printHeap() {
            // array view
            StdOut.print("[ ");
            for (int i = 1; i <= n; i++) {
                StdOut.print(pq[i]);
                if (i < n) StdOut.print(", ");
            }
            StdOut.println(" ]");

            // levels
            int i = 1, levelSize = 1, level = 1;
            while (i <= n) {
                int end = Math.min(i + levelSize - 1, n);
                StdOut.print("Level " + level + ": ");
                for (int j = i; j <= end; j++) {
                    StdOut.print(pq[j]);
                    if (j < end) StdOut.print(' ');
                }
                StdOut.println();
                i = end + 1;
                levelSize <<= 1;
                level++;
            }
        }
    }

    public static void main(String[] args) {
        Character[] keys = {'E','A','S','Y','Q','U','E','S','T','I','O','N'};
        MaxHeap<Character> maxHeap = new MaxHeap<>(keys.length);
        for (Character k : keys) {
            maxHeap.insert(k);
        }
        maxHeap.printHeap();
    }
}

// [ Y, T, U, S, Q, N, E, A, S, I, O, E ]
// Level 1: Y
// Level 2: T U
// Level 3: S Q N E
// Level 4: A S I O E