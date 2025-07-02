import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

// To find maximum element in O(1) time using stack or queue,
// we have to keep track of extra states with axiliary space.
public class P_2_4_2 {
    public static class MaxStack<E extends Comparable<E>> {
        private Stack<E> data = new Stack<>();
        private Stack<E> max = new Stack<>();

        public void push(E item) {
            data.push(item);
            if (max.isEmpty() || item.compareTo(max.peek()) > 0) {
                max.push(item);
            } else {
                max.push(max.peek());
            }
        }

        public E pop() {
            max.pop();
            return data.pop();
        }

        public E max() {
            return max.peek();
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }
    }

    public static class MaxQueue<E extends Comparable<E>> {
        private Deque<E> data = new LinkedList<>();
        private Deque<E> candidates = new LinkedList<>();

        public void enqueue(E item) {
            data.addLast(item);
            // evict all smaller elements from candidates 
            // so that the first element in candidates is always the max
            while (!candidates.isEmpty() && item.compareTo(candidates.peekLast()) > 0) {
                candidates.removeLast();
            }
            candidates.addLast(item);
        }

        public E dequeue() {
            if (data.isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            E val = data.removeFirst();
            // if the dequeued element is the maximum, remove it from candidates.
            if (val.equals(candidates.peekFirst())) {
                candidates.removeFirst();
            }
            return val;
        }

        public E max() {
            return candidates.peekFirst();
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }
    }
    public static void main(String[] args) {
        MaxStack<Integer> ms = new MaxStack<>();
        ms.push(4);
        ms.push(7);
        ms.push(5);
        StdOut.println("Current max on stack: " + ms.max()); // 7
        ms.pop(); // pops 5
        ms.pop(); // pops 7
        StdOut.println("New max on stack: " + ms.max());     // 4

        MaxQueue<Integer> mq = new MaxQueue<>();
        mq.enqueue(2);
        mq.enqueue(6);
        mq.enqueue(3);
        StdOut.println("Current max in queue: " + mq.max()); // 6
        mq.dequeue(); // removes 2
        mq.dequeue(); // removes 6
        StdOut.println("New max in queue: " + mq.max());    // 3
    }
}