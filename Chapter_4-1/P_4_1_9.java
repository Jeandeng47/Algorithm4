import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="tinyGex2.txt 0"

public class P_4_1_9 {
    static class DFS {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        public DFS(_Graph G, int s) {
            this.s = s;
            this.edgeTo = new int[G.V()];
            this.marked = new boolean[G.V()];
            dfs(G, s);
        }

        public void dfs(_Graph G, int v) {
            dfsHelper(G, v, 0);
        }

        private void dfsHelper(_Graph G, int v, int depth) {
            printIndentation(v, depth);

            marked[v] = true;

            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    dfsHelper(G, w, depth + 1);
                } 
            }
        }

        private void printIndentation(int v, int depth) {
            for (int i = 0; i < depth; i++) {
                StdOut.print("--"); // Indentation for each level
            }
            StdOut.printf(" dfs(%d)\n", v);
        }

        public void printEdgeTo() {
            StdOut.println("DFS Tree (edgeTo array):");
            for (int v = 0; v < edgeTo.length; v++) {
                if (v != s) { // Skip the source vertex
                    StdOut.printf("%d -> %d\n", edgeTo[v], v);
                }
            }
        }
    }

    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        // Perform DFS and print trace
        DFS dfs = new DFS(G, s);

        // Print the edgeTo tree
        dfs.printEdgeTo();
        
    }
}


// dfs(0)
// -- dfs(2)
// ---- dfs(5)
// ------ dfs(10)
// -------- dfs(3)
// ---------- dfs(6)
// DFS Tree (edgeTo array):
// 0 -> 1
// 0 -> 2
// 10 -> 3
// 0 -> 4
// 2 -> 5
// 3 -> 6
// 0 -> 7
// 0 -> 8
// 0 -> 9
// 5 -> 10
// 0 -> 11