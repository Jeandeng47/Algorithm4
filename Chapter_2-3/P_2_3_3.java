import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_3 {
    private static int maxExch;
    private static Comparable max;

    public static int getMaxExch() {
        return maxExch;
    }

    public static void quickSort(Comparable[] a) {

        // record the max value
        max = findMax(a);
        maxExch = 0;

        int N = a.length;
        sort(a, 0, N - 1);
    }
    
    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;

        int j = partitionTrace(a, lo, hi);
        StdOut.print("\n---------------------------------");
        StdOut.printf("%nlo = %d, j = %d, hi = %d%n", lo, j, hi);
        printRange(a, lo, hi);
        StdOut.print("---------------------------------\n");

        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    public static int partitionTrace(Comparable[] a, int lo, int hi) {
        int i = lo;        // ← match the real partition
        int j = hi + 1;    // ← match the real partition
        Comparable v = a[lo];
        StdOut.printf("Partitioning [%d..%d], pivot a[%d] = %s%n", lo, hi, lo, v);

        while (true) {
            // StdOut.printf("%nPartition: i = %d, j = %d%n", i, j);

            while (true) {
                if (i == hi) break;         
                i++;
                if (!less(a[i], v)) break;
                print(a);
                printMarker(i, "^");
            }
            print(a);
            printMarker(i, "^");

            while (true) {
                if (j == lo) break;         
                j--;
                if (!less(v, a[j])) break;
                print(a);
                printMarker(j, " *");
            }
            print(a);
            printMarker(j, " *");

            if (i >= j) break;

            StdOut.printf("%nswap i=%d('%s') ↔ j=%d('%s')%n",
                        i, a[i], j, a[j]);
            print(a);
            print2Markers(i, j, "^", " *");
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


    private static Comparable findMax(Comparable[] a) {
        Comparable max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(max) > 0) {
                max = a[i];
            }
        }
        return max;
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

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        if (a[i].equals(max) || a[j].equals(max)) {
            maxExch++;
        }
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
        
    }

    // Design array that could maximize the times max value get exchanged
    // Idea: max could not be moved by just 1 position, where it would become 
    // the pivot element and moved to final position. Therefore, we must ensure
    // the max gets moved by 2 positions for each partition.
    private static List<Integer> getArray(int N) {
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();
        // e = [2, 4, 6, ...], o = [1, 3, 5, ...]
        for (int x = 1; x < N; x++) {
            if (x % 2 == 0) evens.add(x);
            else           odds.add(x);
        }

        List<Integer> nums = new ArrayList<>();
        nums.add(evens.get(0)); // put first even, 2
        nums.add(N); // add max
        for (int i = 1; i < evens.size(); i++) {
            // put next pivot, 4
            nums.add(evens.get(i)); 
            // put the corresponding stop point of this even number, 1
            nums.add(odds.get(i-1));
        }

        // Add rest of the odds
        for (int j = evens.size() - 1; j < odds.size(); j++) {
            nums.add(odds.get(j));
        }

        return nums;
    }

    public static void main(String[] args) {
        int N = 10;
        List<Integer> arr = getArray(N);
        Integer[] a = arr.toArray(new Integer[0]); 

        StdOut.println("Before: " + java.util.Arrays.toString(a));
        quickSort(a);
        StdOut.println();
        StdOut.println("After : " + java.util.Arrays.toString(a));
        StdOut.printf("Largest item %s was exchanged %d times.%n",
                      max, getMaxExch()); // max times: N / 2
        
    }
}


// Index:  0   1  2  3  4  5  6  7  8  9
// Value:  2, 10, 4, 1, 6, 3, 8, 5, 7, 9

// 1. Piviot = a[lo] always 
// 2. Max should be placed at a[hi-1]
// 3. To maximize the steps could move, we want minimize each steps.
// Thus, we prefer max to start at lo + 1, and we want j to stop 
// closed to max, we need to put the less-than-pivot element near max.
// 3. However, we do not want max to become the next pivot
// since it would be placed at the final position. The arrangement
// [ p | max | <p ] would make max the next pivot.

// [ p | max | <p ]  
//       i     j       -> stop, exch(i, j)
// [ p | <p | max ]
//       j     i       -> swap pivot, a[j]
// [<p |  p | max ] 
//        j   j+1 
// 3. We put a next pivot between max and less-than-pivot element 
// such that we never choose max as next pivot.

// [ p1 | max | p2 |  <p ]  
//         i           j       -> stop, exch(i, j)
// [ p1 | <p  | p2 | max ] 
//         j     i             -> swap pivot, a[j]
// [ p1 | <p  | p2 | max ] 
//         j    j+1            -> recursive call sort a[j+1, hi], p2 is new pivot


// 1st call: a[0...9]
// lo = 0, p = a[0] = 2
// a[1] = 10 swapped to index 3
// pivot swap puts 2 into index 1, split a[0...0] and a[2...9]
// <1 2 4 10 6 3 8 5 7 9>

// 2nd call: a[2...9]
// lo = 2, p = a[2] = 4
// a[3] = 10 is swapped into index 5
// pivot swap puts 4 into index 3, split a[2...2] and a[4...9]
// 1 2 <3 4 6 10 8 5 7 9>

// 3rd call: a[4...9]
// lo = 4, p = a[4] = 6
// a[5] = 10 is swapped into index 7,...
// 1 2 3 4 <5 6 8 10 7 9>

