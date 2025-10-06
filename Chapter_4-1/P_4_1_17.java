import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_17 {
    public static class GraphProperties {
        private Graph g;
        private int V;
        private int[] ecc;

        // Constructor (exception if G not connected)
        public GraphProperties(Graph g) {
            this.g = g;
            this.V = g.V();
            if (!isConnected(g)) {
                throw new IllegalArgumentException("Graph is not connected");
            }

            ecc = new int[V];
            for (int i = 0; i < V; i++) {
                ecc[i] = -1;
            }
        }

        // If a graph is connected, every vertex has a path to any given vertex
        private boolean isConnected(Graph g) {
            BreadthFirstPaths bfs = new BreadthFirstPaths(g, 0);
            for (int v = 0; v < g.V(); v++) {
                if(!bfs.hasPathTo(v)) {
                    return false;
                }
            }
            return true;
        }

        // Eccentricity: length of the shorted path from v to furthest vertex from v
        public int eccentricity(int v) {
            checkVertex(v);

            int maxLen = 0;
            BreadthFirstPaths bfs = new BreadthFirstPaths(g, v); // v as source
            for (int w = 0; w < g.V(); w++) {
                int len = bfs.distTo(w); // shortest path from v to other vertex
                if (len > maxLen) maxLen = len;
            }
            ecc[v] = maxLen;
            return maxLen;
        }

        // max eccentricity of any vertex
        public int diameter() {
            int maxE = 0;
            for (int i = 0; i < V; i++) {
                int e = eccentricity(i);
                if (e > maxE) { maxE = e; }
            }
            return maxE;
        }

        // min eccentricity of any vertex
        public int radius() {
            int minE = Integer.MAX_VALUE;
            for (int i = 0; i < V; i++) {
                int e = eccentricity(i);
                if (e < minE) { minE = e; }
            }
            return minE;
        }

        // a vertex whose eccentricity = radius
        public int center() {
            int radius = radius();
            for (int i = 0; i < V; i++) {
                int e = eccentricity(i);
                if (e == radius) {
                    return i;
                }
            }
            return -1; // connected graph must have a center
        }

        private void checkVertex(int v) {
            if (v < 0 || v >= V) throw new IllegalArgumentException("vertex out of range: " + v);
        }

        // Length of the shorted cycle
        // 1. Run BFS from each vertex
        // 2. For each vertex v and its neighbor w, check:
        //      - if both v, w were marked
        //      - w is not direct parent of v
        //      - then (v, w) closes a cycle
        // 3. cycle length = distTo(v) + distTo(w) + 1
  
        public int girth() {
            int minCycle = Integer.MAX_VALUE;

            for (int s = 0; s < g.V(); s++) {
                StdOut.printf("BFS(%d)%n", s);
                BreadthFirstPaths bfs = new BreadthFirstPaths(g, s);
                
                for (int v = 0; v < g.V(); v++) {
                    if (!bfs.hasPathTo(v)) continue;

                    Integer pv = parentOf(bfs, s, v); // find parent of v
                    StdOut.printf("  visit v=%d  dist=%d  parent=%s%n",
                        v, bfs.distTo(v), (pv == null ? "-" : pv.toString()));

                    for (int w : g.adj(v)) {
                        if (!bfs.hasPathTo(w)) continue;

                        Integer pw = parentOf(bfs, s, w);

                        // if (v, w) is tree edge & v-w are direct parent-child, skip this edge
                        if ((pv != null && pv == w) || (pw != null && pw == v)) {
                            StdOut.printf("    skip tree edge (%d,%d)%n", v, w);
                            continue;
                        }
    
                        int cycleLen = bfs.distTo(v) + bfs.distTo(w) + 1;
                        StdOut.printf("    cycle via edge (%d,%d): d(v)=%d, d(w)=%d  => len=%d%n",
                            v, w, bfs.distTo(v), bfs.distTo(w), cycleLen);
                        if (cycleLen < minCycle) minCycle = cycleLen;

                    }
                }
            }
            return (minCycle == Integer.MAX_VALUE) ? Integer.MAX_VALUE : minCycle;
        }
    }

    private static Integer parentOf(BreadthFirstPaths bfs, int s, int x) {
        Integer prev = null;
        for (int u : bfs.pathTo(x)) { // path s -> x
            if (u == x) return prev;
            prev = u;
        }
        return null;
    }

    public static void main(String[] args) {
        // Graph with simple single cycle
        Graph G1 = new Graph(4);
        G1.addEdge(0, 1);
        G1.addEdge(1, 2);
        G1.addEdge(2, 3);
        G1.addEdge(3, 0);

        StdOut.println(G1);
        
        GraphProperties gp1 = new GraphProperties(G1);
        int g1 = gp1.girth();
        StdOut.println();
        StdOut.printf("Girth (final) = %s%n",
                (g1 == Integer.MAX_VALUE ? "INF (acyclic)" : String.valueOf(g1)));
        
        
        // Graph with multiple cycles
        Graph G2 = new Graph(10);

        // triangle: girth = 3
        G2.addEdge(0, 1);
        G2.addEdge(1, 2);
        G2.addEdge(2, 0);

        // square (another cycle of length 4)
        G2.addEdge(3, 4);
        G2.addEdge(4, 5);
        G2.addEdge(5, 6);
        G2.addEdge(6, 3);

        // connect triangle and square + add extra cycles
        G2.addEdge(2, 3);    // connects the two regions
        G2.addEdge(6, 7);
        G2.addEdge(7, 8);
        G2.addEdge(8, 2);    // forms a longer cycle 2-3-6-7-8-2
        G2.addEdge(8, 9);
        G2.addEdge(9, 0);    // tie back to triangle region

        StdOut.println(G2);

        GraphProperties gp2 = new GraphProperties(G2);
        // int g2 = gp2.girth();
        // StdOut.println();
        // StdOut.printf("Girth (final) = %s%n",
        //         (g2 == Integer.MAX_VALUE ? "INF (acyclic)" : String.valueOf(g2)));
    }
}

// 0 - 1 - 2
// |       |
// +---3---+
// Edges: 0-1, 1-2, 2-3, 3-0

// BFS from 0:
// distTo(1)=1, distTo(2)=2, distTo(3)=1
// When checking edge (2,3):
//  - both discovered, not direct parent-child → cycle length = 2+1+1=4
//  - minCycle = 4

//      1
//     / \
//    0---2-----------3-----------4
//    |   |           |           |
//    |   |           |           |
//    9---8           6-----------5
//         |
//         |
//         7

// Girth (final) = 3