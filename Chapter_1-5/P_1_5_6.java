import edu.princeton.cs.algs4.StdOut;

public class P_1_5_6 {
    private static final int SECSINDAY = 86400;
    public static void main(String[] args) {
        double n = 1e9; // number of points
        double p = 1e6; // number of connections
        double instrPerSec = 1e9;   // machine speed
        double instrPerLoop = 10;   // cost per loop iteration

        // weighted 
        // cost / union = (2 * cost_of_find())
        // = 2 * height * instrPerLoop
        double height = Math.log(n) / Math.log(2);
        double instrPerUnion = 2 * height * instrPerLoop;
        double totalInstr = instrPerUnion * p;
        double secs = totalInstr / instrPerSec;
        double days = secs / SECSINDAY;

        StdOut.printf("Weighted quick union time:      %.2e days (%.2f seconds)%n",
                          days, secs);
    }
}

// Weighted quick union time:      6.92e-06 days (0.60 seconds)
