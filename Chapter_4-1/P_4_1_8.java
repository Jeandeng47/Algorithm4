import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make ARGS="tinyG.txt 2"
public class P_4_1_8 {
    // Search API
    // Search(Graph G, int s): find vertices connected to a source vertex
    // boolean marked(int v): is v connected to s?
    // int count(): how many vertices are connected to s?

    static class Search {
        private boolean[] marked;
        private int count;

        public Search(Graph G, int s) {
            validateVertex(G, s);
            this.marked = new boolean[G.V()];
            UF uf = new UF(G.V());

            // Union connected vertices
            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    uf.union(w, v);
                }
            }

            // Check vertices that are connected to s
            // Vertices that are connected to s should share same parent with S
            int rootS = uf.find(s);
            for (int v = 0; v < G.V(); v++) {
                if (uf.find(v) == rootS) {
                    marked[v] = true;
                    count++;
                }
            }
            
        }

        public boolean marked(int v) {
            return marked[v];
        }

        public int count() {
            return count;
        }

        private void validateVertex(Graph G, int v) {
            if (v < 0 || v >= G.V()) {
                throw new IllegalArgumentException("Vertex " + v + " is out of bounds");
            }
        }
    }

    static class UF {
        private int[] parent; // parent[i]: parent of i
        private int[] size; // size of components for roots
        private int count; // number of connected components

        public UF(int N) {
            if (N < 0) {
                throw new IllegalArgumentException("Number of elements must be non-negative");
            }
            this.parent = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
            }
            this.size = new int[N];
            for (int i = 0; i < N; i++) {
                size[i] = 1;
            }
            this.count = N;
        }
        
        public int count() {
            return this.count;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public int find(int p) {
            while (p != parent[p]) {
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int i = find(p);
            int j = find(q);
            if (i == j) return;

            // Make smaller root point to the larger one
            if (size[i] < size[j]) {
                parent[i] = j;
                size[j] += size[i];
            } else {
                parent[j] = i;
                size[i] += size[j];
            }
            count--;
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        Search search = new Search(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v)) {
                System.out.print(v + " ");
            }
        }
        StdOut.println();

        if (search.count() != G.V()) {
            StdOut.print("NOT ");
        }
        StdOut.println("connected");
    }
}

// Same output as _TestSearch:
// 0 1 2 3 4 5 6 
// NOT connected
