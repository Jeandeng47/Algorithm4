import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_10 {
    static class DFS {
        private boolean[] marked;
        private int[] edgeTo;
        private final int s;
        // find the vertex that can be removed without disconnecting the graph
        private int removable = -1;

        public DFS(_Graph G, int s) {
            this.s = s;
            this.edgeTo = new int[G.V()];
            this.marked = new boolean[G.V()];
            dfs(G, s);
        }

        public void dfs(_Graph G, int v) {
            marked[v] = true;
            boolean isRemovable = true;

            // In a DFS tree, we start search in the root
            // other vertices are reached by their parents
            // For leaf node, all its neighbours are visited
            // If we remove the leaf node, the graph is still connected
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    // if v has an unvisited neighbour, it is not removable
                    isRemovable = false;
                    dfs(G, w);
                } 
            }

            if (isRemovable) {
                removable = v;
            }
        }

        public int getRemovableVertex() {
            return this.removable;
        }
    }

    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        // Perform DFS
        DFS dfs = new DFS(G, s);
        int removable = dfs.getRemovableVertex();
        StdOut.println("Removable vertex is " + removable);
    }
}
