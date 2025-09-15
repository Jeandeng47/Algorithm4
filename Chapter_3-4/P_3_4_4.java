import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_4 {
    // Return the relative position of character c in the alphabet
    private static int kOf(char c) {
        return Character.toUpperCase(c) - 'A' + 1;
    }

    // Return 1 if a and M are co-prime to each other
    public static int gcd(int a, int b) {
        return (b == 0)? a : gcd(b, b % a);
    }

    // Check if (a * k) % M is injective for the given ks
    public static boolean isPerfect(int a, int m, int[] ks) {
        boolean[] seen = new boolean[m];
        for (int k : ks) {
            int h = (a * k) % m; // hash code
            if (seen[h]) return false;
            seen[h] = true;
        }
        return true;
    }

    public static void main(String[] args) {
        char[] keys = {'S','E','A','R','C','H','X','M','L'};
        int[] ks = Arrays.stream(new String(keys).chars().toArray())
                    .map(c -> kOf((char) c)).toArray();


        // For each M, find the a that give us (a, M)
        int bestM = -1, bestA = -1;
        boolean found = false;
        for (int M = keys.length; ; M++) {
            for (int a = 1; a < M - 1; a++) {
                if (gcd(a, M) != 1) continue; // a must be co-prime to M
                if (isPerfect(a, M, ks)) {
                    bestM = M; bestA = a;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        StdOut.println("Minimal M = " + bestM + ", example a = " + bestA);
        StdOut.println("Mapping (a * k % M):");
        for (char key : keys) {
            int k = kOf(key);
            int h = (bestA * k) % bestM;
            System.out.printf("%c(%d) -> %d%n", key, k, h);
        }

    }
}

// To find the perfect hash:
//  1. Map each letter to its alphabet index (A=1, B=2, ..., Z=26).
//     Example keys {S,E,A,R,C,H,X,M,P,L} → {19,5,1,18,3,8,24,13,16,12}.
//  2. We want a and M such that h(k) = (a * k) % M gives distinct values (no collisions).
//     => every bucket has just 1 entry
//  3. Since we have 10 keys, M must be at least 10. Start from the smallest possible M and go up.
//  4. For each candidate M, test values of a that are coprime with M (gcd(a, M) = 1).
//  5. For each (a, M), compute all hashes; if all are distinct, we found a perfect hash.

// Minimal M = 22, example a = 3
// Mapping (a * k % M):
// S(19) -> 13
// E(5) -> 15
// A(1) -> 3
// R(18) -> 10
// C(3) -> 9
// H(8) -> 2
// X(24) -> 6
// M(13) -> 17
// L(12) -> 14
