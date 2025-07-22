import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_11 {
    public static class _WeightedQuickFind {
        private int[] id;
        private int[] sz;
        private int count;

        public _WeightedQuickFind(int N) {
            this.id = new int[N];
            this.sz = new int[N];
            this.count = N;
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
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
            int pid = id[p];
            int qid = id[q];
            if (pid == qid) return;
            
            for (int i = 0; i < id.length; i++) {
                if (id[i] == pid) {
                    id[i] = qid;
                }
            }
            count--;
        }

        public void unionWeigted(int p, int q) {
            int pid = id[p]; // root of p
            int qid = id[q]; // root of q
            if (pid == qid) return;

            // attach the smaller tree to larger 
            if (sz[pid] < sz[qid]) {
                for (int i = 0; i < id.length; i++) {
                    // if the node belong to p root, change its root
                    if (id[i] == pid) id[i] = qid;
                }
                sz[qid] += sz[pid];
            } else {
                for (int i = 0; i < id.length; i++) {
                    if (id[i] == qid) id[i] = pid;
                }
                sz[pid] += sz[qid];
            }
            count--;
        }

        public void printId() {
            StdOut.print(" id[] = ");
            for (int v : id) StdOut.print(v + " ");
            StdOut.println();
        }
        public void printSz() {
            StdOut.print(" sz[] = ");
            for (int v : sz) StdOut.print(v + " ");
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        // Read number of sites
        int N = StdIn.readInt();
        _WeightedQuickFind wqf = new _WeightedQuickFind(N); // use unionWeighted
        _WeightedQuickFind qf = new _WeightedQuickFind(N); // use union

        // Process all pairs
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            StdOut.printf("Weighted union(%d, %d)%n", p, q);
            wqf.unionWeigted(p, q);
            wqf.printId();
            wqf.printSz();
            StdOut.printf(" components = %d%n%n", wqf.count());

            StdOut.printf("Normal union(%d, %d)%n", p, q);
            qf.union(p, q);
            qf.printId();
            qf.printSz();
            StdOut.printf(" components = %d%n%n", qf.count());

            StdOut.println();
        }
    }
}

// Quick-find:
// find(q): O(1)
// find(p): O(N), scan the whole id[]

// Weighted Quick-find:
// find(q): O(1)
// find(p): O(N), scan the whole id[], but 
// only renames the smaller component's nodes,
// amortized write drops from O(N) to O(min{size(p), size(q)}) 


// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2

// output:
// Weighted union(9, 0)
//  id[] = 9 1 2 3 4 5 6 7 8 9 
//  sz[] = 1 1 1 1 1 1 1 1 1 2 
//  components = 9

// Normal union(9, 0)
//  id[] = 0 1 2 3 4 5 6 7 8 0 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 9


// Weighted union(3, 4)
//  id[] = 9 1 2 3 3 5 6 7 8 9 
//  sz[] = 1 1 1 2 1 1 1 1 1 2 
//  components = 8

// Normal union(3, 4)
//  id[] = 0 1 2 4 4 5 6 7 8 0 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 8


// Weighted union(5, 8)
//  id[] = 9 1 2 3 3 5 6 7 5 9 
//  sz[] = 1 1 1 2 1 2 1 1 1 2 
//  components = 7

// Normal union(5, 8)
//  id[] = 0 1 2 4 4 8 6 7 8 0 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 7


// Weighted union(7, 2)
//  id[] = 9 1 7 3 3 5 6 7 5 9 
//  sz[] = 1 1 1 2 1 2 1 2 1 2 
//  components = 6

// Normal union(7, 2)
//  id[] = 0 1 2 4 4 8 6 2 8 0 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 6


// Weighted union(2, 1)
//  id[] = 9 7 7 3 3 5 6 7 5 9 
//  sz[] = 1 1 1 2 1 2 1 3 1 2 
//  components = 5

// Normal union(2, 1)
//  id[] = 0 1 1 4 4 8 6 1 8 0 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 5


// Weighted union(5, 7)
//  id[] = 9 7 7 3 3 7 6 7 7 9 
//  sz[] = 1 1 1 2 1 2 1 5 1 2 
//  components = 4

// Normal union(5, 7)
//  id[] = 0 1 1 4 4 1 6 1 1 0 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 4


// Weighted union(0, 3)
//  id[] = 9 7 7 9 9 7 6 7 7 9 
//  sz[] = 1 1 1 2 1 2 1 5 1 4 
//  components = 3

// Normal union(0, 3)
//  id[] = 4 1 1 4 4 1 6 1 1 4 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 3


// Weighted union(4, 2)
//  id[] = 7 7 7 7 7 7 6 7 7 7 
//  sz[] = 1 1 1 2 1 2 1 9 1 4 
//  components = 2

// Normal union(4, 2)
//  id[] = 1 1 1 1 1 1 6 1 1 1 
//  sz[] = 1 1 1 1 1 1 1 1 1 1 
//  components = 2