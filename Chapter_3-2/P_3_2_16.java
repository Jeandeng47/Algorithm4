import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_16 {
    public static class Node { 
        int k; 
        Node l, r; 
        Node(int k){ this.k = k; } 
    }
    
    public static Node insert(Node x, int k) {
        if (x == null) return new Node(k);
        if (k < x.k) x.l = insert(x.l, k);
        else if (k > x.k) x.r = insert(x.r, k);
        else    x.k = k;
        return x;
    }

    // Computer internal and external path length recursively
    public static int[] computePath(Node x, int d) {
        // Base: for null node x, IPL += 0, EPL += d, N += 0
        if (x == null) return new int[]{0, d, 0};
        int[] left = computePath(x.l, d + 1);
        int[] right = computePath(x.r, d + 1);

        // ipl(x) = depth(x) + ipl(x.left) + ipl(x.right)
        // epl(x) = epl(x.left) + epl(x.right)
        // sz(x) = 1 + sz(x.left) + sz(x.right)
        int ipl = d + left[0] + right[0];
        int epl = left[1] + right[1];
        int n = 1 + left[2] + right[2];
        return new int[]{ipl, epl, n};
    }

    // Check if E - I = 2N
    private static void check(Node r) {
        int[] t = computePath(r, 0);
        int ipl = t[0], epl = t[1], n = t[2];
        StdOut.printf("N=%d, ipl=%d, epl=%d, E-I=2N? %s%n", n, ipl, epl, 
        (epl-ipl == 2*n)? "T" : "F");
    }
    

    public static void main(String[] args) {
        Random rnd = new Random(42);
        for (int N = 1; N <= 10; N++) {
            List<Integer> a = new ArrayList<>(N);
            for (int i = 1; i <= N; i++) a.add(i);
            for (int t = 0; t < 2; t++) {
                Collections.shuffle(a, rnd);
                Node r = null;
                for (int k : a) r = insert(r, k);
                check(r);
            }
        }
    }
}


//  For any binary tree with N nodes: E - I = 2N
//      I (internal path length) = sum over all real nodes v of depth(v) in EDGES
//      E (external path length) = sum over all null links of their depth in EDGES

// 1. Goal: in BST
// (1) Average successful search cost  ≈ 1 + I / N
// (2) Average unsuccessful search cost ≈ E / (N + 1)

//  Keys: 5,2,8,1,3,7,9
//  
//           5(0)
//         /      \
//       2(1)     8(1)
//      /  \     /   \
//    1(2) 3(2) 7(2) 9(2)
//  
//   Internal path length (edges):
//     I = 0 + 1 + 1 + 2 + 2 + 2 + 2 = 10
//   External path length:
//     There are N+1 = 8 null links, each at depth 3  =>  E = 8 * 3 = 24
//   Check:
//     E - I = 24 - 10 = 14 = 2N (N=7)
//  