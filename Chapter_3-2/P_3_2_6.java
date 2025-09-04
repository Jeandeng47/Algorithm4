import edu.princeton.cs.algs4.StdOut;

public class P_3_2_6 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;
        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            private int size;       // subtree size
            private int height;     // subtree height (cached)

            public Node(Key key, Value val, int size, int height) {
                this.key = key;
                this.val = val;
                this.size = size;
                this.height = height;
            }
        }

        public BST() {}

        public boolean isEmpty() {  return size() == 0; }
        public int size() { return size(root); }
        private int size(Node x) { 
            if (x == null) return 0;
            else return x.size;
        }

        // Height (O(1) per query)
        public int height() {   return height(root); }
        private int height(Node x) {
            if (x == null) return -1;
            else return x.height;
        }

        // Height (on-demand recursive)
        public int heightRec() { return heightRec(root); }
        private int heightRec(Node x) {
            if (x == null) return -1;
            int hLeft = heightRec(x.left);
            int hRight = heightRec(x.right);
            return 1 + (hLeft > hRight? hLeft : hRight);
        }

        
        // modified put(): cache subtree size & height
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("null key");
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            // new node: size = 1, height = 0
            if (x == null) return new Node(key, val, 1, 0);
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = put(x.left, key, val);
            else if (cmp > 0) x.right = put(x.right, key, val);
            else x.val = val;

            // update size & height
            x.size = 1 + size(x.left) + size(x.right);
            x.height = 1 + Math.max(height(x.left), height(x.right));
            return x;
        }
    }

    public static void main(String[] args) {
            BST<Integer, Integer> bst = new BST<>();

            // Build a balanced-ish tree
            int[] keys = {5, 2, 8, 1, 3, 7, 9, 6};
            for (int k : keys) bst.put(k, k);

            StdOut.println("size = " + bst.size());                // 8
            StdOut.println("height (cached)     = " + bst.height()); // 3
            StdOut.println("height (recursive)  = " + bst.heightRec()); // 3

            // Build a skewed tree (worst case) to see height grow to N-1
            BST<Integer, Integer> skew = new BST<>();
            for (int k = 1; k <= 6; k++) skew.put(k, k);               // strictly increasing
            StdOut.println("skew.size = " + skew.size());          // 6
            StdOut.println("skew.height (cached)    = " + skew.height());         // 5
            StdOut.println("skew.height (recursive) = " + skew.heightRec()); // 5
        }
}
