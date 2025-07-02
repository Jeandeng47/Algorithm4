import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class P_2_4_5 {
    public static void main(String[] args) {
        // 1-based indexing
        char[] heap = new char[13];
        int n = 0;

        char[] keys = {'E','A','S','Y','Q','U','E','S','T','I','O','N'};

        // Insert keys into the heap
        for (char key : keys) {
            heap[++n] = key;
            swim(heap, n);
        }

        // Print the heap
        StdOut.println("Heap after insertions:");
        for (int i = 1; i <= n; i++) {
            StdOut.print(heap[i] + " ");
        }
        StdOut.println(); // Y T U S Q N E A S I O E
        
    }

    // Restore heap order by swimming item at i up
    private static void swim(char[] chars, int i) {
        while (i > 1 && chars[i / 2] < chars[i]) {
            // Swap parent and child
            char temp = chars[i / 2];
            chars[i / 2] = chars[i];
            chars[i] = temp;
            i /= 2; // Move up to parent
        }
    }
}
