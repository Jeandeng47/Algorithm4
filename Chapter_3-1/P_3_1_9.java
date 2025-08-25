import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_9 {
    public static void main(String[] args) {
        In in = new In(args[0]);                 // "algs4-data/tale.txt"
        int minLen = Integer.parseInt(args[1]);  // try "1", "8", "10"
        
        int distinct = 0;
        int words = 0;
        ST<String, Integer> st = new ST<>();

        String lastInserted = null;
        int wordsBeforeLast = 0;

        while (!in.isEmpty()) {
            String key = in.readString();
            if (key.length() < minLen) continue;
            words++;

            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            } else {
                st.put(key, 1);
                distinct++;
                // trace last call to put(0)
                lastInserted = key;
                wordsBeforeLast = words - 1;
            }
        }

        // find a key with the highest frequency count (textbook pattern)
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);

        // Our instrumentation
        if (lastInserted != null) {
            StdOut.println("lastInserted = " + lastInserted);
            StdOut.println("wordsBeforeLastInsert = " + wordsBeforeLast);
        } else {
            StdOut.println("lastInserted = (none)");
            StdOut.println("wordsBeforeLastInsert = 0");
        }

    }
}

// minLen = 1
// the 7989
// distinct = 10674
// words    = 135643
// lastInserted = blots
// wordsBeforeLastInsert = 135548

// business 122
// distinct = 5126
// words    = 14346
// lastInserted = anniversary
// wordsBeforeLastInsert = 14337

// monseigneur 101
// distinct = 2257
// words    = 4579
// lastInserted = anniversary
// wordsBeforeLastInsert = 4576

