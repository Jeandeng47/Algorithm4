import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_7 {
    public static class _QuickFind {
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

    public static class _QuickUnion {
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

    public static void main(String[] args) {
        int n = StdIn.readInt();
        _QuickFind qf = new _QuickFind(n);
        _QuickUnion qu = new _QuickUnion(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            
            // quick find
            if (!qf.connected(p, q)) {
                qf.union(p, q);
            }

            // quick union
            if (!qu.connected(p, q)) {
                qu.union(p, q);
            }
        }

        StdOut.println();
        StdOut.printf("QuickFind components:  %d%n", qf.count());
        StdOut.printf("QuickUnion components: %d%n", qu.count());

    }


    
}

// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2

// output:
// QuickFind components:  2
// QuickUnion components: 2