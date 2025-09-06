import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.*;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class P_3_2_20 {
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

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
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


        public Iterable<Key> keys() {
            if(isEmpty()) return new Queue<Key>();
            return keys(min(), max());
        }

        // Record the number of nodes visited
        private int visited = 0;
        public int getVisted() {
            return visited;
        }

        // Return the keys between lo and hi (both inclusive)
        public Iterable<Key> keys(Key lo, Key hi) {
            if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
            if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

            visited = 0; // reset counter
            Queue<Key> queue = new Queue<Key>();
            keys(root, queue, lo, hi);
            return queue;
        }

        private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
            if (x == null) return;

            // visit ndoes
            visited++;
            int cmplo = lo.compareTo(x.key);
            int cmphi = hi.compareTo(x.key);
            
            // lo <= x <= hi
            if (cmplo <= 0 && cmphi >= 0) { queue.enqueue(x.key);} 
            // x < lo < hi, search right
            if (cmphi > 0) { keys(x.right, queue, lo, hi); }
            // lo < hi < x, search left
            if (cmplo < 0) { keys(x.left, queue, lo, hi); }
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        public int size(Key lo, Key hi) {
            if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
            if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

            if (lo.compareTo(hi) > 0)   return 0; 
            if (contains(hi))           return rank(hi) - rank(lo) + 1;
            else                        return rank(hi) - rank(lo);
        }

        public int height() {
            return height(root);
        }

        // Count height by edge
        private int height(Node x) {
            if (x == null) return -1;
            return 1 + Math.max(height(x.left),  height(x.right));
        }
    }
    public static void main(String[] args) {
        Random rnd = new Random(42);

        Function<Integer, BST<Integer, Integer>> buildRndBST = (N) -> {
            BST<Integer, Integer> t = new BST<>();
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < N; i++) l.add(i);
            Collections.shuffle(l, rnd);
            for (Integer k : l) t.put(k, 0);
            return t;
        };

        Function<Integer, BST<Integer, Integer>> buildChainBST = (N) -> {
            BST<Integer, Integer> t = new BST<>();
            for (int i = 0; i < N; i++) t.put(i, 0);
            return t;
        };

        int[] Ns = {8, 32, 128, 512};
        StdOut.println("Random BST: ");
        String HDR = "%6s %6s %6s %6s %9s %12s %12s %11s%n";
        String ROW = "%6d %6d %6d %6d %,9d %,12d %,12d %11.3f%n";
        
        StdOut.printf(HDR, "N", "H", "lo", "hi", "K", "visited", "bound", "v/(K+H)");
        for (int N : Ns) {
            for (int t = 0; t < 5; t++) {
                BST<Integer, Integer>  bst = buildRndBST.apply(N);
                // get rand number in [0...N]
                int a = rnd.nextInt(N + 1);
                int b = rnd.nextInt(N + 1);
                int lo = Math.min(a, b);
                int hi = Math.max(a, b);

                Iterable<Integer> it = bst.keys(lo, hi);
                int k = 0; for (Integer x : it) k++;
                int h = bst.height();
                int visited = bst.getVisted();

                int bound = k + h; 

                
                StdOut.printf(ROW, N, h, lo, hi, k, visited, bound, (double)visited/bound);
            }
        }
        StdOut.println();

        StdOut.println("Chain BST: ");
        StdOut.printf(HDR, "N", "H", "lo", "hi", "K", "visited", "bound", "v/(K+H)");
        for (int N : Ns) {
            for (int t = 0; t < 3; t++) {
                BST<Integer, Integer>  bstc = buildChainBST.apply(N);
                
                int h = bstc.height();

                int[][] tests = {
                    {N/3, 2*N/3},     // middle slice
                    {1, N}            // full range
                };

                for (int[] rg : tests) {
                    int lo = Math.min(rg[0], rg[1]), hi = Math.max(rg[0], rg[1]);
                    Iterable<Integer> it = bstc.keys(lo, hi);
                    int k = 0; for (Integer x : it) k++;
                    int visited = bstc.getVisted();
                    int bound = k + h;

                    StdOut.printf(ROW, N, h, lo, hi, k, visited, bound, (double)visited/bound);
                }
            }
        }
        
    }
}

// Prove: The running time of keys() is propotional to tree height
// + the number of keys in that range: O(K + H)



// Random BST: 
//      N      H     lo     hi         K      visited        bound     v/(K+H)
//      8      4      2      7         6            6           10       0.600
//      8      6      6      6         1            2            7       0.286
//      8      5      0      7         8            8           13       0.615
//      8      3      2      5         4            5            7       0.714
//      8      5      5      7         3            7            8       0.875
//     32      9     19     29        11           15           20       0.750
//     32      9     14     20         7           14           16       0.875
//     32      8      8     13         6           14           14       1.000
//     32     10      6      9         4           12           14       0.857
//     32      7     16     23         8           12           15       0.800
//    128     13     91    124        34           38           47       0.809
//    128     11     32    114        83           87           94       0.926
//    128     11     56    122        67           71           78       0.910
//    128     14     78    115        38           45           52       0.865
//    128     11     92    100         9           16           20       0.800
//    512     18    449    463        15           26           33       0.788
//    512     19    328    380        53           67           72       0.931
//    512     17    144    458       315          324          332       0.976
//    512     17      3    483       481          484          498       0.972
//    512     20    217    357       141          153          161       0.950

// Chain BST: 
//      N      H     lo     hi         K      visited        bound     v/(K+H)
//      8      7      2      5         4            6           11       0.545
//      8      7      1      8         7            8           14       0.571
//      8      7      2      5         4            6           11       0.545
//      8      7      1      8         7            8           14       0.571
//      8      7      2      5         4            6           11       0.545
//      8      7      1      8         7            8           14       0.571
//     32     31     10     21        12           22           43       0.512
//     32     31      1     32        31           32           62       0.516
//     32     31     10     21        12           22           43       0.512
//     32     31      1     32        31           32           62       0.516
//     32     31     10     21        12           22           43       0.512
//     32     31      1     32        31           32           62       0.516
//    128    127     42     85        44           86          171       0.503
//    128    127      1    128       127          128          254       0.504
//    128    127     42     85        44           86          171       0.503
//    128    127      1    128       127          128          254       0.504
//    128    127     42     85        44           86          171       0.503
//    128    127      1    128       127          128          254       0.504
//    512    511    170    341       172          342          683       0.501
//    512    511      1    512       511          512        1,022       0.501
//    512    511    170    341       172          342          683       0.501
//    512    511      1    512       511          512        1,022       0.501
//    512    511    170    341       172          342          683       0.501
//    512    511      1    512       511          512        1,022       0.501