import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_3_3_17 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;

        private class Node {
            Key key;
            Value val;
            Node left, right;
            int size;
            public Node(Key key, Value val, int size) {
                this.key = key;
                this.val = val;
                this.size = size;
            }
        }
        public BST() {}
        public int size() { return size(root); }
        private int size(Node x) {
            if (x == null) return 0;
            return x.size;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            if (x == null) { return new Node(key, val, 1); }
            int cmp = key.compareTo(x.key);
            if (cmp < 0)        x.left =  put(x.left, key, val);
            else if (cmp > 0)   x.right = put(x.right, key, val);
            else                x.val = val;
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        public void printBST() {
            if (root == null) { StdOut.println("(empty)"); return; }
            printBST(root.right, "", false);   
            StdOut.println(root.key + "(" + root.val + ")");          
            printBST(root.left,  "", true);  
        }

        private void printBST(Node x, String prefix, boolean isLeft) {
            if (x == null) return;
            printBST(x.right, prefix + (isLeft ? "│   " : "    "), false);
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key + "(" + x.val + ")");
            printBST(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    public static class RBT<Key extends Comparable<Key>, Value> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private Node root;
        private class Node {
            Key key; Value val; Node left, right; int size;
            boolean color;

            public Node(Key key, Value val, boolean color, int size) {
                this.key = key;
                this.val = val;
                this.color = color;
                this.size = size;
            }
        }

        public RBT() { }
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

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key is null");
            root = put(root, key, val);
            root.color = BLACK;
        }
        
        private Node put(Node h, Key key, Value val) {
            if (h == null) return new Node(key, val, RED, 1);
            
            int cmp = key.compareTo(h.key);
            if (cmp < 0)        h.left = put(h.left, key, val);
            else if (cmp > 0)   h.right = put(h.right, key, val);
            else                h.val = val;
            
            // fix-up
            // case 1: red right child + black left child, rotate left
            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            // case 2: red left child + red left chidren, rotate right
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            // case 3: both children are red, flip color
            if (isRed(h.left) && isRed(h.right)) flipColors(h);
            return h;
        }

        private Node rotateLeft(Node h) {
            Node x = h.right; // pick the larger one
            h.right = x.left; // handle original left
            x.left = h;

            x.color = h.color;
            h.color = RED;

            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;

        }

        private Node rotateRight(Node h) {
            Node x = h.left;  // pick the smaller
            h.left = x.right; // handle the original right
            x.right = h;

            x.color = h.color;
            h.color = RED;

            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;
        }

        private void flipColors(Node h) {
            // h must have opposite color of its two children
            h.color = !h.color;
            h.left.color = !h.left.color;
            h.right.color = !h.right.color;
        }


        public void printRBT() {
            if (root == null) { StdOut.println("(empty)"); return; }
            printRBT(root.right, "", false);   
            StdOut.println(root.key + "(B)");          
            printRBT(root.left,  "", true);  
        }

        private void printRBT(Node x, String prefix, boolean isLeft) {
            if (x == null) return;
            printRBT(x.right, prefix + (isLeft ? "│   " : "    "), false);
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key + (x.color? "(R)" : "(B)"));
            printRBT(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }

        public int height() {
            return height(root);
        }
        private int height(Node x) {
            if (x == null) return -1;
            return 1 + Math.max(height(x.left), height(x.right));
        }
    }

    public static void main(String[] args) {
        int N = 16;

        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            keys.add(i + 1);
        }
        Collections.shuffle(keys);
        
        // construct BST
        StdOut.println("The result BST: ");
        BST<Integer, Integer> bst = new BST<>();
        for (Integer k : keys) { bst.put(k,0);}
        bst.printBST();
        StdOut.println();

        // construct RBT
        StdOut.println("The result RBT: ");
        RBT<Integer, Integer> rbt = new RBT<>();
        for (Integer k : keys) { rbt.put(k,0);}
        rbt.printRBT();
        StdOut.println();
    }
}

// The result BST: 
// The result BST: 
//     ┌── 16(0)
//     │   │   ┌── 15(0)
//     │   └── 14(0)
// ┌── 13(0)
// 12(0)
// └── 11(0)
//     │               ┌── 10(0)
//     │               │   └── 9(0)
//     │           ┌── 8(0)
//     │           │   └── 7(0)
//     │       ┌── 6(0)
//     │       │   └── 5(0)
//     │   ┌── 4(0)
//     └── 3(0)
//         └── 2(0)
//             └── 1(0)

// The result RBT: 
//     ┌── 16(B)
// ┌── 15(B)
// │   │   ┌── 14(B)
// │   └── 13(R)
// │       └── 12(B)
// 11(B)
// │       ┌── 10(B)
// │       │   └── 9(R)
// │   ┌── 8(B)
// │   │   └── 7(B)
// └── 6(R)
//     │   ┌── 5(B)
//     │   │   └── 4(R)
//     └── 3(B)
//         └── 2(B)
//             └── 1(R)
