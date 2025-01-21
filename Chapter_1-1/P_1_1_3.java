

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_3 {
    public static void main(String[] args) {
        System.out.println("Please enter three integers:");

        // Read three integers from standard input
        int num1 = StdIn.readInt();
        int num2 = StdIn.readInt();
        int num3 = StdIn.readInt();

        // Check if all three integers are equal
        if (num1 == num2 && num2 == num3) {
            StdOut.println("equal");
        } else {
            StdOut.println("not equal");
        }

    }
}
