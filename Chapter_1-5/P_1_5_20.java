import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class P_1_5_20 {

    public interface DynamicWQU {
        int newSite();                 // create a new site and return its id
        int count();                  // number of components
        void union(int p, int q);     // union operation
        boolean connected(int p, int q); // are p and q connected?
        int find(int p);              // find root of p
    }

    public static class WQUDynArr implements DynamicWQU {
        private int[] id;  // store parents
        private int[] sz;  // store subtree size
        private int count; // number of components
        private int N;     // current number of sites

        public WQUDynArr() {
            this.id = new int[2];
            this.sz = new int[2];
            this.N = 0;
            this.count = 0;
        }

        public int count() {
            return this.count;
        }

        private void resize(int capacity) {
            int[] tempId = new int[capacity];
            int[] tempSz = new int[capacity];

            for (int i = 0; i < id.length; i++) {
                tempId[i] = id[i];
                tempSz[i] = sz[i];
            }
            this.id = tempId;
            this.sz = tempSz;
        }

        // Return id of new site
        public int newSite() {
            if (N == id.length) {
                // resize both id[] and sz[]
                resize(N * 2);
            }
            id[N] = N;
            sz[N] = 1;
            count++;
            return N++;
        }

        private void validate(int p) {
            if (p < 0 || p >= N)
                throw new IllegalArgumentException("Index " + p + " is not between 0 and " + (N-1));
        }
        

        // Find root of site p
        public int find(int p) {
            validate(p);
            while (p != id[p]) {
                p = id[p];
            }
            return p;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            if (sz[rootP] < sz[rootQ]) {
                id[rootP] = rootQ;
                sz[rootQ] += sz[rootP];
            } else {
                id[rootQ] = rootP;
                sz[rootP] += sz[rootQ];
            }
            count--;
        }
        
    }

    public static class WQULList implements DynamicWQU {

        private static class Node {
            Node parent;
            int size;
            int id;

            Node(int id) {
                this.id = id;           // = index i
                this.parent = this;     // = id[i] 
                this.size = 1;          // = sz[i]
            }

        }

        private List<Node> sites;
        private int count;

        // constructor without knowing capacity
        public WQULList() {
            this.sites = new LinkedList<>();
            this.count = 0;
        }

        // return id of new site
        public int newSite() {
            Node newNode = new Node(sites.size());
            sites.add(newNode);
            count++;
            return newNode.id;
        }

        public int count() {
            return this.count;
        }

        private Node getNode(int id) {
            if (id < 0 || id >= sites.size())
                throw new IllegalArgumentException("Invalid site ID: " + id);
            return sites.get(id);
        }

        // Find root of site p
        public int find(int p) {
            // climb up the tree until we meet the root
            Node root = getNode(p);
            while (root != root.parent) {
                // if root still has parent
                root = root.parent;
            }
            return root.id;
        }

        private Node findRoot(int p) {
            Node root = getNode(p);
            while (root != root.parent) {
                root = root.parent;
            }
            return root;
        }

        public boolean connected(int p, int q) {
            return findRoot(p) == findRoot(q);
        }

        public void union(int p, int q) {
            Node rootP = findRoot(p);
            Node rootQ = findRoot(q);
            if (rootP == rootQ) return;

            if (rootP.size < rootQ.size) {
                rootP.parent = rootQ;
                rootQ.size += rootP.size;
            } else {
                rootQ.parent = rootP;
                rootP.size += rootQ.size;
            }
            count--;
        }
        
    }

    public static void runTest(DynamicWQU wqu) {
        int a = wqu.newSite(); // 0
        int b = wqu.newSite(); // 1
        int c = wqu.newSite(); // 2
        int d = wqu.newSite(); // 3

        StdOut.printf("a = %d, b = %d, c = %d, d = %d\n", a, b, c, d);

        wqu.union(a, b); // connect a and b
        StdOut.println("a connected to b? " + wqu.connected(a, b)); // true
        StdOut.println("b connected to c? " + wqu.connected(b, c)); // false

        wqu.union(c, d); // connect c and d
        StdOut.println("c connected to d? " + wqu.connected(c, d)); // true

        wqu.union(b, d); // connect (a-b) and (c-d)
        StdOut.println("a connected to d? " + wqu.connected(a, d)); // true

        StdOut.println("Final component count: " + wqu.count()); // should be 1
    }

    public static void main(String[] args) {
        StdOut.println("=== Testing array-based implementation ===");
        DynamicWQU arrayWQU = new WQUDynArr();
        runTest(arrayWQU);

        StdOut.println("\n=== Testing linked-list-based implementation ===");
        DynamicWQU listWQU = new WQULList();
        runTest(listWQU);

    }
}

// === Testing array-based implementation ===
// a = 0, b = 1, c = 2, d = 3
// a connected to b? true
// b connected to c? false
// c connected to d? true
// a connected to d? true
// Final component count: 1

// === Testing linked-list-based implementation ===
// a = 0, b = 1, c = 2, d = 3
// a connected to b? true
// b connected to c? false
// c connected to d? true
// a connected to d? true
// Final component count: 1