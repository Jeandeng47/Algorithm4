import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_9 {
    // ---- 2-way Quick sort ----
    private static int cmp2;

    private static int quick2Count(Comparable[] a) {
        cmp2 = 0;
        StdRandom.shuffle(a);               
        sort2(a, 0, a.length - 1);
        return cmp2;
    }

    private static void sort2(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition2(a, lo, hi);
        sort2(a, lo, j - 1);
        sort2(a, j + 1, hi);
    }

    private static int partition2(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less2(a[++i], v)) if (i == hi) break;
            while (less2(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less2(Comparable v, Comparable w) {
        cmp2++;
        return v.compareTo(w) < 0;
    }

    // ---- 3-way quicksort ----
    private static long cmp3;

    private static long quick3Count(Comparable[] a) {
        cmp3 = 0;
        StdRandom.shuffle(a);               
        sort3(a, 0, a.length - 1);
        return cmp3;
    }

    private static void sort3(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int c = cmp3(a[i], v);
            if (c < 0)      exch(a, lt++, i++);
            else if (c > 0) exch(a, i, gt--);  
            else            i++;
        }
        sort3(a, lo, lt - 1);
        sort3(a, gt + 1, hi);
    }

    private static int cmp3(Comparable x, Comparable y) {
        cmp3++;
        return x.compareTo(y);
    }

    // ---- Generators: exactly 2 or 3 distinct keys ----
    private static Integer[] twoKeys(int N, int countA) {
        // values 0 and 1; exactly countA zeros and (N-countA) ones
        Integer[] a = new Integer[N];
        int k = 0;
        for (int i = 0; i < countA; i++) a[k++] = 0;
        for (int i = countA; i < N; i++) a[k++] = 1;
        StdRandom.shuffle(a);
        return a;
    }

    private static Integer[] threeKeys(int N, int countA, int countB) {
        // values 0,1,2; counts are (countA, countB, N-countA-countB)
        Integer[] a = new Integer[N];
        int k = 0;
        for (int i = 0; i < countA; i++) a[k++] = 0;
        for (int i = 0; i < countB; i++) a[k++] = 1;
        while (k < N) a[k++] = 2;
        StdRandom.shuffle(a);
        return a;
    }


    private static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    private static double log2(double x) { return Math.log(x) / Math.log(2.0); }

    private static void run(String name, int N, int trials, java.util.function.Supplier<Integer[]> gen) {
        long sum2 = 0, sum3 = 0;
        for (int t = 0; t < trials; t++) {
            Integer[] a2 = gen.get();
            Integer[] a3 = a2.clone();  // identical multiset each trial
            sum2 += quick2Count(a2);
            sum3 += quick3Count(a3);
        }
        double mean2 = (double) sum2 / trials;
        double mean3 = (double) sum3 / trials;

        double base2 = N * log2(N);   // ~ N log2 N for 2-way with many duplicates
        double base3 = Math.max(0, N - 1); // ~ N for 3-way

        StdOut.printf("[%s] N=%d, trials=%d%n", name, N, trials);
        StdOut.printf("  2-way compares avg = %.1f, NlgN = %.1f, ratio=%.3f%n",
                mean2, base2, mean2 / base2);
        StdOut.printf("  3-way  compares avg = %.1f, N = %d, ratio=%.3f%n%n",
                mean3, (int) base3, mean3 / base3);
    }

    public static void main(String[] args) {
        int trials = 50; // bump up if you want tighter averages

        int[] Ns = {10_000, 100_000};
        for (int N : Ns) {
            // Two keys: balanced and skewed
            run("2 keys, p=0.5", N, trials, () -> twoKeys(N, N/2));
            run("2 keys, p=0.9", N, trials, () -> twoKeys(N, (int)(0.9 * N)));

            // Three keys: uniform and skewed
            run("3 keys, uniform", N, trials, () -> threeKeys(N, N/3, N/3));
            run("3 keys, skewed (0.8,0.1,0.1)", N, trials, () -> threeKeys(N, (int)(0.8*N), (int)(0.1*N)));
        }
    }
}


// [2 keys, p=0.5] N=10000, trials=50
//   2-way compares avg = 124254.5, NlgN = 132877.1, ratio=0.935
//   3-way  compares avg = 14998.0, N = 9999, ratio=1.500

// [2 keys, p=0.9] N=10000, trials=50
//   2-way compares avg = 122967.5, NlgN = 132877.1, ratio=0.925
//   3-way  compares avg = 11638.0, N = 9999, ratio=1.164

// [3 keys, uniform] N=10000, trials=50
//   2-way compares avg = 125807.6, NlgN = 132877.1, ratio=0.947
//   3-way  compares avg = 19063.6, N = 9999, ratio=1.907

// [3 keys, skewed (0.8,0.1,0.1)] N=10000, trials=50
//   2-way compares avg = 124416.6, NlgN = 132877.1, ratio=0.936
//   3-way  compares avg = 14537.0, N = 9999, ratio=1.454

// [2 keys, p=0.5] N=100000, trials=50
//   2-way compares avg = 1574361.1, NlgN = 1660964.0, ratio=0.948
//   3-way  compares avg = 149998.0, N = 99999, ratio=1.500

// [2 keys, p=0.9] N=100000, trials=50
//   2-way compares avg = 1574536.0, NlgN = 1660964.0, ratio=0.948
//   3-way  compares avg = 114798.0, N = 99999, ratio=1.148

// [3 keys, uniform] N=100000, trials=50
//   2-way compares avg = 1599449.8, NlgN = 1660964.0, ratio=0.963
//   3-way  compares avg = 188663.7, N = 99999, ratio=1.887

// [3 keys, skewed (0.8,0.1,0.1)] N=100000, trials=50
//   2-way compares avg = 1585025.5, NlgN = 1660964.0, ratio=0.954
//   3-way  compares avg = 147597.0, N = 99999, ratio=1.476

// 2 way 
// 2 - 3 distinct keys: close to O(NlogN)

// 3 way:
// 2 distinct keys: close to O(N)
// -- One partition pass compares each element once 
// -- After the first part, each side is homogeneous
// -- Solve in linear time
// 3 distinct keys: O(N), with larger coefficient
// -- First pass group all copies of the pivot
// -- If piviot is an extreme key (max, min), one side is empty
// -- the other side has 2 keys
// -- If piviot is the middle key, both sides are homo, each cost O(N)

// With more duplicates, 3-way quick-sort acts better. 
// It could bring performance from O(NlgN) to O(N)
