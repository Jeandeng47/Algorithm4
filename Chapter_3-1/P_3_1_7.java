import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_1_7 {

    // Theoretical expectation: M * (1 - (1 - 1/M)^N)
    // 0...M-1 bins, N balls, Ii = the ith bin is at least hit once
    // Pr(Ii) = 1 - (1-1/M)^N
    // E[Sum_i=1...M(Ii)] = M * (1 - (1-1/M)^N) = M(1 - e^(-W/M))
    private static double expected(int N, int M) {
        return M * (1.0 - Math.pow(1.0 - 1.0 / M, N));
    }

    // Draw N integers in [0, M-1], count distinct
    private static int simulate(int N, int M) {
        boolean[] seen = new boolean[M];
        int distinct = 0;
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            int x = rand.nextInt(M);
            if (!seen[x]) {
                seen[x] = true;
                distinct++;
                if (distinct == M) break;
            }
        }
        return distinct;
    }

    
    public static void main(String[] args) {
        int trials = 20;
        int M = 10_000;
        int[] Ns = {10, 100, 1_000, 10_000, 100_000, 1_000_000};

        System.out.printf("%10s %18s %18s%n", "N", "avgDistinct", "expected");
        for (int N : Ns) {
            long sum = 0;
            for (int t = 0; t < trials; t++) {
                sum += simulate(N, M);
            }
            double avg = sum / (double) trials;
            double theory = expected(N, M);
            StdOut.printf("%10d %18.3f %18.3f%n", N, avg, theory);
        }

    }
}

//          N        avgDistinct           expected
//         10             10.000              9.996
//        100             99.400             99.507
//       1000            950.100            951.671
//      10000           6318.650           6321.390
//     100000           9999.500           9999.546
//    1000000          10000.000          10000.000

// 1. If key comes from a fixed set (M = 10_000), 
// it does not grow with the input size without bound.
// 2. The distinct key numbers follows a S-curve:
// D = M(1 - e^(-W/M))
// 3. Resource prediction:
// (1) Memory: the number of distinct key
// (2) Time: number of put() and get() is an function of W and D