// Compare quick-find, quick-union, weighted-quick-union's 
// cost and amortized cost using mediumUF.txt

// To run this program:
// javac -cp .:algs4.jar ./Chapter_1-5/P_1_5_16.java
// java -cp .:algs4.jar:Chapter_1-5 P_1_5_16 < algs4-data/mediumUF.txt

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.princeton.cs.algs4.StdIn;

public class P_1_5_16 {
    public interface UF {
        public boolean connected(int p, int q);
        public void union(int p, int q);
        public int cost(); // return the cost of last operation
        public void resetCost();
    }

    public static class QuickFind implements UF {
        private int[] id;
        private int count;
        private int lastCost = 0;

        public QuickFind(int N) {
            this.count = N;
            this.id = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
            }
        }

        public int count() {
            return this.count;
        }

        public int find(int p) {
            return id[p];
        }

        public boolean connected(int p, int q) {
            // 2 reads
            lastCost += 2;
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int pid = id[p];
            int qid = id[q];
            if (pid == qid) return;

            for (int i = 0; i < id.length; i++) {
                lastCost++; // 1 read
                if (id[i] == pid) {
                    id[i] = qid;
                    lastCost++; // 1 write
                }
            }
            count--;
        }

        @Override
        public int cost() {
            return this.lastCost;
        }

        @Override
        public void resetCost() {
            this.lastCost = 0;
        }   
    }

    public static class QuickUnion implements UF {
        private int[] id;
        private int count;
        private int lastCost;

        public QuickUnion(int N) {
            this.count = N;
            this.id = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
            }
        }

        public int count() {
            return this.count;
        }

        public int find(int p) {
            while (p != id[p]) {
                p = id[p];
            }
            return p;
        }

        private int findCost(int p) {
            int cost = 0;
            while (p != id[p]) {
                cost += 1; // read
                p = id[p];
                cost += 1; // write
            }
            cost += 1; // final read
            return cost;
        }

        @Override
        public boolean connected(int p, int q) {
            lastCost = findCost(p) + findCost(q);
            return find(p) == find(q);
        }

        @Override
        public void union(int p, int q) {
            int cost = findCost(p) + findCost(q);
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // change root of P to Q
            id[rootP] = rootQ;
            cost++;

            lastCost = cost;
        }

        @Override
        public int cost() {
            return lastCost;
        }

        @Override
        public void resetCost() {
            this.lastCost = 0;
        }

    }

    public static class WeightedQuickUnion implements UF {
        private int[] id;
        private int[] sz;
        private int count;
        private int lastCost;

        public WeightedQuickUnion(int N) {
            this.count = N;
            this.id = new int[N];
            this.sz = new int[N];

            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
        }

        public int count() {
            return this.count;
        }

        public int find(int p) {
            while (p != id[p]) {
                p = id[p];
            }
            return p;
        }

        private int findCost(int p) {
            int cost = 0;
            while (p != id[p]) {
                cost += 1; // read
                p = id[p];
                cost += 1; // write
            }
            cost += 1; // final read
            return cost;
        }

        @Override
        public boolean connected(int p, int q) {
            lastCost = findCost(p) + findCost(q);
            return find(p) == find(q);
        }

        @Override
        public void union(int p, int q) {
            int cost = findCost(p) + findCost(q);
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // change root of P to Q
            if (sz[rootP] < sz[rootQ]) {
                // p subtree is smaller
                id[rootP] = rootQ;
                sz[rootQ] += sz[rootP];
                cost++;
            } else {
                id[rootQ] = rootP;
                sz[rootP] += sz[rootP];
                cost++;
            }

            lastCost = cost;
        }

        @Override
        public int cost() {
            return lastCost;
        }

        @Override
        public void resetCost() {
            this.lastCost = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        int N = StdIn.readInt();
        UF[] algs = {
            new QuickFind(N),
            new QuickUnion(N),
            new WeightedQuickUnion(N),
        };

        String[] names = {
            "Quick-find",
            "Quick-union",
            "Weighted-quick-union"
        };

        // One CSV file per algorithm
        String outDir = "Chapter_1-5";
        Path outPath = Paths.get(outDir);
        if (!Files.exists(outPath)) {
            Files.createDirectories(outPath); // ensure outDir exist
        }

        BufferedWriter[] outs = new  BufferedWriter[algs.length];
        for (int k = 0; k < algs.length; k++) {
            outs[k] = new  BufferedWriter(
                new FileWriter(outDir + File.separator + names[k]+ ".csv")
                );
            outs[k].write("op,costCon,avgCon,costUnion,avgUnion\n");
        }

        long[] totalCon = new long[algs.length];
        long[] totalUnion = new long[algs.length];
        int op = 0; // represenet operation to a pair (p, q)

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            op++;

            for (int k = 0; k < algs.length; k++) {
                UF uf = algs[k];

                // cost of connected
                uf.resetCost();
                uf.connected(p, q);
                int costCon = uf.cost();
                totalCon[k] += costCon;
                double avgCon = (double) totalCon[k] / op;

                // cost of union
                uf.resetCost();
                uf.union(p, q);
                int costUnion = uf.cost();
                totalUnion[k] += costUnion;
                double avgUnion = (double) totalUnion[k] / op;


                // write data into file
                outs[k].write(String.format(
                    "%d,%d,%.3f,%d,%.3f\n",
                    op, costCon, avgCon, costUnion, avgUnion
                ));
            }
        }

        for (var out : outs) out.close();
    }
}


// Output csv files:
// Quick-find.csv
// Quick-union.csv
// Weighted-Quick-union.csv