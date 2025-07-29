import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_3 {
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
        int i = lo;
        int j = mid + 1;
        
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // header
        printIdentation(depth);
        StdOut.printf("merge(a, %d, %d, %d)%n", lo, mid, hi);

        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                        a[k] = aux[i++];

        }
        printRow(a, lo, hi, depth);
        StdOut.println();
        
    }

    // Merge sort bottom up
    public static void sortBU(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];

        int depth = 0;
        for (int size = 1; size < N; size = 2 * size) {
            StdOut.printf("Size = %d%n", size * 2);
            for (int lo = 0; lo < N - size; lo += 2 * size) {
                int mid = lo + size - 1;
                int hi = Math.min(lo + 2 * size - 1, N - 1);
                mergeTrace(a, lo, mid, hi, depth);
            }
            depth += 1;
        }
    }

    public static void main(String[] args) {
        // String[] keys ={"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        String[] keys = { "E","A","S","Y","Q","U", "E", "S", "T", "I", "O", "N"};

        // Top‑down trace
        StdOut.println("Bottom-up mergesort trace:");
        sortBU(keys);
        System.out.println();
    }
}

// Size = 2
//  merge(a, 0, 0, 1)
//  [<A E> S Y Q U E S T I O N ]

//  merge(a, 2, 2, 3)
//  [A E <S Y> Q U E S T I O N ]

//  merge(a, 4, 4, 5)
//  [A E S Y <Q U> E S T I O N ]

//  merge(a, 6, 6, 7)
//  [A E S Y Q U <E S> T I O N ]

//  merge(a, 8, 8, 9)
//  [A E S Y Q U E S <I T> O N ]

//  merge(a, 10, 10, 11)
//  [A E S Y Q U E S I T <N O> ]

// Size = 4
// -- merge(a, 0, 1, 3)
// -- [<A E S Y> Q U E S I T N O ]

// -- merge(a, 4, 5, 7)
// -- [A E S Y <E Q S U> I T N O ]

// -- merge(a, 8, 9, 11)
// -- [A E S Y E Q S U <I N O T> ]

// Size = 8
// ---- merge(a, 0, 3, 7)
// ---- [<A E E Q S S U Y> I N O T ]

// Size = 16
// ------ merge(a, 0, 7, 11)
// ------ [<A E E I N O Q S S T U Y> ]