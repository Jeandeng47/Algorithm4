import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_22 {

    public static void threeWayMergeSort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        sort(a, aux, 0, N - 1);
    }

    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;

        // divide the array into 3 parts
        // a = [ lo...m1 | m1+1...m2 | m2+1...hi]
        int len = hi - lo + 1;
        int third = len / 3;
        int mid1 = lo + third - 1;
        int mid2 = lo + 2 * third - 1;

        // handle special case where len < 3
        if (mid1 < lo) mid1 = lo;
        if (mid2 < mid1 + 1) mid2 = mid1;

        // sort three parts
        sort(a, aux, lo, mid1);
        sort(a, aux, mid1 + 1, mid2);
        sort(a, aux, mid2 + 1, hi);

        // merge three sorted runs
        merge(a, aux, lo, mid1, mid2, hi);
    }

    public static void merge(Comparable[] a, Comparable[] aux, int lo,
                            int mid1, int mid2, int hi) {
        // copy to aux
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // compare all three lists and take the min
        int i = lo;
        int j = mid1 + 1;
        int k = mid2 + 1;
        int p = lo;
        while (i <= mid1 && j <= mid2 && k <= hi) {
            Comparable vi = aux[i];
            Comparable vj = aux[j];
            Comparable vk = aux[k];
            
            if (minOfThree(vi, vj, vk)) { // min = vi
                a[p++] = vi;
                i++;
            } else if (minOfThree(vj, vi, vk)) { // min = vj
                a[p++] = vj;
                j++;
            } else {
                a[p++] = vk;
                k++;
            }
        }

        // merge any two remaining runs
        while (i <= mid1 && j <= mid2) { 
            a[p++] = (lessEqual(aux[i], aux[j])? aux[i++] : aux[j++]);
        }
        while (i <= mid1 && k <= hi) {
            a[p++] = (lessEqual(aux[i], aux[k])? aux[i++] : aux[k++]);
        }
        while (j <= mid2 && k <= hi) {
            a[p++] = (lessEqual(aux[j], aux[k])? aux[j++] : aux[k++]);
        }

        // merge any remaining run (only one run has remainings)
        while (i <= mid1) a[p++] = aux[i++];
        while (j <= mid2) a[p++] = aux[j++];
        while (k <= hi)   a[p++] = aux[k++];
    }

    private static boolean minOfThree(Comparable a, Comparable b, Comparable c) {
        return a.compareTo(c) <= 0 && a.compareTo(b) <= 0;
    }

    private static boolean lessEqual(Comparable a, Comparable b) {
        return a.compareTo(b) <= 0;    
    }

    public static Integer[] getRandIntArr(int lo, int hi) {
        if (lo >= hi) {
            throw new IllegalArgumentException("lo must be less than hi");
        }

        Random random = new Random();
        Integer[] arr = new Integer[hi - lo];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lo + random.nextInt(hi - lo);
        }
        return arr;
    }

    public static void main(String[] args) {
        // verify threeWayMergeSort() works correctly
        int n = 20;
        Integer[] test1 = getRandIntArr(0, n);
        Integer[] test2 = Arrays.copyOf(test1, test1.length);

        Arrays.sort(test1);
        StdOut.println("Array sorted: " + Arrays.toString(test1));
        threeWayMergeSort(test2);
        StdOut.println("3-way sorted: " + Arrays.toString(test2));


        // relationship between size and time: O(Nlog3(N))
        int[] sizes = {100_000, 200_000, 300_000, 400_000, 500_000, 
            600_000, 700_000, 800_000, 900_000, 1_000_000};
        int trials = 5;
        Random rnd = new Random(42);

        StdOut.printf("%-10s %15s %15s %15s%n",
    "N", "AvgTime_ns", "N·log3(N)", "Time/N·log3(N)");

        for (int N : sizes) {
            long sum = 0;
            for (int t = 0; t < trials; t++) {
                Integer[] a = new Integer[N];
                for (int i = 0; i < N; i++) a[i] = rnd.nextInt();
                long start = System.nanoTime();
                threeWayMergeSort(a);
                sum += System.nanoTime() - start;
            }
            double avg    = sum / (double) trials;
            double nLogN  = N * (Math.log(N) / Math.log(3));
            double ratio  = avg / nLogN;
            StdOut.printf("%-10d %15.0f %15.1f %15.5f%n",N, avg, nLogN, ratio);
        }
    }
}

// Divide logic
// Ex1: N = 10, third = 3, mid1 = 0+3-1 = 2, mid2 = 0+6-1 = 5
// a = [0 1 2 | 3 4 5 | 6 7 8 9]

// Ex2: N = 2, third = 0
// mid1 = lo - 1 < lo => mid1 = lo = 0 
// mid2 = lo - 1 < mid1 + 1 => mid2 = mid1 = 0
// a = [ 0 |   | 1 ] -> no element between [mid1 + 1, mid2]

// Ex3: N = 3, third = 1, mid1 = 0+1-1 = 0, mid2 = 0+2-1=1
// a = [ 0 | 1 | 2 ] -> each part has only one element

// Time complexity: O(NlogN)
// 1. Divide: 3 chunks, each of size N/3, cost T(N/3)
// 2. Conquer: merge the 3 sorted array back, O(N) for each level
// 3. Levels: reduce the problem size by 3 each time, log3N levels
// 4. Total: O(N) * O(log3N) = O(N) * O(log3N)


// Example output:

// Array sorted: [1, 2, 2, 3, 4, 5, 5, 7, 9, 10, 12, 12, 13, 13, 13, 15, 15, 15, 17, 19]
// 3-way sorted: [1, 2, 2, 3, 4, 5, 5, 7, 9, 10, 12, 12, 13, 13, 13, 15, 15, 15, 17, 19]
// N               AvgTime_ns       N·log3(N)  Time/N·log3(N)
// 100000            50877492       1047951.6        48.54947
// 200000            24987100       2222089.2        11.24487
// 300000            31116917       3443854.9         9.03549
// 400000            44110875       4696550.4         9.39219
// 500000            56261875       5972244.9         9.42056
// 600000            96749800       7266267.7        13.31492
// 700000           121644667       8575532.1        14.18509
// 800000           138178650       9897844.5        13.96048
// 900000           155553850      11231564.7        13.84970
// 1000000          173425267      12575419.6        13.79081