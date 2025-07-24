import java.util.Random;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// make run ARGS=1000

public class P_1_5_17 {

    public static int count(int N) {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        Random rand = new Random();
        int connections = 0;

        while (uf.count() > 1) {
            int p = rand.nextInt(N);
            int q = rand.nextInt(N);
            if (!uf.connected(p, q)) {
                uf.union(p, q);
            }
            connections++;
        }
        return connections;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: provide a positive integer N");
            return;
        }
        int N = Integer.parseInt(args[0]);
        int result = count(N);
        StdOut.printf("To connect %d sites, generated %d random connections.%n", N, result);
    }
}


// example input: N = 1000
// To connect 1000 sites, generated 5171 random pairs.
