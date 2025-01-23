public class _TwoColor {
    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColorable = true;

    public _TwoColor(_Graph G) {
        this.marked = new boolean[G.V()];
        this.color = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
            }
        }
    }

    private void dfs(_Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
               color[w] = !color[v];
               dfs(G, w);
            } else if (color[w] == color[v]) {
                // two vertices on the same edge should
                // have different colors
                isTwoColorable = false;
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }
}
