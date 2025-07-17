import edu.princeton.cs.algs4.Stack;

public class P_1_3_12 {
    public static Stack<String> copy(Stack<String> original) {
        // Store stack content to temp (order reversed)
        Stack<String> temp = new Stack<>();
        for (String s: original) {
            temp.push(s);
        }

        // Restore order
        Stack<String> copy = new Stack<>();
        for (String s: temp) {
            copy.push(s);
        }
        return copy;
    }
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        String[] str = {"A", "B", "C", "D"};
        for (String s : str) {
            stack.push(s);
        }

        // make a copy
        Stack<String> stackCopy = copy(stack);

        // show that both have the same contents in the same order
        System.out.print("Original stack (top→bottom): ");
        for (String s : stack) System.out.print(s + " ");
        System.out.println();

        System.out.print("Copied   stack (top→bottom): ");
        for (String s : stackCopy) System.out.print(s + " ");
        System.out.println();
    }
}
