import edu.princeton.cs.algs4.StdOut;

public class P_1_1_4 {
    public static void main(String[] args) {
        int a = 5;
        int b = 3;
        int c = -1;

        // a. if (a > b) then c = 0; -> Incorrect
        if (a > b) {
            c = 0;
        }
        StdOut.println("a. c = " + c);
        
        // b. if a > b { c = 0; } -> Incorrect
        c = -1;
        if (a > b) {
            c = 0;
        }
        StdOut.println("b. c = " + c);

        // c. if (a > b) c = 0;
        c = -1;
        if (a > b) c = 0;
        StdOut.println("c. c = " + c);

        // d. if (a > b) c = 0 else b = 0; -> Incorrect
        c = -1;
        if (a > b) {
            c = 0;
        } else {
            b = 0;
        }
        StdOut.println("d. c = " + c + ", b = " + b);
    }
}
