import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_6 {

    public static class MaxHeap<Key extends Comparable<Key>> {
        private Key[] pq;
        private int n;

        public MaxHeap(int cap) {
            this.pq = (Key[]) new Comparable[cap + 1]; // 1-based indexing
            this.n = 0;
        }

        public boolean isEmpty() {return n == 0;}

        public int size() {return n;}

        public void insert(Key key) {
            if (n == pq.length - 1) {
                resize(pq.length * 2);
            }
            pq[++n] = key;
            swim(n); // restore heap order
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

        private void swim(int k) {
            while (k > 1 && less(k/2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && less(j, j+1)) { j++; }
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
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

        public String heapState() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 1; i <= n; i++) {
                if (i > 1) sb.append(' ');
                sb.append(pq[i]);
            }
            sb.append(']');
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String ops = "P R I O * R * * I * T * Y * * * Q U E * * * U * E";
        String[] tokens = ops.split(" ");

        MaxHeap<String> heap = new MaxHeap<>(tokens.length);
        int step = 0;
        
        for (int i = 0; i < tokens.length; i++) {
            step++;
            String s = tokens[i];
            if (s.equals("*")) {
                heap.delMax();
                StdOut.printf("%2d) * : %s%n", step, heap.heapState());
            } else {
                heap.insert(s);
                StdOut.printf("%2d) %s : %s%n", step, s, heap.heapState());
            }
        }
    }
    
}

//  1) P : [P]
//  2) R : [R P]
//  3) I : [R P I]
//  4) O : [R P I O]
//  5) * : [P O I]
//  6) R : [R P I O]
//  7) * : [P O I]
//  8) * : [O I]
//  9) I : [O I I]
// 10) * : [I I]
// 11) T : [T I I]
// 12) * : [I I]
// 13) Y : [Y I I]
// 14) * : [I I]
// 15) * : [I]
// 16) * : []
// 17) Q : [Q]
// 18) U : [U Q]
// 19) E : [U Q E]
// 20) * : [Q E]
// 21) * : [E]
// 22) * : []
// 23) U : [U]
// 24) * : []
// 25) E : [E]