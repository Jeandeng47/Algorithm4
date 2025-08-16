import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_4 {

    // Max-heap property: parent larger than child

    // Check max-heap for 0-based array
    // parent(i) = (i - 1) / 2
    public static <T extends Comparable<T>> boolean isMaxHeap0(T[] a) {
        for (int i = 1; i < a.length; i++) {
            int p = (i - 1) / 2;
            if (a[p].compareTo(a[i]) < 0) return false;
        }
        return true;
    }

    // Check max-heap for 1-based array
    // parent(i) = i / 2
    public static <T extends Comparable<T>> boolean isMaxHeap1(T[] a) {
        for (int i = 1; i < a.length; i++) {
            int p = i / 2;
            if (a[p].compareTo(a[i]) < 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // strictly decreasing -> TRUE
        Integer[] dec = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        StdOut.println("Decreasing (0-based): " + Arrays.toString(dec));
        StdOut.println("isMaxHeap0? " + isMaxHeap0(dec));

        // valid max-heap that's not sorted -> TRUE
        Integer[] heapNotSorted = {9, 7, 8, 3, 6, 5, 4, 1, 2};
        StdOut.println("\nHeap but not sorted: " + Arrays.toString(heapNotSorted));
        StdOut.println("isMaxHeap0? " + isMaxHeap0(heapNotSorted));

        // not a heap (child bigger than parent) -> FALSE
        Integer[] notHeap = {9, 8, 7, 6, 5, 9, 3, 2, 1}; // 9 under 5 breaks property
        StdOut.println("\nNot a heap: " + Arrays.toString(notHeap));
        StdOut.println("isMaxHeap0? " + isMaxHeap0(notHeap));
    }
}


// Decreasing (0-based): [9, 8, 7, 6, 5, 4, 3, 2, 1]
// isMaxHeap0? true

// Heap but not sorted: [9, 7, 8, 3, 6, 5, 4, 1, 2]
// isMaxHeap0? true

// Not a heap: [9, 8, 7, 6, 5, 9, 3, 2, 1]
// isMaxHeap0? false