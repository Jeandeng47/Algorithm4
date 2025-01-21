import edu.princeton.cs.algs4.StdOut;

public class P_1_1_7 {
    public static void main(String[] args) {
        // Calculation of Square Root:
        // t_new = (t_old + x/t_old) / 2
        // a. double t: 3.00009
        double t = 9.0;
        while (Math.abs(t - 9.0/t) > .001) {
            t = (9.0/t + t) / 2.0;
        }
        StdOut.printf("%.5f\n", t);

        // Sum of N Numbers: 1 + 2 + 3 + ... + N = N * (N + 1) / 2
        // b. sum1: 499500
        int sum1 = 0;
        for (int i = 1; i < 1000; i++) { // outer: runs i from 1-999
            for (int j = 0; j < i; j++) // inner: runs j from 0 to i-1
                sum1++; // 0 + 1 + 2 + 3 + ... + 999 = 999 * 1000 / 2 = 499500
        }
            
        StdOut.println(sum1);

        // Sun of Power of 2
        // c. sum2: 1023
        int sum2 = 0;
        for (int i = 1; i < 1000; i *= 2) { // outer: runs i from 1, 2, 4 to 512
            for (int j = 0; j < i; j++) // inner: runs j from 0 to i-1
                sum2++; // 1 + 2 + 4 + 8 + ... + 512 = 1023
        }
        StdOut.println(sum2);
    }
}
