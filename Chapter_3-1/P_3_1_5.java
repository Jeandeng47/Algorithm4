import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_5 {
    public static class SequentialSearchST<Key, Value> {
        private Node first;
        private int N;

        private class Node {
            Key key;
            Value val;
            Node next;
            public Node(Key k, Value v, Node n) {
                key = k;
                val = v;
                next = n;
            }
        }

        public SequentialSearchST() {
            first = null;
            N = 0;
        }


        // Search the value corresponding to the given key in the list
        // return the found value or null
        public Value get(Key key) {
            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key)) {
                    return x.val;
                }
            }
            return null;
        }

        // Find the given key in the list, update the value
        // if it's found, otherwise create a new K-V node
        // and insert it at the front of the list
        public void put(Key key, Value val) {
            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key)) {
                    x.val = val;
                    return;
                } 
            }
            first = new Node(key, val, first);
            N++;
        }

        public int size() { return N; }

        public void delete(Key key) {
            // empty
            if (first == null) return;
            // only one node
            if (key.equals(first.key)) {
                first = first.next;
                N--;
                return;
            }

            Node prev = first;
            Node curr = first.next;
            while (curr != null) {
                if (key.equals(curr.key)) {
                    prev.next = curr.next;
                    N--;
                    return;
                }
                prev = curr;
                curr = curr.next;
            }
        }

        public Iterable<Key> keys() {
            Queue<Key> q = new Queue<>();
            for (Node x = first; x != null; x = x.next) {
                q.enqueue(x.key);
            }
            return q;
        }

    }

    public static void main(String[] args) {

        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        st.put("A", 1);
        st.put("B", 2);
        st.put("C", 3);
        StdOut.println("After put, size = " + st.size()); // 3
        for (String k : st.keys()) StdOut.print(k + " "); // C B A
        StdOut.println();

        // get + update
        StdOut.println("get(B) = " + st.get("B"));          // 2
        st.put("B", 20);                                        // update
        StdOut.println("get(B) after update = " + st.get("B")); // 20

        // delete missing
        st.delete("ZZ");
        StdOut.println("After delete(ZZ), size = " + st.size()); // 3

        // delete head (current head is C)
        st.delete("C");
        StdOut.println("After delete(C), size = " + st.size());  // 2
        StdOut.print("keys = ");
        for (String k : st.keys()) StdOut.print(k + " ");   // B A (likely)
        StdOut.println();

        // delete tail (A) and remaining (B)
        st.delete("A");
        st.delete("B");
        StdOut.println("After delete A & B, size = "+ st.size()); // 0
        StdOut.print("keys = ");
        for (String k : st.keys()) StdOut.print(k + " ");
        StdOut.println();
        StdOut.println("get(A) on empty = " + st.get("A")); // null
    }
}

// After put, size = 3
// C B A
// get(B) = 2
// get(B) after update = 20
// After delete(ZZ), size = 3
// After delete(C), size = 2
// keys = B A
// After delete A & B, size = 0
// keys =
// get(A) on empty = null
