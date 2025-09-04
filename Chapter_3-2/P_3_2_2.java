import edu.princeton.cs.algs4.StdOut;

public class P_3_2_2 {
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

        // Worst path: all internal nodes have only 1 child
        public boolean isWorstPath() {
            return isWorst(root);
        }

        private boolean isWorst(Node x) {
            if (x == null) return false;
            int childCnt = (x.left != null? 1 : 0) + (x.right != null? 1 : 0);
            if (childCnt == 0) return true;
            if (childCnt == 1) {
                return isWorst(x.left != null? x.left : x.right);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        String[][] cases = {
            {"A","C","E","H","R","S","X"},  // 1) increasing
            {"X","S","R","H","E","C","A"},  // 2) decreasing
            {"A","X","S","R","H","E","C"},  // 3) min, max, then decreasing in (A, X)
            {"X","A","C","E","H","R","S"},  // 4) max, min, then increasing in (A, X)
            {"A","X","C","S","E","R","H"},  // 5) min, max interleaving
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
            StdOut.println("Worst-case path? " + bst.isWorstPath()
                            + " | height=" + bst.height() 
                            + " | N=" + bst.size());
            StdOut.println();
        }
    }
}


// Case 1: A C E H R S X
// BST structure:
//                     ┌── X
//                 ┌── S
//             ┌── R
//         ┌── H
//     ┌── E
// ┌── C
// A
// Worst-case path? true | height=7 | N=7

// Case 2: X S R H E C A
// BST structure:
// X
// └── S
//     └── R
//         └── H
//             └── E
//                 └── C
//                     └── A
// Worst-case path? true | height=7 | N=7

// Case 3: A X S R H E C
// BST structure:
// ┌── X
// │   └── S
// │       └── R
// │           └── H
// │               └── E
// │                   └── C
// A
// Worst-case path? true | height=7 | N=7

// Case 4: X A C E H R S
// BST structure:
// X
// │                   ┌── S
// │               ┌── R
// │           ┌── H
// │       ┌── E
// │   ┌── C
// └── A
// Worst-case path? true | height=7 | N=7

// Case 5: A X C S E R H
// BST structure:
// ┌── X
// │   │   ┌── S
// │   │   │   │   ┌── R
// │   │   │   │   │   └── H
// │   │   │   └── E
// │   └── C
// A
// Worst-case path? true | height=7 | N=7