import edu.princeton.cs.algs4.StdOut;

public class P_1_1_19 {
    // Array for memoization
    private static long[] memo; 
    // Counters for time complexity
    private static int recursiveCalls = 0;
    private static int memoCalls = 0;
    
    public static long fibonacci(int N) {
        recursiveCalls++;
        if (N == 0) return 0;
        if (N == 1) return 1;
        return fibonacci(N - 1) + fibonacci(N - 2);
    }

    public static long fibonacciMemo(int N) {
        memoCalls++;
        if (memo[N] != -1) {
            return memo[N]; // cached result
        }
        if (N == 0) return 0;
        if (N == 1) return 1;
        memo[N] = fibonacciMemo(N - 1) + fibonacciMemo(N - 2);
        return memo[N];

    }

    public static void main(String[] args) {
        int max = 10;
        memo = new long[max + 1]; // size of memo should be N+1

        for (int i = 0; i < max + 1; i++) { // boundary check
            memo[i] = -1;
        }

        // Navied method
        for (int N = 0; N < max; N++) {
            StdOut.println(N + " " + fibonacci(N));
        }
        StdOut.println("Number of recursive calls: " + recursiveCalls);

        // Memoized method
        for (int N = 0; N < max; N++) {
            StdOut.println(N + " " + fibonacciMemo(N));
        }
        StdOut.println("Number of memoized calls: " + memoCalls);        
    }
}

// 0 0
// 1 1
// 2 1
// 3 2
// 4 3
// 5 5
// 6 8
// 7 13
// 8 21
// 9 34
// Number of recursive calls: 276
// Number of memoized calls: 26
