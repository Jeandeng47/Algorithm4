import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class P_4_1_18 {
    // for printing
    private static final int ACTION_W = 12; // leftmost column width
    private static final int CELL_W   = 2;  // cell width
    private static final String GAP   = "   "; // gap between marked[], id[]

    // connected component 
    public static class CC {
        private int[] id;
        private boolean[] marked;
        private int count;
        private int V;
        
        public CC(Graph G) {
            this.V = G.V();
            this.marked = new boolean[V];
            this.id = new int[V];

            printHeader();
            for (int s = 0; s < V; s++) {
                if (!marked[s]) {
                    printRow("dfs(" + s + ")s");
                    dfs(G, s);
                    count++;
                }
            }
        }

        private void dfs(Graph G, int v) {
            
            marked[v] = true;
            id[v] = count;

            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    printRow("dfs(" + w + ")s");
                    dfs(G, w);
                }
            }
        }

        public boolean connected(int v, int w) {
            return id[v] == id[w];
        }

        public int id(int v) {
            return id[v];
        }

        public int count() {
            return count;
        }

        private void printHeader() {
            StdOut.println();
            // header
            StdOut.printf("%-" + ACTION_W + "s", "action");

            StdOut.print("marked[] ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "d", i);   // index
            }

            StdOut.print(GAP + "id[] ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "d", i);
            }

            StdOut.print(GAP + "count ");
            StdOut.println();
        }

        private void printRow(String action) {
            StdOut.printf("%-" + ACTION_W + "s", action);

            // marked[] 
            StdOut.print("         ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "s", marked[i] ? "T" : "");
            }

            // id[] 
            StdOut.print(GAP + "    "); 
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "s", marked[i] ? String.valueOf(id[i]) : "");
            }

            StdOut.print(GAP + "    ");
            StdOut.printf("%d", count);
            StdOut.println();
        }

    }

    public static void main(String[] args) {
        Graph g1 = new Graph(new In("algs4-data/tinyG.txt"));
        CC cc1 = new CC(g1);
        StdOut.printf("%ncomponents = %d%n", cc1.count());

        Graph g2 = new Graph(new In("algs4-data/tinyGex2.txt"));
        CC cc2 = new CC(g2);
        StdOut.printf("%ncomponents = %d%n", cc2.count());
    }
}
