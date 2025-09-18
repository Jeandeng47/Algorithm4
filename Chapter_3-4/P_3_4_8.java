import edu.princeton.cs.algs4.StdOut;

public class P_3_4_8 {

    // Return the smallest prime greater or equal than
    // the number x
    private static int minPrime(int x) {
        while (!isPrime(x)) {
            x++;
        }
        return x;
    }

    // Check whether a number is prime
    private static boolean isPrime(int n) {
        if (n < 2) return false; // prime start from 2;
        if (n % 2 == 0 && n != 2) return false; // even number not prime
        for (int i = 3; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] Ns = {10, 100, 1000, 10000, 100000, 1000000};
        StdOut.printf("%-10s %-10s %-10s %-10s%n", "N", "M", "E_empty", "Empty / M");
        for (int N : Ns) {
            int M = minPrime(N / 5);
            double expEmpty = M * Math.pow(1.0 - 1.0 / M, N);
            double percent = expEmpty / (double) M;
            StdOut.printf("%-10d %-10d %-10.2f %-10.5f%n", N, M, expEmpty, percent);
        }
    }
}

// Exercise 2.5.31: The expected number of empty chains
// for hash table with separate chaining is :
// M * (1 - (1/M))^N


// N          M          E_empty    Empty / M 
// 10         2          0.00       0.00098   
// 100        23         0.27       0.01174   
// 1000       211        1.82       0.00865   
// 10000      2003       13.58      0.00678   
// 100000     20011      135.19     0.00676   
// 1000000    200003     1347.69    0.00674   