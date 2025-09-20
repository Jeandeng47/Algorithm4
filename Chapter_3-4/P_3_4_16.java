import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.princeton.cs.algs4.StdOut;

public class P_3_4_16 {

    private static void theory(int M, int N, int trials, Random rnd) {
        int allOccupied = 0;

        // Collect target pos
        Set<Integer> target = new HashSet<>();
        for (int i = 0; i <= M; i = i + 100) {
            if (i < M) target.add(i);
        }

        // Simulate randomly-occupied hash table
        for (int t = 0; t < trials; t++) {
            Set<Integer> occupied = new HashSet<>();
            while (occupied.size() < N) {
                occupied.add(rnd.nextInt(M)); // [0...M-1]
            }
            // check probability
            if (occupied.containsAll(target)) {
                allOccupied++;
            }
        }
        double prob = allOccupied / (double) trials;
        StdOut.printf("Estimated probability = %.20f%n", prob);
    }

    public static void main(String[] args) {
        int M = 1_000_000;             // table size
        int N = M / 2;           // occupied slots
        int trials = 20;  // Monte Carlo iterations
        Random rnd = new Random(42);

        theory(M, N, trials, rnd);
    }
}

// Estimated probability = 0.00000000000000000000

// Idea:
// In a very large linear-probing hash table, even if the load
// factor is 50%, the probability that a specific sparse pattern 
// would be all occupied is near to 0.