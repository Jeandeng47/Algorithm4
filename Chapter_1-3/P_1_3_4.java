import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class P_1_3_4 {
    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
            || (open == '[' && close == ']')
            || (open == '{' && close == '}');
    }

    public static boolean checkParenthese(String str) {
        Stack<Character> stack = new Stack<>();
        for (Character c : str.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty() || !matches(stack.pop(), c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "[()]{}{[()()]()} ";
        String s2 = "[(])";
        StdOut.printf("S1: %s\n", checkParenthese(s1)? "true" : "false"); // true
        StdOut.printf("S2: %s\n", checkParenthese(s2)? "true" : "false"); // false
    } 
}
