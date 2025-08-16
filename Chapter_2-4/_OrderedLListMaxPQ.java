// This class implements an ordered linked-list-based max priority queue.
// Insert: O(n)
// Delete max: O(1)
// Max: O(1)
// Space: O(n)

public class _OrderedLListMaxPQ<Key extends Comparable<Key>> {
    private Node first;
    private int n;

    private class Node {
        Key key;
        Node next;

        Node(Key key) {
            this.key = key;
            this.next = null;
        }
    }

    public _OrderedLListMaxPQ() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(Key key) {
        Node newNode = new Node(key);
        // insert it at head if it's the largest
        if (first == null || first.key.compareTo(key) < 0) {
            newNode.next = first;
            first = newNode;
        } else {
            // find the correct position to insert
            Node curr = first;
            while (curr.next != null && curr.next.key.compareTo(key) >= 0) {
                curr = curr.next;
            }
            newNode.next = curr.next;
            curr.next = newNode;
        }
        n++;
    }

    public Key max() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue underflow");
        }
        return first.key; // the largest element is always at the head
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue underflow");
        }
        Key maxKey = first.key;
        first = first.next; // remove the head
        n--;
        return maxKey;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Node x = first; x != null; x = x.next) {
            sb.append(x.key);
            if (x.next != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString().trim();
    }
}
