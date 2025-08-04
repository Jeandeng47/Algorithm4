import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_16 {
    private static <T extends Comparable<? super T>> void mergeSortNatural(T[] a) {
        int N = a.length;
        Queue<T[]> runs = new LinkedList<>();

        // 1. Find runs in the original array, add to a queue
        int i = 0;
        while (i < N) {
            // keep increasing i if the array is non-decreasing
            int start = i++;
            while (i < N && a[i-1].compareTo(a[i]) <= 0) {
                i++;
            }
            runs.offer(Arrays.copyOfRange(a, start, i));
        }

        // 2.Merge all the queues in the queue
        while (runs.size() > 1) {
            T[] left = runs.poll();
            T[] right = runs.poll();
            T[] merged = merge(left, right);
            runs.offer(merged);
        }

        // Copy the result back to original array
        T[] result = runs.poll();
        System.arraycopy(result, 0, a, 0, N);
    }

    private static <T extends Comparable<? super T>> T[] merge(T[] left, T[] right) {
        int lenL = left.length;
        int lenR = right.length;
        T[] result = (T[]) new Comparable[lenL + lenR];
        
        int i = 0, j = 0, k = 0;
        while (i < lenL && j < lenR) {
            if (left[i].compareTo(right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < lenL) result[k++] = left[i++];
        while (j < lenR) result[k++] = right[j++];
        return result;
    }

    // Generate a completely random array of size N
    public static Integer[] randArray(int N, Random r) {
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++) a[i] = r.nextInt();
        return a;
    }

    // Generate an array of size N, with M sorted runs
    public static Integer[] sortedRunsArray(int N, int M, Random r) {

        Integer[] a = new Integer[N];

        int baseRun = N / M;
        int rem = N % M;
        int idx = 0;

        // make M runs
        for (int run = 0; run < M; run++) {
            int size = baseRun + (run < rem? 1 : 0);
            // Get a sorted subarray 
            Integer[] tmp = new Integer[size];
            for (int i = 0; i < size; i++) tmp[i] = r.nextInt();
            Arrays.sort(tmp);
            // copy the sorted subarray back to a
            System.arraycopy(tmp, 0, a, idx, size);
            idx += size;
        }
        return a;
    }

    // Time one sort (in nanoseconds)
    public static long timeSort(Integer[] a) {
        long start = System.nanoTime();
        mergeSortNatural(a);
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        final int TRIALS = 5;
        Random r = new Random(42);

        int[] sizes = {10_000, 20_000, 50_000, 100_000, 200_000, 500_000};
        // 1) Random arrays: Time vs N
        StdOut.println("== Random arrays: Time vs N ==");
        StdOut.printf("%-8s\t%-15s\t%-15s\t%-15s%n",
                    "N", "AvgTime_ns", "N·log2(N)", "Time/(N·log2(N))");
        for (int N : sizes) {
            long sum = 0;
            for (int t = 0; t < TRIALS; t++) {
                Integer[] a = randArray(N, r);
                sum += timeSort(a);
            }
            double avg   = sum / (double)TRIALS;
            double nLogN = N * (Math.log(N)/Math.log(2));
            double ratio = avg / nLogN;
            StdOut.printf("%-8d\t%-,15.0f\t%-,15.1f\t%-,15.5f%n",
                        N, avg, nLogN, ratio);
        }

        StdOut.println();

        // 2) Time vs M (run count) for fixed N
        final int FIXED_N = 100_000;
        int[] Ms = {10, 20, 50, 100, 200, 500, 1_000};
        StdOut.println("== Fixed N=" + FIXED_N + ": Time vs M (runs) ==");
        StdOut.printf("%-8s\t%-15s\t%-15s\t%-15s%n",
                    "M", "AvgTime_ns", "N·log2(M)", "Time/(N·log2(M))");
        for (int M : Ms) {
            if (M > FIXED_N) break;
            long sum = 0;
            for (int t = 0; t < TRIALS; t++) {
                Integer[] a = sortedRunsArray(FIXED_N, M, r);
                sum += timeSort(a);
            }
            double avg  = sum / (double)TRIALS;
            double nLogM = FIXED_N * (Math.log(M)/Math.log(2));
            double ratio = avg / nLogM;
            StdOut.printf("%-8d\t%-,15.0f\t%-,15.1f\t%-,15.5f%n",
                        M, avg, nLogM, ratio);
        }
    }
}

// Idea of natural mergesort

// 1. Use exsiting runs
// -- (1) raw data usually contains some sorted subarrays
// -- (2) instead of always splitting array into size 1 in 
// -- (3) bottom-up merge sort, we could utilize these runs

// 2. Queue of runs
// -- (1) Scan once, find max ascending run and construct a queue
// -- (2) Enqueue these sequences into a queue
// -- (3) Continue to merge the queue until only one queue left
// -- (4) The reuslt queue is sorted

// 3. Running time
// -- (1) Scan: scan through the array, compare a[i-1] and a[i], cost O(N)
// -- (2) Merge: every round we merge 2 runs into 1 run, forming a binary
// -- tree of height log2(M), we need log2M merges. Within each merge, we
// -- handle N elments. The total cost of merge() is O(Nlog2(M))

// -- Round 1: merge M0 and M1, M0-1 and M2.. until M/2 runs are left,
// -- we handle total N elements in this round
// -- Round 2: merge the left M/2 runs, handle total N elements
// -- ....
// -- Round log2(M): only 1 run is left, handle total N elements

// == Random arrays: Time vs N ==
// N               AvgTime_ns      N·log2(N)       Time/(N·log2(N))
// 10000           2,541,308       132,877.1       19.12525       
// 20000           2,375,992       285,754.2       8.31481        
// 50000           6,339,650       780,482.0       8.12274        
// 100000          13,823,883      1,660,964.0     8.32281        
// 200000          28,630,675      3,521,928.1     8.12926        
// 500000          78,902,808      9,465,784.3     8.33558        

// == Fixed N=100000: Time vs M (runs) ==
// M               AvgTime_ns      N·log2(M)       Time/(N·log2(M))
// 10              2,811,725       332,192.8       8.46414        
// 20              3,479,100       432,192.8       8.04988        
// 50              4,300,025       564,385.6       7.61895        
// 100             4,988,559       664,385.6       7.50853        
// 200             5,475,425       764,385.6       7.16317        
// 500             6,466,458       896,578.4       7.21237        
// 1000            6,995,500       996,578.4       7.01952  