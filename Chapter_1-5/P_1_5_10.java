import edu.princeton.cs.algs4.StdOut;

public class P_1_5_10 {
        public static class _WeightedQuickUnion {
            private int[] id;
            private int[] sz;
            private int count;
            
            public _WeightedQuickUnion(int N) {
                this.count = N;
                this.id = new int[N];
                this.sz = new int[N];

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
                while (p != id[p]) {
                    p = id[p];
                }
                return p;
            }

            public void union(int p, int q) {
                int i = find(p);
                int j = find(q);
                if (i == j) return;

                // Proper: attach the root of the smaller subtree
                // to the larger tree root
                if (sz[i] < sz[j]) {
                    id[i] = j;
                    sz[j] += sz[i];
                } else {
                    id[j] = i;
                    sz[i] += sz[j];
                }
                count--;
                
            }

            public void unionBad(int p, int q) {
                int i = find(p);
                int j = find(q);
                if (i == j) return;

                // Bad: attach the root of the smaller subtree
                // to the smaller tree root
                id[i] = q;
                sz[q] += sz[i];

                count--;
            }

            public int maxDepth() {
                int max = 0;
                // for each point, find it's subtree's height
                for (int i = 0; i < id.length; i++) {
                    int depth = 0;
                    int p = i;
                    while (p != id[p]) {
                        p = id[p];
                        depth++;
                    }
                    // find the subtree with max height
                    if (depth > max) max = depth;
                }
                return max;
            }
        }

    public static void main(String[] args) {
        int n = 100;
        _WeightedQuickUnion wquGood = new _WeightedQuickUnion(n);
        _WeightedQuickUnion wquBad = new _WeightedQuickUnion(n);

        // worst case union: (0, 1), (2, 1), (3, 2), ..., (N-1, N-2)
        for (int i = 1; i < n; i++) {
            wquGood.union(i, i - 1);
            wquBad.unionBad(i, i - 1);
        }

        StdOut.printf("After %d unions:%n", n - 1);
        StdOut.printf("Proper WQU max depth = %d (log2N = %.1f)%n", 
        wquGood.maxDepth(), Math.log(n) / Math.log(2));
        StdOut.printf("Bad WQU max depth %d%n", wquBad.maxDepth());
    }
}


// After 99 unions:
// Proper WQU max depth = 1 (log2N = 6.6)
// Bad WQU max depth 99
