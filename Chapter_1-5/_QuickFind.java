public class _QuickFind {
    private int[] id;       // id of component id
    private int count;      // number of components

    public _QuickFind(int N) {
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
        return id[p];
    }

    
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);
        // if p and q are already in same component, do nothing
        if (pid == qid) return;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid; // change p to q
            }
        }
        count--;
    }
}
