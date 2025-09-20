import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;
import java.util.Map;

public class P_3_4_17 {
    public static class LinearProbingHT {
        private final int m;            // FIXED size (no resizing)
        private int n;                  // number of k-v pairs
        private Character[] keys;
        private Integer[] vals;

        // mapping to match the figure (S→6, E→10, A→4, R→14, C→5, H→4, X→15, M→1, P→14, L→6)
        private static final Map<Character,Integer> HASH = new HashMap<>();
        static {
            HASH.put('S', 6); HASH.put('E',10); HASH.put('A', 4);
            HASH.put('R',14); HASH.put('C', 5); HASH.put('H', 4);
            HASH.put('X',15); HASH.put('M', 1); HASH.put('P',14);
            HASH.put('L', 6);
        }

        public LinearProbingHT(int cap) {
            this.m = cap;
            this.keys = new Character[m];
            this.vals = new Integer[m];
        }

        private int hash(Character key) {
            Integer h = HASH.get(key);
            if (h == null) {                 // fallback if some other letter appears
                h = (key.hashCode() & 0x7fffffff) % m;
            }
            return h % m;                     // table is size 16
        }

        public Integer get(Character key) {
            for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
                if (keys[i].equals(key)) return vals[i];
            }
            return null;
        }

        public void put(Character key, Integer val) {
            // NO RESIZE — to match the figure’s fixed table
            int i;
            for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
                if (keys[i].equals(key)) { vals[i] = val; return; } // update
            }
            keys[i] = key; vals[i] = val; n++;
        }

        public boolean contains(Character key) {
            return get(key) != null;
        }

        public void delete(Character key) {
            if (!contains(key)) return;
            
            // find & delete the key
            int i = hash(key);
            while (!key.equals(keys[i])) {
                i = (i + 1) % m;
            }
            keys[i] = null; vals[i] = null; n--;

            // rehash
            i = (i + 1) % m;
            while (keys[i] != null) {
                Character k2 = keys[i];
                Integer v2 = vals[i];
                keys[i] = null; vals[i] = null; n--;
                put(k2, v2);
                i = (i + 1) % m;
            }
        }

        public void printTable() {
            StdOut.printf("LinearProbingHT m=%d, n=%d, load=%.3f (no resizing)\n", m, n, (double)n/m);
            for (int i = 0; i < m; i++) {
                StdOut.printf("%3d: ", i);
                if (keys[i] == null) StdOut.println("—");
                else StdOut.println(keys[i] + " -> " + vals[i]);
            }
        }
    }

    public static void main(String[] args) {
        char[] seq = {'S','E','A','R','C','H','E','X','A','M','P','L','E'};
        LinearProbingHT ht = new LinearProbingHT(16);
        for (int i = 0; i < seq.length; i++) ht.put(seq[i], i);

        StdOut.println("Before deleteing C: ");
        ht.printTable();
        StdOut.println();

        StdOut.println("After deleteing C: ");
        ht.delete('C');
        ht.printTable();
    }
}


// Before deleteing C: 
// LinearProbingHT m=16, n=10, load=0.625 (no resizing)
//   0: P -> 10
//   1: M -> 9
//   2: —
//   3: —
//   4: A -> 8
//   5: C -> 4
//   6: S -> 0
//   7: H -> 5
//   8: L -> 11
//   9: —
//  10: E -> 12
//  11: —
//  12: —
//  13: —
//  14: R -> 3
//  15: X -> 7

// After deleteing C: 
// LinearProbingHT m=16, n=9, load=0.563 (no resizing)
//   0: P -> 10
//   1: M -> 9
//   2: —
//   3: —
//   4: A -> 8
//   5: H -> 5
//   6: S -> 0
//   7: L -> 11
//   8: —
//   9: —
//  10: E -> 12
//  11: —
//  12: —
//  13: —
//  14: R -> 3
//  15: X -> 7

// The letters after 'C' && in same cluster are moved forward
// otherwise, we will never find the letters ['H', 'S', 'L']
// since we will stop at ht[5] = null (where C was)