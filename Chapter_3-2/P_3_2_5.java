import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;


public class P_3_2_5 {
    public static class OptBST {
        private String[] keys; // sorted keys
        private int[] w;  // frequencies
        private int n;

        // DP tables
        // cost[i][j]: minimal weighted path length for keys [i...j]
        private int[][] cost;
        // root[i][j]: index r choosen as root for [i...j]
        private int[][] root;
        // prefix sums of w to query [i...j]
        private int[] prefix;

        public OptBST(String[] sortedKeys, int[] freq) {
            this.keys = sortedKeys.clone();
            this.w = freq.clone();
            this.n = keys.length;
            build();
        }

        // The cost to quetry keys [i...j]
        private int sumW(int i, int j) {
            return prefix[j + 1] - prefix[i];
        }

        private void build() {
            this.cost = new int[n][n];
            this.root = new int[n][n];
            this.prefix = new int[n + 1];

            // len = 1
            for (int i = 0; i < n; i++) {
                prefix[i+1] = prefix[i] + w[i];
            }
            for (int i = 0; i < n; i++) {
                cost[i][i] = w[i]; // single key
                root[i][i] = i;
            }

            // len >= 2
            for (int len = 2; len <= n; len++) {
                StdOut.println("\n[DEBUG]-- Intervals of length " + len + " --");
                for (int i = 0; i + len - 1 < n; i++) {

                    int j = i + len - 1;
                    int best = Integer.MAX_VALUE;
                    int bestR = -1;
                    int add = sumW(i, j); // extra depth of shifting-down keys
                    StdOut.printf("[DEBUG]Interval [%d...%d] keys=%s...%s, sumW=%d%n",
                    i, j, keys[i], keys[j], add);

                    for (int r = i; r <= j; r++) {
                        // cost[i][r-1]: min-cost path from keys [i...r-1] (left subtree)
                        int left = (r > i)? cost[i][r-1] : 0;
                        // cost[r+1][j]: min-cost path from keys [r+1...j] (right subtree) 
                        int right = (r < j)? cost[r+1][j] : 0;
                        // DP formula: 
                        // cost[i][j] = MIN(r=i..j){cost[i][r - 1] + cost[r+1][j] + Sum(k=i...j)w[k]} 
                        int total = left + right + add;
                        StdOut.printf("[DEBUG]  try root r=%d (%s): left=%d, right=%d, total=%d%n",
                                r, keys[r], left, right, total);
                        if (total < best) {
                            best = total;
                            bestR = r;
                        }
                    }
                    cost[i][j] = best;
                    root[i][j] = bestR;    
                    StdOut.printf("[DEBUG]=> best for [%d..%d]: root=%d (%s), cost=%d%n",
                            i, j, bestR, keys[bestR], best);      
                }
            }
            StdOut.println("[DEBUG]=== DP Done ===\n");
        }
        
