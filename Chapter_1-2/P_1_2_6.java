import edu.princeton.cs.algs4.StdOut;

public class P_1_2_6 {
    public static boolean isCircularRotation(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        String tt = t + t;
        return tt.contains(s);
    }
    public static void main(String[] args) {
        String s = "ACTGACG";
        String t = "TGACGAC"; // TGACG[AC TGACG]AC

        
        if (isCircularRotation(s, t)) {
            StdOut.println(s + " is a circular rotation of " + t);
        } else {
            StdOut.println(s + " is NOT a circular rotation of " + t);
        }
    }
}

