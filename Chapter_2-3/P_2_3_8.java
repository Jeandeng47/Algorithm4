import edu.princeton.cs.algs4.StdOut;

public class P_2_3_8 {
    private static int cmp2way;
    private static int cmp3way;

    // QUICK-SORT 2 WAY
    public static int getCmp2way() {
        return cmp2way;
    }

    public static void quickSort2way(Comparable[] a) {
        cmp2way = 0; // reset
        int N = a.length;
        sort2(a, 0, N - 1);
    }

    public static void sort2(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        
        int j = partition(a, lo, hi);
        sort2(a, lo, j - 1);
        sort2(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
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
        cmp2way++;
        return v.compareTo(w) < 0;
    }

    // QUICK-SORT 3 WAY

    public static int getCmp3way() {
        return cmp3way;
    }

    public static void quickSort3way(Comparable[] a) {
        cmp3way = 0; // reset
        int N = a.length;
        sort3(a, 0, N - 1);
    }

    private static void sort3(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        Comparable v = a[lo];

        while (i <= gt) {
            int c = cmp3(a[i], v);
            if (c < 0) exch(a, i++, lt++); // a[i] < v, put to left
            else if (c > 0) exch(a, i, gt--); // a[i] > v, put it to right
            else i++;
        }
        sort3(a, lo, lt - 1);
        sort3(a, gt + 1, hi);
    }

    private static int cmp3(Comparable a, Comparable b) {
        cmp3way++;
        return a.compareTo(b);
    }

    // Helper functions

    private static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }

    private static double log2(double x) { return Math.log(x) / Math.log(2.0); }

    private static Integer[] getEqualArr(int n, int val) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = val;
        return a;
    }


    private static void run(int N) {
        Integer[] a1 = getEqualArr(N, 7);
        Integer[] a2 = a1.clone();

        quickSort2way(a1);
        int c2 = getCmp2way();

        
        quickSort3way(a2);
        int c3 = getCmp3way();

        double baseline = N * log2(N);   // ~ N log2 N
        StdOut.printf("N = %d%n", N);
        StdOut.printf("Quick.sort compares=%d, NlgN=%.1f, ratio=%.3f%n", c2, baseline, c2 / baseline);
        StdOut.printf("Quick3way compares=%d, N-1=%d, ratio=%.3f%n%n",
                c3, (N > 0 ? N - 1 : 0), (N > 0 ? (double)c3 / (N - 1) : 0.0));
    }


    public static void main(String[] args) {
        int[] Ns = {100, 1_000, 10_000, 100_000};
        for (int N : Ns) run(N);
    }
}

// N = 100
// Quick.sort compares=564, NlgN=664.4, ratio=0.849
// Quick3way compares=99, N-1=99, ratio=1.000

// N = 1000
// Quick.sort compares=8898, NlgN=9965.8, ratio=0.893
// Quick3way compares=999, N-1=999, ratio=1.000

// N = 10000
// Quick.sort compares=121034, NlgN=132877.1, ratio=0.911
// Quick3way compares=9999, N-1=9999, ratio=1.000

// N = 100000
// Quick.sort compares=1561130, NlgN=1660964.0, ratio=0.940
// Quick3way compares=99999, N-1=99999, ratio=1.000

// With array of all-equal elements, time complexity is O(NlgN)