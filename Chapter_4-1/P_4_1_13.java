import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="algs4-data/tinyGex2.txt 0"

public class P_4_1_13 {
    public static class BFS {
        private boolean[] marked;
        private int[] edgeTo;
        private int[] distTo; // record distance from source 
        private int s;
        
        public BFS(Graph g, int s) {
            this.s = s;
            this.marked = new boolean[g.V()];
            this.edgeTo = new int[g.V()];
            this.distTo = new int[g.V()];
            for (int i = 0; i < g.V(); i++) {
                edgeTo[i] = -1;
                distTo[i] = Integer.MAX_VALUE;
            }
            bfs(g, s);
        }

        private void bfs(Graph g, int s) {
            Queue<Integer> q = new Queue<>();
            
            marked[s] = true;
            edgeTo[s] = s;
            distTo[s] = 0;
            q.enqueue(s);
            
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w: g.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        marked[w] = true;
                        distTo[w] = distTo[v] + 1;
                        q.enqueue(w); 
                    }
                }
            }
        }

        // Return the number of edges on the shortest path between 
        // the source vertex and given vertex
        public int distTo(int v) {
            return distTo[v];
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        BFS bfs = new BFS(G, s);
        //      0 
        //     /  \
        //    2   6
        //   / \
        //  5  3
        //  /
        // 10

        StdOut.println("Distance from source to 3: ");
        StdOut.println(bfs.distTo(3)); // expect 2

        
        StdOut.println("Distance from source to 7 (unreachable from 0): ");
        StdOut.println(bfs.distTo(7)); // expect Integer.MAX
        
    }
}