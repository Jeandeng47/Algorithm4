import java.util.Arrays;

import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// To run this class:
// make run ARGS="5"

public class P_1_1_39 {
    
    private static double countMatches(int[] a, int[] b) {
        Arrays.sort(a);
        int count = 0;
        for (int key : b) {
            if (BinarySearch.indexOf(a, key) != -1) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Provide an integer T <trials>");
            return;
        }

        int T = Integer.parseInt(args[0]);
        int[] trialSizes = { 1000, 10000, 100000, 1000000};
    
        
        StdOut.printf("%10s %15s%n", "N", "Avg # Matches");
        // For each size
        for (int N : trialSizes) {
            double totalMatches = 0;
            // For each trial
            for (int t = 0; t < T; t++) {
                // generate 2 random arrays
                int[] a = new int[N];
                int[] b = new int[N];

                // populate the array with 6-digits numbers
                for (int i = 0; i < N; i++) {
                    a[i] = StdRandom.uniformInt(100_000, 1_000_000);
                    b[i] = StdRandom.uniformInt(100_000, 1_000_000);
                }
                
                // count the overlaps
                totalMatches += countMatches(a, b);
            }

            double avg = totalMatches / T;
            StdOut.printf("%10d %15.2f%n", N, avg);
        }
        
        
    }
}

// Example result: T = 5
//          N   Avg # Matches
//       1000            0.60
//      10000          109.00
//     100000        10517.80
//    1000000       670603.80