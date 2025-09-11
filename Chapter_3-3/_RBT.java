public class _RBT <Key extends Comparable<Key>, Value> {
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

        h.size = 1 + size(h.left) + size(h.right);

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


}
