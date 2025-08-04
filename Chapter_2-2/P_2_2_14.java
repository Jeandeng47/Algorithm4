import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_14 {

    public static <T extends Comparable<? super T>> Queue<T> merge(Queue<T> q1, Queue<T> q2) {
        Queue<T> merged = new LinkedList<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (less(q1.peek(), q2.peek())) {
                merged.add(q1.poll());
            } else {
                merged.add(q2.poll());
            }
        }
        while (!q1.isEmpty()) merged.add(q1.poll());
        while (!q2.isEmpty()) merged.add(q2.poll());
        return merged;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    public static void main(String[] args) {
        Queue<Integer> a = new LinkedList<>(List.of(1, 3, 5, 7));
        Queue<Integer> b = new LinkedList<>(List.of(2, 4, 6, 8));
        Queue<Integer> c = merge(new LinkedList<>(a), new LinkedList<>(b)); // pass a copy
        
        StdOut.println("a: " + a);
        StdOut.println("b: " + b);
        StdOut.println("Merged into c: " + c);
    }
}
