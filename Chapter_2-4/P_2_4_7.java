import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_7 {
    
    public static int depth(int i) {
        if (i <= 0) throw new IllegalArgumentException("i must be > 0");
        return Integer.SIZE - 1 - Integer.numberOfLeadingZeros(i);
    }

    // In binary heap, the k-th largest could only be placed at
    // depth 1...k-1, since the ancestors along the way is the 1st,
    // 2nd, 3rd, ..., (k-1)th largest

    // Therefore, the kth largest element has depth <= k - 1.
    // position depth = Floor(log2(i))

    public static List<Integer> canAppear(int n, int k) {
        if (k == 1) {
            return n >= 1 ? List.of(1) : List.of();
        }
        List<Integer> result = new ArrayList<>();
        // If position i satisfies depth condition, add it
        for (int i = 2; i <= n; i++) {
            int d = depth(i);
            if (d >= 1 && d <= k - 1) result.add(i);
        }
        return result;
    }

    public static List<Integer> cannotAppear(int n, int k) {
        Set<Integer> can = new HashSet<>(canAppear(n, k));
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) if (!can.contains(i)) res.add(i);
        return res;
    }
    
    public static void main(String[] args) {
        int n =31;
        int[] ks = {2, 3, 4};
        
        for (int k : ks) {
            StdOut.println();
            List<Integer> can = canAppear(n, k);
            List<Integer> cannot = cannotAppear(n, k);
            
            StdOut.println("k = " + k + ", n = " + n);
            StdOut.print("can: ");
            for (int v : can) {
                StdOut.print(v + " ");
            }
            StdOut.println();

            StdOut.print("cannot: ");
            for (int v : cannot) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}

// k = 2, n = 31
// can: 2 3
// cannot: 1 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31

// k = 3, n = 31
// can: 2 3 4 5 6 7
// cannot: 1 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31

// k = 4, n = 31
// can: 2 3 4 5 6 7 8 9 10 11 12 13 14 15
// cannot: 1 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
