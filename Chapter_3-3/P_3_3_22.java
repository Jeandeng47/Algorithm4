import java.util.Arrays;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_3_22 {

    private static boolean found = false;
    // Backtrack and generate all enumerations
    public static void dfsBuild(int[] seq, boolean[] used, int pos, int N) {
        // stop when finished
            if (pos == N) {
                BST<Integer, Integer> bst = new BST<>();
                RedBlackBST<Integer, Integer> rbt = new RedBlackBST<>();
                for (int k : seq) {
                    bst.put(k, 0);
                    rbt.put(k, 0);
                }
                int hb = bst.height();
                int hr = rbt.height();

                if (hb < hr) {
                    StdOut.println("Sequence: " + Arrays.toString(seq));
                    StdOut.printf("h(BST) = %d, h(RBT) = %d%n", hb, hr);
                    found = true; 
                }
                return;
            }

            for (int x = 0; x < N; x++) {
                if (!used[x]) {
                    used[x] = true;
                    seq[pos] = x;
                    dfsBuild(seq, used, pos + 1, N);
                    used[x] = false;
                }
            }
    }

    public static class _RBT <Key extends Comparable<Key>, Value> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;
        private Node root;
        
        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            private boolean color;
            private int size;

            public Node(Key key, Value val, boolean color, int size) {
                this.key = key;
                this.val = val;
                this.color = color;
                this.size = size;
            }
        }

        public _RBT() { }

        // Helper method
        private boolean isRed(Node x) {
            if (x == null) return false;
            return x.color == RED;
        }

        public int size() { return size(root); }
        private int size(Node x) {
            if (x == null) return 0;
            return x.size;
        }
        public boolean isEmpty() { return root == null; }

        // Standard BST search

        // Red-black tree insertion
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            root = put(root, key, val);
            root.color = BLACK;
        }
        private Node put(Node h, Key key, Value val) {
            if (h == null) return new Node(key, val, RED, 1);

            int cmp = key.compareTo(h.key);
            if      (cmp < 0) h.left  = put(h.left,  key, val);
            else if (cmp > 0) h.right = put(h.right, key, val);
            else              h.val   = val;

            // fix 
            // case 1: if right child is RED, left child is BLACK, rotate left
            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            // case 2: if left child and left grandchild is RED, rotate right
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            // case 3: if both left and right children is RED, re-color
            if (isRed(h.left) && isRed(h.right)) flipColors(h);

            return h;
        }

        
        // RBT helpers
        // Make a left-leaning link lean to the right
        // (Pick the larger node on the red link as root)
        //        E<=h
        //       / \
        //      a   S<=x
        //         / \
        // old L=>b   c

        //         S<=x                   
        //        / \
        //     E<=h   c          
        //    / \
        //   a   b

        private Node rotateLeft(Node h) {
            assert(h != null) && isRed(h.right);
            Node x = h.right; // pick the larger
            h.right = x.left; // h is to become new-left, handle the original left
            x.left = h;
            
            x.color = h.color; // color of h could be R/B
            h.color = RED;
            
            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;
        }

        // Make a right-leaning link lean to the left
        // (Pick the smaller node on the red link as root)
        //         S<=h                     
        //        / \
        //     E<=x   c          
        //    / \
        //   a   b<=old R

        //        E<=x
        //       / \
        //      a   S<=h
        //         / \
        //        b   c

        private Node rotateRight(Node h) {
            assert(h != null) && isRed(h.left);
            Node x = h.left;  // pick the smaller
            h.left = x.right; // handle the original right
            x.right = h;

            x.color = h.color;
            h.color = RED;

            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;

        }

        // flip the colors of a node and its two children
        private void flipColors(Node h) {
            // h must have opposite color of its two children
            h.color = !h.color;
            h.left.color = !h.left.color;
            h.right.color = !h.right.color;
        }

        public int height() {
            return height(root);
        }
        private int height(Node x) {
            if (x == null) return -1;
            return 1 + Math.max(height(x.left), height(x.right));
        }

        public void printBST() {
                if (root == null) { StdOut.println("(empty)"); return; }
                printBST(root.right, "", false);   
                StdOut.println(root.key + "(" + (root.color ? "R" : "B") + ")");          
                printBST(root.left,  "", true);  
            }

            private void printBST(Node x, String prefix, boolean isLeft) {
                if (x == null) return;
                printBST(x.right, prefix + (isLeft ? "│   " : "    "), false);
                StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key + "(" + (x.color ? "R" : "B") + ")");
                printBST(x.left,  prefix + (isLeft ? "    " : "│   "), true);
            }
    }


    public static void main(String[] args) {
        int N = 6; // number of keys
        int[] seq = new int[N];
        boolean[] used = new boolean[N + 1];

        dfsBuild(seq, used, 0, N);

        if (!found) {
            StdOut.printf("Exhaustive search finished. No witness found for N = %d%n", N);
        }

        // check
        _RBT<Integer, Integer> rbt = new _RBT<>();
        int[] example = new int[]{3, 5, 4, 1, 2, 0};
        for (int n : example) {
            rbt.put(n, 0);
            StdOut.println("After insert " + n);
            rbt.printBST();
        }
    }
}


// N -- 6
// Sequence: [3, 5, 4, 1, 2, 0]
// h(BST) = 2, h(RBT) = 3

// BST: height = 2
//        3
//      /   \ 
//    1     5
//  /  \    /
//  0  2  4

// RBT: height = 3
//      4
//     /  \
//    2     5
//   / \
//  1   3
//  /
// 0    


// After insert 3
// 3(B)
// After insert 5
// 5(B)
// └── 3(R)
// After insert 4
// ┌── 5(B)
// 4(B)
// └── 3(B)
// After insert 1
// ┌── 5(B)
// 4(B)
// └── 3(B)
//     └── 1(R)
// After insert 2
// ┌── 5(B)
// 4(B)
// │   ┌── 3(B)
// └── 2(R)
//     └── 1(B)
// After insert 0
// ┌── 5(B)
// 4(B)
// │   ┌── 3(B)
// └── 2(R)
//     └── 1(B)
//         └── 0(R)