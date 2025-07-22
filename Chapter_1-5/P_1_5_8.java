import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_5_8 {
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

        public void wrongUnion(int p, int q) {
            if (connected(p, q)) return;
            // rename p's component to q
            // issue: dynamically changing id[p] while checking id[p]
            for (int i = 0; i < id.length; i++) {
                if (id[i] == id[p]) {
                    id[i] = id[q];
                }
            }
            count--;
        }

        private void printIdArray() {
            StdOut.print("id: ");
            ArrayPrint.printArray(id);
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        _QuickFind correct = new _QuickFind(n);
        _QuickFind wrong = new _QuickFind(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            
            StdOut.printf("Correct: Connect %d and %d\n", p, q);
            correct.union(p, q);
            correct.printIdArray();

            StdOut.printf("Wrong: Connect %d and %d\n", p, q);
            wrong.wrongUnion(p, q);
            wrong.printIdArray();
            StdOut.println();
        }

        StdOut.printf("Final correct component count: %d%n", correct.count());
        StdOut.printf("Final wrong component count: %d%n",   wrong.count());
    }

}

// input:
// 6
// 2 3 2 4

// Correct: Connect 2 and 3
// id: 0 1 3 3 4 5
// Wrong: Connect 2 and 3
// id: 0 1 3 3 4 5

// Correct: Connect 2 and 4
// id: 0 1 4 4 4 5
// Wrong: Connect 2 and 4
// id: 0 1 4 3 4 5