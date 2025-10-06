import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="algs4-data/tinyGex2.txt 0"

public class P_4_1_9 {
    static class DFS {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        public DFS(Graph G, int s) {
            this.s = s;
            this.edgeTo = new int[G.V()];
            this.marked = new boolean[G.V()];
            dfs(G, s);
        }

        public void dfs(Graph G, int v) {
            dfsHelper(G, v, 0);
        }

        private void dfsHelper(Graph G, int v, int depth) {
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
                StdOut.print("--"); 
            }
            StdOut.printf(" DFS(%d)\n", v);
        }

        public void printEdgeTo() {
            StdOut.println("DFS Tree (edgeTo array):");
            for (int v = 0; v < edgeTo.length; v++) {
                if (v != s && marked[v]) { // Skip the source vertex
                    StdOut.printf("%d -> %d\n", edgeTo[v], v);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        DFS dfs = new DFS(G, s);
        StdOut.println();
        dfs.printEdgeTo();
        
    }
}

//  DFS(0)
// -- DFS(2)
// ---- DFS(5)
// ------ DFS(10)
// -------- DFS(3)
// ---------- DFS(6)

// DFS Tree (edgeTo array):
// 0 -> 2
// 10 -> 3
// 2 -> 5
// 3 -> 6
// 5 -> 10
