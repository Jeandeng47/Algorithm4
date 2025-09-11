import edu.princeton.cs.algs4.StdOut;

public class P_3_3_14 {
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
        String str = "A B C D E F G H I J K";
        String[] letters = str.split("\\s+");
        RBT<String, Integer> rbt = new RBT<>();
        for (String l : letters) { 
            StdOut.println("After inserting " + l);
            rbt.put(l, 0); 
            rbt.printRBT();
            StdOut.println();
        }
    }
}

// 1. For ascending order, a red–black tree won’t degenerate like a BST.
//    The self-balancing mechanism ensures height ~2 log N.
// 2. Rotations alternate direction:
//    Ascending → mainly left rotations.
// 3. Recoloring happens frequently to push red links up and maintain black balance.

// After inserting A
// A(B)

// After inserting B
// B(B)
// └── A(R)

// After inserting C
// ┌── C(B)
// B(B)
// └── A(B)

// After inserting D
// ┌── D(B)
// │   └── C(R)
// B(B)
// └── A(B)

// After inserting E
// ┌── E(B)
// D(B)
// │   ┌── C(B)
// └── B(R)
//     └── A(B)

// After inserting F
// ┌── F(B)
// │   └── E(R)
// D(B)
// │   ┌── C(B)
// └── B(R)
//     └── A(B)

// After inserting G
//     ┌── G(B)
// ┌── F(B)
// │   └── E(B)
// D(B)
// │   ┌── C(B)
// └── B(B)
//     └── A(B)

// After inserting H
//     ┌── H(B)
//     │   └── G(R)
// ┌── F(B)
// │   └── E(B)
// D(B)
// │   ┌── C(B)
// └── B(B)
//     └── A(B)

// After inserting I
//     ┌── I(B)
// ┌── H(B)
// │   │   ┌── G(B)
// │   └── F(R)
// │       └── E(B)
// D(B)
// │   ┌── C(B)
// └── B(B)
//     └── A(B)

// After inserting J
//     ┌── J(B)
//     │   └── I(R)
// ┌── H(B)
// │   │   ┌── G(B)
// │   └── F(R)
// │       └── E(B)
// D(B)
// │   ┌── C(B)
// └── B(B)
//     └── A(B)

// After inserting K
//     ┌── K(B)
// ┌── J(B)
// │   └── I(B)
// H(B)
// │       ┌── G(B)
// │   ┌── F(B)
// │   │   └── E(B)
// └── D(R)
//     │   ┌── C(B)
//     └── B(B)
//         └── A(B)
