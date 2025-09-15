import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_1 {
    public static class SeparateChainingHT<Key, Value> {
        private static final int INIT_CAP = 4;
        private int n; // #K-V pairs
        private int m; // #llist
        private SequentialSearchST<Key, Value>[] st; // array of llists

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

        // Use k % m to hash (k: relative position of this char in alphabet); 
        // otherwise fallback to std hash
        private int hash(Key key) {
            if (key instanceof Character) {
                char c = Character.toUpperCase((Character) key);
                if (c >= 'A' && c <= 'Z') {
                    int k = (c - 'A') + 1; // k = 1...26
                    return (11 * k) % m;
                }
            }
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

        public void put(Key key, Value val) {
            // double table size if list is too long (len >= 10)
            if (n >= 10 * m) { resize(2 * m); }

            int i = hash(key);
            if (!contains(key)) n++;
            st[i].put(key, val);
        }

        private void resize(int cap) {
            SeparateChainingHT<Key, Value> temp = new SeparateChainingHT<>(cap);
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys()) {
                    temp.put(key, st[i].get(key));
                }
            }
            this.m  = temp.m;
            this.n  = temp.n;
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
    }
}

// Hash table with 5 chains:
// 0: O -> T -> Y -> E
// 1: U -> A
// 2: Q
// 3: —
// 4: N -> I -> S