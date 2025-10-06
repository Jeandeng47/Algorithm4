// DFS:
// Start from the source vertex, perform the steps until all vertices visited:
// 1. Mark the vertex as visited
// 2. Visit (recursively) all the unmarked vertices adjacent to it.

import edu.princeton.cs.algs4.Stack;

public class _DepthFirstPaths {
    // has dfs() been called for this vertex?
    private boolean[] marked; 
    // last vertex on known path to this vertex
    // edgeTo[w] = v: v->w is the edge we cross to visit v
    private int[] edgeTo;
    // source vertex
    private final int s;     
    
    public _DepthFirstPaths(_Graph G, int s) {
        this.s = s;
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        dfs(G, s);
    }

    public void dfs(_Graph G, int v) {
        // Mark this vertex
        marked[v] = true;
        for (int w : G.adj(v)) {
            // Visit all the vertices that are adjacent to s
            if (!marked[w]) {
                edgeTo[w] = v;
                // use a pushdown stack (managed by the 
                // system to support the recursive nature of DFS) 
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        // if the v is visited during dfs, there
        // exists a path between s and v
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        // Iterate the marked array
        // for (x = end; x != source; x = x.parent)
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s); // push the source
        return path;
    }
}
