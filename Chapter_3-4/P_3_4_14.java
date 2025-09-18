import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_14 {

    public static class LPT {
        private Character[] keys;
        private int M;

        public LPT(int m) {
            this.M = m;
            this.keys = new Character[M];
        }

        // Instead of hash function, we pass hash index from the outside
        public void put(char c, int home) {
            int i = ((home % M) + M) % M;
            while (keys[i] != null) {
                i = (i + 1) % M;
            }
            keys[i] = c;
        }

        public int missFrom(int start) {
            int i = ((start % M) + M) % M;
            int probes = 0;
            while (keys[i] != null) {
                probes++;
                i = (i + 1) % M;
            }
            return probes + 1; // include the probe that hits empty slot
        }

        // Simulate a "miss" whose hash is uniformly random in [0..M-1]
        // (Assuming each search key is likely to hash table position)
        public double avgMissProbes(int trials, Random rnd) {
            int total = 0;
            for (int t = 0; t < trials; t++) {
                int start = rnd.nextInt(M);
                total += missFrom(start);
            }
            return total / (double) trials;
        }
    }

    // Define interface for “forced home index” mapping in each scenario.
    public interface Indexer { int idx(int k); }

    private static void testScenario(String label, char[] items, 
                                        int M, Indexer f, Random rnd) {
        LPT ht = new LPT(M);

        // Avoid hash table being full, otherwise while(keys[i] != null)
        // will give us an infinite loop -> add at most M - 1 items
        int limit = Math.min(items.length, M - 1);
        for (int i = 0; i < limit; i++) {
            ht.put(items[i], f.idx(i));
        }

        int trials = 200_000;
        double avgMiss = ht.avgMissProbes(trials, rnd);
        StdOut.printf("%s:  avg_miss_probes = %.2f%n",
                label, avgMiss);
    }
    
    public static void main(String[] args) {
        int M = 4000;
        int N = 2000;
        char[] items = new char[N];
        for (int i = 0; i < N; i++) items[i] = (char) ('A' + (i % 26));
        Random rnd = new Random(42);

        StdOut.println("Average probes for search miss: ");
        testScenario("a. All same index", items, M, k -> 0, rnd);
        testScenario("b. All distinct indices", items, M, k -> k, rnd);
        testScenario("c. All even index", items, M, k -> 2, rnd); 
        testScenario("d. Distinct even indices", items, M, k -> 2*k, rnd);
    }
}

// Average probes for search miss: 
// a. All same index:  avg_miss_probes = 500.12
// b. All distinct indices:  avg_miss_probes = 501.24
// c. All even index:  avg_miss_probes = 501.44
// d. Distinct even indices:  avg_miss_probes = 1.50