public class _BubbleSort {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(Comparable[] a) {
        // check ascending order
        for (int i = 1; i < a.length; i++) {
            // if a[i] is less than a[i-1], a reverse pair
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    } 

    public static void sort(Comparable[] a) {
        int N = a.length;
        boolean swapped = true;

        do {
            // if no swap, swapped is false, exit
            swapped = false;
            for (int i = 1; i < N; i++) {
                // if a pass has any inversion
                // swapped would be set to true
                if (less(a[i], a[i-1])) {
                    exch(a, i, i-1);
                    swapped = true;
                }
            }
            N--; // shrink unsorted region
        } while (swapped);

    }
}

// Idea:
// 1. For each pass, compare each pair of adjacent elements
// if left is greater than right, swap
// 2. After a pass, the element is guaranteed to be at the 
// final correct position
// 3. If a pass has no swap, it is sorted

// Example: a = [5, 1, 4, 2, 8]

// Pass 1: N = 5
//                a = [5, 1, 4, 2, 8]
// 5 > 1, swap -> a = [1, 5, 4, 2, 8]
// 5 > 4, swap -> a = [1, 4, 5, 2, 8]
// 5 > 2, swap -> a = [1, 4, 2, 5, 8]
// 5 < 8, no swap

// Pass 2: N = 4
//                a = [1, 4, 2, 5, 8]
// 1 < 4, no swap
// 4 > 2, swap -> a = [1, 2, 4, 5, 8]
// 4 < 5, no swap
// 5 < 8, no swap

// Pass 3: N = 3
//                a = [1, 2, 4, 5, 8]
// 1 < 2, no swap
// 2 < 4, no swap
// 4 < 5, no swap
// sorted!