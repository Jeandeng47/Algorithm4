import edu.princeton.cs.algs4.StdOut;

public class P_1_1_20 {

    private static double[] memo;

    public static double lgFactorial(int N) {
        // Base case: ln(0!) = ln(1!) = 0
        if (N == 0 || N == 1) {
            return 0; 
        }
        if (memo[N] != -1) {
            return memo[N];
        }
        // Recurrence: lg(N!) = lg(N) + lg((N-1)!) 
        memo[N] = Math.log(N) + lgFactorial(N - 1);
        return memo[N];
    }

    public static void main(String[] args) {
        
        int N = 10;
        memo = new double[N + 1]; // size of memo should be N+1
        for (int i = 0; i < N + 1; i++) {
            memo[i] = -1;
        }

        double result = lgFactorial(N);
        StdOut.println("ln(" + N + "!) = " + result);
        
    }
}
