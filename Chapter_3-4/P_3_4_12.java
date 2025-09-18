import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_12 {

    private static final int M = 7; // init size of table
    private static final char[] KEYS = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    private static final int[] Hidx = {2, 0, 0, 4, 4, 4, 2}; // the given hash value

    private static final String[] NAMES = {"a","b","c","d","e","f"};
    private static final char[][] CANDS = {
        {'E','F','G','A','C','B','D'},  // a
        {'C','E','B','G','F','D','A'},  // b
        {'B','D','F','A','C','E','G'},  // c
        {'C','G','B','A','D','E','F'},  // d
        {'F','G','B','D','A','C','E'},  // e
        {'G','E','C','A','D','B','F'}   // f
    };

    private static void permutate(char[] k, Map<String, char[]> seen, int d) {
        if (d == k.length) {
            // k is one complete insertion order
            // simulate linear probing to get a table
            char[] table = simLinearProb(k);
            for (int i = 0; i < CANDS.length; i++) {
                if (seen.containsKey(NAMES[i])) continue; // already has valid order
                if (Arrays.equals(CANDS[i], table)) {
                    seen.put(NAMES[i], k.clone()); // store the order
                }
            }
            return; // stop for this one permutation
        }

        for (int i = d; i < k.length; i++) {
            swap(k, i, d);
            permutate(k, seen, d + 1);
            swap(k, i, d);

            if (seen.size() == CANDS.length) return; // all candidates check
        }
    }

    // linear probing insert simulation (no deletes, no resizing)
    private static char[] simLinearProb(char[] order) {
        char[] t = new char[M];
        for (char key : order) {
            int i = Hidx[key - 'A'];
            while (t[i] != 0) { i = (i + 1) % M; }
            t[i] = key;
        }
        return t;
    }

    private static void swap(char[] a, int i, int j) { 
        char tmp = a[i]; 
        a[i] = a[j]; 
        a[j] = tmp; 
    }

    private static boolean isPossible(char[] table) {
         // check slots 0,1 must be B or C
        if (!(table[0] == 'B' || table[0] == 'C')) return false;
        if (!(table[1] == 'B' || table[1] == 'C')) return false;

        // check slots 2,3 must be A or G
        if (!(table[2] == 'A' || table[2] == 'G')) return false;
        if (!(table[3] == 'A' || table[3] == 'G')) return false;

        // check slots 4–6 must be D/E/F
        for (int i = 4; i <= 6; i++) {
            if (!(table[i] == 'D' || table[i] == 'E' || table[i] == 'F')) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        // Find the first insertion order that gives us the candidate
        Map<String, char[]> seen = new HashMap<>();
        permutate(KEYS.clone(), seen, 0);

        // report
        for (int i = 0; i < CANDS.length; i++) {
            String name = NAMES[i];
            char[] r = seen.get(name);
            StdOut.print(name + ": ");
            if (r == null) {
                StdOut.println("IMPOSSIBLE");
            } else {
                StdOut.println("POSSIBLE, e.g. " + Arrays.toString(r));
            }
        }
        StdOut.println();

        // report
        for (int i = 0; i < CANDS.length; i++) {
            String name = NAMES[i];
            char[] r = seen.get(name);
            StdOut.print(name + ": ");
            if (!isPossible(CANDS[i])) {
                StdOut.println("IMPOSSIBLE");
            } else {
                StdOut.println("POSSIBLE, e.g. " + Arrays.toString(CANDS[i]));
            }
        }

    }
}


// For our hash mapping:
// A,G -> home = 2
// B,C -> home = 0
// D,E,F -> home = 4

// Idea: In linear probing, each key always ends up in the first empty slot
// starting from its home bucket (its hash index).
// Implication:
// 1. All keys share the same hash index form contiguous cluster in the table
// 2. The leftmost cell of teh cluster must be occupied by one of the keys
// whose home is that position

// Given the hash index of the table, we could infer 
// the final hash table must be:
// slots 0–1 : {B, C} in some order
// slots 2–3 : {A, G} in some order
// slots 4–6 : {D, E, F} in some order


// a: IMPOSSIBLE
// b: IMPOSSIBLE
// c: IMPOSSIBLE
// d: IMPOSSIBLE
// e: IMPOSSIBLE
// f: IMPOSSIBLE