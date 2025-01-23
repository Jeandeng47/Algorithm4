import edu.princeton.cs.algs4.StdOut;

public class P_4_1_5 {
    public static void main(String[] args) {
        _Graph g = new _Graph(5);

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
    
        StdOut.println(g);
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