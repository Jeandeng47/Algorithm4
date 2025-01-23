public class _Cycle {
    private boolean[] marked;
    private boolean hasCycle;

    public _Cycle(_Graph G) {
        this.marked = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s, s);
            }
        }
    }

    // v: the current vertex, u: parent vertex of v
    private void dfs(_Graph G, int v, int u) {
        // Detection of cycle: a - b - c  - a 
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                // v becomes parent vertex of w
                dfs(G, w, v);
            } else if (w != u) {
                // if w is visited and not parent of v
                hasCycle = true;
            }
        }

    }

    public boolean hasCycle() {
        return hasCycle;
    }
}

// 1 -- 2
// |    |
// 4 -- 3
// dfs(1, 1): mark v1, explore v2 and v4
// dfs(2, 1): mark v2, explore v3 (v1 marked)
// dfs(3, 2): mark v3, explore v4 (v2 marked)
// dfs(4, 3): mark v4, check v3 and v4
//   v3 marked & v3 is parent (u == v3), do nothing
//   v1 marked & v1 is not parent, detect a cycle
