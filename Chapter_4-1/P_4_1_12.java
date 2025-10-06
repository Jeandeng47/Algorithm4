import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="algs4-data/tinyGex2.txt 0"

public class P_4_1_12 {
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

        // Return the distance between any two non-root vertices
        // dist(u, v) = distTo[u] + distTo[v] - 2 * distTo[LCA(u, v)]
        //  0
        // / \
        // 1   2
        // |   |
        // 3   4
        // dist(1,4): d[1] = 1, d[4] = 2
        // 1. Lift deeper vertex up to the same level, d++; 4->2, d[1] = d[2] = 1
        // 2. Starting from the same level, climb up until meeting the
        // the first lowest common ancestor (LCA)

        public int distBtw(int u, int v) {
            if (u == v) return 0;
            if (!marked[u] || !marked[v]) return -1; // either unreachable from root

            int du = distTo[u], dv = distTo[v];
            int dist = 0;

            // lift deeper vertex up
            while (du > dv) { u = edgeTo[u]; du--; dist++; }
            while (dv > du) { v = edgeTo[v]; dv--; dist++; }

            // climb up to find LCA
            while (u != v) {
                u = edgeTo[u];
                v = edgeTo[v];
                dist += 2;
            }
            return dist;
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

        StdOut.println("Distance between vertex 3 and 10: ");
        StdOut.println(bfs.distBtw(3, 10)); // expect 3

        StdOut.println("Distance between vertex 3 and 7 (unreachable from 0): ");
        StdOut.println(bfs.distBtw(3, 7)); // expect -1


    }
}

// Distance between vertex 3 and 10: 
// 3
// Distance between vertex 3 and 7 (unreachable from 0): 
// -1