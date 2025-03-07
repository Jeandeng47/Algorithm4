import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_1_1_27 {
    private static int count = 0;

    private static double[][] memo;
    private static int countMemo = 0;

    // Binomial distribution: probability of getting k successes in N trials (p: success probability)
    // Formula: P(X = k) = C(N, k) * p^k * (1 - p)^(N - k)

    // Base case: N = 0 or k < 0
    // If N = 0, there is no trial, so no success
    // If k < 0, it is not possible to have negative number of successes

    // Case 1: If the current trial is a success (with probability p)
    // We have N - 1 trials left and k - 1 successes left
    // thus we have p * B(N - 1, k - 1)

    // Case 2: If the current trial is a failure (with probability 1 - p)
    // We have N - 1 trials left and k successes left
    // thus we have (1 - p) * B(N - 1, k)

    // Recurrence: B(N, k) = (1 - p) * B(N - 1, k) + p * B(N - 1, k - 1)

    public static double binomial(int N, int k, double p) {
        // printIndentation(count, N, k);
        count++;

        if (N == 0 && k == 0) return 1.0;
        if (N < 0 || k < 0) return 0.0;
        return (1.0 - p) * binomial(N - 1, k, p) + p * binomial(N - 1, k - 1, p);
    }

    // Improve the performance by using memoization
    public static double binomialMemo(int N, int k, double p) {
        memo = new double[N + 1][k + 1];
        for (double[] row : memo) {
            Arrays.fill(row, -1);
        }
        return binomialMemoHelper(N, k, p);
    }

    private static double binomialMemoHelper(int N, int k, double p) {
        // printIndentation(count, N, k);
        countMemo++;

        if (N == 0 && k == 0) return 1.0;
        if (N < 0 || k < 0) return 0.0;
        if (memo[N][k] != -1.0) {
            return memo[N][k]; // calcalated before
        }

        memo[N][k] = (1.0 - p) * binomialMemoHelper(N - 1, k, p) + p * binomialMemoHelper(N - 1, k - 1, p);
        return memo[N][k];
    }

    private static void printIndentation(int count, int N, int k) {
        for (int i = 0; i < count; i++) {
            StdOut.print("-");
        }
        StdOut.printf(" C = %d  N = %d  K = %d%n", count, N, k);
    }

    public static void main(String[] args) {

        // N = 10, k = 5, p = 0.45
        // Without Memoization:
        // Result: 0.23403270759257822
        // Recursive Call Count: 2467

        // With Memoization:
        // Result: 0.23628711267947267
        // Recursive Memo Call Count: 101
        
        StdOut.println("Without Memoization:");
        StdOut.println("Result: " + binomial(10, 5, 0.45));
        StdOut.println("Recursive Call Count: " + count);

        StdOut.println("\nWith Memoization:");
        StdOut.println("Result: " + binomialMemo(10, 5, 0.455));
        StdOut.println("Recursive Memo Call Count: " + countMemo);   
    }
}
