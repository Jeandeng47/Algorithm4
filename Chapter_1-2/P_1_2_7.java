import edu.princeton.cs.algs4.StdOut;

public class P_1_2_7 {
    public static String mystery(String s) {
        return mysteryHelper(s, 0);
    }

    // Recursively reverse the string by half
    private static String mysteryHelper(String s, int depth) {
        printIndentation(s, depth);
        int N = s.length();
        if (N <= 1) return s;
        String a = s.substring(0, N / 2);
        String b = s.substring(N / 2, N);
        return mysteryHelper(b, depth + 1) + mysteryHelper(a, depth + 1);
    }

    private static void printIndentation(String s, int depth) {
        for (int i = 0; i < depth; i++) {
            StdOut.print("--");
        }
        StdOut.print(" " +s);
        StdOut.println();
    }

    public static void main(String[] args) {
        String s = "abcdef";
        StdOut.println(s);
        StdOut.println(mystery(s));
    }    
}


// abcdef
//  abcdef
// -- def
// ---- ef
// ------ f
// ------ e
// ---- d
// -- abc
// ---- bc
// ------ c
// ------ b
// ---- a
// fedcba