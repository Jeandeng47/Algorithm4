import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_3_3 {
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

    

    // Back-tracking: find the insertion order that yields height of 1
    public static List<List<String>> findOrder(String[] keys) {
        List<List<String>> result = new ArrayList<>();
        backtrack(keys, 0, result);
        return result;
    }

    private static void backtrack(String[] keys, int d, List<List<String>> result) {
        // check the height of keys[0..d-1], stop
        if (d > 0 && prefixH1(keys, d) > 1) return;

        // all keys added, stop
        if (d == keys.length) {
            if (prefixH1(keys, d) == 1) {
                List<String> perm = new ArrayList<>(Arrays.asList(keys.clone()));
                result.add(perm);
            }
        }

        // in-place permutation
        for (int i = d; i < keys.length; i++) {
            swap(keys, i, d); // put k[i] to d pos of prefix
            backtrack(keys, d + 1, result); // extend backtracking
            swap(keys, i, d); // put k[i] back
        }
    }

    private static void swap(String[] a, int i, int j) {
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static int prefixH1(String[] keys, int d) {
        RBT<String, Integer> bst = new RBT<>();
        for (int i = 0; i < d; i++) { bst.put(keys[i], 0);}
        int height23 = bst.height23();
        return height23;
    }


    
    public static void main(String[] args) {
        String[] keys = "S E A R C H X M".split("\\s+");
        List<List<String>> orders = findOrder(keys);
        StdOut.println("The number of orders that result in RBT of height 1: " + orders.size()); // 5760

        int LIMIT = 50;
        StdOut.println("Print out the first 50 orders: ");
        for (int i = 0; i < Math.min(LIMIT, orders.size()); i++) {
            StdOut.println("Order "+ (i+1) + ": "+ String.join(", ", orders.get(i)));
        }
        StdOut.println();

        // print the three 2-3 tree out to verify
        StdOut.println("Construct 2-3 trees: ");
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {

            int r = rand.nextInt(orders.size());
            List<String> order = orders.get(r);
            StdOut.println("Tree " + (r+1));

            // print order
            for (int j = 0; j < order.size(); j++) {
                StdOut.printf(order.get(j) + " ");
            }
            StdOut.println();
            // print tree
            RBT<String, Integer> rbt = new RBT<>();
            for(int j = 0; j < order.size(); j++) {
                rbt.put(order.get(j), 0);
            }
            rbt.print23();
        }

    }
}

// Construct 2-3 trees: 
// Tree 5359
// X C E M R A S H 
//    ┌── [S | X]
// [E | R]
//    ├── [H | M]
//    └── [A | C]
// Tree 5333
// X C E R M S H A 
//    ┌── [S | X]
// [E | R]
//    ├── [H | M]
//    └── [A | C]
// Tree 1230
// E R C A M X S H 
//    ┌── [S | X]
// [E | R]
//    ├── [H | M]
//    └── [A | C]


//   Backtracking with in-place permutation 

// void perm(String[] a, int d) {
//     if (d > 0 && height23(buildTreeFrom(a[0..d-1])) > 1) return;

//     if (d == a.length) {
//         if (height23(buildTreeFrom(a)) == 1) save(a);
//         return;
//     }

//     for (int i = d; i < a.length; i++) {
//         swap(a, d, i);       // put unused candidate to position d of prefix
//         perm(a, d + 1);      // move to next position
//         swap(a, d, i);       // backtrack: recover the shape
//     }
// }


//   At recursion depth d:
//     - a[0..d-1] is the chosen prefix (already fixed).
//     - a[d..n-1] are the candidates (not fixed yet).
//  
//   Core 3 steps at each depth d:
//   for i from d to n-1:
//     1) CHOOSE  : swap(a, d, i)       // put candidate a[i] at position d
//     2) EXPLORE : recurse on d+1      // grow the prefix by one
//     3) UNDO    : swap(a, d, i)       // swap back to restore the array
//  

// Tiny example on [A, B, C]
// START: a = [A, B, C]

// d=0:
//   i=0 -> put A at pos0 (swap(0,0))
//     d=1:
//       i=1 -> put B at pos1 (swap(1,1))
//         d=2:
//           i=2 -> put C at pos2 (swap(2,2))  => ACCEPT [A, B, C]
//       i=2 -> put C at pos1 (swap(1,2))
//         d=2:
//           i=2 -> put B at pos2 (swap(2,1)) => ACCEPT [A, C, B]
//   i=1 -> put B at pos0
//     d=1:
//       i=1 -> put A at pos1
//         d=2:
//           i=2 -> put C at pos2  => ACCEPT [B, A, C]
//       i=2 -> put C at pos1
//         d=2:
//           i=2 -> put A at pos2  => ACCEPT [B, C, A]
//   i=2 -> put C at pos0
//     d=1:
//       i=1 -> put B at pos1
//         d=2:
//           i=2 -> put A at pos2  => ACCEPT [C, B, A]
//       i=2 -> put A at pos1
//         d=2:
//           i=2 -> put B at pos2  => ACCEPT [C, A, B]

// Permutations produced (in order):
// [A, B, C], [A, C, B], [B, A, C], [B, C, A], [C, B, A], [C, A, B]
