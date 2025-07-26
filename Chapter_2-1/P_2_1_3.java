import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdOut;

public class P_2_1_3 {
    private static int updates = 0;

    public static void selectionSort(Comparable[] a) {
        updates = 0; // reset
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[minIdx])) {
                    minIdx = j;
                    updates++;
                }
            }
            exch(a, i, minIdx);
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i , int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        Integer[] des = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Integer[] asc = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        StdOut.println("Original array: ");
        ArrayPrint.printArray(des);

        selectionSort(des);
        StdOut.println("Sorted array: ");
        ArrayPrint.printArray(des);
        StdOut.printf("Number of min updates: %d\n", updates);

        StdOut.println("Original array: ");
        ArrayPrint.printArray(asc);

        selectionSort(asc);
        StdOut.println("Sorted array: ");
        ArrayPrint.printArray(asc);
        StdOut.printf("Number of min updates: %d\n", updates);

    }
}


// When selectionSort() sorts ascendingly, an descending array would make 
// every check of a[j] < a[min] fails   

// Original array: 
// 9 8 7 6 5 4 3 2 1
// Sorted array: 
// 1 2 3 4 5 6 7 8 9
// Number of min updates: 20

// Original array: 
// 0 1 2 3 4 5 6 7 8
// Sorted array: 
// 0 1 2 3 4 5 6 7 8
// Number of min updates: 0