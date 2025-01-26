import java.util.Arrays;

import edu.princeton.cs.algs4.Counter;
import edu.princeton.cs.algs4.StdOut;

public class P_1_2_9 {
    public static int rank(int key, int[] a, Counter counter) {
        // Input array must be sorted
        int lo = 0;
        int hi = a.length - 1; // make sure not out of bound
        while (lo <= hi) {
            counter.increment();
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
        int[] whitelist = {1, 3, 5, 7, 9, 11, 13, 15};
        Arrays.sort(whitelist); 

        int[] keysToSearch = {3, 6, 9, 12};
        
        Counter counter = new Counter("Keys");

        for (int key : keysToSearch) {
            int result = rank(key, keysToSearch, counter);
            if (result == -1) {
                StdOut.printf("Key %d not found.", key);
                StdOut.println();
            } else {
                StdOut.printf("Key %d found at index %d", key, result);
                StdOut.println();
            }
        }

        StdOut.println("Total keys searched: " + counter.tally());

    }
}

// Key 3 found at index 0
// Key 6 found at index 1
// Key 9 found at index 2
// Key 12 found at index 3
// Total keys searched: 8