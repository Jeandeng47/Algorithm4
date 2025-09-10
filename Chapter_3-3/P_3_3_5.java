import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class P_3_3_5 {
    // Construct a 2-3 Tree Node that ignores the order of the children
    public static class T23 {
        private boolean isThree; // whether the ndoe is 3-node
        private String sig;
        private List<T23> kids;
        private int h; // height
        private int sz; // size, #keys
        
        public T23(boolean isThree, List<T23> kids, String sig, 
        int h, int sz) {
            this.isThree = isThree;
            this.kids = kids;
            this.sig = sig;
            this.h = h;
            this.sz = sz; 
        }

        public static T23 leaf2() {
            return new T23(false, List.of(), "L2", 0, 1);
        };
        
        public static T23 leaf3() {
            return new T23(true, List.of(), "L3", 0, 2);
        };

        // Construct a 2-node with 2 children
        public static T23 node2(T23 a, T23 b) {
            if (a.h != b.h) throw new IllegalArgumentException("Children must have the same height");
            List<T23> child = new ArrayList<>();
            child.add(a); child.add(b);
            child.sort(Comparator.comparing(t -> t.sig));
            String sig = "N2(" + child.get(0).sig + "," + child.get(1).sig + ")";
            int h = 1 + a.h;
            int sz = 1 + a.sz + b.sz;
            return new T23(false, child, sig, h, sz);
        }

        // Construct a 3-node with 3 children
        public static T23 node3(T23 a, T23 b, T23 c) {
            if (!((a.h == b.h) && (b.h == c.h))) 
            throw new IllegalArgumentException("Children must have the same height");
            List<T23> child = new ArrayList<>();
            child.add(a); child.add(b); child.add(c);
            child.sort(Comparator.comparing(t -> t.sig));
            String sig = "N3(" + child.get(0).sig + "," + child.get(1).sig 
            +  "," + child.get(2).sig + ")";
            int h = 1 + a.h;
            int sz = 2 + a.sz + b.sz + c.sz;
            return new T23(true, child, sig, h, sz);
        }
        
    }

    // Generate ALL unordered 2–3 shapes of exact height h (no N filtering yet).
    // Memoized by height.
    private static Map<Integer, List<T23>> memo = new HashMap<>();
    public static List<T23> shapesOfH(int h) {
        if (memo.containsKey(h)) return memo.get(h);

        // base case: h = 0, a 2-node/ 3-node
        List<T23> out = new ArrayList<>();
        if (h == 0) {
            out.add(T23.leaf2());
            out.add(T23.leaf3());
            memo.put(h, out);
            return out;
        }
        
        // recursive case: h >= 1
        List<T23> child = shapesOfH(h - 1);
        int m = child.size();

        // Dedup
        Map<String, T23> uniq = new LinkedHashMap<>();

        // Parents as 2-node: choose a multiset of 2 children from 'child' 
        // (only consider i <= j）
        // m = 2, child = {L2, L3}
        // (i, j): {(0, 0), (0, 1), (1, 1)}
        // p: {N2(L2, L2), N2(L2, L3), N2(L3, L3)}, size={3, 4, 5}
        for (int i = 0; i < m; i++) { 
            for (int j = i; j < m; j++) { 
                T23 p = T23.node2(child.get(i), child.get(j));
                uniq.put(p.sig, p); 
            }
        }
        // Parents as 3-node: choose a multiset of 3 children from 'child'
        // (only consider i <= j <= k）
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                for (int k = j; k < m; k++) {
                    T23 p = T23.node3(child.get(i), child.get(j), child.get(k));
                    uniq.put(p.sig, p);
                }
            }
        }

        out.addAll(uniq.values()); // all structure at this height
        memo.put(h, out);
        return out;   
    }

    // All shapes with exactly N keys (across all feasible heights)
    public static List<T23> shapesOfN(int N) {
        List<T23> result = new ArrayList<>();
        // minKeys(h) ≤ N ≤ maxKeys(h)
        for (int h = 0; minKeys(h) <= N; h++) {
            if (N > maxKeys(h)) continue; // skip
            for (T23 t : shapesOfH(h)) {
                if (t.sz == N) result.add(t);
            }
        }

        // (1) sort by height (2) sort by signature
        result.sort(Comparator.comparingInt((T23 t) -> t.h).thenComparing(t -> t.sig));
        return result;
    }

    // Helper to calculate #keys given height
    private static int minKeys(int h) { // all 2-nodes
        if (h == 0) return 1;
        return 1 + 2 * minKeys(h - 1);
    }

    private static int maxKeys(int h) { // all 3-nodes
        if (h == 0) return 2;
        return 2 + 3 * maxKeys(h - 1);
    }

    private static void printT23(T23 r) { printT23(r, "", 0, 1); }
    private static void printT23(T23 x, String prefix, int pos, int siblings) {
        if (x == null) return;

        String conn;
        if (prefix.isEmpty())            conn = "";
        else if (pos == 0)               conn = "┌── ";
        else if (pos < siblings - 1)     conn = "├── ";
        else                              conn = "└── ";

        String branch = (pos == siblings - 1 ? "    " : "│   ");
        String childPrefix = prefix + branch;

        // print children top→bottom
        List<T23> kids = x.kids;
        int m = kids.size();
        if (m > 0) printT23(kids.get(m - 1), childPrefix, 0, m);

        StdOut.println(prefix + conn + (x.isThree ? "[••]" : "[•]") + "  (keys=" + (x.isThree ? 2 : 1) + ")");
        for (int i = m - 2, idx = 1; i >= 0; i--, idx++) {
            printT23(kids.get(i), childPrefix, idx, m);
        }
    }


    // Show all the 2-3 trees of different structures given N distinct key
    public static void main(String[] args) {
        int[] Ns = {1, 2, 3, 4, 5, 6};
        for (int N : Ns) {
            List<T23> shapes = shapesOfN(N);
            StdOut.println("======================================");
            StdOut.println("N = " + N + ",  unique shapes = " + shapes.size());
            int idx = 1;
            for (T23 t : shapes) {
                System.out.println("\n-- Shape #" + (idx++) + "  (height=" + t.h + ") --");
                printT23(t);
            }
        }
    }
}

