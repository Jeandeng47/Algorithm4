import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_4 {
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
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

        public boolean hasEdge(int v, int w) {
            for (int n : adj[v]) {
                if (n == w) return true;
            }
            return false;
        }

    }


    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);

        StdOut.println("Has edge 0-1: " + g.hasEdge(0, 1));
        StdOut.println("Has edge 1-2: " + g.hasEdge(1, 2));
    }
}


// Has edge 0-1: true
// Has edge 1-2: true