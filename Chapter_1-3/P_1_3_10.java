import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;

public class P_1_3_10 {

    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") ||
        s.equals("*") || s.equals("/");
    }

    private static int precedence(String op) {
        if ("*".equals(op) || "/".equals(op)) return 2;
        if ("+".equals(op) || "-".equals(op)) return 1;
        return 0;
    }

    private static void printStack(Stack<String> stack) {
        for (String s : stack) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }

    public static String infixToPostfix(String[] tokens) {
        Stack<String> vals = new Stack<>();
        Stack<String> ops = new Stack<>();

        // Ex: a * ( b + c ) * ( d + e ) - f
        // vals: a (b c + ) (d e +)
        // ops: * *          -> pre("-") <= pre("*")
        //                   -> pop * *
        
        // vals: a (b c + ) (d e +) *
        // ops: *

        // vals: a (b c + ) (d e +) * * f
        // ops: -

        // vals: a (b c +) (d e +) * * f -

        for (String t : tokens) {

            if (t.equals("(")) {
                // record "(" such that we know how to match ")"
                
                // StdOut.printf("\n[DBG] push %s to ops\n", t);
                ops.push(t);

            } else if (isOperator(t)) {

                // StdOut.printf("\n[DBG] Meet op %s\n", t);

                // pop all operators of >= t's precedence, then push t
                while (!ops.isEmpty() 
                && isOperator(ops.peek()) 
                && precedence(ops.peek()) > precedence(t)) {
                    vals.push(ops.pop());
                }
                ops.push(t);
              
            } else if (t.equals(")")) {

                // StdOut.printf("\n[DBG] Meet op %s\n", t);

                // pop until we meet "("
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    vals.push(ops.pop());
                }
                // discard the "("
                if (!ops.isEmpty() && ops.peek().equals("(")) {
                    ops.pop();
                }

            } else {
                // push operands
                // StdOut.printf("\n[DBG] push %s to vals\n", t);
                vals.push(t);
            }
        }

        // If there are remaining ops, go to output
        while (!ops.isEmpty()) {
            vals.push(ops.pop());
        }

        // Build the output
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.size(); i++) {
            sb.append(vals.get(i));
            if (i < vals.size() - 1) sb.append(" ");
        }
        return sb.toString();
        
    }
    public static void main(String[] args) {

        String[] infixArr = {
            "a + b * c", // a b c * +
            "( a + b ) * c ", // "a b + c *"
            "a + ( b + c ) * ( d + e ) - f", //  a b c + d e + * f - +
            "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"  // 1 2 3 + 4 5 * * +
        };

        for (String infix : infixArr) {
            String[] tokens = infix.split("\\s+");
            String postfix = infixToPostfix(tokens);
            StdOut.printf("infix:   %s%n", infix);
            StdOut.printf("postfix: %s%n%n", postfix);
        }
    }
}

// infix:   a + b * c
// postfix: a b c * +

// infix:   ( a + b ) * c 
// postfix: a b + c *

// infix:   a + ( b + c ) * ( d + e ) - f
// postfix: a b c + d e + * f - +

// infix:   ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )
// postfix: 1 2 3 + 4 5 * * +
