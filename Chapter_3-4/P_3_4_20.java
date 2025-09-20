import java.security.Key;
import java.util.NoSuchElementException;

public class P_3_4_20 {
    public static class LinearProbingHashST<Key, Value> {
        private static final int INIT_CAPACITY = 4;
        private int n;        
        private int m;       
        private Key[] keys;
        private Value[] vals; // the values

        public LinearProbingHashST(int capacity) {
            m = Math.max(INIT_CAPACITY, capacity);
            keys = (Key[])   new Object[m];
            vals = (Value[]) new Object[m];
        }

        public LinearProbingHashST() { this(INIT_CAPACITY); }

        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % m;
        }

        public int size() { return n; }

        public boolean isEmpty() { return n == 0; }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("contains() with null key");
            return get(key) != null;
        }

        private void resize(int capacity) {
            LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<>(capacity);
            for (int i = 0; i < m; i++) {
                if (keys[i] != null) temp.put(keys[i], vals[i]);
            }
            this.m = temp.m;
            this.n = temp.n;
            this.keys = temp.keys;
            this.vals = temp.vals;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("put() with null key");
            if (val == null) { delete(key); return; }

            if (n >= m/2) resize(2*m);

            int i = hash(key);
            while (keys[i] != null) {
                if (keys[i].equals(key)) { vals[i] = val; return; }
                i = (i + 1) % m;
            }
            keys[i] = key;
            vals[i] = val;
            n++;
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("get() with null key");
            int i = hash(key);
            while (keys[i] != null) {
                if (keys[i].equals(key)) return vals[i];
                i = (i + 1) % m;
            }
            return null;
        }

        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("delete() with null key");
            if (!contains(key)) return;

            int i = hash(key);
            while (!key.equals(keys[i])) i = (i + 1) % m;

            keys[i] = null;
            vals[i] = null;

            i = (i + 1) % m;
            while (keys[i] != null) {
                Key keyToRedo = keys[i];
                Value valToRedo = vals[i];
                keys[i] = null;
                vals[i] = null;
                n--;
                put(keyToRedo, valToRedo);
                i = (i + 1) % m;
            }

            n--;

            if (n > 0 && n <= m/8) resize(m/2);
        }

        // Average cost (number of probes) of a successful search
        public double avgSearchHitCost() {
            if (n == 0) return 0.0;

            int totalProbes = 0;
            for (int idx = 0; idx < m; idx++) {
                if (keys[idx] != null) {
                    totalProbes += hitProbes(keys[idx]);
                }
            }
            return (double) totalProbes / n;
        }

        // Count how many probes "get(key)" would make, given key exists
        private int hitProbes(Key key) {
            int probes = 1;            
            int i = hash(key);
            while (keys[i] != null && !keys[i].equals(key)) {
                i = (i + 1) % m;
                probes++;
            }
            if (keys[i] == null) {
                throw new NoSuchElementException("Key unexpectedly not found during probe count");
            }
            return probes;
        }

        public double loadFactor() { return (double) n / m; }

        // E[hit] = 1/2 * [1 + 1/(1-a)]
        public double theoreticalAvgHitCost() {
            double alpha = loadFactor();
            return 0.5 * (1.0 + 1.0 / (1.0 - alpha));
        }

    }
   
    public static void main(String[] args) {
        LinearProbingHashST<Integer, String> st = new LinearProbingHashST<>(8);

        // Insert a few keys that will create clusters
        int[] keys = { 1, 9, 17, 25, 2, 10, 3, 11, 4, 12 };
        for (int k : keys) {
            st.put(k, "v" + k);
        }

        System.out.println("N = " + st.size());
        System.out.println("M = " + st.m);
        System.out.printf("Load factor a = %.4f%n", st.loadFactor());
        System.out.printf("Average successful-search cost (measured) = %.4f probes%n", st.avgSearchHitCost());
        System.out.printf("Theoretical estimate (Knuth)             = %.4f probes%n", st.theoreticalAvgHitCost());
    }
}

// N = 10
// M = 32
// Load factor a = 0.3125
// Average successful-search cost (measured) = 1.0000 probes
// Theoretical estimate (Knuth)             = 1.2273 probes