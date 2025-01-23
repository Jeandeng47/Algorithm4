import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// To compile and run this program:
// % javac -cp .:algs4.jar ./Chapter_1-1/P_1_1_23.java
// % java -cp .:algs4.jar:Chapter_1-1 P_1_1_23 algs4-data/tinyW.txt - < algs4-data/tinyT.txt
// % java -cp .:algs4.jar:Chapter_1-1 P_1_1_23 algs4-data/tinyW.txt + < algs4-data/tinyT.txt

public class P_1_1_23 {

    public static int rank(int key, int[] a) {
        // Input array must be sorted
        int lo = 0;
        int hi = a.length - 1; // make sure not out of bound
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                // key in the left half
                hi = mid - 1;
            } else if (key > a[mid]) {
                // key in the right half
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1; // key not found
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String symbol = args[1];
        int[] whiteList = in.readAllInts();

        Arrays.sort(whiteList);

        while (!StdIn.isEmpty()) {
            // Read each key, print if not in the whiteList
            int key = StdIn.readInt();
            int rank = rank(key, whiteList);
            
            if (rank > 0 && symbol.equals("-")) {
                // - to print numbers in the white list
                StdOut.println(key);
            } else if (rank == -1 && symbol.equals("+")) {
                // + to print numbers not in the white list
                StdOut.println(key);
            }

        }
    }
}

// Numbers in the white list:
// 23
// 18
// 23
// 98
// 84
// 11
// 48
// 77
// 54
// 98
// 77
// 77
// 68

// Numbers not in the white list:
// 50
// 99
// 13
