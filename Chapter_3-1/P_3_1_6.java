import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_6 {
    private static int putCalls;
    private static int getCalls;

    public static void main(String[] args) {

        In in = new In(args[0]);                    // "algs4-data/tale.txt"
        int minLen = Integer.parseInt(args[1]);     // "2"

        int words = 0;
        int distinct = 0;

        ST<String, Integer> st = new ST<String, Integer>();
        
        // frequency count
        while (!in.isEmpty()) {
            String key = in.readString();
            if (key.length() < minLen) continue;
            words++;
            if (st.contains(key)) {
                putCalls++;
                getCalls++;
                st.put(key, st.get(key) + 1);
            } else {
                putCalls++;
                st.put(key, 1);
                distinct++;
            }
        }

        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);

        int observedPuts = putCalls;
        int observedGets = getCalls;

        // Theoretical counts
        int theoryPuts = words;                      // W
        int theoryGets = words - distinct; // W - D

        StdOut.println();
        StdOut.println("Observed puts = " + observedPuts);
        StdOut.println("Observed gets = " + observedGets);
        StdOut.println();
        StdOut.println("Theory puts (W) = " + theoryPuts);
        StdOut.println("Theory gets (W - D) = " + theoryGets);
    }
}

// distinct = 10662
// words    = 130770

// Observed puts = 130770
// Observed gets = 120108

// Theory puts (W) = 130770
// Theory gets (W - D) = 120108
