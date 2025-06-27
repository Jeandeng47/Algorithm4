// Dijkstra’s Two-Stack Algorithm for Expression Evaluation

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class _Evaluate {
    public static void main(String[] args) {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        // Read the expression from standard input
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals( "(")) {
                // Do nothing for left parenthesis
            } else if (isOperator(s)) {
                // Push operator onto the stack
                ops.push(s);
            } else if (s.equals(")")) {
                // Pop the top operator and two values from the stacks
                String op = ops.pop();
                double v = vals.pop();
                
                switch (op) {
                    case "+":
                        v = vals.pop() + v;
                        break;
                    case "-":
                        v = vals.pop() - v;
                        break;
                    case "*":
                        v = vals.pop() * v;
                        break;
                    case "/":
                        v = vals.pop() / v;
                        break;
                    case "sqrt":
                        v = Math.sqrt(v);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + op);
                }
                // Push the result back onto the values stack
                vals.push(v);
            } else {
                vals.push(Double.parseDouble(s));
            }
        }
        // The final result is the only value left in the stack
        StdOut.printf("Value of expression: %.2f\n", vals.pop());
    }

    // Helper method to check if a string is an operator
    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") 
        || s.equals("*") || s.equals("/") || s.equals("sqrt");
    }

    
}
