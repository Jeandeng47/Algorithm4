import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_10 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;

        private class Node {
            Key key;
            Value val;
            Node left, right;
            int size;

            public Node(Key k, Value v, int size) {
                this.key = k;
                this.val = v;
                this.size = size;
            }
        }

        public BST() {}

        // size()
        public int size() { return size(root); }
        private int size(Node x) {
            if (x == null) return 0;
            else return x.size;
        } 
        public boolean isEmpty() { return size() == 0; }
        
        // put()
        public void put(Key k, Value v) {
            if (k == null) throw new IllegalArgumentException("Key cannot be null");
            root = put(root, k, v);
        }

        private Node put(Node x, Key k, Value v) {
            if (x == null) return new Node(k, v, 1);
            int cmp = k.compareTo(x.key);
            if      (cmp < 0)   x.left = put(x.left, k, v);
            else if (cmp > 0)   x.right = put(x.right, k, v);
            else                x.val = v;
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        // get()
        public Value get(Key k) {
            if (isEmpty()) throw new NoSuchElementException("Empty Symbol table");
            if (k == null) throw new IllegalArgumentException("Key cannot be null");
            return get(root, k);
        }

        private Value get(Node x, Key k) {
            if (x == null) return null;
            int cmp = k.compareTo(x.key);
            if      (cmp < 0)   return get(x.left, k);
            else if (cmp > 0)   return get(x.right, k);
            else                return x.val;
        }

        // min()
        public Key min() {
            if (isEmpty()) throw new NoSuchElementException("Empty Symbol table");
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null) return x;
            else                return min(x.left);
        }

        // max()
        public Key max() {
            if (isEmpty()) throw new NoSuchElementException("Empty Symbol table");
            return max(root).key;
        }

        private Node max(Node x) {
            if (x.right == null) return x;
            else                 return max(x.right);
        }

        // floor(): the largest that's smaller than current key
        public Key floor(Key k) {
            if (k == null) throw new IllegalArgumentException("Key cannot be null");
            if (isEmpty()) throw new NoSuchElementException("Empty Symbol table");
            
            Node x = floor(root, k);
            if (x == null) throw new NoSuchElementException("Argument too small");
            else return x.key;
        }

        private Node floor(Node x, Key k) {
            if (x == null) return null;
            int cmp = k.compareTo(x.key);
            if      (cmp == 0)  return x;
            else if (cmp < 0)   return floor(x.left, k);
            Node t = floor(x.right, k);
            if (t != null) return t;
            else return x;
        }

        // ceiling(): the smallest that's larger than the current ke
        public Key ceiling(Key k) {
            if (k == null) throw new IllegalArgumentException("Key cannot be null");
            if (isEmpty()) throw new NoSuchElementException("Empty symbol table");

            Node x = ceiling(root, k);
            if (x == null) throw new NoSuchElementException("Argument too large");
            else return x.key;
        }

        private Node ceiling(Node x, Key k) {
            if (x == null) return null;
            int cmp = k.compareTo(x.key);
            if (cmp == 0) return x;
            if (cmp > 0) return ceiling(x.right, k);
            Node t = ceiling(x.left, k);
            if (t != null) return t;
            else return x;
        }

        // select() and rank()

        public Key select(int rank) {
            if (rank >= size() || rank < 0) {
                throw new IllegalArgumentException("Rank out of boundary");
            }
            return select(root, rank);
        }
        
        private Key select(Node x, int rank) {
            if (x == null) return null;
            int leftSz = size(x.left);
            if (leftSz > rank)      return select(x.left, rank);
            else if (leftSz < rank) return select(x.right, rank - leftSz - 1);
            else                    return x.key; 
        }

        public int rank(Key k) {
            if (k == null) throw new IllegalArgumentException("Key cannot be null");
            return rank(root, k);
        }

        private int rank(Node x, Key k) {
            if (x == null) return 0;
            int cmp = k.compareTo(x.key);
            if (cmp < 0)        return rank(x.left, k);
            else if (cmp > 0)   return 1 + size(x.left) + rank(x.right, k);
            else                return size(x.left);
        }

        // deleteMin() and deleteMax()

        public void deleteMin() {
            if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
            root = deleteMin(root);
        }

        private Node deleteMin(Node x) {
            // base: recurse to the node with no left child
            if (x.left == null) return x.right;
            x.left = deleteMin(x.left);
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        public void deleteMax() {
            if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
            root = deleteMax(root);
        }

        private Node deleteMax(Node x) {
            if (x.right == null) return x.left;
            x.right = deleteMax(x.right);
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        // delete()
        public void delete(Key k) {
             if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
            root = delete(root, k);
        }
        
        private Node delete(Node x, Key k) {
            if (x == null) return null;
            int cmp = k.compareTo(x.key);
            if (cmp < 0)        x.left = delete(x.left, k);
            else if (cmp > 0)    x.right = delete(x.right, k);
            else {
                // case 1: only 1 child
                if (x.left == null)     return x.right;
                if (x.right == null)    return x.left;
                // case 2: 2 children
                Node t = x;
                x = min(t.right); // find successor
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        public boolean contains(Key k) {
            if (k == null) throw new IllegalArgumentException("Key cannot be null");
            try {
                return get(k) != null;
            } catch (NoSuchElementException e) {
                return false;
            }
        }

        public Iterable<Key> keys() {
            List<Key> result = new ArrayList<>();
            inorder(root, result);
            return result;
        }

        private void inorder(Node x, List<Key> result) {
            if (x == null) return;
            inorder(x.left, result);
            result.add(x.key);
            inorder(x.right, result);
        }

    }

   

    public static void main(String[] agrs) {
        String[] str = {"S", "E", "A", "R", "C", "H", "E", "X", 
        "A", "M", "P", "L", "E"};
        BST<String, Integer> bst = new BST<>();
        for (int i = 0; i < str.length; i++) {
            bst.put(str[i], i);
        }

        // 1) Basic properties
        StdOut.println("Size (distinct keys): " + bst.size());            // 10
        StdOut.println("Min: " + bst.min());                              // "A"
        StdOut.println("Max: " + bst.max());                              // "X"

        // 2) Inorder traversal (sorted keys)
        StdOut.print("Keys in order: ");
        for (String k : bst.keys()) StdOut.print(k + " ");
        StdOut.println(); // Expected: A C E H L M P R S X

        // 3) get/contains
        StdOut.println("Contains E? " + bst.contains("E"));               // true
        StdOut.println("Contains Z? " + bst.contains("Z"));               // false
        StdOut.println("get(E): " + bst.get("E"));                        // last index of 'E' in str

        // 4) floor / ceiling
        StdOut.println("floor(G) = " + bst.floor("G"));                   // E
        StdOut.println("ceiling(G) = " + bst.ceiling("G"));               // H
        StdOut.println("floor(A) = " + bst.floor("A"));                   // A
        StdOut.println("ceiling(A) = " + bst.ceiling("A"));               // A
        
    }
}
