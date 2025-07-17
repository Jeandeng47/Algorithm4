
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class P_1_3_3 {

    private static boolean checkSequence(int[] seq) {
        Stack<Integer> stack = new Stack<>();
        int nextPush = 0;
        int n = seq.length;
        
        for (int want : seq) {
            // If stack top doesn't match current num, push until we reach this num
            while (nextPush < n && ( stack.isEmpty() || stack.peek() != want)) {
                stack.push(nextPush);
                nextPush++;
            }

            if (!stack.isEmpty() && stack.peek() == want) {
                stack.pop();
            } else {
                // StdOut.printf("Expect %d but get %d at top\n", want, stack.peek());
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        int[][] sequences = {
            {4,3,2,1,0,9,8,7,6,5},     // a
            {4,6,8,7,5,3,2,9,0,1},     // b
            {2,5,6,7,4,8,9,3,1,0},     // c
            {4,3,2,1,0,5,6,7,8,9},     // d
            {1,2,3,4,5,6,9,8,7,0},     // e
            {0,4,6,5,3,8,1,7,2,9},     // f
            {1,4,7,9,8,6,5,3,0,2},     // g
            {2,1,4,3,6,5,8,7,9,0}      // h
        };
        char[] labels = {'a','b','c','d','e','f','g','h'};

        for (int i = 0; i < sequences.length; i++) {
            boolean valid = checkSequence(sequences[i]);
            StdOut.printf("%c: %s%n", labels[i], valid? "valid" : "invalid");
        }
    }
}


// a: valid
// b: invalid
// c: valid
// d: valid
// e: valid
// f: invalid
// g: invalid
// h: valid