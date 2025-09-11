import edu.princeton.cs.algs4.StdOut;

public class P_3_3_20 {
    public static class Node {
        int key;
        Node left, right;
        Node(int key, Node l, Node r) { this.key = key; left = l; right = r; }
    }

    // Build a perfectly balanced BST on keys [lo..hi] by choosing the middle as root
    public static Node build(int lo, int hi) {
        if (lo > hi) return null;
        int mid = lo + (hi - lo) / 2;
        return new Node(mid, build(lo, mid - 1), build(mid + 1, hi));
    }

    // Compute internal path length
    public static int ipl(Node x, int d) {
        if (x == null) return 0;
        return d + ipl(x.left, d + 1) + ipl(x.right, d + 1);
    }

    public static void printTree(Node x, String prefix, boolean isLeft) {
        if (x == null) return;
        printTree(x.right, prefix + (isLeft ? "│   " : "    "), false);
        StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key);
        printTree(x.left, prefix + (isLeft ? "    " : "│   "), true);
    }

    // # of levels = POWER
    // IPL = Sum {i=0..n(h-1)} i * 2^i
    public static int iplBylevels(int h) {
        int sum = 0;
        int pow2 = 1; // 2^0
        for (int i = 0; i < h; i++) {
            sum += i * pow2;
            pow2 <<= 1;
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int POWER = 1; POWER <= 4; POWER++) {
            int N = (1 << POWER) - 1; // N = 2^POWER - 1
            Node root = build(1, N);
            int ipl = ipl(root, 0);

            StdOut.printf("%nN = %d:%n", N);
            printTree(root, "", false);
            StdOut.printf("IPL = %d, Expected IPL = %d%n", ipl, iplBylevels(POWER));
        }
    }
}


// When N = 2^h - 1, IPL = SUM{i=0...(h-1)}[ i * 2^(h-1)]

// N = 1:
// ┌── 1
// IPL = 0, Expected IPL = 0

// N = 3:
//     ┌── 3
// ┌── 2
// │   └── 1
// IPL = 2, Expected IPL = 2

// N = 7:
//         ┌── 7
//     ┌── 6
//     │   └── 5
// ┌── 4
// │   │   ┌── 3
// │   └── 2
// │       └── 1
// IPL = 10, Expected IPL = 10

// N = 15:
//             ┌── 15
//         ┌── 14
//         │   └── 13
//     ┌── 12
//     │   │   ┌── 11
//     │   └── 10
//     │       └── 9
// ┌── 8
// │   │       ┌── 7
// │   │   ┌── 6
// │   │   │   └── 5
// │   └── 4
// │       │   ┌── 3
// │       └── 2
// │           └── 1
// IPL = 34, Expected IPL = 34