import edu.princeton.cs.algs4.Queue;

public class _LinearProbingHST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;  // number of K-V pairs
    private int m;  // size of linear probing table (M >= N)
    private Key[] keys;
    private Value[] vals;

    public _LinearProbingHST() {
        this(INIT_CAPACITY);   
    }

    public _LinearProbingHST(int cap) {
        this.n = 0;
        this.m = cap;
        this.keys = (Key[]) new Object[m];
        this.vals = (Value[]) new Object[m];        
    }

    public int size() { return n; }
    public boolean isEmpty() { return size() == 0; }

    private int hashSimple(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4); // use more high-bits info
        return h & (m-1); // equal to h % m
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        // start with i=h(k), do linear search until null is hit
        for (int i = hash(key); keys[i] != null; i = (i+1) % m) {
            if (keys[i].equals(key)) return vals[i];
        }
        return null; // key not found
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        if (val == null) { delete(key); return; }

        // double table size if 50% full (must keep utilization < 1/2)
        if (n >= m/2) resize(2*m);
        
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) { 
                vals[i] = val;  // update val
                return; }
        }
        // put new KV pair
        keys[i] = key;
        vals[i] = val;
        n++;

    }

    // Resize the hash table to given capacity bt re-hashing all the key
    private void resize(int capacity) {
        _LinearProbingHST<Key, Value> temp = new _LinearProbingHST<>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) temp.put(keys[i], vals[i]); 
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        if (!contains(key)) return;

        // find position i of key (hash(key) != pos)
        int i = hash(key);
        while (!key.equals(keys[i])) { i = (i + 1) % m; }

        // delete K-V
        keys[i] = null; vals[i] = null; n--;
        
        // rehash all the keys in the same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete & reinsert
            Key k2Redo = keys[i]; Value v2Redo = vals[i];
            keys[i] = null; vals[i] = null; n--;
            put(k2Redo, v2Redo);
            i = (i + 1) % m;
        }
        
        // resizing:  halves size of array if it's 12.5% (1/8) full or less
        if (n > 0 && n <= m/8) resize(m/2);
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) q.enqueue(keys[i]);
        }
        return q; 
    }
    
}
