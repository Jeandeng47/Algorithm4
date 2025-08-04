import java.util.Random;
import edu.princeton.cs.algs4.StdOut;

public class P_2_2_13 {
    private static int compares;

    public static void mergeSort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        sort(a, aux, 0, N - 1);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy to aux
        for (int k = lo; k <= hi; k++) aux[k] = a[k];
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            }                 
            else if (j > hi) {
                a[k] = aux[i++];
            }           
            else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
                compares++;
            } else {
                a[k] = aux[i++];
                compares++;
            }                      
        }
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // ⌈log2(N!)⌉ ≈ ∑_{i=1}^N log2(i)
    public static double lowerBound(int N) {
        double sum = 0.0;
        for (int i = 1; i < N; i++) {
            sum += Math.log(i) / Math.log(2);
        }
        return sum;
    }

    private static void shuffle(Comparable[] a, Random r) {
        int N = a.length;
        for (int i = N - 1; i > 0; i--) {
            int j = r.nextInt(i + 1); // [0, i]
            exch(a, i, j);
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int trials = 10000;
        Random r = new Random(42);

        // generate array
        Integer[] base = new Integer[N];
        for (int i = 0; i < N; i++) base[i] = i;

        int totalComp = 0;
        Integer[] a = new Integer[N];
        for (int t = 0; t < trials; t++) {
            // copy base array and shuffle
            for (int i = 0; i < N; i++) a[i] = base[i];
            shuffle(a, r);

            // reset cmp and sort
            compares = 0; 
            mergeSort(a);
            totalComp += compares;
        }

        double avgComps = (double) totalComp / trials;
        double lb       = lowerBound(N);
        double nlogn    = N * (Math.log(N)/Math.log(2));

        StdOut.printf("N = %d, trails = %d%n", N, trials);
        StdOut.printf("Avg compares (merge)     = %.2f%n", avgComps);
        StdOut.printf("Info-theoretic log2(N!)  = %.2f%n", lb);
        StdOut.printf("N·log2(N)                = %.2f%n", nlogn);
    }
    
}


// N = 100, trails = 10000
// Avg compares (merge)     = 541.90
// Info-theoretic log2(N!)  = 518.12
// N·log2(N)                = 664.39

// The lower bound is Log2(N!), no comparison-based sort could beat that average

