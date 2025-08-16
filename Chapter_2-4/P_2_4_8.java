import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_8 {
    
    //  Count nodes in subtree rotted at position i 
    // (1-based)

    // Example
    // idx  0  1. 2. 3. 4. 5  6. 7
    // a = [-, 1, 2, 3, 4, 5, 6, 7]
    // level 0: 1
    // level 1: 2 3
    // level 3: 4 5 6 7

    // subtreeSize(n=7, i=4)
    // Op       cnt     l       r       q
    // add(3)   0       -       -       [3]
    // poll3    1       6       7       [6, 7]
    // poll6    2       12      13      [7]
    // poll7    3       14      15      [-]

    private static int subtreeSize(int n, int i) {
        int cnt = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();

        if (i <= n) q.add(i);
        // BFS: count all springs of i
        while (!q.isEmpty()) {
            int k = q.poll(); // take out parent
            cnt++;
            int l = 2 * k; // left child
            int r = l + 1; // right child
            if (l <= n) q.add(l);
            if (r <= n) q.add(r);
        }
        return cnt;
    }


    // In a max-heap, all the keys in the subtree of k-th smallest 
    // key are smaller than it. Thus, the subtree size at most has
    // k nodes (the k-th smallest key + subtree)

    public static List<Integer> canAppear(int n, int k) {
        List<Integer> result = new ArrayList<>();
        // check if position i satisfy the subtree condition
        for (int i = 1; i <= n; i++) {
            if (subtreeSize(n, i) <= k) {
                result.add(i);
            }
        }
        return result;
    }

    public static List<Integer> cannotAppear(int n, int k) {
        HashSet<Integer> can = new HashSet<>(canAppear(n, k));
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) if (!can.contains(i)) res.add(i);
        return res;
    }

    public static void main(String[] args) {
        int n = 31;
        int[] ks = {2,3,4};
        for (int k : ks) {
            StdOut.println("k=" + k);
            StdOut.println("can    : " + canAppear(n, k));
            StdOut.println("cannot : " + cannotAppear(n, k));
            StdOut.println();
        }
    }
}


// The subtree rooted at i=3 has size 3 (3, 6, 7)


// k=2
// can    : [16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
// cannot : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]

// k=3
// can    : [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
// cannot : [1, 2, 3, 4, 5, 6, 7]

// k=4
// can    : [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
// cannot : [1, 2, 3, 4, 5, 6, 7]