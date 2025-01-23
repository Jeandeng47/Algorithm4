import edu.princeton.cs.algs4.StdOut;

public class P_4_1_4 {
    public static void main(String[] args) {
        _Graph g = new _Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);

        StdOut.println("Has edge 0-1: " + g.hasEdge(0, 1));
        StdOut.println("Has edge 1-2: " + g.hasEdge(1, 2));
    }
}


// Has edge 0-1: true
// Has edge 1-2: true