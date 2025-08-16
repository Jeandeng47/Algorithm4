import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

// IndexPQ allows us to refer to keys that are already on the priority
// queue, to do this we associate the index with each key.

// public APIs:
// void insert(int i , Key k)


public class _IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int maxN;       // capacity of pq
    private int[] pq;       // pq[i] = x, the ith element in heap is keys[i]
    private int[] qp;       // qp[i] = y, keys[i] is at position y in the heap
    private int n;          // number of elements on pq
    private Key[] keys;     // stores the actual key

    // IndexMinPQ creates a priority queue of capacity maxN with
    // possible indices between 0 and maxN - 1
    public _IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException("Capacity must be non-negative");
        this.maxN = maxN;
        this.n = 0;
        
        this.keys = (Key[]) new Comparable[maxN + 1];
        this.pq = new int[maxN + 1];
        this.qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    // Insert key and asscoiate it with i
    public void insert(int i, Key key) {
        validateIndex(i);
        if (contains(i)) {
            throw new IllegalArgumentException("Index i is already in PQ");
        }
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;

        // call swim() to maintain heap property
        swim(n);
    }

    // Change the key asscociated with i to key
    public void change(int i, Key key) {
        validateIndex(i);
        if (!contains(i)) {
            throw new NoSuchElementException("Index i is not in PQ");
        }
        keys[i] = key;
        // we do not know the new key is greater/smaller
        swim(qp[i]);
        sink(qp[i]);
    }

    // Is i associated with some key?
    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    // Remove i and its associated key
    public void delete(int i) {
        validateIndex(i);
        if (!contains(i)) {
            throw new NoSuchElementException("Index i is not in PQ");
        }
        // exchange the item to be deleted and the last
        // element in the heap
        int index = qp[i];
        exch(index, n--); // now new item is at qp[i] = index position of heap

        swim(index);
        sink(index);

        keys[i] = null;
        qp[i] = -1;
    }

    // Return a minimal key
    public Key min() {
        // The order is stored at pq, the minimal item is palced pq[0]
        // use pq[0] to index into keys
        if (n == 0) {
            throw new NoSuchElementException("PQ underflow");
        }
        return keys[pq[1]];
    }

    // Return a minimal item's index
    public int minIndex() {
        if (n == 0) {
            throw new NoSuchElementException("PQ underflow");
        }
        return pq[1];
    }

    // Remove a minimum key and returns its index
    public int delMin() {
        if (n == 0) {
            throw new NoSuchElementException("PQ underflow");
        }
        int min = pq[1]; // min index
        exch(1, n--); // swap the first and last key in heap
        sink(1);
        assert min == pq[n+1];
        
        keys[min] = null;
        qp[min] = -1;
        pq[n+1] = -1;

        return min;
    }

    // Is the priority queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // Return number of items in the priority queue
    public int size() {
        return n;
    }

    // Helpers
    private void validateIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Index must be non-negative");
        }
        if (i > maxN) {
            throw new IllegalArgumentException("Index >= capacity: " + i);
        }
    }

    // MinPQ property is broken if children is 
    // smaller than parent
    private void swim(int k) { // k: in qp[]
        while (k > 1 && greater(k/2, k)) {
            exch(k, k / 2);
            k = k / 2; // move up
        }
    }

    // MinPQ property is broken if parent is
    // larger than children
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k; // left child
            if (j < n && greater(j, j+1)) {
                j++; // smaller child
            }
            // if parent k is smaller than the smaller child j
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        } 
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    // Only modify pq and qp
    private void exch(int i, int j) {
        // exchange order
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        // qp[k]: the position of k in pq[]
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    // Iterator that iterates keys in heap in ascending order
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private _IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new _IndexMinPQ<>(pq.length - 1);
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i], keys[pq[i]]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
        
    }

    // example test client
    public static void main(String[] args) {
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        _IndexMinPQ<String> pq = new _IndexMinPQ<>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // print each key
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            StdOut.println(i + " " + strings[i]);
        }
        StdOut.println();


        // iterator print
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        for (int i : pq) {
            StdOut.println(i + " " + strings[i]);
        }
        while (!pq.isEmpty()) {
            pq.delMin();
        }
    }
}


// Example: raw data
// n = 0
// pq   = [_, _, _, _, _]      // shows the heap-ordered index
// qp   = [-1, -1, -1, -1, -1] // the natural order of elements to be inserted
// keys = [null, null, null, null, null] // actual keys


// (1) Insert(0, D)
// keys[0] = D, pq[1] = 0, qp[0] = 1

// n = 1
// pq   = [_, 0, _, _, _]       
// qp   = [1, -1, -1, -1, -1]
// keys = [D, null, null, null, null]


// (2) Insert(1, B)
// keys[1] = B, pq[2] = 1, qp[1] = 2

// n = 2
// pq   = [_, 0, 1, _, _]       
// qp   = [1, 2, -1, -1, -1]
// keys = [D, B, null, null, null]

// swim(2)
// - parent(2) = 2 / 2 = 1, keys[pq[1]] = keys[0] = D
// < keys[pq[2]] = B, exch(1, 2)
// pq[1] = pq[2] = 1, pq[2] = pq[1] = 0  
// qp[1] = 1, pq[0] = 2 

// n = 2
// pq   = [_, 1, 0, _, _] ->  B, D     
// qp   = [2, 1, -1, -1, -1] -> index 0 is at pq pos2, index 1 is at pq pos1
// keys = [D, B, null, null, null]
// Heap order: [1(B), 0(D)]
