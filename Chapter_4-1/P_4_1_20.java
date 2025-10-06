import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_20 {
    public static class TwoColor {
        private static final int ACTION_W = 12; 
        private static final int CELL_W   = 2;  
        private static final String GAP   = "   ";
        
        private boolean[] marked;
        private boolean[] color;
        private boolean isTwoColorable = true;
        private int V;
        
        public TwoColor(Graph G) {
            this.V = G.V();
            this.marked = new boolean[G.V()];
            this.color = new boolean[G.V()];

            printHeader();
            for (int s = 0; s < G.V(); s++) {
                if (!marked[s]) {
                    printRow("dfs(" + s + ")");
                    dfs(G, s);
                }
            }
        }

        private void dfs(Graph G, int v) {
            marked[v] = true;

            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    color[w] = !color[v];
                    printRow("dfs(" + w + ")");
                    dfs(G, w);
                } else if (color[w] == color[v]) {
                    // if we have visited w and v-w has same color
                    isTwoColorable = false;
                }
            }
        }

        public boolean isBipartite() {
            return isTwoColorable;
        }


        private void printHeader() {
            StdOut.println();
            // header
            StdOut.printf("%-" + ACTION_W + "s", "action");

            StdOut.print("marked[] ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "d", i);   // index
            }

            StdOut.print(GAP + "color[] ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "d", i);
            }

            StdOut.println();
        }

        private void printRow(String action) {
            StdOut.printf("%-" + ACTION_W + "s", action);

            // marked[] 
            StdOut.print("         ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "s", marked[i] ? "T" : "");
            }

            // color[] 
            StdOut.print(GAP + "        "); 
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "s", color[i] ? "+" : "-");
            }

            StdOut.println();
        }

    }

    public static void main(String[] args) {
        Graph g1 = new Graph(6);
        // graph with a cycle of even #vertices -> two-colorable
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(5, 0);
        g1.addEdge(2, 3);
        g1.addEdge(2, 4);
        g1.addEdge(3, 5);
        TwoColor tc1 = new TwoColor(g1);
        StdOut.printf("%nisBipartite = %s%n", tc1.isBipartite()); 

        // graph with a cycle of odd #vertices -> non two-colorable
        Graph g2 = new Graph(new In("algs4-data/tinyGex2.txt"));
        TwoColor tc2 = new TwoColor(g2);
        StdOut.printf("%nisBipartite = %s%n", tc2.isBipartite());
    }
}

// action      marked[]  0 1 2 3 4 5   color[]  0 1 2 3 4 5
// dfs(0)                                       - - - - - -
// dfs(5)                T                      - - - - - +
// dfs(3)                T         T            - - - - - +
// dfs(2)                T     T   T            - - + - - +
// dfs(4)                T   T T   T            - - + - - +
// dfs(1)                T   T T T T            - + + - - +

// isBipartite = true

// action      marked[]  0 1 2 3 4 5 6 7 8 91011   color[]  0 1 2 3 4 5 6 7 8 91011
// dfs(0)                                                   - - - - - - - - - - - -
// dfs(2)                T                                  - - + - - - - - - - - -
// dfs(5)                T   T                              - - + - - - - - - - - -
// dfs(10)               T   T     T                        - - + - - - - - - - + -
// dfs(3)                T   T     T         T              - - + - - - - - - - + -
// dfs(6)                T   T T   T         T              - - + - - - + - - - + -
// dfs(1)                T   T T   T T       T              - - + - - - + - - - + -
// dfs(4)                T T T T   T T       T              - - + - + - + - - - + -
// dfs(8)                T T T T T T T       T              - - + - + - + - - - + -
// dfs(11)               T T T T T T T   T   T              - - + - + - + - - - + +
// dfs(7)                T T T T T T T   T   T T            - - + - + - + - - - + +
// dfs(9)                T T T T T T T T T   T T            - - + - + - + - - - + +

// isBipartite = false