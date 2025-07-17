import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;

public class P_1_3_11 {

    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") ||
        s.equals("*") || s.equals("/");
    }

    private static int calculate(String op, int v1, int v2) {
         switch (op) {
            case "+": return v1 + v2;
            case "-": return v1 - v2;
            case "*": return v1 * v2;
            case "/": return v1 / v2;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }  
    }

    public static int evaluatePostfix(String[] tokens) {
        Stack<Integer> vals = new Stack<>();

        for (String t : tokens) {
            if (isOperator(t)) {
                int v2 = vals.pop();
                int v1 = vals.pop();
                int sub = calculate(t, v1, v2);
                vals.push(sub);

            } else {
                // push operands
                vals.push(Integer.parseInt(t));
            }
        }
        return vals.pop();
    }

    public static void main(String[] args) {
        String[] posfixArr = {
            "1 2 3 * +", // 1 + 2 * 3
            "1 2 + 3 *", // 1 * 2 + 3
            "1 2 3 + 4 5 + * 6 - +", // 1 + (2 + 3) * (4 + 5) - 6
            "1 2 3 + 4 5 * * +", // ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )
        };        

        for (String pos : posfixArr) {
            String[] tokens = pos.split("\\s+");
            int result = evaluatePostfix(tokens);
            StdOut.printf("%s = %d\n", pos, result);
        }
    }
}


// infix:   1 + 2 * 3 = 7
// postfix: 1 2 3 * +

// infix:   ( 1 + 2 ) * 3 = 9
// postfix: 1 2 + 3 *

// infix:   1 + ( 2 + 3 ) * ( 4 + 5 ) - 6 = 40
// postfix: 1 2 3 + 4 5 + * 6 - +

// infix:   ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) = 101
// postfix: 1 2 3 + 4 5 * * +
