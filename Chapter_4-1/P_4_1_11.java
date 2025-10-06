import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="algs4-data/tinyGex2.txt 0"

public class P_4_1_11 {
    public static class BFS {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;

        public BFS(Graph g, int s) {
            this.s = s;
            this.marked = new boolean[g.V()];
            this.edgeTo = new int[g.V()];
            bfs(g, s);
        }

        public void bfs(Graph g, int s) {
            Queue<Integer> q = new Queue<>();

            marked[s] = true;
            q.enqueue(s);

            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : g.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        marked[w] = true;
                        q.enqueue(w);
                    }
                }
            }
        }

        public void printEdgeTo() {
            StdOut.println("BFS Tree (edgeTo array):");
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

        BFS bfs = new BFS(G, s);
        bfs.printEdgeTo();
    }
}

// BFS Tree (edgeTo array):
// 0 -> 2
// 2 -> 3
// 2 -> 5
// 0 -> 6
// 5 -> 10