// ======================================
// N = 1,  unique shapes = 1

// -- Shape #1  (height=0) --
// [•]  (keys=1)
// ======================================
// N = 2,  unique shapes = 1

// -- Shape #1  (height=0) --
// [••]  (keys=2)
// ======================================
// N = 3,  unique shapes = 1

// -- Shape #1  (height=1) --
//     ┌── [•]  (keys=1)
// [•]  (keys=1)
//     └── [•]  (keys=1)
// ======================================
// N = 4,  unique shapes = 1

// -- Shape #1  (height=1) --
//     ┌── [••]  (keys=2)
// [•]  (keys=1)
//     └── [•]  (keys=1)
// ======================================
// N = 5,  unique shapes = 2

// -- Shape #1  (height=1) --
//     ┌── [••]  (keys=2)
// [•]  (keys=1)
//     └── [••]  (keys=2)

// -- Shape #2  (height=1) --
//     ┌── [•]  (keys=1)
// [••]  (keys=2)
//     ├── [•]  (keys=1)
//     └── [•]  (keys=1)
// ======================================
// N = 6,  unique shapes = 1

// -- Shape #1  (height=1) --
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [•]  (keys=1)
//     └── [•]  (keys=1)


// Idea:
// 1. Property of 2-3 tree:
//     - Two node types:
//         • 2-node: carries 1 key; if internal, has 2 children.
//         • 3-node: carries 2 keys; if internal, has 3 children.
//     - All leaves are at the same depth.

// 2. Same structure -> Same height:
//       minKeys(0) = 1                 // a 2-leaf
//       minKeys(h) = 1 + 2  minKeys(h-1)
//  
//       maxKeys(0) = 2                 // a 3-leaf
//       maxKeys(h) = 2 + 3  maxKeys(h-1)
//  
//     For a given N, any height h with  minKeys(h) ≤ N ≤ maxKeys(h) is feasible.
//     Examples:
//       h=0: [1,2], h=1: [3,8], h=2: [7,26]


// 3. Enumerating all feasible h given N -> (N, h)
// (1) Base case: h = 0
//       - N == 1 → { 2-leaf }    
//       - N == 2 → { 3-leaf }   
//       - otherwise → empty
//  
// (2) Recursive cases (h > 0):
//       Children must have height (h-1). We split N between the root and its children.
//  
//       A) Root is a 2-node (+1 key):
//          Let child keys be n1, n2 with n1 + n2 = N - 1, minKeys(h-1) ≤ n1 ≤ n2 ≤ maxKeys(h-1) (ignore order)
//          →  combine all A ∈ Shapes(n1, h-1) with B ∈ Shapes(n2, h-1) via make2(A, B).
//  
//       B) Root is a 3-node (+2 keys):
//          Let child keys be n1, n2, n3 with n1 + n2 + n3 = N - 2, minKeys(h-1) ≤ n1 ≤ n2 ≤ n3 ≤ maxKeys(h-1).
//          →  combine all A ∈ Shapes(n1, h-1), B ∈ Shapes(n2, h-1), C ∈ Shapes(n3, h-1) via make3(A, B, C).

// 4. Ignore subtree order
//    - Canonical signature: 
//      - 2-leaf: "2"; 3-leaf: "3"
//      - 2-node: "2(" + sort(sig(left), sig(right)) + ")"
//      - 3-node: "3(" + sort(sig(a), sig(b), sig(c)) + ")"
//    - When building a parent, sort children by sig before forming the parent’s sig.
//    - Deduplicate by storing results in a Map<sig, tree> (unordered children now collapse to one shape).
//  

//  5. Memoization:
//     - Large tree is built from small trees -> subproblems recur many time
//     - Cache Shapes(N, h) in a map keyed by N->h to avoid recomputation.
//  
// 6. Overall algorithm:
//     1) For a target N, iterate all heights h with minKeys(h) ≤ N ≤ maxKeys(h).
//     2) For each h, compute Shapes(N, h) by the recursion above (using memoization and dedup).
//     3) Union all Shapes(N, h), sort for deterministic output, and print each shape.
