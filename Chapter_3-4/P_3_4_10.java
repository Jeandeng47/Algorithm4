import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_10 {
    public static class LinearProbingHT<Key, Value> {
        private static final int INIT_CAP = 4;

        private int m; // size of table
        private int n; // number of k-v pairs
        private Key[] keys;
        private Value[] vals;

        public LinearProbingHT() {
            this(INIT_CAP);
        }

        public LinearProbingHT(int cap) {
            this.n = 0;
            this.m = cap;
            this.keys = (Key[]) new Object[m];
            this.vals = (Value[]) new Object[m];
        }

        public int size() { return n; }
        public boolean isEmpty() { return size() == 0; }

        // Use k % m to hash (k: relative position of this char in alphabet); 
        // otherwise fallback to std hash
        private int hash(Key key) {
            if (key instanceof Character) {
                char c = Character.toUpperCase((Character) key);
                if (c >= 'A' && c <= 'Z') {
                    int k = (c - 'A') + 1; // 1, 2, ..., 26
                    return (11 * k) % m;
                }
            }
            return (key.hashCode() & 0x7fffffff) % m;
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            int i;
            for (i = hash(key); keys[i] != null; i = (i+1) % m) {
                if(keys[i].equals(key)) {
                    return vals[i];
                }
            } 
            return null;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");

            // double table size if 50% full
            // if (n >= m/2) resize(2 * m);

            int i;
            for (i = hash(key); keys[i] != null; i = (i+1) % m) {
                if (keys[i].equals(key)) {
                    vals[i] = val;
                    return; // update
                }
            }
            keys[i] = key;
            vals[i] = val;
            n++;
        }

        // private void resize(int cap) {
        //     LinearProbingHT<Key, Value> temp = new LinearProbingHT<>(cap); 
        //     for (int i = 0; i < m; i++) {
        //         if (keys[i] != null) {
        //             temp.put(keys[i], vals[i]);
        //         }
        //     }
        //     keys = temp.keys;
        //     vals = temp.vals;
        //     m = temp.m;
        // }

        public Iterable<Key> keys() {
            Queue<Key> q = new Queue<>();
            for (int i = 0; i < m; i++) {
                if (keys[i] != null) q.enqueue(keys[i]);
            }
            return q; 
        }

        public void printTable() {
            StdOut.printf("LinearProbingHT m=%d, n=%d, load=%.3f (no resizing)%n", m, n, (double)n/m);
            for (int i = 0; i < m; i++) {
                StdOut.printf("%3d: ", i);
                if (keys[i] == null) {
                    StdOut.println("—");
                } else {
                    StdOut.println(keys[i] + " -> " + vals[i]);
                }
            }
        }
            
    }

    public static void main(String[] args) {
        char[] keys = {'E','A','S','Y','Q','U','T','I','O','N'};

        LinearProbingHT<Character, Integer> ht16 = new LinearProbingHT<>(16);
        LinearProbingHT<Character, Integer> ht10 = new LinearProbingHT<>(Math.min(keys.length, 10)); // table size could not be less than keys.lengh
        for (int idx = 0; idx < keys.length; idx++) {
            ht16.put(keys[idx], idx); 
            ht10.put(keys[idx], idx); 
        }
        ht16.printTable();
        StdOut.println();
        ht10.printTable();
    }
}


// LinearProbingHT m=16, n=10, load=0.625 (no resizing)
//   0: —
//   1: S -> 2
//   2: —
//   3: Y -> 3
//   4: I -> 7
//   5: O -> 8
//   6: —
//   7: E -> 0
//   8: U -> 5
//   9: —
//  10: N -> 9
//  11: A -> 1
//  12: Q -> 4
//  13: T -> 6
//  14: —
//  15: —

// LinearProbingHT m=10, n=10, load=1.000 (no resizing)
//   0: T -> 6
//   1: A -> 1
//   2: U -> 5
//   3: I -> 7
//   4: N -> 9
//   5: E -> 0
//   6: Y -> 3
//   7: Q -> 4
//   8: O -> 8
//   9: S -> 2