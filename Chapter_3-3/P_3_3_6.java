import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;


public class P_3_3_6 {
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

        // Convert a RBT to a 2-3T
        public T23 to23() {
            if (root == null) return null;
            return to23Black(root);
        } 

        private T23 to23Black(Node h) {
            if (h == null) return null;
            boolean leftRed = isRed(h.left);
            if (!leftRed) { // 2-node
                if (h.left == null && h.right == null) return T23.leaf2(); // base
                return T23.node2(asBlack(h.left), asBlack(h.right));
            } else { // 3-node
                Node l = h.left;
                if (h.right == null && l.left == null && l.right == null) return T23.leaf3();
                return T23.node3(asBlack(l.left), asBlack(l.right), asBlack(h.right));
            }
        }

        // Since we always merge the red left child in last level of recursion,
        // we will never recurse on a non-black node
        private T23 asBlack(Node x) {
            if (x == null) return null;
            if (isRed(x)) throw new IllegalArgumentException("Red node should not occur here");
            return to23Black(x);
        }

    }  

    // Construct a 2-3 Tree Node that ignores the order of the children
    public static class T23 {
        private boolean isThree; // whether the ndoe is 3-node
        private String sig;
        private List<T23> kids;
        private int h; // height
        private int sz; // size, #keys
        
        public T23(boolean isThree, List<T23> kids, String sig, 
        int h, int sz) {
            this.isThree = isThree;
            this.kids = kids;
            this.sig = sig;
            this.h = h;
            this.sz = sz; 
        }

        public static T23 leaf2() { return new T23(false, List.of(), "L2", 0, 1); };
        
        public static T23 leaf3() { return new T23(true, List.of(), "L3", 0, 2); };

        // Construct a 2-node with 2 children
        public static T23 node2(T23 a, T23 b) {
            if (a.h != b.h) throw new IllegalArgumentException("Children must have the same height");
            List<T23> child = new ArrayList<>();
            child.add(a); child.add(b);
            child.sort(Comparator.comparing(t -> t.sig));
            String sig = "N2(" + child.get(0).sig + "," + child.get(1).sig + ")";
            int h = 1 + a.h;
            int sz = 1 + a.sz + b.sz;
            return new T23(false, child, sig, h, sz);
        }

        // Construct a 3-node with 3 children
        public static T23 node3(T23 a, T23 b, T23 c) {
            if (!((a.h == b.h) && (b.h == c.h))) 
            throw new IllegalArgumentException("Children must have the same height");
            List<T23> child = new ArrayList<>();
            child.add(a); child.add(b); child.add(c);
            child.sort(Comparator.comparing(t -> t.sig));
            String sig = "N3(" + child.get(0).sig + "," + child.get(1).sig 
            +  "," + child.get(2).sig + ")";
            int h = 1 + a.h;
            int sz = 2 + a.sz + b.sz + c.sz;
            return new T23(true, child, sig, h, sz);
        }
        
    }

    private static void printT23(T23 r) { printT23(r, "", 0, 1); }
    private static void printT23(T23 x, String prefix, int pos, int siblings) {
        if (x == null) return;

        String conn;
        if (prefix.isEmpty())            conn = "";
        else if (pos == 0)               conn = "┌── ";
        else if (pos < siblings - 1)     conn = "├── ";
        else                              conn = "└── ";

        String branch = (pos == siblings - 1 ? "    " : "│   ");
        String childPrefix = prefix + branch;

        // print children top→bottom
        List<T23> kids = x.kids;
        int m = kids.size();
        if (m > 0) printT23(kids.get(m - 1), childPrefix, 0, m);

        StdOut.println(prefix + conn + (x.isThree ? "[••]" : "[•]") + "  (keys=" + (x.isThree ? 2 : 1) + ")");
        for (int i = m - 2, idx = 1; i >= 0; i--, idx++) {
            printT23(kids.get(i), childPrefix, idx, m);
        }
    }

    // Do random sampling
    public static void runN(int N, int T, int SEED, int top) {
        // Enumerate
        int[] a = new int[N];
        for (int i = 0; i < N; i++) { a[i] = i + 1; };
        Random rnd = new Random(SEED);

        // For each N, generate a list of unique shapes -> sig
        // freq: sig -> count
        // shapes: sig -> shape
        Map<String, Integer> freq = new HashMap<>();
        Map<String, T23> shapes = new HashMap<>();

        for (int t = 0; t < T; t++) {
            // shuffle array
            for (int i = N - 1; i > 0; i--) {
                int j = rnd.nextInt(i+1);
                int tmp=a[i]; a[i]=a[j]; a[j]=tmp;
            }

            // construct RBT
            RBT<Integer, Integer> rbt = new RBT<>();
            for (int i = 0; i < N; i++) { rbt.put(a[i], 0); }

            // convert to 23T (only structure is preserved)
            T23 s = rbt.to23();
            String sig = s.sig;
            freq.merge(sig, 1, Integer::sum);
            shapes.putIfAbsent(sig, s);
        }

        // sort by probability 
        List<Map.Entry<String, Integer>> list = new ArrayList<>(freq.entrySet());
        list.sort((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()));

        StdOut.printf("N=%d, trials=%,d, unique shapes=%d%n", N, T, list.size());
        int shown=0;
        for(var e : list){
            if(shown++==top) break;
            double p = e.getValue() / (double) T;
            StdOut.printf("  #%d  prob=%.5f  count=%d  sig=%s%n", shown, p, e.getValue(), e.getKey());
            String sig = e.getKey();
            T23 shape = shapes.get(sig);
            printT23(shape);
        }
        StdOut.println();
    }


    // Show all the 2-3 trees of different structures given N distinct key.
    // Find the probability of each of the 2-3 trees as the result of the 
    // insertion of N random distinct keys into an initially empty tree
    public static void main(String[] args) {
        int TRIALS = 100_000;
        int SEED = 42;
        int top = 5; // print the top 5
        
        int[] Ns = {2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int N : Ns) { runN(N, TRIALS, SEED, top); }
    }
}

