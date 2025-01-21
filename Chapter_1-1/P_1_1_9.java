import edu.princeton.cs.algs4.StdOut;

public class P_1_1_9 {
    public static void main(String[] args) {

        int N = 5;
        String s =  "";

        StdOut.println("Integer N: " + N);

        while (N > 0) {
            s  = (N % 2) + s; // add remainder to the front
            N = N / 2; // get the next bit
        }

        StdOut.println("Binary representation: " + s);
    }
}
