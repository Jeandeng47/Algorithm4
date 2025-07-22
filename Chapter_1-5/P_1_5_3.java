import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_3 {
    public static class _WeightedQuickUnion {
        private int[] id;   // parent link
        private int[] sz;   // size of component for roots
        private int count;  

        private int arrayAccess = 0;

        public _WeightedQuickUnion(int N) {
            this.id = new int[N];
            this.sz = new int[N];
            this.count = N;
            
            for (int i = 0; i < N; i++) {
                id[i] = i;
            }

            for (int i = 0; i < N; i++) {
                sz[i] = 1;
            }
        }

        public int count() {
            return this.count;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public int find(int p) {
            // find the root node
            while (p != id[p]) {
                p = id[p];
                // one compare, one write
                arrayAccess += 2;
            }
            return p;
        }

        public void union(int p, int q) {
            int i = find(p);
            int j = find(q);
            if (i == j) return;

            // always attach the small tree's root to the large tree

            arrayAccess += 2; // compare sz[i] & sz[k]
            if (sz[i] < sz[j]) {
                id[i] = j;
                sz[j] += sz[i];
                arrayAccess += 2; // write id[i], sz[j]
            } else {
                id[j] = i;
                sz[i] += sz[j];
                arrayAccess += 2; // write id[j], sz[i]
            }
            count--;
        }

        private int getArrayAccess() {
            return this.arrayAccess;
        }

        private void resetArrayAccess() {
            this.arrayAccess = 0;
        }

        private void printIdArray() {
            StdOut.print("id array: ");
            ArrayPrint.printArray(id); 
        }

        private void printSzArray() {
            StdOut.print("sz array: ");
            ArrayPrint.printArray(sz); 
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
        _WeightedQuickUnion wqu = new _WeightedQuickUnion(n);
        while (!StdIn.isEmpty()) {

            int p = StdIn.readInt();
            int q = StdIn.readInt();

            // reset for every pair
            wqu.resetArrayAccess();
            
            // operation
            if (wqu.connected(p, q)) continue;
            wqu.union(p, q);

            // operation cost and array state
            StdOut.printf("Connect %d and %d\n", p, q);
            wqu.printIdArray();
            wqu.printSzArray();
            StdOut.printf("Accesses: %d\n", wqu.getArrayAccess());
            
            StdOut.println("Forest:");
            wqu.drawForest();
            StdOut.println();
            
        }
    }

}

// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2


// Connect 9 and 0
// id array: 9 1 2 3 4 5 6 7 8 9
// sz array: 1 1 1 1 1 1 1 1 1 2
// Accesses: 4
// Forest:
// 1
// 2
// 3
// 4
// 5
// 6
// 7
// 8
// 9
//     └── 0

// Connect 3 and 4
// id array: 9 1 2 3 3 5 6 7 8 9
// sz array: 1 1 1 2 1 1 1 1 1 2
// Accesses: 4
// Forest:
// 1
// 2
// 3
//     └── 4
// 5
// 6
// 7
// 8
// 9
//     └── 0

// Connect 5 and 8
// id array: 9 1 2 3 3 5 6 7 5 9
// sz array: 1 1 1 2 1 2 1 1 1 2
// Accesses: 4
// Forest:
// 1
// 2
// 3
//     └── 4
// 5
//     └── 8
// 6
// 7
// 9
//     └── 0

// Connect 7 and 2
// id array: 9 1 7 3 3 5 6 7 5 9
// sz array: 1 1 1 2 1 2 1 2 1 2
// Accesses: 4
// Forest:
// 1
// 3
//     └── 4
// 5
//     └── 8
// 6
// 7
//     └── 2
// 9
//     └── 0

// Connect 2 and 1
// id array: 9 7 7 3 3 5 6 7 5 9
// sz array: 1 1 1 2 1 2 1 3 1 2
// Accesses: 8
// Forest:
// 3
//     └── 4
// 5
//     └── 8
// 6
// 7
//     ├── 1
//     └── 2
// 9
//     └── 0

// Connect 5 and 7
// id array: 9 7 7 3 3 7 6 7 5 9
// sz array: 1 1 1 2 1 2 1 5 1 2
// Accesses: 4
// Forest:
// 3
//     └── 4
// 6
// 7
//     ├── 1
//     ├── 2
//     └── 5
//         └── 8
// 9
//     └── 0

// Connect 0 and 3
// id array: 9 7 7 9 3 7 6 7 5 9
// sz array: 1 1 1 2 1 2 1 5 1 4
// Accesses: 8
// Forest:
// 6
// 7
//     ├── 1
//     ├── 2
//     └── 5
//         └── 8
// 9
//     ├── 0
//     └── 3
//         └── 4

// Connect 4 and 2
// id array: 9 7 7 9 3 7 6 7 5 7
// sz array: 1 1 1 2 1 2 1 9 1 4
// Accesses: 16
// Forest:
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
