import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;

public class _BST <Key extends Comparable<Key>, Value>{
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

    public _BST() {}

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

    // Return the maximum key in the table
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else                 return max(x.right);            
    }

    // Return the largest key less than or equal to key
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        // when cmp < 0, x is not qualified, continue to 
        // recurse on the left subtree
        if (cmp < 0) return floor(x.left, key);
        // when cmp > 0, it means key > x.key
        // case (1): x is a candidate, remember it
        // case (2): there could exists z in x.right s.t. z.key <= key
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    // Return the smallest key greater than or equal to key
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too large");
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        // when cmp < 0, it means key < x.left
        // case (1): x is a candidate, remember it
        // case (2): there could exists z in x.left s.t. z >= key
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

    // Return the key in the symbol table of the given rank
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("Rank out of boundary");
        }
        return select(root, rank);
    }

    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if      (leftSize > rank) return select(x.left, rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
        else                      return x.key;
    }

    // Return the number of keys strictly less than the given key
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   return rank(x.left, key);
        else if (cmp > 0)   return 1 + size(x.left) + rank(x.right, key);
        else                return size(x.left);
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

    // Remove the largest key and its value
    public void deletMax() {
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        // recurse into the node with no right child
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1; // update
        return x;
    }

    // Remove the specified. key and its value from symbol table
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        // recursive case
        if      (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            // case 1: only have 1 child
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            // case 2: have 2 children
            Node t = x;                     // remember the node to be deleted -> t
            x = min(t.right);               // find successor of t -> x
            x.right = deleteMin(t.right);   // return modified root 
            x.left = t.left;                // concat the original left tree to new root x
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        // case 1: lo <= k <= hi
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        // case 2: lo < k && hi < k
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        // case 3: k > lo && k > hi
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    } 
    
}
