import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_3 {
    public static class SeparateChainingHT<Key, Value> {
        private static final int INIT_CAP = 4;
        private int n; // # of key-value pairs currently in the table
        private int m; // # of chains
        
        private SequentialSearchST<Key, Value>[] st;     // value chains
        private SequentialSearchST<Key, Integer>[] stamp; // stamp chains (insertion index)

        public SeparateChainingHT() {
            this(INIT_CAP);
        }

        public SeparateChainingHT(int m) {
            if (m <= 0) throw new IllegalArgumentException("m must be > 0");
            this.m = m;
            this.st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
            this.stamp = (SequentialSearchST<Key, Integer>[]) new SequentialSearchST[m];
            for (int i = 0; i < m; i++) {
                st[i] = new SequentialSearchST<>();
                stamp[i] = new SequentialSearchST<>();
            }
        }

        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % m;
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            return get(key) != null;
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            int i = hash(key);
            return st[i].get(key);
        }

        public Integer getStamp(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            int i = hash(key);
            return stamp[i].get(key);
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");

            // double table size if average list length >= 10
            if (n >= 10 * m) resize(2 * m);

            int i = hash(key);
            if (!st[i].contains(key)) {
                n++;
                st[i].put(key, val);
                stamp[i].put(key, n);
            } else {
                st[i].put(key, val);
            }
        }

        private void resize(int cap) {
            SeparateChainingHT<Key, Value> temp = new SeparateChainingHT<>(cap);
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys()) {
                    temp.st[ temp.hash(key) ].put(key, st[i].get(key));
                    temp.stamp[ temp.hash(key) ].put(key, stamp[i].get(key));
                }
            }
            this.m = temp.m;
            this.st = temp.st;
            this.stamp = temp.stamp;
        }

        /**
         * Delete all keys (and values) whose insertion-stamp is greater than k.
         * The stamp is the number of entries in the table at the time that pair was first inserted.
         */
        public void delete(int k) {
            if (n == 0) return;

            for (int i = 0; i < m; i++) {
                // traverse keys safely even while deleting
                Queue<Key> toDelete = new Queue<>();
                for (Key key : st[i].keys()) {
                    Integer s = stamp[i].get(key);
                    if (s != null && s > k) {
                        toDelete.enqueue(key);
                    }
                }
                while (!toDelete.isEmpty()) {
                    Key key = toDelete.dequeue();
                    if (st[i].contains(key)) {
                        st[i].delete(key);
                        stamp[i].delete(key);
                        n--;
                    }
                }
            }

            if (m > INIT_CAP && n <= 2 * m) return;
        }

        public Iterable<Key> keys() {
            Queue<Key> q = new Queue<>();
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys()) {
                    q.enqueue(key);
                }
            }
            return q;
        }

        public void printTable() {
            StdOut.println("Hash table with " + m + " chains (n=" + n + "):");
            for (int i = 0; i < m; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(i).append(": ");
                boolean first = true;
                for (Key k : st[i].keys()) { // head-first order (SequentialSearchST inserts at head)
                    if (!first) sb.append(" -> ");
                    sb.append(k);
                    Integer s = stamp[i].get(k);
                    if (s != null) sb.append("[").append(s).append("]");
                    first = false;
                }
                if (first) sb.append("—"); // empty chain
                StdOut.println(sb);
            }
        }
    }

    public static void main(String[] args) {
        char[] keys = {'E','A','S','Y','Q','U','T','I','O','N'};

        SeparateChainingHT<Character, Integer> ht = new SeparateChainingHT<>(5);
        for (int idx = 0; idx < keys.length; idx++) {
            ht.put(keys[idx], 0);
        }

        StdOut.println("== Initial table ==");
        ht.printTable();

        // delete everything inserted after the 6th insertion
        int k = 6;
        StdOut.println("\n== delete(stamp > " + k + ") =="); // expect to delete T I O N
        ht.delete(k);
        ht.printTable();
    }
}
