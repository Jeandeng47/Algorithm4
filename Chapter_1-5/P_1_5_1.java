import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_1 {
    public static class _QuickFind {
        private int[] id;       // id of component id
        private int count;      // number of components

        private int arrayAccess = 0;

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
            arrayAccess++;
            return id[p];
        }

        
        public void union(int p, int q) {
            int pid = find(p);
            int qid = find(q);
            // if p and q are already in same component, do nothing
            if (pid == qid) return;
            for (int i = 0; i < id.length; i++) {
                arrayAccess++; // read 
                if (id[i] == pid) {
                    id[i] = qid; // change p to q
                    arrayAccess++; // write
                }
            }
            count--;
        }

        private int getArrayAccess() {
            return this.arrayAccess;
        }

        private void resetArrayAccess() {
            this.arrayAccess = 0;
        }

        private void printIdArray() {
            StdOut.print("id array:  ");
            ArrayPrint.printArray(id); 
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        _QuickFind qf = new _QuickFind(n);
        while (!StdIn.isEmpty()) {

            int p = StdIn.readInt();
            int q = StdIn.readInt();

            // reset for every pair
            qf.resetArrayAccess();
            
            // operation
            if (qf.connected(p, q)) continue;
            qf.union(p, q);

            // operation cost and array state
            StdOut.printf("Connect %d and %d\n", p, q);
            qf.printIdArray();
            StdOut.printf("Accesses: %d\n", qf.getArrayAccess());
            StdOut.println();
            
        }
    }
}

// input:
// 10
// 9 0 3 4 5 8 7 2 2 1 5 7 0 3 4 2

// output:
// Connect 9 and 0
// id array:  0 1 2 3 4 5 6 7 8 0
// Accesses: 15

// Connect 3 and 4
// id array:  0 1 2 4 4 5 6 7 8 0
// Accesses: 15

// Connect 5 and 8
// id array:  0 1 2 4 4 8 6 7 8 0
// Accesses: 15

// Connect 7 and 2
// id array:  0 1 2 4 4 8 6 2 8 0
// Accesses: 15

// Connect 2 and 1
// id array:  0 1 1 4 4 8 6 1 8 0
// Accesses: 16

// Connect 5 and 7
// id array:  0 1 1 4 4 1 6 1 1 0
// Accesses: 16

// Connect 0 and 3
// id array:  4 1 1 4 4 1 6 1 1 4
// Accesses: 16

// Connect 4 and 2
// id array:  1 1 1 1 1 1 6 1 1 1
// Accesses: 18