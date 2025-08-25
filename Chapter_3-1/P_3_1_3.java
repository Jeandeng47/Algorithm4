// Implement symbol table using an ordered linked list
// search: O(N) (cannot utilize binary search)
// insert: O(N)

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_3 {
    public static class OrderedLlistST<Key extends Comparable<Key>, Value>  {
        private Node first;
        private int N;

        private class Node {
            Key key;
            Value val;
            Node next;
            Node(Key k, Value v, Node n) { key = k; val = v; next = n; }
        }

        public int size()               { return N; }
        public boolean isEmpty()        { return N == 0; }
        public boolean contains(Key k)  { return get(k) != null; }

        public Value get(Key key) {
            Node x = first;
            // linear search
            while (x != null && (x.key).compareTo(key) < 0) {
                x = x.next; // x stops at >= key
            }
            if ( x != null && (x.key).compareTo(key) == 0) {
                return x.val;
            }
            return null;
        }
        
        public void put(Key key, Value val) {
            if (val == null) { delete(key); return; }
            
            // use two pointers
            Node prev = null;
            Node curr = first;
            while (curr != null && (curr.key).compareTo(key) < 0) {
                prev = curr;
                curr = curr.next;
            }

            // if found, update the value
            if (curr != null && (curr.key).compareTo(key) == 0) {
                curr.val = val;
                return;
            }

            // if not found, insert between prev & curr
            Node newNode = new Node(key, val, curr);
            if (prev == null) {
                first = newNode; // insert into empty list
            } else {
                prev.next = newNode;
            }
            N++;
        }

        public void delete(Key key) {
            Node prev = null;
            Node curr = first;
            while (curr != null && (curr.key).compareTo(key) < 0) {
                prev = curr;
                curr = curr.next; // stop at curr >= key
            }
            // if not found, return
            if (curr == null || (curr.key).compareTo(key) != 0) return;

            // if found, delete it between prev and curr
            if (prev == null) {
                first = curr.next;
            } else {
                prev.next = curr.next;
            }
            N--;
        }

        public Iterable<Key> keys() {
            Queue<Key> q = new Queue<>();
            for (Node x = first; x != null; x = x.next) q.enqueue(x.key);
            return q;
        }
            
    }
    public static void main(String[] args) {
        OrderedLlistST<String, Integer> st = new OrderedLlistST<>();

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