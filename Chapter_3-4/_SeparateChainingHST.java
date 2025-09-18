

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;

public class _SeparateChainingHST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;  // number of K-V pairs
    private int m;  // number of linked lists
    private SequentialSearchST<Key, Value>[] st; // Array of linked lists

    public _SeparateChainingHST() {
        this(INIT_CAPACITY);
    }

    public _SeparateChainingHST(int m) {
        this.m = m;
        this.st = new SequentialSearchST[m];
        // create M empty linked list
        for (int i = 0; i < m; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    public int size() { return n; }
    public boolean isEmpty() { return size() == 0; }

    // Simple hash function from textbook
    // returns value between 0 and m-1
    private int hashSimple(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // Hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
    // Example: h = 0x12345678
    // h >>> 20 = 0x00001234 -> take [20, 31] bit
    // h >>> 12 = 0x00012345 -> take [12, 31] bit
    // h >>> 7  = 0x002468AC
    // h >>> 4  = 0x01234567
    // h = h ^ (h>>>20) ^ (h>>>12) ^ (h>>>7) ^ (h>>>4)
    //   = 0x12345678 ^ 0x00001234 ^ 0x00012345 ^ 0x002468AC ^ 0x01234567
    //   = 0x111111FE

    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4); // use more high-bits info
        return h & (m-1); // equal to h % m
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        int i = hash(key);
        return st[i].get(key);
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        if (val == null) { delete(key); return; }
        
        // double table size if list is too long (len >= 10)
        if (n >= 10 * m) { resize(2 * m); }

        int i = hash(key);
        if (!contains(key)) n++;
        st[i].put(key, val);
    }

    // resize the hash table to have the given number of chains,
    // rehashing all of the keys!!!
    private void resize(int chains) {
        // new hash table
        _SeparateChainingHST<Key, Value> temp = new _SeparateChainingHST<>(chains);
        // move & rehash original keys
        for (int i = 0; i < m; i++) {
            for (Key k : st[i].keys()) {
                temp.put(k, st[i].get(k));
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        
        int i = hash(key);
        if (st[i].contains(key)) n--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2*m) resize(m / 2);
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                q.enqueue(key);
            }
        }
        return q;
    }
    
}