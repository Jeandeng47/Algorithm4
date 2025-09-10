import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_3_4 {
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

        // BST height
        public int height() {
            return height(root);
        }
        private int height(Node x) {
            if (x == null) return -1;
            return 1 + Math.max(height(x.left), height(x.right));
        }

        // RBT insertion
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

            // fix-up
            // case 1: right child RED, left child BLACK, rotate left
            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            // case 2: left child RED, left grandchild BLACK, rotate right
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            // case 3: both child RED, flip color
            if (isRed(h.left) && isRed(h.right)) flipColors(h);
            h.size = 1 + size(h.left) + size(h.right); 

            return h;
        }

        private Node rotateLeft(Node h) {
            assert (h != null) && isRed(h.right);
            
            Node x = h.right; // pick the larger node
            h.right = x.left; // handle original left
            x.left = h;
            
            x.color = h.color;
            h.color = RED;
            
            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;
        }

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

        private void flipColors(Node h) {
            // h must have opposite color of its two children
            h.color = !h.color;
            h.left.color = !h.left.color;
            h.right.color = !h.right.color;
        }

        // 2-3 Tree printing utility
        private class Node23 {
            List<Key> keys = new ArrayList<>(2);
            Node23 left, mid, right;
            
            Node23(Key k) { keys.add(k); }
            Node23(Key k1, Key k2) { keys.add(k1); keys.add(k2); }
        }

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


        // Calculate the height of 2-3 tree
        public int height23() {
            Node23 root23 = to23N(root);
            return height23(root23);
        }

        private int height23(Node23 x) {
            if (x == null) return -1;
            int hl = height23(x.left);
            int hm = height23(x.mid);
            int hr = height23(x.right);
            return 1 + Math.max(hl, Math.max(hm, hr));
        }
    }

    private static int hMin(int N) {
        return (int)Math.floor(Math.log(N + 1.0) / Math.log(3.0)) - 1;
    }
    private static int hMax(int N) {
        return (int)Math.ceil(Math.log(N + 1.0) / Math.log(2.0)) - 1;
    }

    public static void main(String[] args) {
        int[] Ns = {1, 2, 3, 7, 8, 15, 31, 32, 63, 64, 100, 256, 1000, 4095, 4096};

        StdOut.printf("%-6s %-6s %-6s %-8s%n", "N", "hMin", "hMax", "observed");

        Random rnd = new Random(42);
        int TRIALS = 30;
        for (int N : Ns) {
            int hi = 0;
            for (int t = 0; t < TRIALS; t++) {
                List<Integer> keys = new ArrayList<>(N);
                for (int k = 0; k < N; k++) keys.add(k);
                Collections.shuffle(keys, rnd);

                RBT<Integer, Integer> rbt = new RBT<>();
                for (int k: keys) {
                    rbt.put(k, 0);
                }
                hi += rbt.height23();
            }
            
            int observed = hi / TRIALS;
            int min = hMin(N);
            int max = hMax(N);

            StdOut.printf("%-6d %-6d %-6d %-8d%n", N, min, max, observed);
        }
    }
}

// All 2-nodes case: N <= 2^(h+1) - 1 
// h_max(N) = ceiling(log_2(N + 1)) - 1

// All 3-nodes case: N >= 3^(h+1) - 1 
// h_min(N) = floor(log_3(N + 1)) - 1

// N      hMin   hMax   observed
// 1      -1     0      0       
// 2      0      1      0       
// 3      0      1      1       
// 7      0      2      1       
// 8      1      3      1       
// 15     1      3      2       
// 31     2      4      3       
// 32     2      5      3       
// 63     2      5      4       
// 64     2      6      4       
// 100    3      6      4       
// 256    4      8      5       
// 1000   5      9      7       
// 4095   6      11     9       
// 4096   6      12     9 