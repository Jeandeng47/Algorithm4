import edu.princeton.cs.algs4.StdOut;


public class P_3_3_9 {
    public static class RBT<Key extends Comparable<Key>, Value> {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private class Node {
            Key key; Value val; 
            Node left, right; int size;
            boolean color; // color of link to parent
            Node(Key key, Value val, boolean color, int size) {
                this.key = key; this.val = val; this.color = color; this.size = size;
            }
        }

        private Node root;
        public void printRBT() {
            if (root == null) { StdOut.println("(empty)"); return; }
            printRBT(root.right, "", false);
            StdOut.println(root.key + "(B)");
            printRBT(root.left,  "", true);
        }
        private void printRBT(Node x, String prefix, boolean isLeft) {
            if (x == null) return;
            printRBT(x.right, prefix + (isLeft ? "│   " : "    "), false);
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key + (x.color ? "(R)" : "(B)"));
            printRBT(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }

        // ----- validator (LLRB rules) -----
        private boolean isRed(Node x){ return x != null && x.color == RED; }
        public boolean isRedBlackBST() {
            // Rule 1: the root is always black
            if (isRed(root)) return false;
            // Rule 2: the tree is binary tree
            if (!isBST(root, null, null)) return false;
            // Rule 3: no red right link & consecutive red links
            if (!is23(root)) return false;
            int bh = blackHeight(root);
            return bh >= 0;
        }
        private boolean isBST(Node x, Key lo, Key hi){
            if (x==null) return true;
            if (lo!=null && x.key.compareTo(lo)<=0) return false;
            if (hi!=null && x.key.compareTo(hi)>=0) return false;
            return isBST(x.left, lo, x.key) && isBST(x.right, x.key, hi);
        }
        private boolean is23(Node x){
            if (x==null) return true;
            if (isRed(x.right)) return false; // left-leaning only
            if (isRed(x) && (isRed(x.left) || isRed(x.right))) return false;
            return is23(x.left) && is23(x.right);
        }

        private int blackHeight(Node x){
            if (x==null) return 1;
            int lh = blackHeight(x.left), rh = blackHeight(x.right);
            if (lh<0 || rh<0 || lh!=rh) return -1;
            return lh + (isRed(x) ? 0 : 1);
        }

        // ----- utility -----
        private int size(Node x){ return x==null?0:x.size; }
        private void recompute(Node x){
            if (x==null) return;
            recompute(x.left); recompute(x.right);
            x.size = 1 + size(x.left) + size(x.right);
        }

        // Build the RBT given the picture

        public void buildPictureI() {
            Node a = new Node(str("A"), null, RED, 1);
            Node d = new Node(str("D"), null, RED  , 1);       // red up to H
            Node h = new Node(str("H"), null, BLACK, 1); h.left = d;
            Node y = new Node(str("Y"), null, BLACK, 1); y.left = h;
            Node c = new Node(str("C"), null, BLACK, 1); c.left = a; c.right = y;
            root = c; recompute(root);
        }

        public void buildPictureII() {
            Node a = new Node(str("A"), null, RED, 1);
            Node f = new Node(str("F"), null, BLACK, 1);
            Node c = new Node(str("C"), null, BLACK  , 1); c.left = a; c.right = f;  // red up to D
            Node d = new Node(str("D"), null, RED  , 1); d.left = c;               // red up to E

            Node g = new Node(str("G"), null, BLACK, 1);
            Node z = new Node(str("Z"), null, BLACK, 1);
            Node h = new Node(str("H"), null, BLACK, 1); h.left = g; h.right = z;

            Node e = new Node(str("E"), null, BLACK, 1); e.left = d; e.right = h;  // root
            root = e; recompute(root);
        }

        public void buildPictureIII() {
            Node a = new Node(str("A"), null, BLACK, 1);
            Node d = new Node(str("D"), null, BLACK, 1);
            Node h = new Node(str("H"), null, BLACK, 1);
            Node z = new Node(str("Z"), null, BLACK, 1);
            Node b = new Node(str("B"), null, BLACK, 1); b.left = a; b.right = d;
            Node y = new Node(str("Y"), null, BLACK, 1); y.left = h; y.right = z;
            Node e = new Node(str("E"), null, BLACK, 1); e.left = b; e.right = y;
            root = e; recompute(root);
        }

        public void buildPictureIV() {
            Node a = new Node(str("A"), null, RED  , 1);       // red up to C
            Node c = new Node(str("C"), null, BLACK, 1); c.left = a;

            Node tN = new Node(str("T"), null, RED  , 1);      // red up to Y
            Node y = new Node(str("Y"), null, BLACK, 1); y.left = tN;

            Node h = new Node(str("H"), null, BLACK, 1); h.left = c; h.right = y;   // root
            root = h; recompute(root);
        }

        private Key str(String s){ return (Key)s; }
    }

    public static void main(String[] args) {
        RBT<String,Integer> t1 = new RBT<>(); t1.buildPictureI();
        RBT<String,Integer> t2 = new RBT<>(); t2.buildPictureII();
        RBT<String,Integer> t3 = new RBT<>(); t3.buildPictureIII();
        RBT<String,Integer> t4 = new RBT<>(); t4.buildPictureIV();

        print("Picture (i)", t1);
        print("Picture (ii)", t2);
        print("Picture (iii)", t3);
        print("Picture (iv)", t4);
    }

    private static void print(String label, RBT<String,Integer> t){
        StdOut.println(label + ":");
        t.printRBT();
        StdOut.println("Is valid LLRB? " + t.isRedBlackBST());
        StdOut.println();
    }
}
