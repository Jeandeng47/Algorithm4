import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class P_3_3_18 {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public static class RBN {
        private boolean color;
        private RBN left, right;
        public RBN(boolean color) { this.color = color;}

        // Convert a 2-3 shape (unordered) to a canonical LLRB shape (left-leaning)
        public static RBN from23(T23 t) {
            // base case
            if (t.h == 0) {
                if (t.isThree()) { // 3-node -> black + left red node
                    RBN black = new RBN(BLACK);
                    RBN redL = new RBN(RED);
                    black.left = redL;
                    return black;
                } else { // 2-node -> single black node
                    RBN black = new RBN(BLACK);
                    return black;
                }
            }

            if (!t.isThree()) {
                // 2-node: BLACK with two children (both height h-1)
                RBN black = new RBN(BLACK);
                black.left  = from23(t.kids().get(0));
                black.right = from23(t.kids().get(1));
                return black;

            } else {
                // 3-node: BLACK parent + RED left child; map 3 children (L, M, R)
                RBN black = new RBN(BLACK);
                RBN redL = new RBN(RED);
                black.left = redL; // connect !!!
                    
                // children already sorted
                T23 L = t.kids().get(0);
                T23 M = t.kids().get(1);
                T23 R = t.kids().get(2);
                redL.left = from23(L);
                redL.right = from23(M);
                black.right = from23(R);
                return black;
            }
        }

        public static void print(RBN r) { print(r, "", false); }
        public static void print(RBN x, String prefix, boolean isLeft) {
            if (x == null) return;
            print(x.right, prefix + (isLeft ? "│   " : "    "), false);
            System.out.println(prefix + (isLeft ? "└── " : "┌── ") + (x.color ? "[R]" : "[B]"));
            print(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    // One to one reletionship btw RBT and 2-3T
    public static class T23 {
        private boolean isThree; // whether the ndoe is 3-node
        private String sig;
        private List<T23> kids;
        private int h; // height
        private int sz; // size, #keys
        
        public T23(boolean isThree, List<T23> kids, String sig, int h, int sz) {
            this.isThree = isThree;
            this.kids = kids;
            this.sig = sig;
            this.h = h;
            this.sz = sz; 
        }

        public int h() { return h; };
        public List<T23> kids() { return kids; } 
        public boolean isThree() { return isThree; }

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

    // Construct 2-3 tree of different structures given N keys
    // 1. Generate all differents shapes of height h
    // (1) base case: h = 0
    //  -  leaf2, leaf3
    // (2) recursive case: h >= 1
    //  - seperate N nodes into root, left subtree & right subtree
    //  - root as node2
    //      -- select shape A from shapes(n1, h-1)
    //      -- select shape B from shapes(n2, h-1)
    //      -- construct a node2 with A and B (when construct, sort (A,B))
    //      -- store at map: name -> N2(Ni, Nj) 
    //  - root as node3
    //     -- select shape A from shapes(n1, h-1)
    //      -- select shape B from shapes(n2, h-1)
    //      -- select shape B from shapes(n3, h-1)
    //      -- construct a node3 with A and B and C (when construct, sort (A,B,C))
    //      -- store at map: name -> N3(Ni, Nj, Nk) 
    // 2. Memoize h -> list of shapes (de-duplicate)
    // 3. Filter by N with minKeys(h) <= N <= maxKeys(h)
    // 4. Print the shapes and check

    // height -> list of unique shapes (before filterde by N)
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

    public static List<T23> shapesOfN(int N) {
        List<T23> result = new ArrayList<>();
        // minKeys(h) <= N <= maxKeys(h) 
        for (int h = 0; minKeys(h) <= N; h++) {
            if (N > maxKeys(h)) continue;
            for (T23 t : shapesOfH(h)) {
                if (t.sz == N) result.add(t);
            }
        }
        result.sort(Comparator.comparingInt((T23 t) -> t.h).thenComparing(t -> t.sig));
        return result;
    }

    // all 2 nodes
    private static int minKeys(int h) {
        if (h == 0) return 1;
        return 1 + 2 * minKeys(h - 1);
    }

    // all 3 nodes
    private static int maxKeys(int h) {
        if (h == 0) return 2;
        return 2 + 3 * maxKeys(h - 1);
    }


    public static void main(String[] args) {
        int[] Ns = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int N : Ns) {
            List<T23> shapes = shapesOfN(N);
            StdOut.println("======================================");
            StdOut.println("N = " + N + "  unique 2-3 shapes = " + shapes.size());

            int idx = 1;
            for (T23 t : shapes) {

                System.out.println("\n-- 23T Shape #" + (idx) + " --");
                printT23(t);
                StdOut.println("\n-- RBT shape #" + (idx) + " --");
                RBN rb = RBN.from23(t);   // convert
                RBN.print(rb);                // draw red-black BST
                idx++;
            }
        }

    }
}

// ======================================
// N = 1  unique 2-3 shapes = 1

// -- 23T Shape #1 --
// [•]  (keys=1)

// -- RBT shape #1 --
// ┌── [B]
// ======================================
// N = 2  unique 2-3 shapes = 1

// -- 23T Shape #1 --
// [••]  (keys=2)

// -- RBT shape #1 --
// ┌── [B]
// │   └── [R]
// ======================================
// N = 3  unique 2-3 shapes = 1

// -- 23T Shape #1 --
//     ┌── [•]  (keys=1)
// [•]  (keys=1)
//     └── [•]  (keys=1)

// -- RBT shape #1 --
//     ┌── [B]
// ┌── [B]
// │   └── [B]
// ======================================
// N = 4  unique 2-3 shapes = 1

// -- 23T Shape #1 --
//     ┌── [••]  (keys=2)
// [•]  (keys=1)
//     └── [•]  (keys=1)

// -- RBT shape #1 --
//     ┌── [B]
//     │   └── [R]
// ┌── [B]
// │   └── [B]
// ======================================
// N = 5  unique 2-3 shapes = 2

// -- 23T Shape #1 --
//     ┌── [••]  (keys=2)
// [•]  (keys=1)
//     └── [••]  (keys=2)

// -- RBT shape #1 --
//     ┌── [B]
//     │   └── [R]
// ┌── [B]
// │   └── [B]
// │       └── [R]

// -- 23T Shape #2 --
//     ┌── [•]  (keys=1)
// [••]  (keys=2)
//     ├── [•]  (keys=1)
//     └── [•]  (keys=1)

// -- RBT shape #2 --
//     ┌── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [R]
// │       └── [B]
// ======================================
// N = 6  unique 2-3 shapes = 1

// -- 23T Shape #1 --
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [•]  (keys=1)
//     └── [•]  (keys=1)

// -- RBT shape #1 --
//     ┌── [B]
//     │   └── [R]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [R]
// │       └── [B]
// ======================================
// N = 7  unique 2-3 shapes = 2

// -- 23T Shape #1 --
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [••]  (keys=2)
//     └── [•]  (keys=1)

// -- RBT shape #1 --
//     ┌── [B]
//     │   └── [R]
// ┌── [B]
// │   │   ┌── [B]
// │   │   │   └── [R]
// │   └── [R]
// │       └── [B]

// -- 23T Shape #2 --
//     │   ┌── [•]  (keys=1)
//     ┌── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #2 --
//         ┌── [B]
//     ┌── [B]
//     │   └── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [B]
// │       └── [B]
// ======================================
// N = 8  unique 2-3 shapes = 2

// -- 23T Shape #1 --
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [••]  (keys=2)
//     └── [••]  (keys=2)

// -- RBT shape #1 --
//     ┌── [B]
//     │   └── [R]
// ┌── [B]
// │   │   ┌── [B]
// │   │   │   └── [R]
// │   └── [R]
// │       └── [B]
// │           └── [R]

// -- 23T Shape #2 --
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #2 --
//         ┌── [B]
//         │   └── [R]
//     ┌── [B]
//     │   └── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [B]
// │       └── [B]
// ======================================
// N = 9  unique 2-3 shapes = 3

// -- 23T Shape #1 --
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [••]  (keys=2)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #1 --
//         ┌── [B]
//         │   └── [R]
//     ┌── [B]
//     │   └── [B]
//     │       └── [R]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [B]
// │       └── [B]

// -- 23T Shape #2 --
//     │   ┌── [•]  (keys=1)
//     ┌── [••]  (keys=2)
//     │   ├── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #2 --
//         ┌── [B]
//     ┌── [B]
//     │   │   ┌── [B]
//     │   └── [R]
//     │       └── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [B]
// │       └── [B]

// -- 23T Shape #3 --
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [••]  (keys=2)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #3 --
//         ┌── [B]
//         │   └── [R]
//     ┌── [B]
//     │   └── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   │   │   └── [R]
// │   └── [B]
// │       └── [B]
// ======================================
// N = 10  unique 2-3 shapes = 3

// -- 23T Shape #1 --
//     │   ┌── [••]  (keys=2)
//     ┌── [••]  (keys=2)
//     │   ├── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #1 --
//         ┌── [B]
//         │   └── [R]
//     ┌── [B]
//     │   │   ┌── [B]
//     │   └── [R]
//     │       └── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   └── [B]
// │       └── [B]

// -- 23T Shape #2 --
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [••]  (keys=2)
// [•]  (keys=1)
//         ┌── [••]  (keys=2)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #2 --
//         ┌── [B]
//         │   └── [R]
//     ┌── [B]
//     │   └── [B]
//     │       └── [R]
// ┌── [B]
// │   │   ┌── [B]
// │   │   │   └── [R]
// │   └── [B]
// │       └── [B]

// -- 23T Shape #3 --
//     │   ┌── [•]  (keys=1)
//     ┌── [••]  (keys=2)
//     │   ├── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [••]  (keys=2)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// -- RBT shape #3 --
//         ┌── [B]
//     ┌── [B]
//     │   │   ┌── [B]
//     │   └── [R]
//     │       └── [B]
// ┌── [B]
// │   │   ┌── [B]
// │   │   │   └── [R]
// │   └── [B]
// │       └── [B]