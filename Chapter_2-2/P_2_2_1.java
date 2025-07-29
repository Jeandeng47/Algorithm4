import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_1 {

    private static Comparable[] aux;

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void mergeTrace(Comparable[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;

        StdOut.printf("Merging a[%d..%d] with mid=%d%n", lo, hi, mid);

        aux = new Comparable[a.length]; // init the aux array here

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
            a[k] = "-"; // make sorting process clear
        }

        StdOut.println("Initial aux: " + Arrays.toString(aux));
        StdOut.println();

        for (int k = lo; k <= hi; k++) {
            StdOut.printf("k=%2d | i=%2d | j=%2d | ", k, i, j);
            if (i > mid) {
                a[k] = aux[j];
                aux[j++] = "-";
                StdOut.printf("take right  %-2s  ", a[k]);
            } else if (j > hi) {
                a[k] = aux[i];
                aux[i++] = "-";
                StdOut.printf("take left   %-2s  ", a[k]);
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j];
                aux[j++] = "-";
                StdOut.printf("take right  %-2s  ", a[k]);
            } else {
                a[k] = aux[i];
                aux[i++] = "-";
                StdOut.printf("take left   %-2s  ", a[k]);
            }
            StdOut.println();
            StdOut.println("Aux: " + Arrays.toString(aux));
            StdOut.println("Arr: " + Arrays.toString(a));
            StdOut.println();
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] chars = { "A","E","Q","S","U","Y","E","I","N","O","S","T" };
        mergeTrace(chars, 0, 5, 11);
    }
}


// Merging a[0..11] with mid=5
// Initial aux: [A, E, Q, S, U, Y, E, I, N, O, S, T]

// k= 0 | i= 0 | j= 6 | take left   A   
// Aux: [-, E, Q, S, U, Y, E, I, N, O, S, T]
// Arr: [A, -, -, -, -, -, -, -, -, -, -, -]

// k= 1 | i= 1 | j= 6 | take left   E   
// Aux: [-, -, Q, S, U, Y, E, I, N, O, S, T]
// Arr: [A, E, -, -, -, -, -, -, -, -, -, -]

// k= 2 | i= 2 | j= 6 | take right  E   
// Aux: [-, -, Q, S, U, Y, -, I, N, O, S, T]
// Arr: [A, E, E, -, -, -, -, -, -, -, -, -]

// k= 3 | i= 2 | j= 7 | take right  I   
// Aux: [-, -, Q, S, U, Y, -, -, N, O, S, T]
// Arr: [A, E, E, I, -, -, -, -, -, -, -, -]

// k= 4 | i= 2 | j= 8 | take right  N   
// Aux: [-, -, Q, S, U, Y, -, -, -, O, S, T]
// Arr: [A, E, E, I, N, -, -, -, -, -, -, -]

// k= 5 | i= 2 | j= 9 | take right  O   
// Aux: [-, -, Q, S, U, Y, -, -, -, -, S, T]
// Arr: [A, E, E, I, N, O, -, -, -, -, -, -]

// k= 6 | i= 2 | j=10 | take left   Q   
// Aux: [-, -, -, S, U, Y, -, -, -, -, S, T]
// Arr: [A, E, E, I, N, O, Q, -, -, -, -, -]

// k= 7 | i= 3 | j=10 | take left   S   
// Aux: [-, -, -, -, U, Y, -, -, -, -, S, T]
// Arr: [A, E, E, I, N, O, Q, S, -, -, -, -]

// k= 8 | i= 4 | j=10 | take right  S   
// Aux: [-, -, -, -, U, Y, -, -, -, -, -, T]
// Arr: [A, E, E, I, N, O, Q, S, S, -, -, -]

// k= 9 | i= 4 | j=11 | take right  T   
// Aux: [-, -, -, -, U, Y, -, -, -, -, -, -]
// Arr: [A, E, E, I, N, O, Q, S, S, T, -, -]

// k=10 | i= 4 | j=12 | take left   U   
// Aux: [-, -, -, -, -, Y, -, -, -, -, -, -]
// Arr: [A, E, E, I, N, O, Q, S, S, T, U, -]

// k=11 | i= 5 | j=12 | take left   Y   
// Aux: [-, -, -, -, -, -, -, -, -, -, -, -]
// Arr: [A, E, E, I, N, O, Q, S, S, T, U, Y]