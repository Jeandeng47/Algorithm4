// Implement symbol table using an unordered array
// search: O(N)
// insert: O(1)

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_2 {
    public static class UnorderedArrST<Key extends Comparable<Key>, Value> {
        private Key[] keys;
        private Value[] vals;
        private int N;
        
        public UnorderedArrST(int cap) {
            keys = (Key[]) new Comparable[cap];
            vals = (Value[]) new Comparable[cap];
            N = 0;
        }

        public int size()               { return N; }
        public boolean isEmpty()        { return N == 0; }
        public boolean contains(Key k)  { return get(k) != null; }

        // Linear search
        public Value get(Key key) {
            for (int i = 0; i < N; i++) {
                if (keys[i].equals(key)) {
                    return vals[i];
                }
            }
            return null;
        }

        public void put(Key key, Value val) {
            if (val == null) {
                delete(key);
                return;
            }

            // update if found
            for (int i = 0; i < N; i++) {
                if (keys[i].equals(key)) {
                    vals[i] = val;
                    return;
                }
            }

            // grow if full
            if (N == keys.length) resize(2 * keys.length);

            // if not found, insert at the end
            keys[N] = key;
            vals[N] = val;
            N++;
        }

        public void delete(Key key) {
            if (isEmpty()) { throw new NoSuchElementException(); }
            for (int i = 0; i < N; i++) {
                if (keys[i].equals(key)) {
                    // move item at N to pos i
                    keys[i] = keys[N - 1];
                    vals[i] = vals[N - 1];
                    keys[N - 1] = null;
                    vals[N - 1] = null;
                    N--;
                    if (N > 0 && N == keys.length / 4) resize(keys.length / 2);
                    return;
                }
            }
        }

        public Iterable<Key> keys() {
            Queue<Key> q = new Queue<Key>();
            for (int i = 0; i < N; i++) q.enqueue(keys[i]);
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
    }

    public static void main(String[] args) {
        UnorderedArrST<String, Integer> st = new UnorderedArrST<>(2);

        StdOut.println("Init, isEmpty=" + st.isEmpty() + ", size=" + st.size()); // true,0

        st.put("A", 1);
        st.put("B", 2);
        st.put("C", 3); // resize
        StdOut.printf("Put ABC, size=%d, get(A)=%d, contains(B)=%s%n", 
        st.size(), st.get("A"), st.contains("B")); // 3, 1, true

        st.put("B", 20); // update
        StdOut.println("Update B, get(B)=" + st.get("B")); // 20

        st.delete("ZZ"); // no-op
        st.delete("B");  // remove present key
        StdOut.println("Delete B, contains(B)=" + st.contains("B") + ", size=" + st.size()); // false,2

        st.put("Y", 7);
        st.put("Y", null); // delete-by-null convention
        StdOut.println("Delete Y, contains(Y)=" + st.contains("Y")); // false

        StdOut.print("Iterate keys: ");
        for (String k : st.keys()) StdOut.print(k + " ");
        StdOut.println();

        st.delete("A");
        st.delete("C");
        StdOut.println("Delete A & C, isEmpty=" + st.isEmpty() + ", size=" + st.size()); // true,0

        
    }
}


// Init, isEmpty=true, size=0
// Put ABC, size=3, get(A)=1, contains(B)=true
// Update B, get(B)=20
// Delete B, contains(B)=false, size=2
// Delete Y, contains(Y)=false
// Iterate keys: A C
// Delete A & C, isEmpty=true, size=0