import java.util.Random;

import edu.princeton.cs.algs4.StdOut;
public class P_3_4_7 {
    private static int h(int k, int a, int m) {
        long t = ((long)a * k) % m;
        if (t < 0) t += m;
        return (int)t;
    }

    // --- Generate different keys pattern --- 

    // keys = 1,2,3,...,n
    private static int[] keysSeq(int n) {
        int[] ks = new int[n];
        for (int i = 0; i < n; i++) ks[i] = i;
        return ks;
    }

    // keys = 0,1*s,2*s,3*s,...
    private static int[] keysStep(int n, int step) {
        int[] ks = new int[n];
        for (int i = 0; i < n; i++) ks[i] = i * step;
        return ks;
    }

    private static int[] keysRandom(int n, int seed) {
        Random r = new Random(seed);
        int[] ks = new int[n];
        for (int i = 0; i < n; i++) ks[i] = r.nextInt();
        return ks;
    }

    //  --- Show distribution of keys  --- 

    // Compute the histogram given keys and hash function
    public static Stats histogram(int[] keys, int a, int M) {
        // hash keys into buckets
        int[] cnts = new int[M];
        for (int k : keys) { cnts[h(k, a, M)]++; }

        // compute statistic
        int max = 0, empty = 0, sum = 0;
        for (int c : cnts) {
            max = Math.max(max, c);
            if (c == 0) empty++;
            sum += c;
        }
        double expect = (double) sum / M; // if distribute uniformly
        double chi2 = 0.0;
        for (int c : cnts) {
            double d = c - expect;
            chi2 += (d * d) / expect;
        }
        return new Stats(M, sum, max, empty, chi2, cnts);
    }

    public static class Stats {
        int M; // #bucktes
        int N; // #keys
        int maxBuck; // max #keys in a bucket
        int emptyBuck; // #empty buckets
        int[] counts; // buckets array
        double chi2; // X^2 distribution
        
        Stats(int M, int N, int maxBuck, int emptyBuck, double chi2, int[] counts) {
            this.M = M; this.N = N; this.maxBuck = maxBuck;
            this.emptyBuck = emptyBuck; this.chi2 = chi2; this.counts = counts;
        }
        public String toString() {
            double avg = (double)N / M;
            return String.format("%-5d %-5d %-5.2f %-5d %-5d %-5.1f",
                    M, N, avg, maxBuck, emptyBuck, chi2);
        }

    }

    // --- Run experiments different patterns of keys, a, M ---
    private static void run(String label, int[] keys, int a, int[] Ms) {
        StdOut.println("--- " + label + " ---");
        StdOut.printf("%-5s %-5s %-5s %-5s %-5s %-5s%n", "M", "N", "Avg", "maxBs", "empBs", "chi2");
        for (int M : Ms) {
            Stats s = histogram(keys, a, M);
            StdOut.println(s);
        }
        StdOut.println();
    }

    //  --- Check bit sensitivity when M = 2^m ---

    // When M = 2^ m, flip over the higher-than-(m-1) bit should 
    // not chnage the hash result
    private static void bitTruc(int M, int a, int trials) {
        int m = Integer.numberOfTrailingZeros(M);
        StdOut.printf("--- Bit truncation demo (M=%d=2^%d, a=%d) ---%n", M, m, a);
        Random rnd = new Random(42);
        for (int t = 0; t < trials; t++) {
            int k = rnd.nextInt();
            int i = m + rnd.nextInt(31 - m);  // change a bit i >= m 
            int k2 = k ^ (1 << i);            // flip this bit
            int hk = h(k,  a, M);
            int hk2 = h(k2, a, M);
            boolean same = (hk == hk2);
            StdOut.printf("flip bit %2d: h(k)=%d, h(k')=%d -> %s%n",
                    i, hk, hk2, same ? "SAME (high bit ignored)" : "DIFF");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int N = 1000;
        int[] seq = keysSeq(N);
        int[] step2 = keysStep(N, 1 << 2); // the lower 2 bits always 0
        int[] rand = keysRandom(N, 42);

        int[] Ms = { 1009, 1000}; // prime: 1009; non-prime: 1000
        int[] As = { 5, 101};     // co-prime with Ms
        
        for (int a : As) {
            StdOut.printf("=== a = %d ===%n" , a);
            run("Sequential keys 0..N-1", seq, a, Ms);
            run("Stepped keys (low 2 bits zero)", step2, a, Ms);
            run("Random keys", rand, a, Ms);
        }

        // check M = 2^m, % operation only keep the lower m bits
        int TRIALS = 5;
        bitTruc(1024, 5, TRIALS);
    }
}

// Prove: for modular hashing h(K) = (a * k) % M, where
// a is an arbitrary fixed prime, check whether nonprime 
// M could give us uniform and stable hashing.

// Combo 1: M is prime, a is co-prime -> stable
// -> M = 1009, a = 3/5/11/101

// Combo 2: M is non-prime, a is co-prime -> unstable
// -> M = 1000, a = 101
// (when non-prime M is applied to keys sequence where keys are 
// increasing by step k, when gcd(M, k) != 1, the hash result is bad)

// Combo 3: M is non-prime, a is not co-prime -> very bad
// -> M = 1000, a = 5

// Combo 4: M is 2^m, a is co-prime -> higher bits ignorde
// -> M = 1024, a = 3/5/11/101

// Implication:
// 1. M should be prime
// 2. gcd(a, m) = 1