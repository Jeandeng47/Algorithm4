import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_12 {
    public static class BinarySearchST<K extends Comparable<K>, V> {

        public static class Item<K extends Comparable<K>, V> implements Comparable<Item<K, V>>{
            K key;
            V val;
            public Item(K key, V val) { this.key = key; this.val = val; }
            @Override
            public int compareTo(Item<K, V> that) { return this.key.compareTo(that.key); }
            @Override
            public String toString() { return key + "=" + val; }
        }

        private Item<K,V>[] a;
        private int n;

        // Default constructor
        public BinarySearchST(int cap) {
            a = (Item<K,V>[]) new Item[cap];
            n = 0;
        }

        // Build from an array of items and use mergesort to sort it
        public BinarySearchST(Item<K, V>[] items) {
            // copy non-null values into b
            int m = 0;
            for (Item<K,V> it : items) { if (it != null) m++; }

            Item<K,V>[] b = (Item<K,V>[]) new Item[m];
            int j = 0;
            for (Item<K,V> it : items) if (it != null) b[j++] = it; 

            // mergesort by key (stable)
            mergesort(b);

            // remove duplicates
            int w = 0;
            for (int i = 0; i < m; i++) {
                if (w == 0 || (b[i].key).compareTo(b[w-1].key) != 0) {
                    b[w++] = b[i]; // distinct key
                } else {
                    b[w-1] = b[i]; // same key, only keep the last
                }
            }
            // update 
            a = (Item<K,V>[]) new Item[w];
            System.arraycopy(b, 0, a, 0, w);
            n = w;

        }

        // Mergesort helpers

        private void mergesort(Item<K,V>[] a) {
            int n = a.length;
            Item<K,V>[] aux = (Item<K,V>[]) new Item[n];
            sort(a, aux, 0, n - 1);
        }

        private void sort(Item<K,V>[] a, Item<K,V>[] aux, int lo, int hi) {
            if (lo >= hi) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid);
            sort(a, aux, mid + 1, hi);
            merge(a, aux, lo, mid, hi);
        }

        private void merge(Item<K,V>[] a, Item<K,V>[] aux, int lo, int mid, int hi) {
            System.arraycopy(a, lo, aux, lo, hi - lo + 1); // copy from a to aux
            int i = lo;
            int j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                if      (i > mid)                       a[k] = aux[j++];
                else if (j > hi)                        a[k] = aux[i++];
                else if (aux[j].compareTo(aux[i]) < 0)  a[k] = aux[j++]; // stable
                else                                    a[k] = aux[i++];
            }
        }

        // Core APIs

        public int size()             { return n; }
        public boolean isEmpty()      { return n == 0; }
        public boolean contains(K k){ return get(k) != null; }
        
        public V get(K key) {
            if (isEmpty()) return null;
            int i = rank(key);
            if (i < n && (a[i].key).compareTo(key) == 0) {
                return a[i].val;
            }
            return null;
        }

        public void put(K key, V val) {
            int i = rank(key);
            if (i < n && (a[i].key).compareTo(key) == 0) {
                a[i].val = val;
                return;
            }
            if (n == a.length) resize(2 * a.length);
            for (int j = n; j > i; j--) {
                a[j] = a[j-1];
            }
        }

        public void delete(K key) {
            if (isEmpty()) return;

        }

        public K min() { return isEmpty() ? null : a[0].key; }
        public K max() { return isEmpty() ? null : a[n - 1].key; }
        public K select(int k)   { return (k < 0 || k >= n) ? null : a[k].key; }

        public Iterable<K> keys() {
            Queue<K> q = new Queue<>();
            for (int i = 0; i < n; i++) q.enqueue(a[i].key);
            return q;
        }

        private int rank(K key) {
            int lo = 0;
            int hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = key.compareTo(a[mid].key);
                if (cmp > 0) { // search right
                    lo = mid + 1;
                } else if (cmp < 0) { // search left
                    hi = mid - 1;
                } else {
                    return mid;
                }
            }
            return lo; // index to insert
        }

        private void resize(int cap) {
            Item<K,V>[] b = (Item<K,V>[]) new Item[cap];
            System.arraycopy(a, 0, b, 0, n);
            a = b;
        }
        
        
    }

    private static void printKeys(BinarySearchST<String, Integer> st) {
        StdOut.print("keys: ");
        for (String k : st.keys()) StdOut.print(k + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
      BinarySearchST.Item<String, Integer>[] items = new BinarySearchST.Item[] {
            new BinarySearchST.Item<>("S", 1),
            new BinarySearchST.Item<>("E", 2),
            new BinarySearchST.Item<>("A", 3),
            new BinarySearchST.Item<>("R", 4),
            new BinarySearchST.Item<>("C", 5),
            new BinarySearchST.Item<>("H", 6),
            new BinarySearchST.Item<>("E", 7)   // duplicate; should overwrite prior E
        };

        BinarySearchST<String, Integer> st = new BinarySearchST<>(items);

        // basics
        StdOut.println("-- After constructor(Item[]) --");
        StdOut.println("size = " + st.size());           // 6 (A,C,E,H,R,S)
        printKeys(st);                                       // expect A C E H R S
        StdOut.println("get(E) = " + st.get("E"));       // 7 (last wins)
        StdOut.println("get(Z) = " + st.get("Z"));       // null
        StdOut.println("min = " + st.min());             // A
        StdOut.println("max = " + st.max());             // S
        StdOut.println("select(0) = " + st.select(0));   // A
        StdOut.println("select(size-1) = " + st.select(st.size()-1)); // S


        // update existing + insert new
        st.put("E", 99);                                     // update
        st.put("M", 42);                                     // insert new in middle
        StdOut.println("\n-- After put(E=99), put(M=42) --");
        StdOut.println("get(E) = " + st.get("E"));       // 99
        StdOut.println("size = " + st.size());           // 7
        printKeys(st);                                       // A C E H M R S

        // delete a couple
        st.delete("R");
        st.delete("A");
        StdOut.println("\n-- After delete(R), delete(A) --");
        StdOut.println("size = " + st.size());           // 5
        
    }
}


// -- After constructor(Item[]) --
// size = 6
// keys: A C E H R S
// get(E) = 7
// get(Z) = null
// min = A
// max = S
// select(0) = A
// select(size-1) = S

// -- After put(E=99), put(M=42) --
// get(E) = 99
// size = 6
// keys: A C E H R R

// -- After delete(R), delete(A) --
// size = 6