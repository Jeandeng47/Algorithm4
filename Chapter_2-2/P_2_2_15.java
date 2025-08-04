import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_15 {

    public static <T extends Comparable<? super T>> Queue<T> mergeSort(Queue<T> items) {
        // Create queue of N queues
        Queue<Queue<T>> qqueue = new LinkedList<>();
        for (T item : items) {
            // create a queue for each item
            Queue<T> qitem = new LinkedList<>();
            qitem.add(item);
            qqueue.add(qitem);
        }

        // Repeatedly merge queue into one queue
        while (qqueue.size() > 1) {
            Queue<T> q1 = qqueue.poll();
            Queue<T> q2 = qqueue.poll();
            Queue<T> merged = merge(q1, q2);
            qqueue.add(merged); // add back merged for next round
        }
        // at the end we have only one queue
        return qqueue.poll();
    }

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
        Queue<Integer> input = new LinkedList<>(List.of(5, 1, 4, 2, 3, 8, 7, 6));
        StdOut.println("Unsorted: " + input);
        Queue<Integer> sorted = mergeSort(input);
        StdOut.println(" Sorted : " + sorted);
    }
}
