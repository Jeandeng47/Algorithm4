import java.util.Arrays;

import Util.ArrayGenerator;
import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_28 {

    public static int[] removeDuplicates(int[] a) {
        int i = 0;
        int j = i + 1;
        while (j < a.length) {
            if (a[i] == a[j]) {
                // Skip duplicates
                j++;
            } else {
                // Move the unique element to the front
                i += 1;
                a[i] = a[j];
                j++;
            }
        }
        // Create a new array with the unique elements
        // Now the first i + 1 elements of a are unique
        int[] b = new int[i + 1];
        for (int k = 0; k < b.length; k++) {
            b[k] = a[k];
        }
        return b;

    }

    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key > a[mid]) {
                lo = mid + 1;
            }
            else if (key < a[mid]) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;    
    }

    public static void main(String[] args) {

        int size = 100;
        int[] arr = ArrayGenerator.getRandIntArr(size, 1, 10);

        StdOut.println("Original array:");
        ArrayPrint.printArray(arr);

        // Remove duplicates
        Arrays.sort(arr);
        StdOut.println("Sort array:");
        ArrayPrint.printArray(arr);
        
        arr = removeDuplicates(arr);
        StdOut.println("Array after removing duplicates:");
        ArrayPrint.printArray(arr);
        
    }
    
}
