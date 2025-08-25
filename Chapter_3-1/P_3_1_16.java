import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_16 {
    
    public static class BinarySearchST<Key extends Comparable<Key>, Value> {
        private Key[] keys;
        private Value[] vals;
        private int N;

        public BinarySearchST(int cap) {
            keys = (Key[]) new Comparable[cap];
            vals = (Value[]) new Object[cap];
        }

        public int size() { return N; }
        public boolean isEmpty() { return N == 0; }
        public boolean contains(Key k)  { return get(k) != null; }

        public Value get(Key key) {
            if(isEmpty()) return null;
            int i = rank(key); // return the index of key
            if (i < N && keys[i].compareTo(key) == 0) {
                return vals[i];
            } else {
                return null;
            }
        }

        public void put(Key key, Value val) {
            int i = rank(key);

            // update to new value if found
            if (i < N && keys[i].compareTo(key) == 0) {
                vals[i] = val;
                return;
            }

            // if not found, insert the v at i
            // move values [i+1 ... N] to the right
            if (N == keys.length) resize(2 * keys.length);
            for (int j = N; j > i; j--) {
                keys[j] = keys[j-1];
                vals[j] = vals[j-1];
            }
            keys[i] = key;
            vals[i] = val;
            N++;
        }

        public void delete(Key key) {
            if (isEmpty()) throw new NoSuchElementException("Underflow");

            int i = rank(key);
            if (i == N || keys[i].compareTo(key) != 0) return;

            // shift left to fill hole
            for (int j = i; j < N; j++) {
                keys[j] = keys[j+1];
                vals[j] = vals[j+1];
            }
            keys[N - 1] = null;
            vals[N - 1] = null;
            N--;

            if (N > 0 && N == keys.length / 4) resize(keys.length / 2);

        }

        public Key min() { return isEmpty() ? null : keys[0]; }

        public Key max() { return isEmpty() ? null : keys[N - 1]; }

        public Key select(int k) {
            if (k < 0 || k >= N) return null;
            return keys[k];
        }

        public Key ceiling(Key key) {
            if (isEmpty()) return null;
            int i = rank(key);
            if (i == N) return null; // greater than all keys
            return keys[i];
        }

        public Key floor(Key key) {
            if (isEmpty()) return null;
            int i = rank(key);
            if (i < N && keys[i].compareTo(key) == 0) return keys[i];
            if (i == 0) return null; // smaller than all keys
            return keys[i - 1];
        }

        public Iterable<Key> keys() {
            if (isEmpty()) return new Queue<Key>();
            return keys(min(), max());
        }

        public Iterable<Key> keys(Key lo, Key hi) {
            Queue<Key> q = new Queue<>();
            if (lo == null || hi == null) return q;
            if (isEmpty()) return q;

            int i = rank(lo);
            int j = rank(hi);
            for (int k = i; k < j; k++) q.enqueue(keys[k]);
            if (j < N && keys[j].compareTo(hi) == 0) q.enqueue(keys[j]);
            return q;
        }

        // Resize both the keys and vals arrays
        private void resize(int cap) {
            Key[] newK = (Key[]) new Comparable[cap];
            Value[] newV = (Value[]) new Comparable[cap];

            for (int i = 0; i < N; i++) {
                newK[i] = keys[i];
                newV[i] = vals[i];
            }
            keys = newK;
            vals = newV;
        }
        

        // Iterative binary search in an ordered array
        private int rank(Key key) {
            int lo = 0;
            int hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = key.compareTo(keys[mid]);
                if (cmp < 0) hi = mid - 1; // search left
                else if (cmp > 0) lo = mid + 1;
                else return mid;
            }
            return lo; // the number of elements that are less than key
        }
    }

    private static void printKeys(BinarySearchST<String, Integer> st) {
        StdOut.print("keys: ");
        for (String k : st.keys()) StdOut.print(k + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<>(10);

        // put (and update)
        st.put("S", 1);
        st.put("E", 2);
        st.put("A", 3);
        st.put("R", 4);
        st.put("C", 5);
        st.put("H", 6);
        st.put("E", 99);  // update existing

        StdOut.println("-- after puts --");
        StdOut.println("size = " + st.size());      // expect 6 (A C E H R S)
        printKeys(st);                                   // in order

        // get / contains
        StdOut.println("get(E) = " + st.get("E"));  // 99
        StdOut.println("get(Z) = " + st.get("Z"));  // null
        StdOut.println("contains(E) = " + st.contains("E")); // true
        StdOut.println("contains(Z) = " + st.contains("Z")); // false

        // ordered ops
        StdOut.println("min = " + st.min());        // A
        StdOut.println("max = " + st.max());        // S
        StdOut.println("select(0) = " + st.select(0));                 // A
        StdOut.println("select(size-1) = " + st.select(st.size()-1));  // S
        StdOut.println("floor(D) = " + st.floor("D"));  // C
        StdOut.println("ceiling(D) = " + st.ceiling("D")); // E

        // range iteration
        StdOut.print("keys(C, R) = ");
        for (String k : st.keys("C","R")) StdOut.print(k + " ");
        StdOut.println();

        // delete
        st.delete("R");
        st.delete("A");
        StdOut.println("-- after delete(R), delete(A) --");
        StdOut.println("size = " + st.size());      // expect 4
        printKeys(st);

        // delete non-existent (no change)
        st.delete("Z");
        StdOut.println("-- after delete(Z) --");
        printKeys(st);
    }

}
