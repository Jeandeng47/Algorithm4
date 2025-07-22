import edu.princeton.cs.algs4.StdOut;

public class P_1_5_5 {
    private static final int SECSINDAY = 86400;
    public static void main(String[] args) {
        double n = 1e9; // number of points
        double p = 1e6; // number of connections
        double instrPerSec = 1e9;   // machine speed
        double instrPerLoop = 10;   // cost per loop iteration

        // quick find:
        // cost / union = instrPerLoop * n
        double instrPerUnion = instrPerLoop * n;
        double totalInstr = p * instrPerUnion;
        double secs = totalInstr / instrPerSec;
        double days = secs / (double) SECSINDAY; 

        StdOut.printf("Quick-Find time:      %.2e days (%.2f seconds)%n",
                          days, secs);
    }

}

// Quick-Find time:      1.16e+02 days (10000000.00 seconds)
