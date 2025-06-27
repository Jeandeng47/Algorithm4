import java.util.ArrayDeque;
import java.util.Deque;

// Implement two stacks using a deque
// Use front as stack 1 and back as stack 2.

public class P_1_3_48 {
    public static class TwoStacksDeque {
        private Deque<Integer> deque;
        private int size1;
        private int size2;

        public TwoStacksDeque() {
            this.deque = new ArrayDeque<>();
            this.size1 = 0;
            this.size2 = 0;
        }

        // Push item onto stack 1
        public void push1(int item) {
            deque.addFirst(item);
            size1++;
        }

        // Push item onto stack 2
        public void push2(int item) {
            deque.addLast(item);
            size2++;
        }

        // Pop item from stack 1
        public int pop1() {
            if (size1 == 0) {
                throw new IllegalStateException("Stack 1 is empty");
            }
            int item = deque.removeFirst();
            size1--;
            return item;
        }

        // Pop item from stack 2
        public int pop2() {
            if (size2 == 0) {
                throw new IllegalStateException("Stack 2 is empty");
            }
            int item = deque.removeLast();
            size2--;
            return item;
        }

        public boolean isEmpty1() { return size1 == 0; }
        public boolean isEmpty2() { return size2 == 0; }
        public int size1() { return size1; }
        public int size2() { return size2; }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Stack 1: [");
            for (int i = 0; i < size1; i++) {
                sb.append(deque.toArray()[i]).append(" ");
            }
            sb.append("], Stack 2: [");
            for (int i = size1; i < size1 + size2; i++) {
                sb.append(deque.toArray()[i]).append(" ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        TwoStacksDeque stacks = new TwoStacksDeque();
        stacks.push1(1);
        stacks.push1(2);
        stacks.push2(3);
        stacks.push2(4);
        System.out.println(stacks); // Stack 1: [2 1 ], Stack 2: [3 4 ]
        System.out.println("Pop from Stack 1: " + stacks.pop1()); // 2
        System.out.println("Pop from Stack 2: " + stacks.pop2()); // 4
        System.out.println(stacks); // Stack 1: [1 ], Stack 2: [3 ]
    }
}
