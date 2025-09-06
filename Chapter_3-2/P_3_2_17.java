
import edu.princeton.cs.algs4.StdOut;

public class P_3_2_17 {
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

        private Node min(Node x) {
            if (x.left == null) return x;
            else return min(x.left);
        } 

        private Node deleteMin(Node x) {
            if (x.left == null) return x.right;
            x.left = deleteMin(x.left);
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }
        

        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            root = delete(root, key);
        } 
        
        private Node delete(Node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x.left = delete(x.left, key);
            else if (cmp > 0) x.right = delete(x.right, key);
            else {
                // case 1: only 1 child
                if (x.left == null) return x.right;
                if (x.right == null) return x.left;
                // case 2: 2 chilren
                Node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
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

    public static void main(String[] args) {
        String input = "E A S Y Q U E S T I O N";
        String[] strs = input.split("\\s+");
        BST<String, Integer> bst = new BST<>();

        // build BST
        for (int i = 0; i < strs.length; i++) {
            bst.put(strs[i], i);
        }
        StdOut.println("The original BST: ");
        bst.printBST();

        // delete by insertion order
        StdOut.println("BST deleted by insertion order:");
        for (String s: strs) {
            StdOut.println("Delete " + s);
            bst.delete(s);
            bst.printBST();
            StdOut.println();
        }
       
    }
}

// The original BST: 
//     ┌── Y(3)
//     │   └── U(5)
//     │       └── T(8)
// ┌── S(7)
// │   └── Q(4)
// │       │   ┌── O(10)
// │       │   │   └── N(11)
// │       └── I(9)
// E(6)
// └── A(1)
// BST deleted by insertion order:
// Delete E
//     ┌── Y(3)
//     │   └── U(5)
//     │       └── T(8)
// ┌── S(7)
// │   └── Q(4)
// │       └── O(10)
// │           └── N(11)
// I(9)
// └── A(1)

// Delete A
//     ┌── Y(3)
//     │   └── U(5)
//     │       └── T(8)
// ┌── S(7)
// │   └── Q(4)
// │       └── O(10)
// │           └── N(11)
// I(9)

// Delete S
//     ┌── Y(3)
//     │   └── U(5)
// ┌── T(8)
// │   └── Q(4)
// │       └── O(10)
// │           └── N(11)
// I(9)

// Delete Y
//     ┌── U(5)
// ┌── T(8)
// │   └── Q(4)
// │       └── O(10)
// │           └── N(11)
// I(9)

// Delete Q
//     ┌── U(5)
// ┌── T(8)
// │   └── O(10)
// │       └── N(11)
// I(9)

// Delete U
// ┌── T(8)
// │   └── O(10)
// │       └── N(11)
// I(9)

// Delete E
// ┌── T(8)
// │   └── O(10)
// │       └── N(11)
// I(9)

// Delete S
// ┌── T(8)
// │   └── O(10)
// │       └── N(11)
// I(9)

// Delete T
// ┌── O(10)
// │   └── N(11)
// I(9)

// Delete I
// O(10)
// └── N(11)

// Delete O
// N(11)

// Delete N
// (empty)