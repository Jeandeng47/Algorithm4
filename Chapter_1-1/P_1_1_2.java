import edu.princeton.cs.algs4.StdOut;

public class P_1_1_2 {
    public static void main(String[] args) {
        // a. (1 + 2.236) / 2
        double a = (1 + 2.236) / 2;
        System.out.println("a. Type: double, Value: " + a);

        // b. 1 + 2 + 3 + 4.0
        double b = 1 + 2 + 3 + 4.0;
        StdOut.println("b. Type: double, Value: " + b);

        // c. 4.1 >= 4
        boolean c = 4.1 >= 4;
        StdOut.println("c. Type: boolean, Value: " + c);

        // d. 1 + 2 + "3"
        String d = 1 + 2 + "3"; // Integer addition occurs first, then concatenation
        StdOut.println("d. Type: String, Value: " + d);
    }
}
