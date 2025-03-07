import java.util.Arrays;

import Util.ArrayGenerator;
import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_29 {
    // return the number of elements that are smaller than the key
    private static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key > a[mid]) {
                lo = mid + 1;
            } else if (key < a[mid]) {
                hi = mid - 1;
            } 
            else {
                // If the key is equal to the mid element, 
                // we need to find the first element that is equal to the key
                while (mid > 0 && a[mid - 1] == key) {
                    mid--;
                }
                return mid;
            }
        }
        return -1;
    }

    // return the number of elements that are equal to the key
    private static int count(int key, int[] a) {
        int firstIndex = rank(key, a);
        if (firstIndex > a.length - 1 || a[firstIndex] != key) {
            return 0; // key is not in the array
        }

        int count = 0;
        while (firstIndex + count < a.length - 1) {
            if (a[firstIndex + count] == key) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public static void main(String[] args) {

        int[] arr = ArrayGenerator.getRandIntArr(100, 1, 10);
        Arrays.sort(arr);

        StdOut.println("Original array:");
        ArrayPrint.printArray(arr);

        int key = 5;

        int firstIndex = rank(key, arr);
        StdOut.println("Number of elements less than " + key + ": " + firstIndex);
        
        int count = count(key, arr);
        StdOut.println("Number of elements equal to " + key + ": " + count);
    }
}