        // Print root and cost tables
        public void printTables() {
            StdOut.println("Cost table:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > j) StdOut.printf("%6s", ".");
                    else       StdOut.printf("%6d", cost[i][j]);
                }
                StdOut.println();
            }
            StdOut.println("\nRoot table (index):");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > j) StdOut.printf("%4s", ".");
                    else       StdOut.printf("%4d", root[i][j]);
                }
                StdOut.println();
            }
            StdOut.println();
        }

        public List<String> optPreorder() {
            List<String> out = new ArrayList<>();
            preorder(0, n - 1, out);
            return out;
        }

        private void preorder(int i, int j, List<String> out) {
            if (i > j) return;
            int r = root[i][j];
            out.add(keys[r]);
            preorder(i, r - 1, out);
            preorder(r + 1, j, out);
        }
    }

    private static class BST<K extends Comparable<K>> {
        private class Node<K> {
            K key;
            Node<K> L, R; 
            int size;
            public Node(K key) {
                this.key = key;
            }
        }
        private Node<K> root;
        public void insert(K key) {
            root = insert(root, key);
        }
        private Node<K> insert(Node<K> x, K key) {
            if (x == null) return new Node<>(key);
            int c = key.compareTo(x.key);
            if (c < 0) x.L = insert(x.L, key);
            else if (c > 0) x.R = insert(x.R, key);
            return x;
        }

        public int depth(K key) {
            return depth(root, key, 0);
        }

        public int depth(Node<K> x, K key, int d) {
            if (x == null) return -1;
            int c = key.compareTo(x.key);
            if (c == 0) return d;
            return (c < 0)? depth(x.L, key, d+1) : depth(x.R, key, d+1);
        }

        public int expCost(K[] keys, int[] freq) {
            int sum = 0;
            for (int i = 0; i < keys.length; i++) {
                int d = depth(keys[i]);
                sum += freq[i] * (d + 1);
            }
            return sum;
        }

        public void printBST() {
            if (root == null) { StdOut.println("(empty)"); return; }
            printBST(root.R, "", false);   
            StdOut.println(root.key);          
            printBST(root.L,  "", true);  
        }

        private void printBST(Node x, String prefix, boolean isLeft) {
            if (x == null) return;
            printBST(x.R, prefix + (isLeft ? "│   " : "    "), false);
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key);
            printBST(x.L,  prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    private static void run(String title, String[] keys, int[] freq) {
        StdOut.println(title);
        StdOut.println("Keys: " + Arrays.toString(keys));
        StdOut.println("Freq: " + Arrays.toString(freq));

        String[] sorted = keys.clone();
        Arrays.sort(sorted);

        // compute best bst structure
        OptBST obst = new OptBST(sorted, freq);
        obst.printTables();

        // arrange as preorder list
        List<String> preorder = obst.optPreorder();
        StdOut.println("Optimal BST preorder insertion order: " + preorder);
        StdOut.println("Optimal total cost: " + obst.cost[0][keys.length - 1]);

        // insert into bST
        BST<String> bst = new BST<>();
        for (String s : preorder) bst.insert(s);
        bst.printBST();
        int exp = bst.expCost(keys, freq);
        StdOut.println("Expected cost of BST: " + exp);
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] keys = {"A","B","C","D","E","F","G"};
        int[] freq = {3, 1, 9, 1, 5, 1, 4};
        run("Case 1 (freq intermixed)", keys, freq);

        // String[] keys2 = {"A","B","C","D","E","F","G"};
        // int[] freq2 = {1, 2, 3, 4, 5, 6, 7};
        // run("Case 2 (increasing freq)", keys2, freq2);

        // String[] keys3 = {"A","B","C","D","E","F","G"};
        // int[] freq3 = {7, 6, 5, 4, 3, 2, 1};
        // run("Case 3 (decreasing freq)", keys3, freq3);


        // String[] keys4 = {"A","B","C","D","E","F","G"};
        // int[] freq4 = {1, 1, 1, 1, 1, 1, 1};
        // run("Case 4 (all equal freq)", keys4, freq4);
    }
}


// Given we know the (1) frequency of each key got visited (2) the order
// of insertion decides the shape of BST
// Goal: construct the tree s.t. it has the lowest expected visit cost
// (1) Prepare keys[] and freq[]
// (2) Use DP to find the optimal BST
//  cost[i][j] = MIN(r=i..j){cost[i][r - 1] + cost[r+1][j] + Sum(k=i...j)w[k]}
// (3) Record for each segemnt [i...j], the root[i][j]
// (4) Use root table to recover pre-order sequence
// (5) Insert the keys into BST with the sequence
// (6) Compare the cost of three kinds of insertion
//  Sum_i(freq[i]) * (depth(key_i) + 1)


// Example:
// Case 1 (freq intermixed)
// Keys: [A, B, C, D, E, F, G]
// Freq: [3, 1, 9, 1, 5, 1, 4]

