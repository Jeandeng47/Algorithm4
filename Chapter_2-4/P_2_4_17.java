import java.util.Arrays;
import java.util.PriorityQueue;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_17 {
    private static int ops = 0;
    // 1. Build a minimum-oriented priority queue of size k
    // 2. Do N - K replace the min operations
    // 3. Leave the k largest of N items in the priority queue
    public static int[] topK(int[] a, int k) {
        if (k <= 0) return new int[0];
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        int n = a.length;
        int i = 0;

        // put the first k elements
        for (; i < n && pq.size() < k; i++) {
            pq.add(a[i]);
        }

        // for the rest N - k elements, insert then delMax
        for (; i < n; i++) {
            pq.add(a[i]);
            pq.poll(); // remove min
            ops++;
        }

        int[] res = new int[pq.size()];
        for (int j = 0; j < res.length; j++) res[j] = pq.poll();
        return res;
        
    }
    public static void main(String[] args) {
        int[] a = {7, 1, 9, 3, 5, 2, 8, 4, 6};
        int k = 4;
        int[] result = topK(a, k);
        StdOut.println("Input: " + Arrays.toString(a));
        StdOut.println("k largest: " + Arrays.toString(result));
        StdOut.println("Number of replaces: " + ops);

        // Verify by sorting
        int[] sorted = a.clone();
        Arrays.sort(sorted);
        int[] expect = Arrays.copyOfRange(sorted, sorted.length - k, sorted.length);
        System.out.println("Expected: " + Arrays.toString(expect));
    }
}
