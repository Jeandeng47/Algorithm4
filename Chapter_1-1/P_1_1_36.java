import java.util.Random;

import Util.ArrayGenerator;

// To run this program:
// make run ARGS="10 1000"

public class P_1_1_36 {

    private static Random rand = new Random();

    private static void initArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
    }

    private static void shuffle(int[] a) {
        int M = a.length;
        for (int i = 0; i < M; i++) {
            int r = i + rand.nextInt(M - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    private static void record(int[] a, int[][] counts) {
        // trials        |  1  |  2  |  3  |          
        // i = 0 => a[0] |  5  |  3  |  4  |  ...   
        // i = 1 => a[1] |  .  |  .  |  .  |  ...
        // For 5, we increase counts[5][0] by 1 ...  
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
            return;
        }
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int[][] counts = new int[M][M];
        
        int[] arr = ArrayGenerator.getAscIntArr(0, M);
        
        for (int t = 0; t < N; t++) {
            // Init
            initArray(arr);
            
            // Shuffle
            shuffle(arr);
            
            // Record position
            record(arr, counts);
        }

        printResult(counts);
    }
}

    
// Example result: M = 10, N = 1000, all entries closes to N / M = 100
    //            0       1       2       3       4       5       6       7       8       9
    //    0     120     114     104     107      81      90      84      86     102     112
    //    1     101      85      94      73     118     105     119     108     102      95
    //    2     109      88     117      83      85     108      79     119     112     100
    //    3     100     105      93     110      98     109      89     106      88     102
    //    4      98     114      87     100     105      90     113     100     102      91
    //    5      96     104      99     107      87     104      96     102      94     111
    //    6      99     101     111     107     120     110      82      92      81      97
    //    7      80      96     102     104     113      86     125     104     104      86
    //    8      93      97     101     107     102      90     114      91     106      99
    //    9     104      96      92     102      91     108      99      92     109     107



