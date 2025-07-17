import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;

public class P_1_3_2 {
    public static void main(String[] args) {
        String input = "it was - the best - of times - - - it  was - the - -";
        String[] tokens = input.split("\\s+");
        Stack<String> stack = new Stack<>();

        StdOut.println("The result: ");
        for (String t : tokens) {
            if (t.equals("-")) {
                StdOut.print(stack.pop() + " ");
            } else {
                stack.push(t);
            }
        }
    }
}

// The result: 
// was best times of the was the it 

