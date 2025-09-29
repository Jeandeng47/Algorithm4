import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_5 {

        public static class Graph {
        private int V;
        private int E;
        private Bag<Integer>[] adj;

        public Graph(int V) {
            this.V = V;
            this.E = 0;
            adj = (Bag<Integer>[]) new Bag[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new Bag<>();
            }
        }

        public int V() { return V; }
        public int E() { return E; }
        public Iterable<Integer> adj(int v) {
            return adj[v];
        }

        public void addEdge(int v, int w) {
            checkSelfLoop(v, w);
            checkParallelEdge(v, w);
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

        private void checkSelfLoop(int v, int w) {
            for (int n : adj[v]) {
                if (v == w) {
                    throw new IllegalArgumentException
                    ("Self-loops found at " + v);
                }
            }
        }

        private void checkParallelEdge(int v, int w) {
            for (int n : adj[v]) {
                if (n == w) {
                    throw new IllegalArgumentException
                    ("Parallel edge found at " + v  + " and " + w);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);

        g.addEdge(0, 1); // Valid
        g.addEdge(1, 2); // Valid
    
        try {
            g.addEdge(0, 0); // Self-loop
        } catch (IllegalArgumentException e) {
            StdOut.println("Error: " + e.getMessage());
        }
    
        try {
            g.addEdge(1, 2); // Parallel edge
        } catch (IllegalArgumentException e) {
            StdOut.println("Error: " + e.getMessage());
        }
    
    }
}

// Error: Self-loops found at vertex 0
// Error: Parallel edge found at vertex 1 and 2
// 5 vertices, 2 edges
// 0 : 1 
// 1 : 2 0 
// 2 : 1 
// 3 : 
// 4 : 