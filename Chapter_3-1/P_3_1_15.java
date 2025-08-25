import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_15 {
    private static final int RATIO = 1000;

    private static void run(int n) {
        int inserts = n / RATIO;
        BinarySearchST<Integer, Integer> st  = new BinarySearchST<>();

        Random rand = new Random(42);
        long tSearch = 0;
        long tInsert = 0;
        

        // Intermixed operations: Do 1000 search then insert 1 new key
        for (int j = 0; j < inserts; j++) {
            // 1000 searches
            int present = Math.max(1, j);
            long t0 = System.nanoTime();
            for (int t = 0; t < RATIO; t++) {
                int k = 1 + rand.nextInt(present); // 1...present
                st.get(k);
            }
            tSearch += System.nanoTime() - t0;

            // one insertion of a fresh key (forces array shift cost)
            int newKey = j + 1;
            long i0 = System.nanoTime();
            st.put(newKey, 1);
            tInsert += System.nanoTime() - i0;
        }
       
        double msSearch = tSearch / 1e6, msInsert = tInsert / 1e6;
        double pctInsert = 100.0 * msInsert / (msInsert + msSearch);

        StdOut.printf("searches=%12d  inserts=%8d  search=%.2f ms  insert=%.2f ms  %%insert=%.3f%%%n",
                n, inserts, msSearch, msInsert, pctInsert);


    }

    public static void main(String[] args) {
        int[] Ns = {1_000, 1_000_000, 1_000_000_000 };
        for (int n : Ns) {
            run(n);
        }
    }
}

// searches=        1000  inserts=       1  search=0.24 ms  insert=0.00 ms  %insert=0.790%
// searches=     1000000  inserts=    1000  search=52.74 ms  insert=0.14 ms  %insert=0.260%
// searches=  1000000000  inserts= 1000000  search=162759.03 ms  insert=97.64 ms  %insert=0.060%

// When #searches is relatively large to #insertions (ratio = 1000), Binary
// searchST is a good choice since %insertion time is decreasing as the key
// size is increasing