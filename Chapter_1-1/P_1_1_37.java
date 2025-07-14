import java.util.Random;

import Util.ArrayGenerator;

// To run this program:
// make run ARGS="10 1000"

public class P_1_1_37 {
     private static Random rand = new Random();

    private static void initArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
    }

    private static void badShuffle(int[] a) {
        int M = a.length;
        for (int i = 0; i < M; i++) {
            int r = rand.nextInt(M); // pick from [0...M-1]
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    private static void record(int[] a, int[][] counts) {
        for (int i = 0; i < a.length; i++) {
            counts[a[i]][i]++;
        }
    }

    private static void printResult(int[][] counts) {

        // Header
        int M = counts.length;
        System.out.printf("%8s", " ");
        for (int i = 0; i < M; i++) {
            System.out.printf("%8d", i);
        }
        System.out.println();

        // Each row
        for (int i = 0; i < M; i++) {
            System.out.printf("%8d", i);
            for (int j = 0; j < M; j++) {
                System.out.printf("%8d", counts[i][j]);
            }
            System.out.println();
        }
        
    }


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Provide 2 integers: M <size of array>, N <shuffle times>");
        }
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int[][] counts = new int[M][M];
        
        int[] arr = ArrayGenerator.getAscIntArr(0, M);
        
        for (int t = 0; t < N; t++) {
            // Init
            initArray(arr);

            // Shuffle
            badShuffle(arr);
            
            // Record position
            record(arr, counts);
        }

        printResult(counts);
    }
    
}



// Good shuffling: Picking from [i…M–1] ensures each of the M! orderings arises 
// Bad shuffling: Picking from [0…M–1] at every step makes elements originally near the front 
// get swapped many more times than those near the end, making some final positions more likely than others.