// [DEBUG]-- Intervals of length 2 --
// [DEBUG]Interval [0...1] keys=A...B, sumW=4
// [DEBUG]  try root r=0 (A): left=0, right=1, total=5
// [DEBUG]  try root r=1 (B): left=3, right=0, total=7
// [DEBUG]=> best for [0..1]: root=0 (A), cost=5
// [DEBUG]Interval [1...2] keys=B...C, sumW=10
// [DEBUG]  try root r=1 (B): left=0, right=9, total=19
// [DEBUG]  try root r=2 (C): left=1, right=0, total=11
// [DEBUG]=> best for [1..2]: root=2 (C), cost=11
// [DEBUG]Interval [2...3] keys=C...D, sumW=10
// [DEBUG]  try root r=2 (C): left=0, right=1, total=11
// [DEBUG]  try root r=3 (D): left=9, right=0, total=19
// [DEBUG]=> best for [2..3]: root=2 (C), cost=11
// [DEBUG]Interval [3...4] keys=D...E, sumW=6
// [DEBUG]  try root r=3 (D): left=0, right=5, total=11
// [DEBUG]  try root r=4 (E): left=1, right=0, total=7
// [DEBUG]=> best for [3..4]: root=4 (E), cost=7
// [DEBUG]Interval [4...5] keys=E...F, sumW=6
// [DEBUG]  try root r=4 (E): left=0, right=1, total=7
// [DEBUG]  try root r=5 (F): left=5, right=0, total=11
// [DEBUG]=> best for [4..5]: root=4 (E), cost=7
// [DEBUG]Interval [5...6] keys=F...G, sumW=5
// [DEBUG]  try root r=5 (F): left=0, right=4, total=9
// [DEBUG]  try root r=6 (G): left=1, right=0, total=6
// [DEBUG]=> best for [5..6]: root=6 (G), cost=6

// [DEBUG]-- Intervals of length 3 --
// [DEBUG]Interval [0...2] keys=A...C, sumW=13
// [DEBUG]  try root r=0 (A): left=0, right=11, total=24
// [DEBUG]  try root r=1 (B): left=3, right=9, total=25
// [DEBUG]  try root r=2 (C): left=5, right=0, total=18
// [DEBUG]=> best for [0..2]: root=2 (C), cost=18
// [DEBUG]Interval [1...3] keys=B...D, sumW=11
// [DEBUG]  try root r=1 (B): left=0, right=11, total=22
// [DEBUG]  try root r=2 (C): left=1, right=1, total=13
// [DEBUG]  try root r=3 (D): left=11, right=0, total=22
// [DEBUG]=> best for [1..3]: root=2 (C), cost=13
// [DEBUG]Interval [2...4] keys=C...E, sumW=15
// [DEBUG]  try root r=2 (C): left=0, right=7, total=22
// [DEBUG]  try root r=3 (D): left=9, right=5, total=29
// [DEBUG]  try root r=4 (E): left=11, right=0, total=26
// [DEBUG]=> best for [2..4]: root=2 (C), cost=22
// [DEBUG]Interval [3...5] keys=D...F, sumW=7
// [DEBUG]  try root r=3 (D): left=0, right=7, total=14
// [DEBUG]  try root r=4 (E): left=1, right=1, total=9
// [DEBUG]  try root r=5 (F): left=7, right=0, total=14
// [DEBUG]=> best for [3..5]: root=4 (E), cost=9
// [DEBUG]Interval [4...6] keys=E...G, sumW=10
// [DEBUG]  try root r=4 (E): left=0, right=6, total=16
// [DEBUG]  try root r=5 (F): left=5, right=4, total=19
// [DEBUG]  try root r=6 (G): left=7, right=0, total=17
// [DEBUG]=> best for [4..6]: root=4 (E), cost=16

