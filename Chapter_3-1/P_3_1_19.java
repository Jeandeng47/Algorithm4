
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_19 {
    public static void main(String[] args) {
        In in = new In(args[0]); // "algs4-data/tale.txt"
        int minLen = Integer.parseInt(args[1]);  // "2"

        int distinct = 0;
        int words = 0;
        ST<String, Integer> st = new ST<>();

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

        int maxFreq = 0;
        Queue<String> maxWords = new Queue<>();
        for (String w : st.keys()) {
            int f = st.get(w);
            if (f > maxFreq) {
                maxFreq = f;
                // reset queue and keep current max
                maxWords = new Queue<>(); 
                maxWords.enqueue(w);
            } else if (f == maxFreq) {
                maxWords.enqueue(w);
            }
        }
        
        // print results
        for (String w : maxWords) StdOut.println(w + " " + maxFreq);
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
    }
}
