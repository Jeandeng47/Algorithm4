import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_12 {
    
    private static long compares;

    private static boolean less(Comparable v, Comparable w) {
        compares++;
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static int[] makeIncrements(int N) {
        int h = 1;
        int cnt = 0;
        while (h < N / 3) {
            h = 3 * h + 1;
            cnt++;
        }
        int[] inc = new int[cnt + 1];
        for (int i = 0; i <= cnt; i++) {
            inc[i] = h;               
            h = (h - 1) / 3;          
        }
        return inc;

    }

    public static void shellSort(Comparable[] a) {
        int N = a.length;
        int[] hs = makeIncrements(N);

        // reset compares for each h
        compares = 0;

        for (int h : hs) {
            long before = compares;

            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }

            long thisPass = compares - before;
            StdOut.printf("  h=%5d  compares/N = %7.3f%n", 
                          h, (double)thisPass / N);

        }
    }
    public static void main(String[] args) {
        Random rand = new Random(42);
        for (int n = 100; n < 1_000_000; n *= 10) {
            Double[] a = new Double[n];
            for (int i = 0; i < n; i++) {
                a[i] = rand.nextDouble();
            }

            StdOut.printf("============ N = %d ============%n", n);
            shellSort(a);
            StdOut.println();
        }     
    }
}

// ============ N = 100 ============
//   h=   40  compares/N =   0.730
//   h=   13  compares/N =   1.590
//   h=    4  compares/N =   2.320
//   h=    1  compares/N =   2.220

// ============ N = 1000 ============
//   h=  364  compares/N =   0.832
//   h=  121  compares/N =   1.654
//   h=   40  compares/N =   2.291
//   h=   13  compares/N =   2.678
//   h=    4  compares/N =   3.569
//   h=    1  compares/N =   2.834

// ============ N = 10000 ============
//   h= 9841  compares/N =   0.016
//   h= 3280  compares/N =   0.898
//   h= 1093  compares/N =   1.724
//   h=  364  compares/N =   2.347
//   h=  121  compares/N =   3.073
//   h=   40  compares/N =   3.969
//   h=   13  compares/N =   5.492
//   h=    4  compares/N =   4.403
//   h=    1  compares/N =   2.736

// ============ N = 100000 ============
//   h=88573  compares/N =   0.114
//   h=29524  compares/N =   0.978
//   h= 9841  compares/N =   1.760
//   h= 3280  compares/N =   2.416
//   h= 1093  compares/N =   2.984
//   h=  364  compares/N =   3.685
//   h=  121  compares/N =   4.596
//   h=   40  compares/N =   7.234
//   h=   13  compares/N =   8.371
//   h=    4  compares/N =   4.610
//   h=    1  compares/N =   2.749


// - The interleaved subsequences has length L ~ N / h
// - On each subsequence, we need to do insertion sort 
// -    costs = h * O(L^2) = h * N^2/h^2 = N^2 / h
// -    costs / N = N / h
// - With h decreases, the 

// 1. Large h: Touch the last few elements (i >= h),
// compares are very small relative to size

// 2. Mid range h: The subsequences have many inversions,
// each round we have cost ~O(N / h), as h decreases,
// the costs will increases

// 3. Small h: The array is almost sorted, each insertion do very
// little insertion and the constant drops