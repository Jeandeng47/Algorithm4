import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class P_4_1_7 {
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

        public Graph(In in) {
            this(in.readInt());
            int E = in.readInt();
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
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

        public String toString() {
            String s = V + " vertices, " + E + " edges\n";
            for (int v = 0; v < V; v++) {
                s += v + " : ";
                for (int w : this.adj(v)) {
                    s += w + " ";
                }
                s += "\n";
            }
            return s;
        }

    }
    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        StdOut.println(G);
    }
}

// Graph of tinyG.txt:
// 13 vertices, 13 edges
// 0 : 6 2 1 5 
// 1 : 0 
// 2 : 0 
// 3 : 5 4 
// 4 : 5 6 3 
// 5 : 3 4 0 
// 6 : 0 4 
// 7 : 8 
// 8 : 7 
// 9 : 11 10 12 
// 10 : 9 
// 11 : 9 12 
// 12 : 11 9 
