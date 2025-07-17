import java.util.function.Predicate;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_1_3_13 {
    public static boolean isValidPerm(int[] perm) {
        int n = perm.length;
        Queue<Integer> q = new Queue<>();
        int nextEnq = 0;
        // 4, 6, 8, 7, 5, 3, 2, 9, 0, 1

        // while loop, want = 4
        // next queue
        // 0    [0]
        // 1.   [0, 1,] 
        // 2.   [0, 1, 2]
        // 3.   [0, 1, 2, 3]
        // 4.   [0, 1, 2, 3, 4]

        // dequeue front = 0 but want is 4

        for (int want : perm) {
            while (nextEnq < n && (q.isEmpty() || q.peek() != want)) {
                q.enqueue(nextEnq);
                nextEnq++;
            }
            // if the head is want, dequeue it
            if (!q.isEmpty() && q.peek() == want) {
                q.dequeue();
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        int[][] sequences = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},  // a
            {4, 6, 8, 7, 5, 3, 2, 9, 0, 1},  // b
            {2, 5, 6, 7, 4, 8, 9, 3, 1, 0},  // c
            {4, 3, 2, 1, 0, 5, 6, 7, 8, 9}   // d
        };
        char[] labels = {'a', 'b', 'c', 'd'};

        for (int i = 0; i < sequences.length; i++) {
            boolean valid = isValidPerm(sequences[i]);
            StdOut.printf("%c: %s%n", labels[i], valid ? "valid" : "invalid");
        }
    }
}