// [DEBUG]-- Intervals of length 4 --
// [DEBUG]Interval [0...3] keys=A...D, sumW=14
// [DEBUG]  try root r=0 (A): left=0, right=13, total=27
// [DEBUG]  try root r=1 (B): left=3, right=11, total=28
// [DEBUG]  try root r=2 (C): left=5, right=1, total=20
// [DEBUG]  try root r=3 (D): left=18, right=0, total=32
// [DEBUG]=> best for [0..3]: root=2 (C), cost=20
// [DEBUG]Interval [1...4] keys=B...E, sumW=16
// [DEBUG]  try root r=1 (B): left=0, right=22, total=38
// [DEBUG]  try root r=2 (C): left=1, right=7, total=24
// [DEBUG]  try root r=3 (D): left=11, right=5, total=32
// [DEBUG]  try root r=4 (E): left=13, right=0, total=29
// [DEBUG]=> best for [1..4]: root=2 (C), cost=24
// [DEBUG]Interval [2...5] keys=C...F, sumW=16
// [DEBUG]  try root r=2 (C): left=0, right=9, total=25
// [DEBUG]  try root r=3 (D): left=9, right=7, total=32
// [DEBUG]  try root r=4 (E): left=11, right=1, total=28
// [DEBUG]  try root r=5 (F): left=22, right=0, total=38
// [DEBUG]=> best for [2..5]: root=2 (C), cost=25
// [DEBUG]Interval [3...6] keys=D...G, sumW=11
// [DEBUG]  try root r=3 (D): left=0, right=16, total=27
// [DEBUG]  try root r=4 (E): left=1, right=6, total=18
// [DEBUG]  try root r=5 (F): left=7, right=4, total=22
// [DEBUG]  try root r=6 (G): left=9, right=0, total=20
// [DEBUG]=> best for [3..6]: root=4 (E), cost=18

// [DEBUG]-- Intervals of length 5 --
// [DEBUG]Interval [0...4] keys=A...E, sumW=19
// [DEBUG]  try root r=0 (A): left=0, right=24, total=43
// [DEBUG]  try root r=1 (B): left=3, right=22, total=44
// [DEBUG]  try root r=2 (C): left=5, right=7, total=31
// [DEBUG]  try root r=3 (D): left=18, right=5, total=42
// [DEBUG]  try root r=4 (E): left=20, right=0, total=39
// [DEBUG]=> best for [0..4]: root=2 (C), cost=31
// [DEBUG]Interval [1...5] keys=B...F, sumW=17
// [DEBUG]  try root r=1 (B): left=0, right=25, total=42
// [DEBUG]  try root r=2 (C): left=1, right=9, total=27
// [DEBUG]  try root r=3 (D): left=11, right=7, total=35
// [DEBUG]  try root r=4 (E): left=13, right=1, total=31
// [DEBUG]  try root r=5 (F): left=24, right=0, total=41
// [DEBUG]=> best for [1..5]: root=2 (C), cost=27
// [DEBUG]Interval [2...6] keys=C...G, sumW=20
// [DEBUG]  try root r=2 (C): left=0, right=18, total=38
// [DEBUG]  try root r=3 (D): left=9, right=16, total=45
// [DEBUG]  try root r=4 (E): left=11, right=6, total=37
// [DEBUG]  try root r=5 (F): left=22, right=4, total=46
// [DEBUG]  try root r=6 (G): left=25, right=0, total=45
// [DEBUG]=> best for [2..6]: root=4 (E), cost=37

// [DEBUG]-- Intervals of length 6 --
// [DEBUG]Interval [0...5] keys=A...F, sumW=20
// [DEBUG]  try root r=0 (A): left=0, right=27, total=47
// [DEBUG]  try root r=1 (B): left=3, right=25, total=48
// [DEBUG]  try root r=2 (C): left=5, right=9, total=34
// [DEBUG]  try root r=3 (D): left=18, right=7, total=45
// [DEBUG]  try root r=4 (E): left=20, right=1, total=41
// [DEBUG]  try root r=5 (F): left=31, right=0, total=51
// [DEBUG]=> best for [0..5]: root=2 (C), cost=34
// [DEBUG]Interval [1...6] keys=B...G, sumW=21
// [DEBUG]  try root r=1 (B): left=0, right=37, total=58
// [DEBUG]  try root r=2 (C): left=1, right=18, total=40
// [DEBUG]  try root r=3 (D): left=11, right=16, total=48
// [DEBUG]  try root r=4 (E): left=13, right=6, total=40
// [DEBUG]  try root r=5 (F): left=24, right=4, total=49
// [DEBUG]  try root r=6 (G): left=27, right=0, total=48
// [DEBUG]=> best for [1..6]: root=2 (C), cost=40

