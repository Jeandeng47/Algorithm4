import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_5 {
    public static void main(String[] args) {
        StdOut.println("Please enter two numbers between 0 and 1:");
        
        Double x = StdIn.readDouble();
        Double y = StdIn.readDouble();

        StdOut.println(x > 0 && x < 1 && y > 0 && y < 1);
    }
}
