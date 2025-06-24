import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_1_2_15 {
    public static int[] readInts(String name) {
        In in = new In(name);
        String input = in.readAll();
        String[] words = input.split("\\s+");
        int[] ints = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            ints[i] = Integer.parseInt(words[i]);
        }
        return ints;
    }

    public static void main(String[] args) {
        int[] ints = readInts("./algs4-data/tinyW.txt");
        for (int i = 0; i < ints.length; i++) {
            StdOut.print(ints[i] + " ");
        }
        StdOut.println();
    }
}
