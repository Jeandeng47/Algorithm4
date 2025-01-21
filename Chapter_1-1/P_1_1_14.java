import edu.princeton.cs.algs4.StdOut;

public class P_1_1_14 {
    public static int lg(int n) {
        int result = 0;

        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        
        while (n > 1) {
            n = n / 2;
            result++;
        }
        return result;
    }
    public static void main(String[] args) {
        int n = 16;
        StdOut.println("lg(" + n + ") >= " + lg(n));
    }
}
