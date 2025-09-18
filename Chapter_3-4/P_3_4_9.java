import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_9 {
    public static class SeparateChainingHT<Key, Value> {
        private int m;
        private int n;
        private SequentialSearchST<Key, Value>st [];
        private static final int INIT_CAP = 4;
        
        public SeparateChainingHT() {
            this(INIT_CAP);
        }

        public SeparateChainingHT(int m) {
            this.m = m;
            this.st = new SequentialSearchST[m];
            for (int i = 0; i < m; i++) {
                st[i] = new SequentialSearchST<>();
            }
        }

        public int size() { return n; }
        public boolean isEmpty() { return size() == 0; }

        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % m;
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            int i = hash(key);
            return st[i].get(key);
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            return get(key) != null;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            if (val == null) { delete(key); }

            // double table size if list is too long (len >= 10)
            if (n >= 10 * m) { resize(2 * m); }
            
            int i = hash(key);
            if (!st[i].contains(key)) n++;
            st[i].put(key, val);
        }

        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            
            int i = hash(key);
            if (st[i].contains(key)) n--;
            st[i].delete(key);

            // halve table size if average length of list <= 2
            if (m > INIT_CAP && n <= 2 * m) resize(m / 2);
        }

        private void resize(int chains) {
            SeparateChainingHT<Key, Value> temp = new SeparateChainingHT<>(chains);
            for (int i = 0; i < m; i++) {
                for (Key k : st[i].keys()) {
                    temp.put(k, st[i].get(k));
                }
            }
            this.m = temp.m;
            this.n = temp.n;
            this.st = temp.st;
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
            StdOut.println("Hash table with " + m + " chains:");
            for (int i = 0; i < m; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(i).append(": ");
                boolean first = true;
                for (Key k : st[i].keys()) { // head-first order
                    if (!first) sb.append(" -> ");
                    sb.append(k);
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
            ht.put(keys[idx], idx); 
        }
        ht.printTable();
        StdOut.println();

        // delete elements
        StdOut.println("After deleteing A, I, K: ");
        ht.delete('A');
        ht.delete('I');
        ht.delete('K'); // delete non-existent
        ht.printTable();
    }
}

// Hash table with 5 chains:
// 0: U -> A
// 1: Q
// 2: —
// 3: N -> I -> S
// 4: O -> T -> Y -> E

// After deleteing A, I, K: 
// Hash table with 2 chains:
// 0: T -> N
// 1: E -> Y -> O -> S -> Q -> U
