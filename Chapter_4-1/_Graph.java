import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class _Graph {
    private final int V; // number of vertices
    private int E; // number of edges
    private Bag<Integer>[] adj; // adjacency lists

    // Create an empty graph with V vertices
    @SuppressWarnings("unchecked")
    public _Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V]; // Create array of lists.
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>(); // Initialize all lists
        }
    }

    // Read a graph from input stream
    public _Graph(In in) {
        this(in.readInt()); // read V and construct this graph
        int E = in.readInt(); // read E
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    // Number of vertices
    public int V() {
        return V;
    }

    // Number of edges
    public int E() {
        return E;
    }

    // Add an edge v-w to this graph   
    public void addEdge(int v, int w) {

        checkValidVertex(v, w);
        // checkselfLoop(v, w);
        // checkParallelEdge(v, w);

        this.adj[v].add(w);
        this.adj[w].add(v);
        E++;
    }

    private void checkValidVertex(int v, int w) {
        // Ensure valid vertices
        if (v < 0 || v >= V || w < 0 || w >= V) {
            throw new IllegalArgumentException("Vertex out of bounds");
        }
    }

    // Return a list of vertices adjacent to v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

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
