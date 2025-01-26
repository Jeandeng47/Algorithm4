import edu.princeton.cs.algs4.StdOut;

public class P_1_2_5 {
    public static void main(String[] args) {
        // String methods return new String objects
        // but they do not modify the orignal string
        String s = "Hello World";
        s.toUpperCase();
        s.substring(6, 11);
        StdOut.println(s); // Hello World

        s = s.toUpperCase();
        s = s.substring(6, 11);
        StdOut.println(s); // WORLD

    }
}
