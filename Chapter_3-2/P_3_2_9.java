import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_9 {

    // Node for printing
    public static class Node {
        Node left, right;
        public Node(Node l, Node r) {
            this.left = l;
            this.right = r;
        }
    }

    // Memo: remember all distinct shapes for size n
    private static Map<Integer, List<Node>> memo = new HashMap<>();
    static {
        memo.put(0, Collections.singletonList(null));
    }

    // Build all tree shapes for N nodes using bottom-up DP
    public static List<Node> shapes(int N) {
        if (memo.containsKey(N)) return memo.get(N);
        List<Node> result = new ArrayList<>();
        for (int leftSz = 0; leftSz < N; leftSz++) {
            int rightSz = N - leftSz - 1;
            // for a left tree size of size i
            for (Node L : shapes(leftSz)) {
                // combine a right subtree of size N−1−i
                for (Node R : shapes(rightSz)) {
                    result.add(new Node(L, R));
                }
            }
        }
        // finish adding all the pairs for size N
        memo.put(N, result);
        return result;
    }

    // Calculate catalan number to verify
    // Cn = Binom(2n,n)/(n+1)
    // Binom(2n, n) = (n+1)(n+2)...(n+n) / 1*2*3*...*n
    private static int catalan(int N) {
        int num = 1, den = 1;
        for (int k = 1; k <= N; k++) {
            num = num * (N + k);
            den = num * k;
            // prevent overflow
            int gcd = gcd(num, den);
            num = num / gcd;
            den = den / gcd;
        }
        return num / (den * (N + 1));
    }

    // Compute the greatest common divisor of 2 numbers
    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private static void printBST(Node root) {
        if (root == null) return;
        printBST(root, "", false);
    }

    private static void printBST(Node x, String prefix, boolean isLeft) {
        if (x == null) return;
        printBST(x.right, prefix + (isLeft ? "│   " : "    "), false);
        System.out.println(prefix + (isLeft ?  "└── " : "┌── ") + "•");
        printBST(x.left,  prefix + (isLeft ? "    " : "│   "), true);
    }

    public static void runN(int N) {
        // Compute shapes for each N
        List<Node> list = shapes(N);
        StdOut.printf("%n==============================%nN = %d%n", N);
        StdOut.printf("Unique shapes: %d (Catalan C_%d = %d)%n",
                list.size(), N, catalan(N));
        
        // print the shapes
        int idx = 1;
        for (Node root : list) {
            StdOut.println("\n-- Shape #" + (idx++) + " --");
            StdOut.println("Shape only:");
            printBST(root);
        }
    }
    
    public static void main(String[] args) {

        for (int N = 2; N <= 4; N++) {
            runN(N);
        }
    }
}

// Example: why permutation is bad
// N = 3, Keys = {1, 2, 3}, insertion sequence = 3! = 6
// Order    Shape
// 1,2,3    right llist
// 1,3,2    right then left
// 2,1,3    v shaped
// 2,3,1    v shaped
// 3,2,1    left llist
// 3,1,2    left then right
// Total = 5

// Example: Catalan numbers (no duplicates)
// A BST shape with N nodes is formed by choosing a left subtree of size 
// i and a right subtree of size  N−1−i, and combining every left shape 
// with every right shape. (cartesian product)

// Shapes(N) = U_{i=0..N-1} Shapes(i) × Shapes(N-1-i)
// N = 3, Keys = {1, 2, 3}, 

// Shape{0} = empty
// Shape{1} = single-node
// Shape{2} = 2 (root with left leaf, root with right leaf)

// Build shapes(3) by spiltting
// i = 0, L = Shape{0} = 0, R = Shape{2} = 2, 1 * 2 = 2
// i = 1, L = Shape{1} = 1, R = Shape{1} = 1, 1 * 1 = 1
// i = 2, L = Shape{2} = 2, R = Shape{0} = 1, 1 * 2 = 2
// Total = 2 + 1 + 2 = 5

// ==============================
// N = 2
// Unique shapes: 2 (Catalan C_2 = 0)

// -- Shape #1 --
// Shape only:
//     ┌── •
// ┌── •

// -- Shape #2 --
// Shape only:
// ┌── •
// │   └── •

// ==============================
// N = 3
// Unique shapes: 5 (Catalan C_3 = 0)

// -- Shape #1 --
// Shape only:
//         ┌── •
//     ┌── •
// ┌── •

// -- Shape #2 --
// Shape only:
//     ┌── •
//     │   └── •
// ┌── •

// -- Shape #3 --
// Shape only:
//     ┌── •
// ┌── •
// │   └── •

// -- Shape #4 --
// Shape only:
// ┌── •
// │   │   ┌── •
// │   └── •

// -- Shape #5 --
// Shape only:
// ┌── •
// │   └── •
// │       └── •

// ==============================
// N = 4
// Unique shapes: 14 (Catalan C_4 = 0)

// -- Shape #1 --
// Shape only:
//             ┌── •
//         ┌── •
//     ┌── •
// ┌── •

// -- Shape #2 --
// Shape only:
//         ┌── •
//         │   └── •
//     ┌── •
// ┌── •

// -- Shape #3 --
// Shape only:
//         ┌── •
//     ┌── •
//     │   └── •
// ┌── •

// -- Shape #4 --
// Shape only:
//     ┌── •
//     │   │   ┌── •
//     │   └── •
// ┌── •

// -- Shape #5 --
// Shape only:
//     ┌── •
//     │   └── •
//     │       └── •
// ┌── •

// -- Shape #6 --
// Shape only:
//         ┌── •
//     ┌── •
// ┌── •
// │   └── •

// -- Shape #7 --
// Shape only:
//     ┌── •
//     │   └── •
// ┌── •
// │   └── •

// -- Shape #8 --
// Shape only:
//     ┌── •
// ┌── •
// │   │   ┌── •
// │   └── •

// -- Shape #9 --
// Shape only:
//     ┌── •
// ┌── •
// │   └── •
// │       └── •

// -- Shape #10 --
// Shape only:
// ┌── •
// │   │       ┌── •
// │   │   ┌── •
// │   └── •

// -- Shape #11 --
// Shape only:
// ┌── •
// │   │   ┌── •
// │   │   │   └── •
// │   └── •

// -- Shape #12 --
// Shape only:
// ┌── •
// │   │   ┌── •
// │   └── •
// │       └── •

// -- Shape #13 --
// Shape only:
// ┌── •
// │   └── •
// │       │   ┌── •
// │       └── •

// -- Shape #14 --
// Shape only:
// ┌── •
// │   └── •
// │       └── •
// │           └── •