// [DEBUG]-- Intervals of length 7 --
// [DEBUG]Interval [0...6] keys=A...G, sumW=24
// [DEBUG]  try root r=0 (A): left=0, right=40, total=64
// [DEBUG]  try root r=1 (B): left=3, right=37, total=64
// [DEBUG]  try root r=2 (C): left=5, right=18, total=47
// [DEBUG]  try root r=3 (D): left=18, right=16, total=58
// [DEBUG]  try root r=4 (E): left=20, right=6, total=50
// [DEBUG]  try root r=5 (F): left=31, right=4, total=59
// [DEBUG]  try root r=6 (G): left=34, right=0, total=58
// [DEBUG]=> best for [0..6]: root=2 (C), cost=47
// [DEBUG]=== DP Done ===

// Optimal BST preorder insertion order: [C, A, B, E, D, G, F]
// Optimal total cost: 47
//     ┌── G
//     │   └── F
// ┌── E
// │   └── D
// C
// │   ┌── B
// └── A
// Expected cost of BST: 47

// ❯ make
// javac -cp .:algs4.jar Chapter_3-2/P_3_2_5.java
// java -cp .:algs4.jar:Chapter_3-2 P_3_2_5
// Case 1 (freq intermixed)
// Keys: [A, B, C, D, E, F, G]
// Freq: [3, 1, 9, 1, 5, 1, 4]

// [DEBUG]-- Intervals of length 2 --
// [DEBUG]Interval [0...1] keys=A...B, sumW=4
// [DEBUG]  try root r=0 (A): left=0, right=1, total=5
// [DEBUG]  try root r=1 (B): left=3, right=0, total=7
// [DEBUG]=> best for [0..1]: root=0 (A), cost=5
// [DEBUG]Interval [1...2] keys=B...C, sumW=10
// [DEBUG]  try root r=1 (B): left=0, right=9, total=19
// [DEBUG]  try root r=2 (C): left=1, right=0, total=11
// [DEBUG]=> best for [1..2]: root=2 (C), cost=11
// [DEBUG]Interval [2...3] keys=C...D, sumW=10
// [DEBUG]  try root r=2 (C): left=0, right=1, total=11
// [DEBUG]  try root r=3 (D): left=9, right=0, total=19
// [DEBUG]=> best for [2..3]: root=2 (C), cost=11
// [DEBUG]Interval [3...4] keys=D...E, sumW=6
// [DEBUG]  try root r=3 (D): left=0, right=5, total=11
// [DEBUG]  try root r=4 (E): left=1, right=0, total=7
// [DEBUG]=> best for [3..4]: root=4 (E), cost=7
// [DEBUG]Interval [4...5] keys=E...F, sumW=6
// [DEBUG]  try root r=4 (E): left=0, right=1, total=7
// [DEBUG]  try root r=5 (F): left=5, right=0, total=11
// [DEBUG]=> best for [4..5]: root=4 (E), cost=7
// [DEBUG]Interval [5...6] keys=F...G, sumW=5
// [DEBUG]  try root r=5 (F): left=0, right=4, total=9
// [DEBUG]  try root r=6 (G): left=1, right=0, total=6
// [DEBUG]=> best for [5..6]: root=6 (G), cost=6

