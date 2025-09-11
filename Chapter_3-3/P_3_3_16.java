import edu.princeton.cs.algs4.StdOut;

public class P_3_3_16 {
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

        public RBT() { }
        private boolean isRed(Node x) {
            if (x == null) return false;
            return x.color == RED;
        }
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
            

            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
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
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key + (x.color ? "(R)" : "(B)"));
            printRBT(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }

        // ----- utility -----
        private int size(Node x){ return x==null? 0 :x.size; }
        private void recompute(Node x){
            if (x==null) return;
            recompute(x.left); recompute(x.right);
            x.size = 1 + size(x.left) + size(x.right);
        }

        // Build the RBT given the picture
        public void buildPicture() {
            Node k = new Node(str("k"), null, RED, 1);
            Node m = new Node(str("m"), null, RED, 1);
            Node o = new Node(str("o"), null, BLACK, 1); o.left = m;
            Node l = new Node(str("l"), null, BLACK, 1); l.left = k; l.right = o;
            
            // the left-leaning chain
            Node p = new Node(str("p"), null, RED, 1);      p.left = l;
            Node q = new Node(str("q"), null, BLACK, 1);    q.left = p;
            Node r = new Node(str("r"), null, RED, 1);      r.left = q;
            Node s = new Node(str("s"), null, BLACK, 1);    s.left = r;
            Node t = new Node(str("t"), null, RED, 1);      t.left = s;
            Node u = new Node(str("u"), null, BLACK, 1);    u.left = t;
            
            // root
            Node j = new Node(str("j"), null, BLACK, 1);    j.right = u;
            root = j; 
            recompute(root);
        }

        private Key str(String s){ return (Key)s; }
    }

    public static void main(String[] args) {
        RBT<String, Integer> t = new RBT<>();
        t.buildPicture();
        StdOut.println("Before inserting n: ");
        t.printRBT();
        
        StdOut.println("After inserting n: ");
        t.put("n", null);
        t.printRBT();
    }
}

// Before inserting n: 
// ┌── u(B)
// │   └── t(R)
// │       └── s(B)
// │           └── r(R)
// │               └── q(B)
// │                   └── p(R)
// │                       │   ┌── o(B)
// │                       │   │   └── m(R)
// │                       └── l(B)
// │                           └── k(R)
// j(B)
// 
// After inserting n: 
// ┌── u(B)
// t(B)
// │       ┌── s(B)
// │   ┌── r(B)
// │   │   │   ┌── q(B)
// │   │   └── p(B)
// │   │       │       ┌── o(B)
// │   │       │   ┌── n(B)
// │   │       │   │   └── m(B)
// │   │       └── l(B)
// │   │           └── k(B)
// └── j(R)
