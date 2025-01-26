import edu.princeton.cs.algs4.StdOut;

public class P_1_2_4 {
    public static void main(String[] args) {
        // Strings in java are immutable. Any changes creating new objects

        String string1 = "hello"; 
        String string2 = string1;
        // String 1 and 2 refers to the same object

        string1 = "world";
        // String 1 now refers to new object
        // String 2 still refers to the original
        StdOut.println(string1); // world
        StdOut.println(string2); // hello
    }
}
