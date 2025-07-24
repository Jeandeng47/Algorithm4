// Quick union with path compression

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_12 {
    public static class QuickUnionPC {
        private int[] id;
        private int count;

        public QuickUnionPC(int N) {
            this.id = new int[N];
            this.count = N;
            for (int i = 0; i < N; i++) {
                id[i] = i;
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
            int pRoot = find(p);
            int qRoot = find(q);

            // if p and q belongs to the same tree, do nothing
            if (pRoot == qRoot) return;
            // change p to q
            id[pRoot] = qRoot;
            count--;
        }

        public void unionPC(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // link p subtree to q
            id[rootP] = rootQ;

            // flatten the entire p->rootP to point at rootQ
            // (for every node in p subtree, change its id[i] to q)
            for (int i = p; i != rootQ; i = id[i]) {
                id[i] = rootQ;
            }

            // flattern the entire q-> rootQ to point at root
            for (int i = q; i != rootQ; i = id[i]) {
                id[i] = rootQ;
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
        QuickUnionPC quPC = new QuickUnionPC(n);
        QuickUnionPC qu = new QuickUnionPC(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!quPC.connected(p, q)) {
                quPC.unionPC(p, q);
            }
            if (!qu.connected(p, q)) {
                qu.union(p, q);
            }
        }

        StdOut.println("Tree of Quick-union: ");
        qu.drawForest();
        StdOut.printf("Connected components: %d\n", qu.count());
        StdOut.println();

        StdOut.println("Tree of Quick-union with path compression: ");
        quPC.drawForest();
        StdOut.printf("Connected components: %d\n", quPC.count());
        StdOut.println();
    }
}

// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2
// Tree of Quick-union: 
// 1
//     ├── 2
//     │   └── 7
//     ├── 4
//     │   ├── 0
//     │   │   └── 9
//     │   └── 3
//     └── 8
//         └── 5
// 6
// Connected components: 2

// Tree of Quick-union with path compression: 
// 1
//     ├── 2
//     ├── 4
//     │   ├── 0
//     │   │   └── 9
//     │   └── 3
//     ├── 5
//     ├── 7
//     └── 8
// 6
// Connected components: 2





// Path of 4:
// 5
// 4 3 3 2 2 1 1 0
// Tree of Quick-union: 
// 0
//     └── 1
//         └── 2
//             └── 3
//                 └── 4
// Connected components: 1

// Tree of Quick-union with path compression: 
// 0
//     └── 1
//         └── 2
//             └── 3
//                 └── 4
// Connected components: 1