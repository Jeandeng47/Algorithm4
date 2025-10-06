import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// % make run ARGS="algs4-data/connectedG.txt" (edges format)

// public class GraphProperties
// GraphProperties(Graph G) (exception if G not connected)
// int eccentricity(int v)  : length of the shorted path from v to furthest vertex from v
// int diameter()           : max eccentricity of any vertex
// int radius()             : min eccentricity of any vertex
// int center()             : a vertex whose eccentricity = radius

public class P_4_1_16 {
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
        // Given vertex v:
        // 1. Use BFS to find length from v to any vertex w
        // 2. Find the path that has longest length, record it as eccentricity
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
    }

    
    public static void main(String[] args) {
        Graph G = new Graph(new In("algs4-data/connectedG.txt"));
        StdOut.println(G);
        GraphProperties gp = new GraphProperties(G);
        StdOut.println("ecc(0) = " + gp.eccentricity(0));
        StdOut.println("diameter = " + gp.diameter());
        StdOut.println("radius = " + gp.radius());
        StdOut.println("center = " + gp.center());
        StdOut.println();

        try {
            Graph gBad = new Graph(new In("algs4-data/tinyG.txt"));
            GraphProperties gpBad = new GraphProperties(gBad);
        } catch (Exception e) {
            StdOut.println("Expected exception: " + e);
        }
    }
}

