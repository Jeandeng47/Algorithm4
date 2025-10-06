// To run this program:
// % make run ARGS="algs4-data/tinyGadj.txt" (adjacent list format)
// % make run ARGS="algs4-data/tinyG.txt" (edges format)

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_15 {
    public static class Graph {
        private int V;
        private int E;
        private Bag<Integer>[] adj;
        
        // Default constructor
        public Graph(int V) {
            this.V = V;
            this.E = 0;
            this.adj = (Bag<Integer>[]) new Bag[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new Bag<Integer>(); 
            }
        }
    

        // Read a graph from input stream
        public Graph(In in) {
            this(in.readInt());
            int expectedE = in.readInt();
            
            // Read line to decide format (conusme one edge)
            String line = nextNonEmptyLine(in);
            String[] tokens = line.split("\\s+");

            if (tokens.length == 2) {
                // Input format: V E (u_i, v_i)
                addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                for (int i = 1; i < expectedE; i++) { // start from 1
                    int v = in.readInt();
                    int w = in.readInt();
                    addEdge(v, w);
                }
                
            } else {
                // Input format: V E (v_i, (u_i, w_i,...)) 
                parseAdjList(tokens);
                String l;
                while ((l = nextNonEmptyLine(in)) != null) {
                    String[] ts = l.split("\\s+");
                    parseAdjList(ts);
                }
            }
        }

        // Read next non-empty line from Stdin
        private static String nextNonEmptyLine(In in) {
            while (!in.isEmpty()) {
                String line = in.readLine();
                if (line == null) return null;
                line = line.trim();
                if (!line.isEmpty()) return line;
            }
            return null;
        }

        
        private void parseAdjList(String[] tokens) {
            int v = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                int w = Integer.parseInt(tokens[i]);
                addEdge(v, w);
            }
        }

        private void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }

        public int V() { return V; }
        public int E() { return E; }
        public Iterable<Integer> adj(int v) { return adj[v]; }

        @Override
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

// Graph of tinyGadj.txt
// 13 vertices, 13 edges
// 0 : 6 5 2 1 
// 1 : 0 
// 2 : 0 
// 3 : 5 4 
// 4 : 6 5 3 
// 5 : 4 3 0 
// 6 : 4 0 
// 7 : 8 
// 8 : 7 
// 9 : 12 11 10 
// 10 : 9 
// 11 : 12 9 
// 12 : 11 9 



// Graph of tinyGex2.tx 
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
