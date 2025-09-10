import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_3_2 {
    public static class RBT <Key extends Comparable<Key>, Value> {
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

        public RBT() { }

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

            h.size = 1 + size(h.left) + size(h.right); // update size

            return h;
        }

        
        // RBT helpers
        // Make a left-leaning link lean to the right
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

        // 2-3 Tree printing utility

        // Construct equivalent 2-3 Tree node from RBT node
        private class Node23 {
            List<Key> keys = new ArrayList<>(2);
            Node23 left, mid, right;

            Node23(Key k) { keys.add(k); }
            Node23(Key k1, Key k2) { keys.add(k1); keys.add(k2); }
        }

        // - If parent has a RED left child, that pair forms a 3-node: [left.key | parent.key]
        //   children are (left.left, left.right, parent.right)
        // - Otherwise, it's a 2-node: [parent.key] with (left, right)
        public Node23 to23N(Node h) {
            if (h == null) return null;
            boolean leftRed = h.left != null && isRed(h.left);
            if (leftRed) { // 3-node
                Node L = h.left;
                Node23 n = new Node23(L.key, h.key);
                n.left = to23N(L.left);
                n.mid = to23N(L.right);
                n.right = to23N(h.right);
                return n;
            } else { // 2 node
                Node23 n = new Node23(h.key);
                n.left = to23N(h.left);
                n.right = to23N(h.right);
                return n;
            }
        }

        private void print23() {
            Node23 root23 = to23N(root);
            if (root23 == null) {
                StdOut.println("Empty");
                return;
            }
            print23(root23, "", "ROOT");
        }

        private void print23(Node23 x, String prefix, String edge) {
            if (x == null) return;

            // choose format according to edge
            String con = switch (edge) {
                case "R"   -> "┌── ";
                case "M"   -> "├── ";
                case "L"   -> "└── ";
                default    -> "";        // ROOT
            };

            String childPrefix = prefix + "   ";

            print23(x.right, childPrefix, "R");
            System.out.println(prefix + con + fmt23Label(x));
            if (x.mid != null) print23(x.mid, childPrefix, "M");
            print23(x.left, childPrefix, "L");
        }

        // Format 2-3 node: [k] for 2-node, [k1 | k2] for 3-node
        private String fmt23Label(Node23 x) {
            if (x.keys.size() == 1) {
                return "[" + x.keys.get(0) + "]";
            } else {
                return "[" + x.keys.get(0) + " | " + x.keys.get(1) + "]";
            }
        }
    }

    public static void main(String[] args) {
        RBT<String, Integer> rbt = new RBT<>();
        String[] seq = "Y L P M X H C R A E S".split("\\s+");
        for (String s : seq) rbt.put(s, 0);

        System.out.println("2-3 tree: ");
        rbt.print23();
    }
   
}

// 2-3 tree: 
//       ┌── [Y]
//    ┌── [X]
//       └── [R | S]
// [P]
//       ┌── [M]
//    └── [C | L]
//       ├── [E | H]
//       └── [A]