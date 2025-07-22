import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_2 {
    public static class _QuickUnion {
        private int[] id;
        private int count;

        public _QuickUnion(int N) {
            // init component id array
            this.count = N;
            this.id = new int[N];
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
            // find the root of p
            while (p != id[p]) {
                p = id[p];
            }
            // the root points to itself
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

        private void printIdArray() {
            StdOut.print("id array:  ");
            ArrayPrint.printArray(id); 
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
        _QuickUnion qu = new _QuickUnion(n);

        while(!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (qu.connected(p, q)) continue;
            qu.union(p, q);

            qu.printIdArray();
            StdOut.println("Forest:");
            qu.drawForest();
            StdOut.println();

        }
    }

}


// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2

// id array:  0 1 2 3 4 5 6 7 8 0
// Forest:
// 0
//     └── 9
// 1
// 2
// 3
// 4
// 5
// 6
// 7
// 8

// id array:  0 1 2 4 4 5 6 7 8 0
// Forest:
// 0
//     └── 9
// 1
// 2
// 4
//     └── 3
// 5
// 6
// 7
// 8

// id array:  0 1 2 4 4 8 6 7 8 0
// Forest:
// 0
//     └── 9
// 1
// 2
// 4
//     └── 3
// 6
// 7
// 8
//     └── 5

// id array:  0 1 2 4 4 8 6 2 8 0
// Forest:
// 0
//     └── 9
// 1
// 2
//     └── 7
// 4
//     └── 3
// 6
// 8
//     └── 5

// id array:  0 1 1 4 4 8 6 2 8 0
// Forest:
// 0
//     └── 9
// 1
//     └── 2
//         └── 7
// 4
//     └── 3
// 6
// 8
//     └── 5

// id array:  0 1 1 4 4 8 6 2 1 0
// Forest:
// 0
//     └── 9
// 1
//     ├── 2
//     │   └── 7
//     └── 8
//         └── 5
// 4
//     └── 3
// 6

// id array:  4 1 1 4 4 8 6 2 1 0
// Forest:
// 1
//     ├── 2
//     │   └── 7
//     └── 8
//         └── 5
// 4
//     ├── 0
//     │   └── 9
//     └── 3
// 6