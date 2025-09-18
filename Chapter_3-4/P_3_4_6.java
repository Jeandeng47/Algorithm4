import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_6 {
    private static int h(int k, int a, int b, int M) {
        long t = ((long) a * k + b) % M;
        if (t < 0) t += M;  
        return (int) t;
    }
    public static void main(String[] agrs) {
        int t = 8;   // check t bits
        int M = 101; // pick an odd prime
        int a = 11;
        int b = 0;
        Random rnd = new Random();

        boolean allDiff = true;
        for (int i = 0; i < t; i++) {
            int mask = 1 << i;
            int x1 = (rnd.nextInt(100)) << (i + 1); // ensure the lower (i+1) bits are 0s
            int x2 = x1 ^ mask; // turn the ith bit from 0 to 1
            int hx = h(x1, a, b, M);
            int hx2 = h(x2, a, b, M);

            // If change of key bits make no difference, their
            // hash code should be the same
            boolean diff = (hx != hx2);

            StdOut.printf("bit %2d: x1=%d, x2=%d | h1=%d vs h2=%d -> %s%n",
                    i, x1, x2, hx, hx2, diff ? "OK" : "FAIL");

            allDiff &= diff;
        }
        StdOut.println("All bits sensitive? " + allDiff);

    }
}

// Prove: for a modular hash function h(k) = (k * a + b) % m
// each bit of the key has the property that there exists 2 keys
// differing only in that bit has different hash values.

// bit  0: x1=44, x2=45 | h1=80 vs h2=91 -> OK
// bit  1: x1=340, x2=342 | h1=3 vs h2=25 -> OK
// bit  2: x1=376, x2=380 | h1=96 vs h2=39 -> OK
// bit  3: x1=768, x2=776 | h1=65 vs h2=52 -> OK
// bit  4: x1=1312, x2=1328 | h1=90 vs h2=64 -> OK
// bit  5: x1=2560, x2=2592 | h1=82 vs h2=30 -> OK
// bit  6: x1=12032, x2=12096 | h1=42 vs h2=39 -> OK
// bit  7: x1=20224, x2=20352 | h1=62 vs h2=56 -> OK
// All bits sensitive? true

// Implication:
// 1. With a modular hash function using prime modulus
// every key bit influences the hash value
// 2. All bits of the key are used -> avoid the case when M = 2^i
// which ignores high-order bits)