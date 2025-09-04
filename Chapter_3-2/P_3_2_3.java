import edu.princeton.cs.algs4.StdOut;

public class P_3_2_3 {
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
            StdOut.println(root.key);          
            printBST(root.left,  "", true);  
        }

        private void printBST(Node x, String prefix, boolean isLeft) {
            if (x == null) return;
            printBST(x.right, prefix + (isLeft ? "│   " : "    "), false);
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key);
            printBST(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }

        public int height() {
            return height(root);
        }

        private int height(Node x) {
            if (x == null) return -1;
            return 1 + Math.max(height(x.left), height(x.right));
        }

        public boolean isPerfect() {
            int h = height();
            int expected = (1 << (h+1)) - 1;   // 2^{h+1}-1
            return isFull(root) && h == expected;
        }

        private boolean isFull(Node x) {
            if (x == null) return true;
            if (x.left == null && x.right == null) return true;
            if (x.left != null && x.right != null) return isFull(x.left) && isFull(x.right);
            return false;
        }
    }

    public static void main(String[] args) {
        // Rule to make perfect tree: insert median H, then median of each side
        // C and S as 2nd, 3rd, then insert the rest in any order
        String[][] cases = {
            {"H","C","S","A","E","R","X"},
            {"H","S","C","X","R","E","A"},
            {"H","C","S","R","X","A","E"},
            {"H","S","C","E","A","R","X"},
            {"H","C","S","E","R","A","X"}
        };

        for (int k = 0; k < cases.length; k++) {
            BST<String, Integer> bst = new BST<>();
            for (int i = 0; i < cases[k].length; i++) {
                bst.put(cases[k][i], i);
            }

            StdOut.println("Case " + (k + 1) + ": " + String.join(" ", cases[k]));
            StdOut.println("BST structure:");
            bst.printBST();
            // checks height
            StdOut.println("Perfect structure? " + bst.isPerfect()
                            + " | height=" + bst.height() 
                            + " | N=" + bst.size());
            StdOut.println();
        }
    }
}


// Case 1: H C S A E R X
// BST structure:
//     ┌── X
// ┌── S
// │   └── R
// H
// │   ┌── E
// └── C
//     └── A
// Perfect structure? false | height=2 | N=7

// Case 2: H S C X R E A
// BST structure:
//     ┌── X
// ┌── S
// │   └── R
// H
// │   ┌── E
// └── C
//     └── A
// Perfect structure? false | height=2 | N=7

// Case 3: H C S R X A E
// BST structure:
//     ┌── X
// ┌── S
// │   └── R
// H
// │   ┌── E
// └── C
//     └── A
// Perfect structure? false | height=2 | N=7

// Case 4: H S C E A R X
// BST structure:
//     ┌── X
// ┌── S
// │   └── R
// H
// │   ┌── E
// └── C
//     └── A
// Perfect structure? false | height=2 | N=7

// Case 5: H C S E R A X
// BST structure:
//     ┌── X
// ┌── S
// │   └── R
// H
// │   ┌── E
// └── C
//     └── A
// Perfect structure? false | height=2 | N=7