// N=2, trials=100,000, unique shapes=1
//   #1  prob=1.00000  count=100000  sig=L3
// [••]  (keys=2)

// N=3, trials=100,000, unique shapes=1
//   #1  prob=1.00000  count=100000  sig=N2(L2,L2)
//     ┌── [•]  (keys=1)
// [•]  (keys=1)
//     └── [•]  (keys=1)

// N=4, trials=100,000, unique shapes=1
//   #1  prob=1.00000  count=100000  sig=N2(L2,L3)
//     ┌── [••]  (keys=2)
// [•]  (keys=1)
//     └── [•]  (keys=1)

// N=5, trials=100,000, unique shapes=2
//   #1  prob=0.40029  count=40029  sig=N2(L3,L3)
//     ┌── [••]  (keys=2)
// [•]  (keys=1)
//     └── [••]  (keys=2)
//   #2  prob=0.59971  count=59971  sig=N3(L2,L2,L2)
//     ┌── [•]  (keys=1)
// [••]  (keys=2)
//     ├── [•]  (keys=1)
//     └── [•]  (keys=1)

// N=6, trials=100,000, unique shapes=1
//   #1  prob=1.00000  count=100000  sig=N3(L2,L2,L3)
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [•]  (keys=1)
//     └── [•]  (keys=1)

// N=7, trials=100,000, unique shapes=2
//   #1  prob=0.42799  count=42799  sig=N2(N2(L2,L2),N2(L2,L2))
//     │   ┌── [•]  (keys=1)
//     ┌── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)
//   #2  prob=0.57201  count=57201  sig=N3(L2,L3,L3)
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [••]  (keys=2)
//     └── [•]  (keys=1)

// N=8, trials=100,000, unique shapes=2
//   #1  prob=0.14250  count=14250  sig=N3(L3,L3,L3)
//     ┌── [••]  (keys=2)
// [••]  (keys=2)
//     ├── [••]  (keys=2)
//     └── [••]  (keys=2)
//   #2  prob=0.85750  count=85750  sig=N2(N2(L2,L2),N2(L2,L3))
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// N=9, trials=100,000, unique shapes=3
//   #1  prob=0.28332  count=28332  sig=N2(N2(L2,L2),N3(L2,L2,L2))
//     │   ┌── [•]  (keys=1)
//     ┌── [••]  (keys=2)
//     │   ├── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)
//   #2  prob=0.28773  count=28773  sig=N2(N2(L2,L2),N2(L3,L3))
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [••]  (keys=2)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)
//   #3  prob=0.42895  count=42895  sig=N2(N2(L2,L3),N2(L2,L3))
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [••]  (keys=2)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)

// N=10, trials=100,000, unique shapes=3
//   #1  prob=0.28578  count=28578  sig=N2(N2(L2,L3),N2(L3,L3))
//     │   ┌── [••]  (keys=2)
//     ┌── [•]  (keys=1)
//     │   └── [••]  (keys=2)
// [•]  (keys=1)
//         ┌── [••]  (keys=2)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)
//   #2  prob=0.34291  count=34291  sig=N2(N2(L2,L2),N3(L2,L2,L3))
//     │   ┌── [••]  (keys=2)
//     ┌── [••]  (keys=2)
//     │   ├── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [•]  (keys=1)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)
//   #3  prob=0.37131  count=37131  sig=N2(N2(L2,L3),N3(L2,L2,L2))
//     │   ┌── [•]  (keys=1)
//     ┌── [••]  (keys=2)
//     │   ├── [•]  (keys=1)
//     │   └── [•]  (keys=1)
// [•]  (keys=1)
//         ┌── [••]  (keys=2)
//     └── [•]  (keys=1)
//         └── [•]  (keys=1)