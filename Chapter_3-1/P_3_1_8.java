import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_8 {
    public static void main(String[] args) {
        In in = new In(args[0]);                    // "algs4-data/tale.txt"
        int minLen = Integer.parseInt(args[1]);     // "10"
        int words = 0;
        int distinct = 0;
        
        ST<String, Integer> st = new ST<>();
        
        // frequency count
        while (!in.isEmpty()) {
            String key = in.readString();
            if (key.length() < minLen) continue;
            words++;
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            } else {
                st.put(key, 1);
                distinct++;
            }
        }

        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println("max word = " + max + ", count = " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
    }
}


// max word = monseigneur, count = 101
// distinct = 2257
// words    = 4579
