import java.util.Random;

public class P_1_1_35 {
    public static final int SIDES = 6;
    public static final int MAX_SUM = 12;
    public static final int MIN_SUM = 2;

    private static double[] computeExactDist(double[] dist) {
        for (int i = 1; i <= SIDES; i++) {
            for (int j = 1; j <= SIDES; j++) {
                dist[i + j] += 1.0;
            }
        }
        for (int k = MIN_SUM; k <= MAX_SUM; k++) {
            dist[k] /= (SIDES * SIDES);
        }
        return dist;
    }

    private static double[] simulateDist(int N) {
        Random r = new Random();
        int[] counts = new int[MAX_SUM + 1];
        for (int i = 0; i < N; i++) {
            int n1 = r.nextInt(SIDES) + 1;
            int n2 = r.nextInt(SIDES) + 1;
            counts[n1 + n2]++;
        }
        double[] freq = new double[MAX_SUM + 1];
        for (int j = MIN_SUM; j <= MAX_SUM; j++) {
            freq[j] = (double) counts[j] / N;
        }
        return freq;
    }

    private static boolean match(double[] empirical, double[] theoretical) {
        for (int i = MIN_SUM; i <= MAX_SUM; i++) {
            if (Math.round(empirical[i] * 1000) != Math.round(theoretical[i] * 1000)) {
                return false;
            }
        }
        return true;
    }

    private static double maxAbsError(double[] empirical, double[] theoretical) {
        double maxError = 0.0;
        for (int i = MIN_SUM; i <= MAX_SUM; i++) {
            double diff = Math.abs(empirical[i] - theoretical[i]);
            if (diff > maxError) maxError = diff;
        }
        return maxError;
    }

    private static void printResults(double[] theoretical, double[] empirical, 
    boolean isMatched, double maxError, int N) {
        System.out.println();
        System.out.println("Results for N = " + N);
        System.out.println("------------------------------------------------");
        System.out.printf("%4s | %7s | %9s | %9s%n", "Sum", "Theoretical", "Empirical", "Error");
        System.out.println("------------------------------------------------");
        for (int k = MIN_SUM; k <= MAX_SUM; k++) {
            double error = Math.abs(empirical[k] -  theoretical[k]);
            System.out.printf("%4d | %7.3f | %9.3f | %9.3f%n", k, theoretical[k], empirical[k], error);
        }
        System.out.println("------------------------------------------------");
        System.out.println("Matches to 3 decimals for all sums? " + (isMatched ? "Yes" : "No"));
        System.out.printf("Maximum absolute error: %.5f%n", maxError);
    }

    public static void main(String[] args) {
        double[] theoretical = new double[MAX_SUM + 1];
        computeExactDist(theoretical);

        int[] trialSizes = {10000, 50000, 100000, 500000, 1000000};
        System.out.println("Sum\tTheoretical\t\tEmpirical\tMatch\tMaxError");
        for (int N : trialSizes) {
            double[] empirical = simulateDist(N);
            boolean isMatched = match(empirical, theoretical);
            double maxError = maxAbsError(empirical, theoretical);
            printResults(theoretical, empirical, isMatched, maxError, N);
        }
    }
}



// Example results:

// Results for N = 1000000
// ------------------------------------------------
//  Sum | Theoretical | Empirical |     Error
// ------------------------------------------------
//    2 |   0.028 |     0.028 |     0.000
//    3 |   0.056 |     0.056 |     0.000
//    4 |   0.083 |     0.083 |     0.000
//    5 |   0.111 |     0.111 |     0.000
//    6 |   0.139 |     0.138 |     0.000
//    7 |   0.167 |     0.166 |     0.000
//    8 |   0.139 |     0.139 |     0.000
//    9 |   0.111 |     0.111 |     0.000
//   10 |   0.083 |     0.083 |     0.000
//   11 |   0.056 |     0.056 |     0.000
//   12 |   0.028 |     0.028 |     0.000
// ------------------------------------------------
// Matches to 3 decimals for all sums? No
// Maximum absolute error: 0.00043