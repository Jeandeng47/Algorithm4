// Weighted quick-union and binomial coefficients
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_15 {
    public static class WeightedQuickUnion {
        private int[] id;   // parent link
        private int[] sz;   // size of component for roots
        private int count;  

        public WeightedQuickUnion(int N) {
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

        public int depth(int p) {
            int depth = 0;
            while (p != id[p]) {
                p = id[p];
                depth++;
            }
            return depth;
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

    // return binomial coefficient C(n, k)
    // C(n, k) = n! / (n - k)!k!
    // n! / (n - k)! = (n - k + 1) * (n - k + 2) * ... * n
    // 1 / k! = 1 / 1 * 2 * 3 *  ... * k
    private static long binomial(int n, int k) {
        if (k < 0 || k > n) return 0;
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - k + 1) / i;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 3;
        int N = 1 << n; // 2^n nodes
        WeightedQuickUnion wqu = new WeightedQuickUnion(N);

        // Build binomial tree:
        // union 2 trees with equal sizes

        // Example:
        // n = 3, N = 8
        // k = 0, step = 2^0 = 1: u(0, 1), u(2, 3), u(4, 5), u(6, 7)
        // k = 1, step = 2^1 = 2: u(0, 2), u(4, 6), 
        // k = 2, step = 2^2 = 4: u(0, 4)
        for (int k = 0; k < n; k++) {
            int step = 1 << k; // tree size to merge = 2^k
            for (int i = 0; i < N; i += 2 * step) {
                wqu.union(i, i + step);
            }
        }
        wqu.drawForest();

        // depths statistics
        int[] hist = new int[n + 1];
        double sum = 0;
        for (int i = 0; i < N; i++) {
            int d = wqu.depth(i);
            hist[d]++;
            sum += d;
        }

        // compare #nodes and binomial coefficient
        StdOut.println("i\t#nodes@depth i\tC(n, i)\t");
        for (int i = 0; i <= n; i++) {
            StdOut.printf("%d\t%d\t\t%d%n", i, hist[i], binomial(n, i));
        }

        // average depth
        double avg = sum / N;
        StdOut.printf("%nAverage depth = %.4f (expected n/2 = %.1f)%n",
                          avg, n / 2.0);
    }

}

// example output: N = 2^3, n = 3
// 0
//     ├── 1
//     ├── 2
//     │   └── 3
//     └── 4
//         ├── 5
//         └── 6
//             └── 7
// i       #nodes@depth i  C(n, i)
// 0       1               1
// 1       3               3
// 2       3               2
// 3       1               0

// Average depth = 1.5000 (expected n/2 = 1.5)

// 1. Worst case for WQU：always merge 2 trees of equal size
// if we have N = 2^n
// (1) merge two trees of size 2^0 to get tree of size 2^1
// (2) merge two trees of size 2^1 to get tree of size 2^2
// ...
// (n) merge two trees of size 2^n-1 to get tree of size 2^n
// At the end we have a binomial tree B_n

// 2. Features of binomial tree B_n
// if the number of nodes in B_n is N = 2^n,
// The number of nodes at level i is C(n, i) =  n!/ i!(n - i)!

// 3. Average depth of node
// Let D = depth of a uniformly random node in Bn
// E[D] = (1/2^n) * Sum[ level i * (# nodes at level i) ]
// E[D] = (1/2^n) * Sum[i * C(n, i) = (1/2^n) * (n * 2^(n-1))
//      = n / 2 = Log2(N) / 2 ~ Log2(N)

// The average node depth is Log2(N)
