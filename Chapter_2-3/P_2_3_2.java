import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_2 {
    public static void quickSortTrace(Comparable[] a) {
        // StdRandom.shuffle(a); // for now ignore shuffle
        sortTrace(a, 0, a.length - 1);
    }

    private static void sortTrace(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        
        // To see the parttion process of each round, use partitionTrace()
        int j = partitionTrace(a, lo, hi);
        // int j = partition(a, lo, hi);
        StdOut.print("\n---------------------------------");
        StdOut.printf("%nlo = %d, j = %d, hi = %d%n", lo, j, hi);
        printRange(a, lo, hi);
        StdOut.print("---------------------------------\n");


        // After partition, we put the a[j] at final position j
        // but a[lo, j-1] and a[j+1, hi] are out-of-order
        // we solve the problem recursively, continue
        // to sort left part and right part

        sortTrace(a, lo, j - 1);
        sortTrace(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        // choose pivot
        Comparable v = a[lo];
        int i = lo;     // index of left part
        int j = hi + 1; // index of right part

        while (true) {
            // i scans from left, stop when a[i] >= v
            while (less(a[++i], v)) if (i == hi) break;
            // j scans from right, stop when a[j] <= v
            while (less(v, a[--j])) if (j == lo) break;

            // check if i and j cross each other
            if (i >= j) break;

            exch(a, i, j);
        }
        // put pivot (v = a[j]) into correct position
        exch(a, lo, j);
        return j;
    }

    
        public static int partitionTrace(Comparable[] a, int lo, int hi) {
        int i = lo;        // ← match the real partition
        int j = hi + 1;    // ← match the real partition
        Comparable v = a[lo];
        StdOut.printf("Partitioning [%d..%d], pivot a[%d] = %s%n", lo, hi, lo, v);

        while (true) {

            // —— left scan
            while (true) {
                if (i == hi) break;          // don’t go past hi
                i++;
                if (!less(a[i], v)) break;   // stop when a[i] >= v
                // still less ⇒ trace it
                print(a);
                printMarker(i, "^");
            }
            print(a);
            printMarker(i, "^");

            // —— right scan
            while (true) {
                if (j == lo) break;          // don’t go before lo
                j--;
                if (!less(v, a[j])) break;   // stop when a[j] <= v
                // still greater ⇒ trace it
                print(a);
                printMarker(j, "*");
            }
            print(a);
            printMarker(j, "*");

            // —— check cross
            if (i >= j) break;

            // —— interior swap
            StdOut.printf("%nswap i=%d('%s') ↔ j=%d('%s')%n",
                        i, a[i], j, a[j]);
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


    private static void printRange(Comparable[] a, int lo, int hi) {
        for (int i = 0; i < a.length; i++) {
            if (i == lo) {
                StdOut.print("<" + a[i] + " ");
            } else if (i == hi) {
                StdOut.print(a[i] + ">" );
            } else {
                StdOut.print(a[i] + " ");
            }
            
        }
        StdOut.println();
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
        // String[] a = { "K",  "R", "A", "T", "E", "L", 
        // "E", "P", "U", "I", "M", "Q", "C", "X", "O", "S"};
        String[] a = {"E", "A", "S", "Y", "Q", 
        "U", "E", "S", "T", "I", "O", "N"};

        StdOut.println("Initial array: " + Arrays.toString(a));
        StdOut.println();

        quickSortTrace(a);
    }
}

// Initial array: [E, A, S, Y, Q, U, E, S, T, I, O, N]

// Partitioning [0..11], pivot a[0] = E
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

// E A E Y Q U S S T I O N 
//       ^
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

// ---------------------------------
// lo = 0, j = 2, hi = 11
// <E A E Y Q U S S T I O N>
// ---------------------------------
// Partitioning [0..1], pivot a[0] = E
// E A E Y Q U S S T I O N 
//   ^
// E A E Y Q U S S T I O N 
//   ^
// E A E Y Q U S S T I O N 
//   *
// swap pivot, lo=0('E') ↔ j=1('A')
// A E E Y Q U S S T I O N 

// ---------------------------------
// lo = 0, j = 1, hi = 1
// <A E>E Y Q U S S T I O N 
// ---------------------------------
// Partitioning [3..11], pivot a[3] = Y
// A E E Y Q U S S T I O N 
//         ^
// A E E Y Q U S S T I O N 
//           ^
// A E E Y Q U S S T I O N 
//             ^
// A E E Y Q U S S T I O N 
//               ^
// A E E Y Q U S S T I O N 
//                 ^
// A E E Y Q U S S T I O N 
//                   ^
// A E E Y Q U S S T I O N 
//                     ^
// A E E Y Q U S S T I O N 
//                       ^
// A E E Y Q U S S T I O N 
//                       ^
// A E E Y Q U S S T I O N 
//                       *
// swap pivot, lo=3('Y') ↔ j=11('N')
// A E E N Q U S S T I O Y 

// ---------------------------------
// lo = 3, j = 11, hi = 11
// A E E <N Q U S S T I O Y>
// ---------------------------------
// Partitioning [3..10], pivot a[3] = N
// A E E N Q U S S T I O Y 
//         ^
// A E E N Q U S S T I O Y 
//                     *
// A E E N Q U S S T I O Y 
//                   *

// swap i=4('Q') ↔ j=9('I')
// A E E N Q U S S T I O Y 
//         ^         *
// A E E N I U S S T Q O Y 

// A E E N I U S S T Q O Y 
//           ^
// A E E N I U S S T Q O Y 
//                 *
// A E E N I U S S T Q O Y 
//               *
// A E E N I U S S T Q O Y 
//             *
// A E E N I U S S T Q O Y 
//           *
// A E E N I U S S T Q O Y 
//         *
// swap pivot, lo=3('N') ↔ j=4('I')
// A E E I N U S S T Q O Y 

// ---------------------------------
// lo = 3, j = 4, hi = 10
// A E E <I N U S S T Q O>Y 
// ---------------------------------
// Partitioning [5..10], pivot a[5] = U
// A E E I N U S S T Q O Y 
//             ^
// A E E I N U S S T Q O Y 
//               ^
// A E E I N U S S T Q O Y 
//                 ^
// A E E I N U S S T Q O Y 
//                   ^
// A E E I N U S S T Q O Y 
//                     ^
// A E E I N U S S T Q O Y 
//                     ^
// A E E I N U S S T Q O Y 
//                     *
// swap pivot, lo=5('U') ↔ j=10('O')
// A E E I N O S S T Q U Y 

// ---------------------------------
// lo = 5, j = 10, hi = 10
// A E E I N <O S S T Q U>Y 
// ---------------------------------
// Partitioning [5..9], pivot a[5] = O
// A E E I N O S S T Q U Y 
//             ^
// A E E I N O S S T Q U Y 
//                   *
// A E E I N O S S T Q U Y 
//                 *
// A E E I N O S S T Q U Y 
//               *
// A E E I N O S S T Q U Y 
//             *
// A E E I N O S S T Q U Y 
//           *
// swap pivot, lo=5('O') ↔ j=5('O')
// A E E I N O S S T Q U Y 

// ---------------------------------
// lo = 5, j = 5, hi = 9
// A E E I N <O S S T Q>U Y 
// ---------------------------------
// Partitioning [6..9], pivot a[6] = S
// A E E I N O S S T Q U Y 
//               ^
// A E E I N O S S T Q U Y 
//                   *

// swap i=7('S') ↔ j=9('Q')
// A E E I N O S S T Q U Y 
//               ^   *
// A E E I N O S Q T S U Y 

// A E E I N O S Q T S U Y 
//                 ^
// A E E I N O S Q T S U Y 
//                 *
// A E E I N O S Q T S U Y 
//               *
// swap pivot, lo=6('S') ↔ j=7('Q')
// A E E I N O Q S T S U Y 

// ---------------------------------
// lo = 6, j = 7, hi = 9
// A E E I N O <Q S T S>U Y 
// ---------------------------------
// Partitioning [8..9], pivot a[8] = T
// A E E I N O Q S T S U Y 
//                   ^
// A E E I N O Q S T S U Y 
//                   ^
// A E E I N O Q S T S U Y 
//                   *
// swap pivot, lo=8('T') ↔ j=9('S')
// A E E I N O Q S S T U Y 

// ---------------------------------
// lo = 8, j = 9, hi = 9
// A E E I N O Q S <S T>U Y 
// ---------------------------------