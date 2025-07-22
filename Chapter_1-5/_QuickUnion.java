public class _QuickUnion {
    private int[] id;
    private int count;

    public _QuickUnion(int N) {
        // init component id array
        this.count = N;
        this.id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        // find the root of p
        while (p != id[p]) {
            p = id[p];
        }
        // the root points to itself
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        // if p and q belongs to the same tree, do nothing
        if (pRoot == qRoot) return;
        // change p to q
        id[pRoot] = qRoot;
        count--;
    }
}
