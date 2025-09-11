import edu.princeton.cs.algs4.StdOut;

public class P_3_1_20 {
    // simulate rank
    private static int rankCnt(int[] a, int n, int key) {
        int lo = 0, hi = n - 1, cmpCnt = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = Integer.compare(key, a[mid]);
            cmpCnt++;

            if (cmp < 0) { hi = mid - 1; }
            else if (cmp > 0) { lo = mid + 1; }
            else return cmpCnt;
        }
        return cmpCnt;
    }

    private static int worstCmp(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = 2 * i; // all evens

        int c = 0;
        // existing keys
        for (int i = 0; i < n; i++) {
            c = Math.max(c, rankCnt(a, n, a[i])); 
        }

        // search all keys in-between
        c = Math.max(c,rankCnt(a, n, -1)); // below min
        for (int i = 0; i < n - 1; i++) {
            int gap = a[i] + 1;                         // between a[i] and a[i+1]
            c = Math.max(c, rankCnt(a, n, gap));
        }
        c = Math.max(c,rankCnt(a, n, 2*n + 1)); // above max
        return c;
    }

    private static int bound(int n) {
        int k = 0, x = n;
        while (x > 1) {
            x >>= 1; // divided by 2
            k++;
        }
        return k + 1;
    }

    public static void main(String[] args) {
        int prev = -1;
        boolean allMono = true;
        boolean allBound = true;
        StdOut.printf("%5s %5s %8s %8s %8s%n", "N", "C(N)", "log2+1", "mono?", "bounded?");

        for (int N = 1; N < 256; N *= 2) {
            int c = worstCmp(N);
            int b = bound(N);
            boolean isMono = (prev <= c) || (prev == -1);
            boolean isbound = c <= b;
            allMono &= isMono;
            allBound &= isbound;
            
            StdOut.printf("%5d %5d %8d %8s %8s%n",
                    N, c, b, isMono ? "T" : "F", isbound? "T" : "F");
            prev = c;
        }

        StdOut.println();
        StdOut.println("Monotonic C(N): " + (allMono ? "verified" : "FAILED"));
        StdOut.println("Bound C(N) <= floor(log2 N)+1: " + (allBound ? "verified" : "FAILED"));
    }
}

// Proposition B: binary search in an ordered array with N keys uses no
// more then LgN + 1 compares for a search (successful or unsuccessful)

// Let C(N) be the worst-case number of compareTo done by rank() on a
// sorted array of size N. We need to prove: for all N >= 1
// C(N) <= floor(log2N) + 1

//     N  C(N)   log2+1    mono? bounded?
//     1     1        1        T        T
//     2     2        2        T        T
//     4     3        3        T        T
//     8     4        4        T        T
//    16     5        5        T        T
//    32     6        6        T        T
//    64     7        7        T        T
//   128     8        8        T        T

// Monotonic C(N): verified
// Bound C(N) <= floor(log2 N)+1: verified