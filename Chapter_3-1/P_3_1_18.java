import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_18 {
    // The rank method in BinarySearchST
    public static int rank(BinarySearchST<Integer, Integer> st, int key) throws Exception {
        Method m = st.getClass().getDeclaredMethod("rank", Comparable.class);
        m.setAccessible(true);
        return (Integer) m.invoke(st, key);
    }

    public static int expectedRank(List<Integer> sorted, int key) {
        int idx = Collections.binarySearch(sorted, key);
        return (idx >= 0) ? idx : -idx - 1;
    }

    public static void main(String[] args) throws Exception {
        Random rand = new Random(42);
        BinarySearchST<Integer, Integer> st = new BinarySearchST<>();

        // insert unique keys
        HashSet<Integer> used = new HashSet<>();
        while (used.size() < 64) {
            int k = rand.nextInt(500) - 250; // [0-250, 500-250)
            if (used.add(k)) {
                st.put(k, 1);
            }
        }

        // sort the array
        ArrayList<Integer> sorted = new ArrayList<>(used);
        Collections.sort(sorted);

        // probes: existing keys + to-be-missed keys
        ArrayList<Integer> probes = new ArrayList<>(sorted);
        for (int i = 0; i < 50; i++) probes.add(rand.nextInt(1000)-500); 

        for (int x : probes) {
            int expect = expectedRank(sorted, x);
            int actual = rank(st, x);
            if (expect != actual) {
                StdOut.println("Mismatch: key = " + x + " expect = " + expect + " actual = " + actual);
                return;
            }
        }
        StdOut.println("BinarySearchST's rank() works correctly. ");
            
    
    
    }
}
