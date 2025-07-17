import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class P_1_3_9 {

    private static boolean isOperator(String t) {
        return (t.equals("+") || t.equals("-") ||
        t.equals("*") || t.equals("/"));
    }
    public static void main(String[] args) {
        String str = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
        String[] tokens = str.split("\\s+");

        Stack<String> ops = new Stack<>();
        Stack<String> vals = new Stack<>();

        for (String t : tokens) {
            if (t.equals("(")) {
                // do nothing
            } else if (isOperator(t)) {
                ops.push(t);
            } else if (t.equals(")")) {
                String op = ops.pop();
                // v1 operand v2
                String v2 = vals.pop();
                String v1 = vals.pop();
                String exp = "(" + v1 + op + v2 + ")";
                vals.push(exp);
            } else {
                // number
                vals.push(t);
            }
        }

        for (String s: vals) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }   
}
