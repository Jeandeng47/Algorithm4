import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_2 {

    private static Comparable[] aux;

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void printRow(Comparable[] a, int lo, int hi, int depth) {
        int N = a.length;
        printIdentation(depth);

        StdOut.print("[");
        for (int i = 0; i < N; i++) {
            if (i == lo) StdOut.print("<");
            if (i == hi) {
                StdOut.print(a[i] + "> ");
            } else {
                StdOut.print(a[i] + " ");
            }       
        }
        StdOut.print("]");
        StdOut.println();
    }

    private static void printIdentation(int depth) {
        for (int i = 0; i < depth; i++) {
            StdOut.printf("--");
        }
        StdOut.printf(" ");
    }

    public static void mergeTrace(Comparable[] a, int lo, int mid, int hi, int depth) {
        StdOut.printf("Size = %d%n", hi - lo + 1);

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // header
        printIdentation(depth);
        StdOut.printf("merge(a, %d, %d, %d)%n", lo, mid, hi);

        for (int k = lo; k <= hi; k++) {
            // now pick into a[k]
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                        a[k] = aux[i++];

        }
        printRow(a, lo, hi, depth);
        StdOut.println();
    }

    // Merge sort Top-dowan
    public static void sortTD(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        sortTDHelper(a, 0, N - 1, 0);
    }

    public static void sortTDHelper(Comparable[] a, int lo, int hi, int depth) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sortTDHelper(a, lo, mid, depth + 1);
        sortTDHelper(a, mid + 1, hi, depth + 1);
        mergeTrace(a, lo, mid, hi, depth + 1);
    }
    

    public static void main(String[] args) {
        // String[] keys ={"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        String[] keys = { "E","A","S","Y","Q","U", "E", "S", "T", "I", "O", "N"};

        // Top‑down trace
        StdOut.println("Top-Down mergesort trace:");
        sortTD(keys);
        System.out.println();
    }
    
}


// Top-Down mergesort trace:
// Size = 2
// -------- merge(a, 0, 0, 1)
// -------- [<A E> S Y Q U E S T I O N ]

// Size = 3
// ------ merge(a, 0, 1, 2)
// ------ [<A E S> Y Q U E S T I O N ]

// Size = 2
// -------- merge(a, 3, 3, 4)
// -------- [A E S <Q Y> U E S T I O N ]

// Size = 3
// ------ merge(a, 3, 4, 5)
// ------ [A E S <Q U Y> E S T I O N ]

// Size = 6
// ---- merge(a, 0, 2, 5)
// ---- [<A E Q S U Y> E S T I O N ]

// Size = 2
// -------- merge(a, 6, 6, 7)
// -------- [A E Q S U Y <E S> T I O N ]

// Size = 3
// ------ merge(a, 6, 7, 8)
// ------ [A E Q S U Y <E S T> I O N ]

// Size = 2
// -------- merge(a, 9, 9, 10)
// -------- [A E Q S U Y E S T <I O> N ]

// Size = 3
// ------ merge(a, 9, 10, 11)
// ------ [A E Q S U Y E S T <I N O> ]

// Size = 6
// ---- merge(a, 6, 8, 11)
// ---- [A E Q S U Y <E I N O S T> ]

// Size = 12
// -- merge(a, 0, 5, 11)
// -- [<A E E I N O Q S S T U Y> ]