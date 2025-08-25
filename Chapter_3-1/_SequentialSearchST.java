// Implement sysmbol table using unordered linked-list
// search: O(N)
// insert: (N)

public class _SequentialSearchST<Key, Value> {
    private Node first;

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
    }
}
