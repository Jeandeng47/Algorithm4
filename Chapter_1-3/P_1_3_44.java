import java.util.ArrayDeque;
import java.util.Deque;

public class P_1_3_44 {
    public static class Buffer {
        Deque<Character> left; // chars to the left of cursor
        Deque<Character> right; // chars to the right of cursor

        public Buffer() {
            this.left = new ArrayDeque<>();
            this.right = new ArrayDeque<>();
        }

        // Insert c at the cursor position
        public void insert(char c) {
            left.push(c);
        }

        // Delete and return the character at the cursor position
        public char delete() {
            if (left.isEmpty()) {
                throw new IllegalStateException("Buffer is empty, cannot delete.");
            }
            return left.pop();
        }

        // Move the cursor k positions to the left
        public void left(int k) {
            if (k < 0) {
                throw new IllegalArgumentException("k must be non-negative");
            }
            for (int i = 0; i < k && !left.isEmpty(); i++) {
                right.push(left.pop());
            }
        }

        // Move the cursor k position to the right
        public void right(int k) {
            if (k < 0) {
                throw new IllegalArgumentException("k must be non-negative");
            }
            for (int i = 0; i < k && !right.isEmpty(); i++) {
                left.push(right.pop());
            }
        }

        // Number of characters in the buffer
        public int size() {
            return left.size() + right.size();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            // left satck: in reverse order
            Deque<Character> temp = new ArrayDeque<>(left);
            while (!temp.isEmpty()) {
                sb.append(temp.removeLast());
            }
            // cursor
            sb.append('|');
            // right stack: in normal order
            for (Character c: right) {
                sb.append(c);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        Buffer b = new Buffer();
        String s = "hello";
        
        for (char c: s.toCharArray()) {
            b.insert(c);
        }
        System.out.println(b); // "hello|"

        // Move cursor to the left by 4 positions
        b.left(4);
        System.out.println(b); // "h|ello"

        // Move cursor to the right by 2 positions
        b.right(2);
        System.out.println(b); // "hel|lo"

        // Insert '!' at the cursor position
        b.insert('!');
        System.out.println(b); // "hel!|lo"

    }
}
