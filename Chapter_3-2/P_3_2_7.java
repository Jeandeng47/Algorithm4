import edu.princeton.cs.algs4.StdOut;

public class P_3_2_7 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;
        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            private int size;       // subtree size 
            private int ipl;

            public Node(Key key, Value val) {
                this.key = key;
                this.val = val;
                this.size = 1;
                this.ipl = 0;
            }
        }

        public BST() {}

        public boolean isEmpty() {  return size() == 0; }
        public int size() { return size(root); }
        private int size(Node x) { 
            if (x == null) return 0;
            else return x.size;
        }

        // modified put(): cached subtree size & ipl
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("null key");
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            if (x == null) return new Node(key, val);
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left  = put(x.left,  key, val);
            else if (cmp > 0) x.right = put(x.right, key, val);
            else  x.val   = val;

            // update size and ipl
            x.size = 1 + size(x.left) + size(x.right);
            // when x is inserted into the tree,
            // each node in left & right subtree of x contribute
            // 1 more depth to ipl, total is size(x.left) + size(x.right)
            x.ipl = ipl(x.left) + ipl(x.right) + size(x.left) + size(x.right);
            return x;
        }

        private int ipl(Node x) {
            if (x == null) return 0;
            else return x.ipl;
        }

        // avgCompares (O(1) per query)
        public double avgCompares() {
            if (root == null) return 0.0;
            return 1.0 + ((double) root.ipl) / root.size;
        }

        // avgCompares (Recursive on demand)
        public double avgComparesRec() {
            if (root == null) return 0.0;
            int sumDepth = iplRec(root, 0);
            return 1 + ((double) sumDepth) / size();
        }

        // Example
        //      a          d = 0
        //   b     c       d = 1
        // c  d   e  f     d = 2

        // iplRec(a, 0)
        //    -> 0   + iplRec(b, 1)                     + iplRec(c, 1)
        //           -> 1 + ipl(c, 2) + ipl(d, 2)       -> 1 + ipl(e, 2) + ipl(e, 2)   
        //           -> 1 + (2+0+0)   + (2+0+0)         -> 1 + (2+0+0)   + (2+0+0)
        //    -> 0 + (1 + 2 + 2) + (1 + 2 + 2) = 10
        private int iplRec(Node x, int depth) {
            if (x == null) return 0;
            return depth + iplRec(x.left, depth + 1) + iplRec(x.right, depth + 1);
        }   

    }
    

    public static void main(String[] args) {
        BST<Integer, Integer> balanced = new BST<>();
        int[] keys = {5, 2, 8, 1, 3, 7, 9, 6};
        for (int k : keys) balanced.put(k, 1);
        StdOut.printf("N=%d     avgCmp(cached)=%.3f     avgCmp(recursive)=%.3f%n",
        balanced.size(), balanced.avgCompares(), balanced.avgComparesRec());

        BST<Integer, Integer> skewed = new BST<>();
        for (int k = 1; k <= 7; k++) skewed.put(k, 1);
        for (int k : keys) skewed.put(k, 1);
        StdOut.printf("N=%d     avgCmp(cached)=%.3f     avgCmp(recursive)=%.3f%n",
        skewed.size(), skewed.avgCompares(), skewed.avgComparesRec());

    }
}

// (1) In BST, if a key i is hit, the number of compares is equal
// to the number of nodes on the passed-path = depth(i) + 1
// (2) AvgCompares = Sum(cmp(i)) / N = Sum(di + 1) / N = 1 + Sum(di) / N
//     where Sum(di) = depth of all nodes on the tree