// [DEBUG]-- Intervals of length 3 --
// [DEBUG]Interval [0...2] keys=A...C, sumW=13
// [DEBUG]  try root r=0 (A): left=0, right=11, total=24
// [DEBUG]  try root r=1 (B): left=3, right=9, total=25
// [DEBUG]  try root r=2 (C): left=5, right=0, total=18
// [DEBUG]=> best for [0..2]: root=2 (C), cost=18
// [DEBUG]Interval [1...3] keys=B...D, sumW=11
// [DEBUG]  try root r=1 (B): left=0, right=11, total=22
// [DEBUG]  try root r=2 (C): left=1, right=1, total=13
// [DEBUG]  try root r=3 (D): left=11, right=0, total=22
// [DEBUG]=> best for [1..3]: root=2 (C), cost=13
// [DEBUG]Interval [2...4] keys=C...E, sumW=15
// [DEBUG]  try root r=2 (C): left=0, right=7, total=22
// [DEBUG]  try root r=3 (D): left=9, right=5, total=29
// [DEBUG]  try root r=4 (E): left=11, right=0, total=26
// [DEBUG]=> best for [2..4]: root=2 (C), cost=22
// [DEBUG]Interval [3...5] keys=D...F, sumW=7
// [DEBUG]  try root r=3 (D): left=0, right=7, total=14
// [DEBUG]  try root r=4 (E): left=1, right=1, total=9
// [DEBUG]  try root r=5 (F): left=7, right=0, total=14
// [DEBUG]=> best for [3..5]: root=4 (E), cost=9
// [DEBUG]Interval [4...6] keys=E...G, sumW=10
// [DEBUG]  try root r=4 (E): left=0, right=6, total=16
// [DEBUG]  try root r=5 (F): left=5, right=4, total=19
// [DEBUG]  try root r=6 (G): left=7, right=0, total=17
// [DEBUG]=> best for [4..6]: root=4 (E), cost=16

// [DEBUG]-- Intervals of length 4 --
// [DEBUG]Interval [0...3] keys=A...D, sumW=14
// [DEBUG]  try root r=0 (A): left=0, right=13, total=27
// [DEBUG]  try root r=1 (B): left=3, right=11, total=28
// [DEBUG]  try root r=2 (C): left=5, right=1, total=20
// [DEBUG]  try root r=3 (D): left=18, right=0, total=32
// [DEBUG]=> best for [0..3]: root=2 (C), cost=20
// [DEBUG]Interval [1...4] keys=B...E, sumW=16
// [DEBUG]  try root r=1 (B): left=0, right=22, total=38
// [DEBUG]  try root r=2 (C): left=1, right=7, total=24
// [DEBUG]  try root r=3 (D): left=11, right=5, total=32
// [DEBUG]  try root r=4 (E): left=13, right=0, total=29
// [DEBUG]=> best for [1..4]: root=2 (C), cost=24
// [DEBUG]Interval [2...5] keys=C...F, sumW=16
// [DEBUG]  try root r=2 (C): left=0, right=9, total=25
// [DEBUG]  try root r=3 (D): left=9, right=7, total=32
// [DEBUG]  try root r=4 (E): left=11, right=1, total=28
// [DEBUG]  try root r=5 (F): left=22, right=0, total=38
// [DEBUG]=> best for [2..5]: root=2 (C), cost=25
// [DEBUG]Interval [3...6] keys=D...G, sumW=11
// [DEBUG]  try root r=3 (D): left=0, right=16, total=27
// [DEBUG]  try root r=4 (E): left=1, right=6, total=18
// [DEBUG]  try root r=5 (F): left=7, right=4, total=22
// [DEBUG]  try root r=6 (G): left=9, right=0, total=20
// [DEBUG]=> best for [3..6]: root=4 (E), cost=18

