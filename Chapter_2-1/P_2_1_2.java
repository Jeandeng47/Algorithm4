import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_2 {

    // Perform selection sort and update exchange counts
    public static int selectionSort(int[] a, int[] exchCount) {
        int totalExch = 0;
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                if (a[j] < a[minIdx]) {
                    minIdx = j;
                }
            }
            if (i != minIdx) {
                int v1 = a[i], v2 = a[minIdx];
                exch(a, i, minIdx);
                exchCount[v1]++;
                exchCount[v2]++;
                totalExch++;
            }
        }
        return totalExch;
    }

    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

     private static void shuffle(int[] a) {
        Random rand = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            exch(a, i, j);
        }
    }

    public static void main(String[] args) {
        int n = 10;
        int trials = 10000;
        
        int[] totalExchCount = new int[n]; // totalExch[i]: total exchanges of element i of all trials

        for (int t = 0; t < trials; t++) {
            // 1. generate array 
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
            shuffle(a);

            // sort the array
            int[] exchCount = new int[n];  // exchCount[i]: exchanges of element i of this trial
            int exch = selectionSort(a, exchCount);
            
            // record culmulative exch
            for (int i = 0; i < n; i++) {
                totalExchCount[i] += exchCount[i];
            }
        
        }

        StdOut.println("Avg number of exchanges per element: ");
        double[] avgExch = new double[n];
        for (int i = 0; i < n; i++) {
            avgExch[i] = (double) totalExchCount[i] / trials;
            StdOut.printf("Element %2d: %4f\n", i, avgExch[i]);
        }
        
        StdOut.println("Avg number that an element is exchanged: ");
        StdOut.printf("Avg: %4f\n", Arrays.stream(avgExch).sum() / (double) n);

    }        
}


// Maximum exchange per element: N - 1

// Average exchange per element: 1 ~ 2

// Avg number of exchanges per element: 
// Element  0: 0.900400
// Element  1: 0.996800
// Element  2: 1.089000
// Element  3: 1.198700
// Element  4: 1.306600
// Element  5: 1.446900
// Element  6: 1.605600
// Element  7: 1.767300
// Element  8: 1.919700
// Element  9: 1.930200
// Avg number that an element is exchanged: 
// Avg: 1.416120

// Monotonic rise by rank:

// 1. Small elements: get pulled forward as the minimum of a suffix 
// and then stay in the sorted prefix, so they only rarely participate in a swap.

// 2. Large elements: whenever they happen to sit at the “front” of the unsorted region 
// they always get swapped out—and earlier swaps keep pushing them back into the 
// unsorted region, giving them more opportunities to move.