import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_19 {

    public static class LinearProbingHST<Key, Value> {
        private static final int INITCAPACITY = 4;

        private int n;  // number of K-V pairs
        private int m;  // size of linear probing table (M >= N)
        private Key[] keys;
        private Value[] vals;

        public LinearProbingHST() {
            this(INITCAPACITY);   
        }

        public LinearProbingHST(int cap) {
            this.n = 0;
            this.m = cap;
            this.keys = (Key[]) new Object[m];
            this.vals = (Value[]) new Object[m];        
        }

        public int size() { return n; }
        public boolean isEmpty() { return size() == 0; }

        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % m;
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("Key is null");
        
            for (int i = hash(key); keys[i] != null; i = (i+1) % m) {
                if (keys[i].equals(key)) return vals[i];
            }
            return null;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");

            if (n >= m/2) resize(2*m);
            
            int i;
            for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
                if (keys[i].equals(key)) { 
                    vals[i] = val;  
                    return; 
                }
            }
            keys[i] = key;
            vals[i] = val;
            n++;

        }

        // Resize the hash table to given capacity bt re-hashing all the key
        private void resize(int capacity) {
            LinearProbingHST<Key, Value> temp = new LinearProbingHST<>(capacity);
            for (int i = 0; i < m; i++) {
                if (keys[i] != null) temp.put(keys[i], vals[i]); 
            }
            keys = temp.keys;
            vals = temp.vals;
            m = temp.m;
        }

        public Iterable<Key> keys() {
            Queue<Key> q = new Queue<>();
            for (int i = 0; i < m; i++) {
                if (keys[i] != null) q.enqueue(keys[i]);
            }
            return q; 
        }
        
    }

    public static class SeparateChainingHST<Key, Value> {
        private static final int INITCAPACITY = 4;

        private int n;  // number of K-V pairs
        private int m;  // number of linked lists
        private SequentialSearchST<Key, Value>[] st; // Array of linked lists

        public SeparateChainingHST() {
            this(INITCAPACITY);
        }

        public SeparateChainingHST(int m) {
            this.m = m;
            this.st = new SequentialSearchST[m];
            // create M empty linked list
            for (int i = 0; i < m; i++) {
                st[i] = new SequentialSearchST<>();
            }
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

            if (n >= 10 * m) { resize(2 * m); }

            int i = hash(key);
            if (!contains(key)) n++;
            st[i].put(key, val);
        }

        private void resize(int chains) {
            // new hash table
            SeparateChainingHST<Key, Value> temp = new SeparateChainingHST<>(chains);
            for (int i = 0; i < m; i++) {
                for (Key k : st[i].keys()) {
                    temp.put(k, st[i].get(k));
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
        
    }

    public static void main(String[] args) {
        char[] keys = {'E','A','S','Y','Q','U','T','I','O','N'};

        LinearProbingHST<Character, Integer> lpt = new LinearProbingHST<>(16);
        SeparateChainingHST<Character, Integer> sct = new SeparateChainingHST<>(4);
        for (int idx = 0; idx < keys.length; idx++) {
            lpt.put(keys[idx], idx); 
            sct.put(keys[idx], idx);
        }

        StdOut.println("Keys in Linear Probing Table: ");
        for (Character k : lpt.keys()) { StdOut.print(k + " "); }
        StdOut.println();

        StdOut.println("Keys in Separate Chaining Table: ");
        for (Character k : sct.keys()) { StdOut.print(k + " "); }
        StdOut.println();
    }
}   