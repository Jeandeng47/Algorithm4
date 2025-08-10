import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_13 {
    private static int maxDepth;

    public static int quicksortDepth(Comparable[] a, boolean shuffle) {
        maxDepth = 0;
        int N = a.length;
        if (shuffle) {
            StdRandom.shuffle(a);
        }
        sort(a, 0, N - 1, 1);
        return maxDepth;
    }

    private static void sort(Comparable[] a, int lo, int hi, int depth) {
        if (lo >= hi) return;
        if (depth > maxDepth) maxDepth = depth;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1, depth + 1);
        sort(a, j + 1, hi, depth + 1);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    public static int quicksortDepthBest(Comparable[] a) {
        maxDepth = 0;
        int N = a.length;
        sortMedian(a, 0, N - 1, 1);
        return maxDepth;
    }

    private static void sortMedian(Comparable[] a, int lo, int hi, int depth) {
        if (hi <= lo) return;
        if (depth > maxDepth) maxDepth = depth;

        int midRank = lo + (hi - lo) / 2;   // rank of the median by value
        // Bring the median-by-value to position midRank
        selectByRank(a, lo, hi, midRank);
        // Make it the pivot at a[lo] 
        exch(a, lo, midRank);

        int j = partition(a, lo, hi); 
        sortMedian(a, lo, j - 1, depth + 1);
        sortMedian(a, j + 1, hi, depth + 1);
    }

    // bring the element of rank k (by value) into position k
    private static int selectByRank(Comparable[] a, int lo, int hi, int k) {
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) {
                lo = j + 1;   // kth in the right side 
            } else if (j > k) {
                hi = j - 1;   // kth in the left side
            } else {
                return j; // a[k] now holds the kth smallest
            }
        }
        return lo; // segemnt size 1
    }


    private static boolean less(Comparable v, Comparable w) { return v.compareTo(w) < 0; }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // Create base array
    private static Integer[] identity(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        return a;
    }

    
    private static double lg2(int n) { return Math.log(n) / Math.log(2.0); }

    private static void runBestWorst(int N) {
        Integer[] best = identity(N);
        Integer[] worst = identity(N);

        int dBest  = quicksortDepthBest(best.clone());
        int dWorst = quicksortDepth(worst.clone(), false); 

        StdOut.printf("N=%-7d  Best-case depth  = %d   (⌊log2 N⌋ ≈ %d)%n",
                N, dBest, (int)Math.floor(lg2(N)));
        StdOut.printf("N=%-7d  Worst-case depth = %d   (N-1 = %d)%n",
                N, dWorst, Math.max(0, N - 1));
    }

    private static void runAverage(int N, int trials, long seed) {
        StdRandom.setSeed(seed);
        long sum = 0;
        Integer[] base = identity(N);
        for (int t = 0; t < trials; t++) {
            Integer[] a = base.clone();
            int d = quicksortDepth(a, true); // shuffle = true
            sum += d; 
        }
        double mean = (double) sum / trials;

        StdOut.printf("N=%-7d  Average-case depth ≈ %.2f  (⌊log2 N⌋ ≈ %d)%n",
                N, mean, (int)Math.floor(lg2(N)));
    }

    public static void main(String[] args) {
        int[] Ns = {100, 200, 400, 800, 1600};  
        int trials = 5;
        long seed = 42L;

        for (int N : Ns) {
            StdOut.println("---- N = " + N + " ----");
            runBestWorst(N);
            runAverage(N, trials, seed);
            StdOut.println();
        }
        
    }
}


// ---- N = 100 ----
// N=100      Best-case depth  = 6   (≈ ⌊log2 N⌋ ≈ 6)
// N=100      Worst-case depth = 99   (≈ N-1 = 99)
// N=100      Average-case depth ≈ 12.40  (sd=1.74)  [trials=5]

// ---- N = 200 ----
// N=200      Best-case depth  = 7   (≈ ⌊log2 N⌋ ≈ 7)
// N=200      Worst-case depth = 199   (≈ N-1 = 199)
// N=200      Average-case depth ≈ 14.00  (sd=0.63)  [trials=5]

// ---- N = 400 ----
// N=400      Best-case depth  = 8   (≈ ⌊log2 N⌋ ≈ 8)
// N=400      Worst-case depth = 399   (≈ N-1 = 399)
// N=400      Average-case depth ≈ 18.20  (sd=1.17)  [trials=5]

// ---- N = 800 ----
// N=800      Best-case depth  = 9   (≈ ⌊log2 N⌋ ≈ 9)
// N=800      Worst-case depth = 799   (≈ N-1 = 799)
// N=800      Average-case depth ≈ 20.20  (sd=1.17)  [trials=5]

// ---- N = 1600 ----
// N=1600     Best-case depth  = 10   (≈ ⌊log2 N⌋ ≈ 10)
// N=1600     Worst-case depth = 1599   (≈ N-1 = 1599)
// N=1600     Average-case depth ≈ 23.20  (sd=1.47)  [trials=5]

// Recursive depth of quick sort
// Best case: O(log2N)
// Worst case: O(N)
// Average case: O(NlogN)