public class _DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public _DepthFirstSearch(_Graph G, int s) {
        this.marked = new boolean[G.V()];
        dfs(G, s);
    }

    public void dfs(_Graph G, int s) {
        // Mark this vertex
        this.marked[s] = true;
        this.count++;
        for (int w : G.adj(s)) {
            // Visit all the vertices that are adjacent to s
            if (!this.marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean marked(int v) {
        return this.marked[v];
    }

    public int count() {
        return this.count;
    }
}
