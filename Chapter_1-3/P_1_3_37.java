// Josephus Problem
// To run this code, use the command:
// make ARGS="N M"

import edu.princeton.cs.algs4.Queue;

public class P_1_3_37 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java P_1_3_37 <N> <M>");
            return;
        }

        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        Queue<Integer> q = new Queue<>();

        for (int i = 1; i < N; i++) {
            q.enqueue(i);
        }

        while (q.size() > 1) {
            // rotate the M-1 elements to the back
            for (int i = 0; i < M - 1; i++) {
                q.enqueue(q.dequeue());
            }
            // the Mth element is eliminated
            int eliminated = q.dequeue();
            System.out.println("Eliminated: " + eliminated);
        }
        System.out.println("Last remaining person: " + q.dequeue());
    }
}
