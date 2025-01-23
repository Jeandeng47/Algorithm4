public class _CC {
    private boolean marked[];
    private int[] id; // id[v] gives the component v belongs to
    private int count; // number of connected components

    public _CC(_Graph G) {
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()]; 
        // After each iteration, dfs marked all the vercties
        // in that one connected component
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(_Graph G, int v) {
        marked[v] = true;
        id[v] = count; // update id of v

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean connected(int v, int w) {
        // if two vertices belongs to same CC,
        // they are connected
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }
}
