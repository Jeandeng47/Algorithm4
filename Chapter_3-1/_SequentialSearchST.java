// Implement sysmbol table using unordered linked-list
// search: O(N)
// insert: (N)

import edu.princeton.cs.algs4.Queue;

public class _SequentialSearchST<Key, Value> {
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

    public _SequentialSearchST() {
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
        return  null;
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