// [DEBUG]-- Intervals of length 5 --
// [DEBUG]Interval [0...4] keys=A...E, sumW=19
// [DEBUG]  try root r=0 (A): left=0, right=24, total=43
// [DEBUG]  try root r=1 (B): left=3, right=22, total=44
// [DEBUG]  try root r=2 (C): left=5, right=7, total=31
// [DEBUG]  try root r=3 (D): left=18, right=5, total=42
// [DEBUG]  try root r=4 (E): left=20, right=0, total=39
// [DEBUG]=> best for [0..4]: root=2 (C), cost=31
// [DEBUG]Interval [1...5] keys=B...F, sumW=17
// [DEBUG]  try root r=1 (B): left=0, right=25, total=42
// [DEBUG]  try root r=2 (C): left=1, right=9, total=27
// [DEBUG]  try root r=3 (D): left=11, right=7, total=35
// [DEBUG]  try root r=4 (E): left=13, right=1, total=31
// [DEBUG]  try root r=5 (F): left=24, right=0, total=41
// [DEBUG]=> best for [1..5]: root=2 (C), cost=27
// [DEBUG]Interval [2...6] keys=C...G, sumW=20
// [DEBUG]  try root r=2 (C): left=0, right=18, total=38
// [DEBUG]  try root r=3 (D): left=9, right=16, total=45
// [DEBUG]  try root r=4 (E): left=11, right=6, total=37
// [DEBUG]  try root r=5 (F): left=22, right=4, total=46
// [DEBUG]  try root r=6 (G): left=25, right=0, total=45
// [DEBUG]=> best for [2..6]: root=4 (E), cost=37

// [DEBUG]-- Intervals of length 6 --
// [DEBUG]Interval [0...5] keys=A...F, sumW=20
// [DEBUG]  try root r=0 (A): left=0, right=27, total=47
// [DEBUG]  try root r=1 (B): left=3, right=25, total=48
// [DEBUG]  try root r=2 (C): left=5, right=9, total=34
// [DEBUG]  try root r=3 (D): left=18, right=7, total=45
// [DEBUG]  try root r=4 (E): left=20, right=1, total=41
// [DEBUG]  try root r=5 (F): left=31, right=0, total=51
// [DEBUG]=> best for [0..5]: root=2 (C), cost=34
// [DEBUG]Interval [1...6] keys=B...G, sumW=21
// [DEBUG]  try root r=1 (B): left=0, right=37, total=58
// [DEBUG]  try root r=2 (C): left=1, right=18, total=40
// [DEBUG]  try root r=3 (D): left=11, right=16, total=48
// [DEBUG]  try root r=4 (E): left=13, right=6, total=40
// [DEBUG]  try root r=5 (F): left=24, right=4, total=49
// [DEBUG]  try root r=6 (G): left=27, right=0, total=48
// [DEBUG]=> best for [1..6]: root=2 (C), cost=40

// [DEBUG]-- Intervals of length 7 --
// [DEBUG]Interval [0...6] keys=A...G, sumW=24
// [DEBUG]  try root r=0 (A): left=0, right=40, total=64
// [DEBUG]  try root r=1 (B): left=3, right=37, total=64
// [DEBUG]  try root r=2 (C): left=5, right=18, total=47
// [DEBUG]  try root r=3 (D): left=18, right=16, total=58
// [DEBUG]  try root r=4 (E): left=20, right=6, total=50
// [DEBUG]  try root r=5 (F): left=31, right=4, total=59
// [DEBUG]  try root r=6 (G): left=34, right=0, total=58
// [DEBUG]=> best for [0..6]: root=2 (C), cost=47
// [DEBUG]=== DP Done ===

// Cost table:
//      3     5    18    20    31    34    47
//      .     1    11    13    24    27    40
//      .     .     9    11    22    25    37
//      .     .     .     1     7     9    18
//      .     .     .     .     5     7    16
//      .     .     .     .     .     1     6
//      .     .     .     .     .     .     4

// Root table (index):
//    0   0   2   2   2   2   2
//    .   1   2   2   2   2   2
//    .   .   2   2   2   2   4
//    .   .   .   3   4   4   4
//    .   .   .   .   4   4   4
//    .   .   .   .   .   5   6
//    .   .   .   .   .   .   6

// Optimal BST preorder insertion order: [C, A, B, E, D, G, F]
// Optimal total cost: 47
//     ┌── G
//     │   └── F
// ┌── E
// │   └── D
// C
// │   ┌── B
// └── A
// Expected cost of BST: 47
