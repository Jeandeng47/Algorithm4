import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_23 {

    public static class BST <Key extends Comparable<Key>, Value>{
        private Node root;

        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            private int size;
            
            public Node(Key key, Value val, int size) {
                this.key = key;
                this.val = val;
                this.size = size;
            }
        }

        public BST() {}

        public int size() { return size(root); }
        public boolean isEmpty() { return size() == 0; }
        private int size(Node x) {
            if (x == null) return 0;
            else return x.size;
        }

        // Return the value associated with the given key
        public Value get(Key key) {
            return get(root, key);
        }
        
        private Value get(Node x, Key key) {
            if (key == null)  throw new IllegalArgumentException("Key cannot be null");
            // base case
            if (x == null)  return null; 
            int cmp = key.compareTo(x.key);
            if      (cmp < 0)   return get(x.left, key);
            else if (cmp > 0 )  return get(x.right, key);
            else                return x.val;
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            return get(key) != null;
        }

        // Insert the key-value pair into the symbol table
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            if (val == null) { delete(key); return; }
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            if (x == null) return new Node(key, val, 1);
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x.left = put(x.left, key, val);
            else if (cmp > 0) x.right = put(x.right, key, val);
            else              x.val = val;
            x.size = 1 + size(x.left) + size(x.right);
            return x;
        }

        // Return the smallest key in the table
        public Key min() {
            if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null) return x;
            else                return min(x.left);
        }

        // Remove the smallest key and its value
        public void deleteMin() {
            if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
            root = deleteMin(root);
        }

        private Node deleteMin(Node x) {
            // base: recurse into the node with no left child
            // when return, the sibling of the deleted node is attached
            // as a left child of their parent
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
            if (cmp < 0) x.left = delete(x.left, key);
            else if (cmp > 0) x.right = delete(x.right, key);
            else {
                // case 1: only 1 child
                if (x.left == null) return x.right;
                if (x.right == null) return x.left;
                // case 2: 2 children
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
            StdOut.println(root.key);          
            printBST(root.left,  "", true);  
        }

        private void printBST(Node x, String prefix, boolean isLeft) {
            if (x == null) return;
            printBST(x.right, prefix + (isLeft ? "│   " : "    "), false);
            StdOut.println(prefix + (isLeft ? "└── " : "┌── ") + x.key);
            printBST(x.left,  prefix + (isLeft ? "    " : "│   "), true);
        }
    }
    public static void main(String[] args) {
        BST<Integer, Integer> bst1 = new BST<>();
        BST<Integer, Integer> bst2 = new BST<>();

        for (int k : new int[]{2, 1, 4, 3}) {
            bst1.put(k, 0);
            bst2.put(k, 0);
        }
        StdOut.println("BST1 & BST2 (same): ");
        StdOut.println("BST1: ");
        bst1.printBST();
        StdOut.println("BST2: ");
        bst2.printBST();

        // order 1
        bst1.delete(1);
        bst1.delete(2);
        StdOut.println("BST1, delete 1 then 2: ");
        bst1.printBST();

        // order 2
        bst2.delete(2);
        bst2.delete(1);
        StdOut.println("BST2, delete 2 then 1: ");
        bst2.printBST();

    }
}


// delete() is not commutative
// BST1 & BST2 (same): 
// BST1: 
// ┌── 4
// │   └── 3
// 2
// └── 1
// BST2: 
// ┌── 4
// │   └── 3
// 2
// └── 1
// BST1, delete 1 then 2: 
// 4
// └── 3
// BST2, delete 2 then 1: 
// ┌── 4
// 3