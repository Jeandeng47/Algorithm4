import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

// To compile and run this program:
// make run ARGS="50"

public class P_1_3_5 {
    private static void printBinary(int n) {
        Stack<Integer> stack = new Stack<>();
        StdOut.printf("The binary representation of %d: ", n);
        while (n > 0) {
            stack.push(n % 2);
            n = n / 2;
        }
        
        for (int d : stack) {
            StdOut.print(d);
        }
        StdOut.println();
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: java <N> (positive int)");
        }
        int N = Integer.parseInt(args[0]);
        printBinary(N);
    }
}

// The binary representation of 50: 110010