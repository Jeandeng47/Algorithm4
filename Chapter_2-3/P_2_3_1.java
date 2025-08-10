import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_1 {
    public static int partitionTrace(Comparable[] a, int lo, int hi) {
        int i = lo + 1; 
        int j = hi;  // init j = hi = N - 1
        Comparable v = a[lo];

        while (true) {
            StdOut.printf("%ni = %d, j = %d%n", i, j);
            while (less(a[i], v)) {
                print(a);
                printMarker(i, "^");
                i++;
                if (i == hi) break;
            } 
            print(a);
            printMarker(i, "^");

            while (less(v, a[j])) {
                print(a);
                printMarker(j, "*");
                j--;
                if (j == lo) break;
            }
            print(a);
            printMarker(j, "*");

            if (i >= j) break;

            StdOut.printf("%nswap i=%d('%s') ↔ j=%d('%s')%n", i, a[i], j, a[j]);
            print(a);
            print2Markers(i, j, "^", "*");
            exch(a, i, j);
            print(a);
            StdOut.println();
        }
        StdOut.printf("swap pivot, lo=%d('%s') ↔ j=%d('%s')%n", lo, a[lo], j, a[j]);
        exch(a, lo, j);
        print(a);
        return j;
    }

    private static void print(Comparable[] a) {
        for (Comparable s : a) {
            StdOut.print(s + " ");   
        }
        StdOut.println();
    }

    private static void printMarker(int pos, String n) {
        for (int k = 0; k < pos; k++) StdOut.print("  ");
        System.out.println(n);
    }

    private static void print2Markers(int pos1, int pos2, String n1, String n2) {
        // 1) indent to pos1
        for (int k = 0; k < pos1; k++) {
            StdOut.print("  ");
        }
        // 2) first marker
        StdOut.print(n1);
        // 3) indent from pos1+1 up to pos2
        for (int k = pos1 + 1; k < pos2; k++) {
            StdOut.print("  ");
        }
        // 4) second marker + newline
        StdOut.println(" " + n2);
    }



    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        String[] a = {"E", "A", "S", "Y", "Q", 
        "U", "E", "S", "T", "I", "O", "N"};
        // String[] a = { "K",  "R", "A", "T", "E", "L", 
        // "E", "P", "U", "I", "M", "Q", "C", "X", "O", "S"};

        StdOut.println("Initial array: " + Arrays.toString(a));
        StdOut.println();

        partitionTrace(a, 0, a.length - 1);
    }
}


// Initial array: 
// [E, A, S, Y, Q, U, E, S, T, I, O, N]


// i = 1, j = 11
// E A S Y Q U E S T I O N 
//   ^
// E A S Y Q U E S T I O N 
//     ^
// E A S Y Q U E S T I O N 
//                       *
// E A S Y Q U E S T I O N 
//                     *
// E A S Y Q U E S T I O N 
//                   *
// E A S Y Q U E S T I O N 
//                 *
// E A S Y Q U E S T I O N 
//               *
// E A S Y Q U E S T I O N 
//             *

// swap i=2('S') ↔ j=6('E')
// E A S Y Q U E S T I O N 
//     ^       *
// E A E Y Q U S S T I O N 


// i = 2, j = 6
// E A E Y Q U S S T I O N 
//     ^
// E A E Y Q U S S T I O N 
//             *
// E A E Y Q U S S T I O N 
//           *
// E A E Y Q U S S T I O N 
//         *
// E A E Y Q U S S T I O N 
//       *
// E A E Y Q U S S T I O N 
//     *
// swap pivot, lo=0('E') ↔ j=2('E')
// E A E Y Q U S S T I O N 
