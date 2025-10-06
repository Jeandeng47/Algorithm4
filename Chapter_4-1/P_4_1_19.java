import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_19 {
    public static class Cycle {
        private static final int ACTION_W = 14;
        private static final int CELL_W   = 2;

        private boolean[] marked;
        private boolean hasCycle;
        
        public Cycle(Graph G) {
            this.marked = new boolean[G.V()];

            printHeader(G.V());
            for (int s = 0; s < G.V(); s++) {
                if (!marked[s]) {
                    printRow("dfs(" + s + ")", G.V());
                    dfs(G, s, s);
                }
            }
        }

        private void dfs(Graph G, int v, int u) {
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    printRow("dfs(" + w + ")", G.V());
                    dfs(G, w, v);
                } else if (w != u) {
                    // if we have visited w & w is not parent of v
                    StdOut.printf("back %d - %d%n", v, w); 
                    hasCycle = true;
                    return;
                }
            }
        }
        
        private boolean hasCycle() {
            return hasCycle;
        } 

        private void printHeader(int V) {
            StdOut.println();
            StdOut.printf("%-" + ACTION_W + "s", "action");
            StdOut.print("marked[] ");
            for (int i = 0; i < V; i++) StdOut.printf("%" + CELL_W + "d", i);
            StdOut.println();
        }

        private void printRow(String action, int V) {
            StdOut.printf("%-" + ACTION_W + "s", action);
            StdOut.print("         ");
            for (int i = 0; i < V; i++) {
                StdOut.printf("%" + CELL_W + "s", marked[i] ? "T" : "");
            }
            StdOut.println();
        }
    }
    public static void main(String[] args) {
        Graph g1 = new Graph(new In("algs4-data/tinyG.txt"));
        Cycle cc1 = new Cycle(g1);
        StdOut.println("Has cycle? " + cc1.hasCycle());

        Graph g2 = new Graph(new In("algs4-data/tinyGex2.txt"));
        Cycle cc2 = new Cycle(g2);
        StdOut.println("Has cycle? " + cc2.hasCycle());
    }
}


// action        marked[]  0 1 2 3 4 5 6 7 8 9101112
// dfs(0)                                           
// dfs(6)                  T                        
// dfs(4)                  T           T            
// dfs(5)                  T       T   T            
// dfs(3)                  T       T T T            
// back 3 - 4
// back 5 - 0
// back 4 - 3
// dfs(2)                  T     T T T T            
// dfs(1)                  T   T T T T T            
// back 0 - 5
// dfs(7)                  T T T T T T T            
// dfs(8)                  T T T T T T T T          
// dfs(9)                  T T T T T T T T T        
// dfs(11)                 T T T T T T T T T T      
// dfs(12)                 T T T T T T T T T T   T  
// back 12 - 9
// dfs(10)                 T T T T T T T T T T   T T
// back 9 - 12
// Has cycle? true

// action        marked[]  0 1 2 3 4 5 6 7 8 91011
// dfs(0)                                         
// dfs(2)                  T                      
// dfs(5)                  T   T                  
// dfs(10)                 T   T     T            
// dfs(3)                  T   T     T         T  
// dfs(6)                  T   T T   T         T  
// back 6 - 2
// back 3 - 2
// back 10 - 3
// back 2 - 6
// back 0 - 6
// dfs(1)                  T   T T   T T       T  
// dfs(4)                  T T T T   T T       T  
// dfs(8)                  T T T T T T T       T  
// back 8 - 1
// back 1 - 8
// dfs(7)                  T T T T T T T   T   T  
// back 7 - 8
// dfs(9)                  T T T T T T T T T   T  
// dfs(11)                 T T T T T T T T T T T  
// back 11 - 8
// Has cycle? true