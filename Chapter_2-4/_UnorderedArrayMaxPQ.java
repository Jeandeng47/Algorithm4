// This class implements an unordered array-based max priority queue.
// Insert: O(1)
// Delete max: O(n)
// Max: O(n)
// Space: O(n)

public class _UnorderedArrayMaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int n;

    @SuppressWarnings("unchecked")
    public _UnorderedArrayMaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
        n = 0;
    }

    public _UnorderedArrayMaxPQ() {
        this(1);
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Key[] newPq = (Key[]) new Comparable[capacity];
        for (int i = 0; i < n; i++) {
            newPq[i] = pq[i];
        }
        pq = newPq;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }


    public void insert(Key key) {
        if (n == pq.length) {
            resize(2 * pq.length);
        }
        pq[n++] = key;
    }

    public Key max() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (pq[i].compareTo(pq[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return pq[maxIndex];
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (pq[i].compareTo(pq[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        Key max = pq[maxIndex];
        pq[maxIndex] = pq[--n]; // replace with the last element
        pq[n] = null; // avoid loitering

        if (n > 0 && n == pq.length / 4) {
            resize(pq.length / 2);
        }

        return max;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n; i++) {
            sb.append(pq[i]);
            if (i < n - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
