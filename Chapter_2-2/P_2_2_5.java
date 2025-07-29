import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_5 {

    private static void printList(List<Integer> list) {
        StdOut.print("[");
        for (int i = 0; i < list.size(); i++) {
            StdOut.print(list.get(i));
            if (i < list.size() - 1) StdOut.print(", ");
        }
        StdOut.print("]");
        StdOut.println("\n");
    }

    // Simulate the process of top-down merge process
    private static void computeTDsize(int lo, int hi, List<Integer> sizes) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        
        computeTDsize(lo, mid, sizes);
        computeTDsize(mid + 1, hi, sizes);
        // no actual merge, but compute size = hi-lo+1
        int sz = hi - lo + 1;
        sizes.add(sz);
    }

    // Simulate the process of bottom-up merge process
    private static void computeBUsize(int N, List<Integer> sizes) {
        for (int size = 1; size < N; size *= 2) {
            for (int lo = 0; lo + size < N; lo += 2 * size) {
                int mid = lo + size - 1;
                int hi = Math.min(lo + 2 * size - 1, N - 1);
                int sz = hi - lo + 1;
                sizes.add(sz);
            }
        }
    }

    public static void main(String[] args) {
        int N = 39;
        
        // Top Down:
        List<Integer> tdSizes = new ArrayList<>();
        computeTDsize(0, N-1, tdSizes);
        StdOut.println("Top-down mergeSort sizes: ");
        printList(tdSizes);
        

        // Bottom up:
        List<Integer> buSizes = new ArrayList<>();
        computeBUsize(N, buSizes);
        StdOut.println("Bottom-up mergeSort sizes: ");
        printList(buSizes);

    }
}


// Top-down mergeSort sizes: 
// [2, 3, 2, 5, 2, 3, 2, 5, 10, 2, 3, 2, 5, 2, 3, 2, 5, 10, 20, 2, 3, 2, 5, 2, 3, 2, 5, 10, 2, 3, 2, 5, 2, 2, 4, 9, 19, 39]

// Bottom-up mergeSort sizes: 
// [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 8, 8, 8, 8, 7, 16, 16, 32, 39]