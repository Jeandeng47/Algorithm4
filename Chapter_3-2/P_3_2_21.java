import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_21 {
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

        public int height() {
            return height(root);
        }

        private int height(Node x) {
            if (x == null) return 0;
            return 1 + Math.max(height(x.right), height(x.left));
        }

        // Insert the key-value pair into the symbol table
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

        private int visited = 0; // actual nodes touched
        public int getVisited() { return visited; }

        public Key randomKey(Random rnd) {
            int r = rnd.nextInt(size(root)); // random rank in [0...N-1]
            visited = 0;
            return select(root, r);   
        }

        private Key select(Node x, int rank) {
        if (x == null) return null;
        visited++;
        int leftSize = size(x.left);
        if      (leftSize > rank) return select(x.left, rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
        else                      return x.key;
    }
        
    }

    public static BST<Integer, Integer> buildRandomBST(int N, Random rnd){
        BST<Integer, Integer> t = new BST<>();
        List<Integer> a = new ArrayList<>(N);
        for (int i=0;i<N;i++) a.add(i);
        Collections.shuffle(a, rnd);
        for (int k: a) t.put(k, 0);
        return t;
    }

    public static BST<Integer, Integer> buildChainBST(int N){
        BST<Integer, Integer> t = new BST<>();
        for (int i=0;i<N;i++) t.put(i, 0); // ascending → worst height
        return t;
    }

     public static void main(String[] args) {
        Random rnd = new Random(42);
        int[] Ns = {8, 32, 128, 512};
        int S = 100; // samples per tree

        System.out.println("Random BSTs (check maxVisited ≤ H+1):");
        System.out.printf("%6s %6s %12s %12s %8s%n", "N", "H", "avgVisited", "maxVisited", "H+1");
        for (int N : Ns) {
            BST<Integer,Integer> t = buildRandomBST(N, rnd);
            int H = t.height();
            long sum = 0; int maxV = 0;
            for (int i = 0; i < S; i++) {
                Integer key = t.randomKey(rnd); // returns a Key
                sum += t.getVisited();
                if (t.getVisited() > maxV) maxV = t.getVisited();
            }
            double avg = (double) sum / S;
            System.out.printf("%6d %6d %,12.3f %,12d %8d%n", N, H, avg, maxV, H + 1);
            if (maxV > H + 1) throw new AssertionError("visited exceeded H+1");
        }

        System.out.println("\nChain BSTs (worst height):");
        System.out.printf("%6s %6s %12s %12s %8s%n", "N", "H", "avgVisited", "maxVisited", "H+1");
        for (int N : Ns) {
            BST<Integer,Integer> t = buildChainBST(N);
            int H = t.height();
            long sum = 0; int maxV = 0;
            for (int i = 0; i < S; i++) {
                t.randomKey(rnd);
                sum += t.getVisited();
                if (t.getVisited() > maxV) maxV = t.getVisited();
            }
            double avg = (double) sum / S;
            System.out.printf("%6d %6d %,12.3f %,12d %8d%n", N, H, avg, maxV, H + 1);
        }

    }

}


// Random BSTs (check maxVisited ≤ H+1):
//      N      H   avgVisited   maxVisited      H+1
//      8      5        2.760            5        6
//     32      7        4.730            7        8
//    128     15        8.290           15       16
//    512     22       10.720           18       23

// Chain BSTs (worst height):
//      N      H   avgVisited   maxVisited      H+1
//      8      8        4.700            8        9
//     32     32       16.850           32       33
//    128    128       62.610          128      129
//    512    512      279.040          510      513