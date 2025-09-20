import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_23 {
    // Polynomial rolling hash:
    // h(s) = Sum{i=0..k-1}[s(i) * R^(k-1-i)] % M
    public static int hash(String s, int R, int M) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            // use byte value 0..255
            h = (R * h + (s.charAt(i) & 0xff)) % M;
        }
        return h;
    }
    public static String shuffle(String s, Random rnd) {
        char[] a = s.toCharArray();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            char t = a[i]; a[i] = a[j]; a[j] = t;
        }
        return new String(a);
    }

    public static void main(String[] args) {
        int R = 256;
        int M_bad = 255;
        int M_good = 257; // better prime

        String base = "helloworld";
        Random rnd = new Random(42);

        StdOut.println("Base string: " + base);
        int badHashBase  = hash(base, R, M_bad);
        int goodHashBase = hash(base, R, M_good);
        StdOut.printf("perm %-10s  M=255: %3d   M=257: %3d%n", base, badHashBase, goodHashBase);


        // generate permutations and compare
        for (int t = 1; t <= 8; t++) {
            String perm = shuffle(base, rnd);
            int badHash  = hash(perm, R, M_bad);
            int goodHash = hash(perm, R, M_good);
            StdOut.printf("perm %-10s  M=255: %3d   M=257: %3d%n", perm, badHash, goodHash);
        }
        
    }
}

// With R = 256, M = 255, the polynomial hash
// h(s) = Sum{i=0..k-1}[s(i) * R^(k-1-i)] % M
// collapes to h(s) = Sum{i=0..k-1}S[i] % 255 (since 
// 256 % 255 = 1)


// Base string: helloworld
// perm helloworld  M=255:  64   M=257:   0
// perm oolerdlwlh  M=255:  64   M=257: 243
// perm rowleohdll  M=255:  64   M=257: 249
// perm oohwdlerll  M=255:  64   M=257:  36
// perm lolrohwdel  M=255:  64   M=257: 247
// perm lledhwoorl  M=255:  64   M=257:   8
// perm wllhlerodo  M=255:  64   M=257: 243
// perm oolewldlrh  M=255:  64   M=257: 237
// perm lrlwhdoole  M=255:  64   M=257:   6

// Observation:
// - All permutations have the SAME hash when M=255 (order ignored).
// - With M=257, hashes typically differ across permutations.