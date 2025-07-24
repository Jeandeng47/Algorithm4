// Weighted Quick union by height

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_14 {

    
    public static class WeightedQuickUnionHeight {
        private int[] id;   // parent link
        private int[] sz;   // size of component for roots
        private int[] h;  // height of component for roots
        private int count;  

        public WeightedQuickUnionHeight(int N) {
            this.id = new int[N];
            this.sz = new int[N];
            this.h = new int[N];
            this.count = N;
            
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
                h[i] = 0; // single node has height 0
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
            }
            return p;
        }

        // Union by height
        public void unionHeight(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // link shorted tree under higher tree
            if (h[rootP] < h[rootQ]) {
                id[rootP] = rootQ;
            } else if (h[rootP] > h[rootQ]) {
                id[rootQ] = rootP;
            } else {
                // equal height: h = max(left, right) + 1
                // inequal height: h = max(left, right)
                id[rootQ] = rootP;
                h[rootP]++;
            }
            count--;
        }

        // union by size
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
        WeightedQuickUnionHeight wquHeight = new WeightedQuickUnionHeight(n);
        WeightedQuickUnionHeight wqu = new WeightedQuickUnionHeight(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            if (!wquHeight.connected(p, q)) {
                wquHeight.unionHeight(p, q);
            }
            if (!wqu.connected(p, q)) {
                wqu.union(p, q);
            }
        }

        StdOut.println("Tree of Weighted Quick-union: ");
        wqu.drawForest();
        StdOut.printf("Connected components: %d\n", wqu.count());
        StdOut.println();

        StdOut.println("Tree of Weighted Quick-union by height: ");
        wquHeight.drawForest();
        StdOut.printf("Connected components: %d\n", wquHeight.count());
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

// Tree of Weighted Quick-union by height: 
// 6
// 9
//     ├── 0
//     ├── 3
//     │   └── 4
//     └── 5
//         ├── 7
//         │   ├── 1
//         │   └── 2
//         └── 8
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

// Tree of Weighted Quick-union by height: 
// 4
//     ├── 0
//     ├── 1
//     ├── 2
//     └── 3
// Connected components: 1

