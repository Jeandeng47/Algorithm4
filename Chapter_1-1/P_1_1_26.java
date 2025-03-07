import edu.princeton.cs.algs4.StdOut;

// To run this program:
// make run ARGS="4 8 5"

public class P_1_1_26 {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);

        StdOut.printf("Before sorting, a: %d, b: %d, c: %d%n", a, b, c);

        int t;

        // Sort a, b, c
        if (a > b) { // swap a and b
            t = a;
            a = b;
            b = t;
        }
        StdOut.printf("Compare a and b, a: %d, b: %d, c: %d%n", a, b, c);

        if (a > c) { // swap a and c
            t = a;
            a = c;
            c = t;
        }
        StdOut.printf("Compare a and b, a: %d, b: %d, c: %d%n", a, b, c);

        if (b > c) { // swap b and c
            t = b;
            b = c;
            c = t;
        }
        StdOut.printf("Compare b and c, a: %d, b: %d, c: %d%n", a, b, c);

        StdOut.printf("After sorting, a: %d, b: %d, c: %d%n", a, b, c);
    }
}

// Before sorting, a: 10, b: 8, c: 5
// Compare a and b, a: 8, b: 10, c: 5
// Compare a and b, a: 5, b: 10, c: 8
// Compare b and c, a: 5, b: 8, c: 10
// After sorting, a: 5, b: 8, c: 10
