import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_11 {

    private static long compares;

    private static long quickSortBad(Comparable[] a) {
        compares = 0;
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        return compares;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            // keep going if a[i] <= v
            while (i < hi && leq(a[++i], v));
            // keep going if a[j] >= v
            while (j > lo && leq(v, a[--j]));
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean leq(Comparable x, Comparable y) {
        compares++;
        return x.compareTo(y) <= 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // Helper functions to create array with constant number
    // of distinct keys
    private static Integer[] twoKeys(int N, double p) {
        int cnt0 = (int) Math.round(p * N);
        Integer[] a = new Integer[N];
        int k = 0;
        for (int i = 0; i < cnt0; i++) a[k++] = 0;
        while (k < N) a[k++] = 1;
        StdRandom.shuffle(a);
        return a;
    }

    private static Integer[] threeKeys(int N, double p0, double p1) {
        int c0 = (int) Math.round(p0 * N);
        int c1 = (int) Math.round(p1 * N);
        Integer[] a = new Integer[N];
        int k = 0;
        for (int i = 0; i < c0; i++) a[k++] = 0;
        for (int i = 0; i < c1; i++) a[k++] = 1;
        while (k < N) a[k++] = 2;
        StdRandom.shuffle(a);
        return a;
    }

    private static void run(String label, int N, int trials, java.util.function.Supplier<Integer[]> gen) {
        long sum = 0;
        for (int t = 0; t < trials; t++) {
            Integer[] a = gen.get();
            sum += quickSortBad(a);
        }
        double mean = (double) sum / trials;
        double ratio = mean / ((double) N * N);
        StdOut.printf("%-28s N=%7d  compares(avg)=%12.0f   compares/N^2=%.4f%n",
                label, N, mean, ratio);   
    }

    public static void main(String[] args) {
        int trials = 5; 

        int[] Ns = {100, 200, 400, 800, 1600};
        for (int N : Ns) {
            run("Two keys p=0.5", N, trials, () -> twoKeys(N, 0.5));
            run("Two keys p=0.9", N, trials, () -> twoKeys(N, 0.9));
            run("Three keys uniform", N, trials, () -> threeKeys(N, 1.0/3, 1.0/3));
            run("Three keys skewed", N, trials, () -> threeKeys(N, 0.8, 0.1));
            StdOut.println();
        }
    }
}



// If we scan over items with keys equal to the partitioning item instead
// of stopping in the original quick-sort, with duplicate keys (only 2-3
// distinct keys), the number of compares is is quadratic to input size N.


// Two keys p=0.5               N=    100  compares(avg)=        4036   compares/N^2=0.4036
// Two keys p=0.9               N=    100  compares(avg)=        5705   compares/N^2=0.5705
// Three keys uniform           N=    100  compares(avg)=        2180   compares/N^2=0.2180
// Three keys skewed            N=    100  compares(avg)=        4747   compares/N^2=0.4747

// Two keys p=0.5               N=    200  compares(avg)=       15767   compares/N^2=0.3942
// Two keys p=0.9               N=    200  compares(avg)=       21432   compares/N^2=0.5358
// Three keys uniform           N=    200  compares(avg)=        8036   compares/N^2=0.2009
// Three keys skewed            N=    200  compares(avg)=       18033   compares/N^2=0.4508

// Two keys p=0.5               N=    400  compares(avg)=       60310   compares/N^2=0.3769
// Two keys p=0.9               N=    400  compares(avg)=       83244   compares/N^2=0.5203
// Three keys uniform           N=    400  compares(avg)=       31038   compares/N^2=0.1940
// Three keys skewed            N=    400  compares(avg)=       67567   compares/N^2=0.4223

// Two keys p=0.5               N=    800  compares(avg)=      245127   compares/N^2=0.3830
// Two keys p=0.9               N=    800  compares(avg)=      324251   compares/N^2=0.5066
// Three keys uniform           N=    800  compares(avg)=      120057   compares/N^2=0.1876
// Three keys skewed            N=    800  compares(avg)=      261784   compares/N^2=0.4090

// Two keys p=0.5               N=   1600  compares(avg)=      959999   compares/N^2=0.3750
// Two keys p=0.9               N=   1600  compares(avg)=     1280990   compares/N^2=0.5004
// Three keys uniform           N=   1600  compares(avg)=      476253   compares/N^2=0.1860
// Three keys skewed            N=   1600  compares(avg)=     1020615   compares/N^2=0.3987


// Note:
// When N is large, this may bad partition may cause stack overflow
// a = [x, x, x, x, x], pivot = a[lo] = x

// while (i < hi && a[++i] <= v)  -> left scan, runs to i == hi
// while (j > lo && v <= a[--j])  -> right scan, runs to j == lo
// break at i >= j, final swap exch(a, lo, j) does nothing
// partition return j == lo
// call sort(a, lo, j - 1) = sort(a, lo, lo - 1) -> empty
// call sort(a, j + 1, hi) = sort(a, lo + 1, hi)

// sort(a, 0, 4) -> j = 0
// sort(a, 1, 4) -> j = 1
// sort(a, 2, 4) -> j = 2
// sort(a, 3, 4) -> j = 3
// Depth = N -> large N make stack explode!