public class _UnorderedLListMaxPQ<Key extends Comparable<Key>> {
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

    public _UnorderedLListMaxPQ() {
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
        Node oldFirst = first;
        first = new Node(key);
        first.next = oldFirst;
        n++;
    }

    public Key max() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue underflow");
        }
        Key m = first.key;
        for (Node x = first.next; x != null; x = x.next) {
            if (x.key.compareTo(m) > 0) {
                m = x.key;
            }
        }
        return m;
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue underflow");
        }
        Node prev = null, curr = first;
        Node maxPrev = null, maxNode = first;
        while (curr != null) {
            if (curr.key.compareTo(maxNode.key) > 0) {
                maxNode = curr;
                maxPrev = prev;
            }
            prev = curr;
            curr = curr.next;
        }
        if (maxPrev == null) {
            first = first.next; // maxNode is the first node
        } else {
            maxPrev.next = maxNode.next; // unlink maxNode
        }
        n--;
        return maxNode.key;
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
