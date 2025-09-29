import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_3 {
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

        // Copy constructor
        public Graph(Graph g) {
            this.V = g.V();
            this.E = g.E();
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
                for (int w : g.adj(v)) {
                    adj[v].add(w);
                }
            }
        }

        public void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

    }
    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        // create a copy of graph
        Graph copy = new Graph(g);
        g.addEdge(4, 5);
        
        // Adding to original graph does not affect copy
        StdOut.println("g.adj[4]: ");
        for (int w : g.adj(4)) {
            StdOut.print(" " + w);
        }
        StdOut.println(); 

        StdOut.println("copy.adj[4]: ");
        for (int w : copy.adj(4)) {
            StdOut.print(" " + w);
        }
        StdOut.println();

    }
}

// g.adj[4]: 
//  5 3
// copy.adj[4]: 
//  3
