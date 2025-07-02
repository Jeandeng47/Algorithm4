public class _OrderedArrayMaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int n;
    
    @SuppressWarnings("unchecked")
    public _OrderedArrayMaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
        n = 0;
    }

    public _OrderedArrayMaxPQ() {
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
        // shift larger elements to the right
        int i = n - 1;
        while (i >= 0 && pq[i].compareTo(key) > 0) {
            pq[i + 1] = pq[i];
            i--;
        }
        pq[i + 1] = key;
        n++;
    }

    public Key max() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return pq[n - 1];
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        Key max = pq[n - 1];
        n--;
        pq[n] = null; // avoid loitering
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
