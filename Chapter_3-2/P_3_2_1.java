import edu.princeton.cs.algs4.StdOut;

public class P_3_2_1 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;
        private static int cmpCnt;

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
            cmpCnt++;
            if (cmp < 0)        x.left =  put(x.left, key, val);
            else if (cmp > 0)   x.right = put(x.right, key, val);
            else                x.val = val;
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        public int getCmp() {
            return cmpCnt;
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

        for (int i = 0; i < strs.length; i++) {
            bst.put(strs[i], i);
        }

        StdOut.println("BST structure:");
        bst.printBST();
        StdOut.println("\nTotal key compares to build tree: " + bst.getCmp());
    }
}


// BST structure:
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

// Total key compares to build tree: 28