import java.util.NoSuchElementException;

public class _MinPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int n;

    @SuppressWarnings("unchecked")
    public _MinPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1]; // 1-based indexing
        n = 0;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(Key v) {
        if (n == pq.length - 1) {
            resize(2 * pq.length);
        }
        pq[++n] = v; // Insert at the end
        swim(n); // Restore heap order
    }

    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key min = pq[1]; // The minimum is at the root
        exch(1, n--); // Move the last element to the root
        pq[n + 1] = null; // Avoid loitering
        sink(1); // Restore heap order

        if (n > 0 && n == (pq.length - 1) / 4) {
            resize(pq.length / 2); 
        }
        return min;
    }

    // Heap order is broken if the child is less than the parent
    // so we need to swim the child up
    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k = k / 2; // Move up to parent
        }
    }

    // Heap order is broken if the parent is greater than the child
    // we need to sink the parent down
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k; // left child
            // pick the smaller child
            if (j < n && less(j + 1, j)) {
                j++;
            }
            if (!less(k, j)) {
                break; // The parent is less than or equal to the child
            }
            exch(k, j);
            k = j; // Move down to child
        }
    }

    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }
}
