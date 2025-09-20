import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_21 {
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

        // ==== Average Probes for Hit ====

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


        // ==== Average Probes for Miss ====
        // How to comput average probes for miss?
        // 1. Single miss: in linear probing, an unsuccessful search
        // started inside a cluster of length r will take (r-s+1) probes
        // when started at the offset s (0-based)
        // 2. Overall misses: Summing from position s = 0, 1, 2, ..., r-1
        // gives r*(r+3)/2 total probes contribued by that cluster

        // -> Total probes in this cluster 
        // = Sum{s=0..r-1}[r-s+1] 
        // = (r+1) + r + (r-1) + ... + 2
        // = r * (r+3) / 2

        // 3. Special case: when starting at an empty slot, it always costs 1 probe
        // -> Total probes in the whole table 
        // -> [#empties + Sum(cluster_probes_i)]/ M
        // -> [(M - N)  + Sum(cluster_probes_i)]/ M
        public double avgSearchMissCost() {
            if (n == 0) return 1.0;
            
            int empty = m - n;
            int totalProbes = empty;

            // Collect cluster
            List<Integer> runs = new ArrayList<>();
            int i = 0;
            while (i < m) {
                if (keys[i] == null) { i++; continue; }
                int r = 0;
                while (i < m && keys[i] != null) { i++; r++; }
                runs.add(r);
            }

            // if both ends of the table are occupied, merge
            // the end & head into 1 cluster
            if (keys[0] != null && keys[m-1] != null && runs.size() >=2) {
                int first = runs.removeFirst();
                int last = runs.removeLast();
                runs.set(0, first + last);
                runs.remove(runs.size() - 1);
            }

            // Sum r(r+3) /2 for all clusters
            for (int r : runs) {
                totalProbes += r * (r + 1) / 2;
            }
            
            return (double) totalProbes / m;
        }


        public double theoreticalAvgMissCost() {
        double a = loadFactor();
        if (a >= 1.0) return Double.POSITIVE_INFINITY;
        double denom = 1.0 - a;
        return 0.5 * (1.0 + 1.0 / (denom * denom));
    }

    }
   
    public static void main(String[] args) {
        LinearProbingHashST<Integer, String> st = new LinearProbingHashST<>(10);

        // Insert a few keys that will create clusters
        int N = 100;
        Random rnd = new Random();
        int[] keys = new int[N];
        for (int i = 0; i < N; i++) {
            keys[i] = rnd.nextInt(N);
        }
        for (int k : keys) {
            st.put(k, "v" + k);
        }

        StdOut.println("N=" + st.size() + " M=" + st.m);
        StdOut.printf("a = %.4f%n", st.loadFactor());
        StdOut.printf("Exact miss cost     = %.4f probes%n", st.avgSearchMissCost());
        StdOut.printf("Theoretical miss    = %.4f probes%n", st.theoreticalAvgMissCost());
        StdOut.printf("Exact hit cost      = %.4f probes%n", st.avgSearchHitCost());
        StdOut.printf("Theoretical hit     = %.4f probes%n", st.theoreticalAvgHitCost());
    }
}



// N=65 M=160
// a = 0.4063
// Exact miss cost     = 1.6688 probes
// Theoretical miss    = 1.9183 probes
// Exact hit cost      = 1.0000 probes
// Theoretical hit     = 1.3421 probes

// ==== Avg. hit cost vs Avg. miss cost ===
// 1. Hit cost
// Scenario: find a key that exists in the table
// Assumption: Each existing key is equally likely to be queried.
// Averaging method: consider those those N keys.

// avgHit = #probes for N keys / N

// 2. Miss cost:
// Scenario: find a key that does not exist in the table
// Assumption: hash function is random, which means the starting slot 
// for the search is equally likely to be any of the M positions
// Averaging method: We only average over those N keys.
// avgMiss =  #probes for M starting points / M


