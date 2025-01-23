import Util.ArrayGenerator;
import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_22 {

    // Recursive version of rank
    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1, 0);
    }

    public static int rank(int key, int[] a, int lo, int hi, int depth) {
        printIndentation(lo, hi, depth);
        // base case
        if (lo > hi) {
            return -1;
        }
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) {
            // key in the left half
            return rank(key, a, lo, mid - 1, depth + 1);
        } else if (key > a[mid]) {
            // key in the right half
            return rank(key, a, mid + 1, hi, depth + 1);
        } else {
            return mid;
        }
    }

    private static void printIndentation(int lo, int hi, int depth) {
        for (int i = 0; i < depth; i++) {
            StdOut.print("-");
        }
        StdOut.printf("%3d %3d %3d", depth, lo, hi);
        StdOut.println();
    } 

    public static void main(String[] args) {
        int[] a = ArrayGenerator.getAscIntArr(20);
        ArrayPrint.printArray(a); 
        
        int key1 = 15;
        StdOut.println("Index of " + key1 + " : " + rank(key1, a));

        int key2 = 100;
        StdOut.println("Index of " + key2 + " : " + rank(key2, a));

    }
}


// 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19
//   0   0  19
// -  1  10  19
// --  2  15  19
// ---  3  15  16
// Index of 15 : 15
//   0   0  19
// -  1  10  19
// --  2  15  19
// ---  3  18  19
// ----  4  19  19
// -----  5  20  19
// Index of 100 : -1