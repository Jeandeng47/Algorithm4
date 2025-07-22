public class _WeightedQuickUnion {
    private int[] id;   // parent link
    private int[] sz;   // size of component for roots
    private int count;  

    public _WeightedQuickUnion(int N) {
        this.id = new int[N];
        this.sz = new int[N];
        this.count = N;
        
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }

        for (int i = 0; i < N; i++) {
            sz[i] = 1;
        }
    }

    public int count() {
        return this.count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        // find the root node
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // always attach the small tree's root to the large tree
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }

    
}