// Counter example:
// Index:  0   1  2  3  4  5  6  7  8  9
// Value:  2, 10, 1, 4, 6, 3, 8, 5, 7, 9

// lo = 0,  p = a[0] = 2
// i = 1, j = 2, swap 1 and 10 -> a = [2, 1, 10]
// i = 2, j = 1, cross, swap a[lo] and a[j] -> a = [1, 2, 10]
// Split into a[0..0] and a[2..9] now 10 becomdes next pivot!!!



// Example output:

// Before: [2, 10, 4, 1, 6, 3, 8, 5, 7, 9]

// Partitioning [0..9], pivot a[0] = 2
// 2 10 4 1 6 3 8 5 7 9 
//   ^
// 2 10 4 1 6 3 8 5 7 9 
//                    *
// 2 10 4 1 6 3 8 5 7 9 
//                  *
// 2 10 4 1 6 3 8 5 7 9 
//                *
// 2 10 4 1 6 3 8 5 7 9 
//              *
// 2 10 4 1 6 3 8 5 7 9 
//            *
// 2 10 4 1 6 3 8 5 7 9 
//          *
// 2 10 4 1 6 3 8 5 7 9 
//        *

// swap i=1('10') ↔ j=3('1')
// 2 10 4 1 6 3 8 5 7 9 
//   ^    *
// 2 1 4 10 6 3 8 5 7 9 

// 2 1 4 10 6 3 8 5 7 9 
//     ^
// 2 1 4 10 6 3 8 5 7 9 
//      *
// 2 1 4 10 6 3 8 5 7 9 
//    *
// swap pivot, lo=0('2') ↔ j=1('1')
// 1 2 4 10 6 3 8 5 7 9 

// ---------------------------------
// lo = 0, j = 1, hi = 9
// <1 2 4 10 6 3 8 5 7 9>
// ---------------------------------
// Partitioning [2..9], pivot a[2] = 4
// 1 2 4 10 6 3 8 5 7 9 
//       ^
// 1 2 4 10 6 3 8 5 7 9 
//                    *
// 1 2 4 10 6 3 8 5 7 9 
//                  *
// 1 2 4 10 6 3 8 5 7 9 
//                *
// 1 2 4 10 6 3 8 5 7 9 
//              *
// 1 2 4 10 6 3 8 5 7 9 
//            *

// swap i=3('10') ↔ j=5('3')
// 1 2 4 10 6 3 8 5 7 9 
//       ^    *
// 1 2 4 3 6 10 8 5 7 9 

// 1 2 4 3 6 10 8 5 7 9 
//         ^
// 1 2 4 3 6 10 8 5 7 9 
//          *
// 1 2 4 3 6 10 8 5 7 9 
//        *
// swap pivot, lo=2('4') ↔ j=3('3')
// 1 2 3 4 6 10 8 5 7 9 

// ---------------------------------
// lo = 2, j = 3, hi = 9
// 1 2 <3 4 6 10 8 5 7 9>
// ---------------------------------
// Partitioning [4..9], pivot a[4] = 6
// 1 2 3 4 6 10 8 5 7 9 
//           ^
// 1 2 3 4 6 10 8 5 7 9 
//                    *
// 1 2 3 4 6 10 8 5 7 9 
//                  *
// 1 2 3 4 6 10 8 5 7 9 
//                *

// swap i=5('10') ↔ j=7('5')
// 1 2 3 4 6 10 8 5 7 9 
//           ^    *
// 1 2 3 4 6 5 8 10 7 9 

// 1 2 3 4 6 5 8 10 7 9 
//             ^
// 1 2 3 4 6 5 8 10 7 9 
//              *
// 1 2 3 4 6 5 8 10 7 9 
//            *
// swap pivot, lo=4('6') ↔ j=5('5')
// 1 2 3 4 5 6 8 10 7 9 

// ---------------------------------
// lo = 4, j = 5, hi = 9
// 1 2 3 4 <5 6 8 10 7 9>
// ---------------------------------
// Partitioning [6..9], pivot a[6] = 8
// 1 2 3 4 5 6 8 10 7 9 
//               ^
// 1 2 3 4 5 6 8 10 7 9 
//                    *
// 1 2 3 4 5 6 8 10 7 9 
//                  *

// swap i=7('10') ↔ j=8('7')
// 1 2 3 4 5 6 8 10 7 9 
//               ^  *
// 1 2 3 4 5 6 8 7 10 9 

// 1 2 3 4 5 6 8 7 10 9 
//                 ^
// 1 2 3 4 5 6 8 7 10 9 
//                *
// swap pivot, lo=6('8') ↔ j=7('7')
// 1 2 3 4 5 6 7 8 10 9 

// ---------------------------------
// lo = 6, j = 7, hi = 9
// 1 2 3 4 5 6 <7 8 10 9>
// ---------------------------------
// Partitioning [8..9], pivot a[8] = 10
// 1 2 3 4 5 6 7 8 10 9 
//                   ^
// 1 2 3 4 5 6 7 8 10 9 
//                   ^
// 1 2 3 4 5 6 7 8 10 9 
//                    *
// swap pivot, lo=8('10') ↔ j=9('9')
// 1 2 3 4 5 6 7 8 9 10 

// ---------------------------------
// lo = 8, j = 9, hi = 9
// 1 2 3 4 5 6 7 8 <9 10>
// ---------------------------------

// After : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
// Largest item 10 was exchanged 5 times.