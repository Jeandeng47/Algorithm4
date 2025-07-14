import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To compile and run this program:
// % make run ARGS="algs4-data/largeW.txt algs4-data/largeT.txt"

public class P_1_1_38 {
    public static int bruteForceSearch(int[] whiteList, int[] queries) {
        int found = 0;
        for (int q : queries) {
            for (int w : whiteList) {
                if (w == q) {
                    found++;
                    break;
                }
            }
        }
        return found;
        
    }

    public static int binarySearch(int[] whiteList, int[] queries) {
        int found = 0;
        for (int q : queries) {
            if (Arrays.binarySearch(whiteList, q) >= -1) {
                found++;
            }
        }
        return found;
    }

    public static void main(String[] args) {
        int[] whiteList = (new In(args[0])).readAllInts();
        int[] queries = (new In(args[1])).readAllInts();

        // Brute Force timing
        long t0 = System.currentTimeMillis();
        int countBF = bruteForceSearch(whiteList, queries);
        long t1 = System.currentTimeMillis();

        // Binary Search timing
        Arrays.sort(whiteList);

        long t2 = System.currentTimeMillis();
        int countBS = binarySearch(whiteList, queries);
        long t3 = System.currentTimeMillis();

        StdOut.printf("Brute-force: found %d/%d in %d ms.%n", countBF, queries.length, (t1 - t0));
        StdOut.printf("Binary search: found %d/%d in %d ms.%n", countBS, queries.length, (t3 - t2));
    }
}

// Brute-force: found 9632034/10000000 in 1189119 ms.
// Binary search: found 9632034/10000000 in 920 ms.
