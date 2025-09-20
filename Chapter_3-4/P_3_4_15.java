import edu.princeton.cs.algs4.StdOut;

public class P_3_4_15 {

    public static class LPT<Key>{
        private Key[] keys;
        private int N;
        private int M;
        private int cmp;

        public LPT(int m) {
            this.keys = (Key[]) new Object[m];
            this.M = m;
        }

        private int hash(Key k) {
            return (0 & 0x7fffffff) % M;
        }

        public void put(Key k) {
            // keep utilization < 50%
            if (N >= M/2) resize(2 * M);

            int i = hash(k);
            while (keys[i] != null) {
                cmp++;
                i = (i + 1) % M;
            }
            keys[i] = k;
            N++; // insert distinct keys
        }

        private void resize(int cap) {
            LPT<Key> temp = new LPT<>(cap);
            for (int i = 0; i < M; i++) {
                if (keys[i] != null) {
                    temp.put(keys[i]); // also trigger cmps here
                }
            }
            keys = temp.keys;
            N = temp.N;
            M = temp.M;
        }

        public int cmp() {
            return this.cmp;
        }

        public int tableSz() {
            return this.M;
        }
        
    }

    public static void main(String[] args) {
        int[] Ns = {250, 500, 1000, 2000, 4000, 8000};
        StdOut.printf("%-8s %-10s %-14s %-12s%n",
                "N", "tableM", "totalCompares", "comp/N^2");
        for (int N : Ns) {
            LPT<Integer> t = new LPT<>(4); // start with small size
            for (int i = 0; i < N; i++) {
                t.put(i);
            }
            // relationship between #cmp / N^2
            double ratio = t.cmp / (double) ((long)N * (long)N);
            System.out.printf("%-8d %-10d %-14d %-12.2f%n",
                    N, t.tableSz(), t.cmp(), ratio);
        }
    }
}


// N        tableM     totalCompares  comp/N^2    
// 250      512        31125          0.50        
// 500      1024       124750         0.50        
// 1000     2048       499500         0.50        
// 2000     4096       1999000        0.50        
// 4000     8192       7998000        0.50        
// 8000     16384      31996000       0.50     


// Number of compares grows with N^2 under the worst case
// 1. For hash table, the worst case would be hashing 
// all the keys to the same home index, forming 1 huge cluster
// 2. The 1st key, 1 cmp; 2nd key, 2 cmps; 3rd key, 3 cmps; ...
// The total compares = 1 + 2 + 3 + N - 1 -> N^2
// 3. Since the hash table performs resize when 50% full, therefore
// the rehash also trigger N^2 compares