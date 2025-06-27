import java.util.NoSuchElementException;
import java.util.Stack;

// Implement a queue with two stacks to simulate O(1) queue operations.

// Lazy evaluation: only move elements when necessary.
// Only move elements from inStack to outStack when outStack is empty.
public class P_1_3_49 {
    
    public static class TwoStackQueue {
        private Stack<Integer> inStack;
        private Stack<Integer> outStack;

        public TwoStackQueue() {
            this.inStack = new Stack<>();
            this.outStack = new Stack<>();
        }

        public boolean isEmpty() {
            return inStack.isEmpty() && outStack.isEmpty();
        }

        public int size() {
            return inStack.size() + outStack.size();
        }

        public void enqueue(int item) {
            inStack.push(item);
        }

        public int dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }
            if (outStack.isEmpty()) {
                transferElements();
            }
            return outStack.pop();
        }

        private void transferElements() {
            while (!inStack.isEmpty()) {
                // the order of elements reversed
                outStack.push(inStack.pop());
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Outstack: [");
            for (int i = outStack.size() - 1; i >= 0; i--) {
                sb.append(outStack.get(i)).append(" ");
            }
            sb.append("] | Instack: [");
            for (int i = inStack.size() - 1; i >= 0; i--) {
                sb.append(inStack.get(i)).append(" ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {

        TwoStackQueue queue = new TwoStackQueue();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println(queue); // Outstack: [] | Instack: [1 2 3 ]

        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println(queue); // Outstack: [2 3 ] | Instack: []

        queue.enqueue(4);
        System.out.println(queue); // Outstack: [2 3] | Instack: [4 ]

        System.out.println("Dequeue: " + queue.dequeue()); // 2
        System.out.println(queue); // Outstack: [3 ] | Instack: [4 ]
    }
        
}
