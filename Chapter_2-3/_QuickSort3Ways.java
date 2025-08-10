public class _QuickSort3Ways {
    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        Comparable v = a[lo];

        // Goal: make sure a[lo...lt-1] all < v, a[gt+1...hi] > v,
        // a[lt...i - 1] = v, a[i...gt] remain unsorted but the
        // range of gt - i will shrink and finally break the loop

        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0)        exch(a, lt++, i++); // a[lt] known to be == v, safe to increase i
            else if (cmp > 0)   exch(a, i, gt--);   // a[gt] unknown, could be >, <, == v, do not increase i
            else                i++;                // -> no exchange
        }

        // elements that equals to v would not participate in 
        // the recursive calls
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}