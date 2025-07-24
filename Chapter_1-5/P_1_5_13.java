// Weighted Quick union with path compression

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_13 {
    public static class WeightedQuickUnionPC {
        private int[] id;
        private int[] sz;
        private int count;
        
        public WeightedQuickUnionPC(int N) {
            this.id = new int[N];
            this.sz = new int[N];
            this.count = N;
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
        }

        public int count() {
            return count;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public int find(int p) {
            while (p != id[p]) {
                p = id[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int i = find(p);
            int j = find(q);
            if (i == j) return;

            // always attach the small tree's root to the large tree
            if (sz[i] < sz[j]) {
                id[i] = j;
                sz[j] += sz[i];
            } else {
                id[j] = i;
                sz[i] += sz[j];
            }
            count--;
        }

        public void unionPC(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // link p subtree to q
            if (sz[rootP] < sz[rootQ]) {
                id[rootP] = rootQ;
                sz[rootQ] += sz[rootP];
            } else {
                id[rootQ] = rootP;
                sz[rootP] += sz[rootQ];
            }

            // path compression:
            int newRoot = (sz[rootP] < sz[rootQ])? rootQ : rootP;
            // flatten p->rootP, attach to newRoot
            for (int i = p; i != newRoot; i = id[i]) {
                id[i] = newRoot;
            }
            // flatten q->rootQ, attach to newRoot
            for (int i = q; i != newRoot; i = id[i]) {
                id[i] = newRoot;
            }
            count--;
        }

        public void drawForest() {
            Map<Integer, List<Integer>> trees = new HashMap<>();
            // Construct representation of forest:
            // root -> list of leaves
            for (int i = 0; i < id.length; i++) {
                if (id[i] == i) {
                    // root
                    trees.computeIfAbsent(i, k -> new ArrayList<>());
                } else {
                    // leaves
                    trees.computeIfAbsent(id[i], k -> new ArrayList<>()).add(i);
                }
            }

            // for each root, draw the tree
            for (int r = 0; r < id.length; r++) {
                if (id[r] == r) {
                    printTree(r, trees, "", true);
                }
            }
        }

        private void printTree(int node, Map<Integer, List<Integer>> trees, String prefix, boolean isTail) {
            // print the current node
            if (prefix.isEmpty()) {
                // top level 
                StdOut.println(node);
            } else {
                // concat the leaf
                StdOut.println(prefix + (isTail ? "└── " : "├── ") + node);
            }

            // print recursivly into the leaves
            List<Integer> leaves = trees.getOrDefault(node, List.of());
            for (int i = 0; i < leaves.size(); i++) {
                boolean last = (i == leaves.size() - 1);
                // indent for next level
                String leafPrefix = prefix + (isTail ? "    " : "│   ");
                printTree(leaves.get(i), trees, leafPrefix, last);
            }
        }

    }

    
    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionPC wquPC = new WeightedQuickUnionPC(n);
        WeightedQuickUnionPC wqu = new WeightedQuickUnionPC(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!wquPC.connected(p, q)) {
                wquPC.unionPC(p, q);
            }
            if (!wqu.connected(p, q)) {
                wqu.union(p, q);
            }
        }

        StdOut.println("Tree of Weighted Quick-union: ");
        wqu.drawForest();
        StdOut.printf("Connected components: %d\n", wqu.count());
        StdOut.println();

        StdOut.println("Tree of Weighted Quick-union with path compression: ");
        wquPC.drawForest();
        StdOut.printf("Connected components: %d\n", wquPC.count());
        StdOut.println();
    }    
}

// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2


// Tree of Weighted Quick-union: 
// 6
// 7
//     ├── 1
//     ├── 2
//     ├── 5
//     │   └── 8
//     └── 9
//         ├── 0
//         └── 3
//             └── 4
// Connected components: 2

// Tree of Weighted Quick-union with path compression: 
// 6
// 7
//     ├── 1
//     ├── 2
//     ├── 4
//     ├── 5
//     │   └── 8
//     └── 9
//         ├── 0
//         └── 3
// Connected components: 2


// Worst input:
// 5
// 4 3 3 2 2 1 1 0

// Tree of Weighted Quick-union: 
// 4
//     ├── 0
//     ├── 1
//     ├── 2
//     └── 3
// Connected components: 1

// Tree of Weighted Quick-union with path compression: 
// 4
//     ├── 0
//     ├── 1
//     ├── 2
//     └── 3
// Connected components: 1