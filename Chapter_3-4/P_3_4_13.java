import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_13 {
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

        public int searchProbes(char c, int idx) {
            int i = ((idx % M) + M) % M;
            int probes = 0;
            while (keys[i] != null) {
                probes++;
                if (keys[i] == c) return probes;
                i = (i + 1) % M;
            }
            return probes;
        }

    }

    // Define interface for “forced home index” mapping in each scenario.
    public interface Indexer {
        int idx(int k);
    }

    private static void testScenario(String label, char[] items, int M, Indexer f) {
        LPT ht = new LPT(M);

        // Avoid hash table being full, otherwise while(keys[i] != null)
        // will give us an infinite loop -> add at most M - 1 items
        int limit = Math.min(items.length, M - 1);
        for (int i = 0; i < limit; i++) {
            ht.put(items[i], f.idx(i));
        }

        // pick random keys and measure average probes
        Random rand = new Random();
        int trials = 1000, probes = 0;
        for (int t = 0; t < trials; t++) {
            int k = rand.nextInt(limit);
            probes += ht.searchProbes(items[k], f.idx(k));
        }
        StdOut.printf("%s: Avg_probes = %.2f%n", label, (double)probes/trials);
    }

    public static void main(String[] args) {
        int M = 4000;
        int N = 2000;
        char[] items = new char[N];
        for (int i = 0; i < N; i++) items[i] = (char)('A' + i);

        StdOut.println("Average probes for search hit: ");
        testScenario("a. All same index", items, M, k -> 0);
        testScenario("b. All distinct indices", items, M, k -> k);
        testScenario("c. All even index", items, M, k -> 2); // force collisions on an even
        testScenario("d. Distinct even indices", items, M, k -> 2*k);
        
    }
}

// Average probes for search hit: 
// a. All same index: Avg_probes = 1004.03
// b. All distinct indices: Avg_probes = 1.00
// c. All even index: Avg_probes = 1001.61
// d. Distinct even indices: Avg_probes = 1.00