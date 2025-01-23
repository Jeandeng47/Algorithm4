import edu.princeton.cs.algs4.StdOut;

public class P_4_1_3 {
    public static void main(String[] args) {
        _Graph g = new _Graph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        // create a copy of graph
        _Graph copy = new _Graph(g);
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
