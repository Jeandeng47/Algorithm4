import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_18 {
    public static class SeparateChainingHST<Key, Value> {
        private static final int INITCAPACITY = 4;

        private int n;  // number of K-V pairs
        private int m;  // number of linked lists
        private double maxAvgList; // maxAvgList = max(N / M)
        private SequentialSearchST<Key, Value>[] st; // Array of linked lists

        public SeparateChainingHST(int init, double max) {
            this.m = nextPrime(Math.max(2, init)); // next prime (m >= 2)
            this.maxAvgList = max;

            this.st = new SequentialSearchST[m];
            for (int i = 0; i < m; i++) {
                st[i] = new SequentialSearchST<>();
            }
        }

        private int nextPrime(int x) {
            if (x <= 2) return 2;
            int p = (x % 2 == 0) ? x + 1 : x;
            while (!isPrime(p)) {
                p += 2; // move to next odd
            }
            return p;
        }

        private boolean isPrime(int n) {
            if (n < 2) return false;
            if (n % 2 == 0) return n == 2;
            for (int d = 3; d * d <= n; d += 2) {
                if (n % d == 0) return false;
            }
            return true;
        }

        public int size() { return n; }
        public boolean isEmpty() { return size() == 0; }

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

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");

            // resize up if exceeds the tolerated bound
            if ((n + 1.0)/ m > maxAvgList) { resize(nextPrime(2 * m)); }

            int i = hash(key);
            if (!contains(key)) n++;
            st[i].put(key, val);
        }

        private void resize(int newM) {
            // new hash table
            SeparateChainingHST<Key, Value> temp = new SeparateChainingHST<>(newM, this.maxAvgList);
            for (int i = 0; i < m; i++) {
                for (Key k : st[i].keys()) {
                    temp.put(k, st[i].get(k));
                }
            }
            this.m  = temp.m;
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
        
        // Create a table with initial chains=4, maxAvgList=2
        SeparateChainingHST<String, Integer> st =new SeparateChainingHST<>(2, 2.0);

        
        String[] keys = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        for (int i = 0; i < keys.length; i++) {
            st.put(keys[i], i);
            if (i % 3 == 0) { 
                StdOut.println("Insert " + keys[i]);
                st.printTable();
                StdOut.println();
            }
        }
    }
}

// Construct a SeparateChainingHashST that specify the average number
// of probes to be tolerate for searches
// 1. Average number of probes = avg list length (n / m)
// 2. Prime modulus: always keep m prime in hash code

// Insert S
// Hash table with 2 chains:
// 0: —
// 1: S

// Insert R
// Hash table with 2 chains:
// 0: R
// 1: A -> E -> S

// Insert E
// Hash table with 5 chains:
// 0: A
// 1: —
// 2: H -> C -> R
// 3: S
// 4: E

// Insert M
// Hash table with 5 chains:
// 0: A
// 1: —
// 2: M -> H -> C -> R
// 3: X -> S
// 4: E

// Insert E
// Hash table with 11 chains:
// 0: X -> M
// 1: C
// 2: —
// 3: E -> P
// 4: —
// 5: R
// 6: S -> H
// 7: —
// 8: —
// 9: —
// 10: L -> A