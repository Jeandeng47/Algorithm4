import edu.princeton.cs.algs4.StdOut;

// To run this program:
// make run ARGS="105 24"

public class P_1_1_24 {

    // Euclid's algorithmn to compute the greatest common divisor of p and q
    public static int gcd(int p, int q) {
        return gcdHelper(p, q, 0);
    }

    private static int gcdHelper(int p, int q, int depth) {

        // GCD algorithmn:
        // If d is a divisor of both a and b (a > b), it must also be a divisor of
        // a % b (a % b is equivalent to a - kb)

        printIndentation(p, q, depth);
        
        if (q == 0) {
            return p;
        }
        int r = p % q;

        return gcdHelper(q, r, depth + 1);
    }

    private static void printIndentation(int p, int q, int depth) {
        for (int i = 0; i < depth; i++) {
            StdOut.print("-");
        }
        StdOut.printf("%3d %3d %3d", depth, p, q);
        StdOut.println();
    } 
    

    public static void main(String[] args) { 
        if (args.length != 2) {
            System.out.println("Usage: <integer1> <integer2>");
            return;
        }

        try {
            int p = Integer.parseInt(args[0]);
            int q = Integer.parseInt(args[1]);
            int gcd = gcd(p, q);
            System.out.printf("Greatest Common Divisor of %d and %d is: %d%n", p, q, gcd);
        } catch (NumberFormatException e) {
            System.out.println("Please provide valid integers.");
        }
      
    }
}

// 0 105  24
// -  1  24   9
// --  2   9   6
// ---  3   6   3
// ----  4   3   0

// 0 1111111 1234567
// -  1 1234567 1111111
// --  2 1111111 123456
// ---  3 123456   7
// ----  4   7   4
// -----  5   4   3
// ------  6   3   1
// -------  7   1   0