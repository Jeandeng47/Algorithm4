import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_6 {
    // Build the 4-cycle: edges 0-1, 1-2, 2-3, 3-0
    private static Graph buildTarget() {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 0);
        return g;
    }

    // Check if the given adjacency lists could come from addEdge() on the 4-cycle
    private static boolean matchesTarget(int[][] adj) {
        Graph g = buildTarget();

        boolean[][] want = new boolean[4][4];
        for (int u = 0; u < 4; u++) {
            for (int v : g.adj(u)) want[u][v] = true;
        }

        boolean[][] in = new boolean[4][4];
        for (int u = 0; u < 4; u++) {
            for (int v : adj[u]) {
                if (v < 0 || v > 3 || v == u) return false; 
                in[u][v] = true;
            }
        }

        // symmetry + no extras/missing
        for (int u = 0; u < 4; u++)
            for (int v = 0; v < 4; v++)
                if (in[u][v] != want[u][v]) return false;

        return true;
    }

    public static void main(String[] args) {
        // A valid adjacency-lists array 
        int[][] g1 = {
            {1, 3}, // 0
            {0, 2}, // 1
            {1, 3}, // 2
            {2, 0}  // 3
        };

        // An impossible adjacency-lists array 
        int[][] g2 = {
            {1},        // 0 (missing 3)
            {0, 2},     // 1
            {1, 3},     // 2
            {2, 0, 1}   // 3 (extra 1)
        };

        StdOut.println("Graph 1 -> " + (matchesTarget(g1) ? "POSSIBLE" : "IMPOSSIBLE"));
        StdOut.println("Graph 2 -> " + (matchesTarget(g2) ? "POSSIBLE" : "IMPOSSIBLE"));
    }
}