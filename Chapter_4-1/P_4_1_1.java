import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_1 {
    public static void main(String[] args) {
        _Graph maxGraph = new _Graph(new In("algs4-data/maxEdges.txt"));
        _Graph minGraph = new _Graph(new In("algs4-data/minEdges.txt"));

        // maxGraph and minGraph has same number of vertices
        int V = minGraph.V();

        // Maximum edges with no parallel edges
        StdOut.println(maxGraph);
        int maxEdges = (V * (V - 1)) / 2; 
        assert(maxEdges == maxGraph.E());
        StdOut.println("Maxgraph has " + maxEdges + " edges");
        StdOut.println();
        
        // Minimum edges for no isolated vertices
        StdOut.println(minGraph);
        int minEdges = V - 1;    
        assert(minEdges == minGraph.E());
        StdOut.println("Mingraph has " + minEdges + " edges");


    }
}
