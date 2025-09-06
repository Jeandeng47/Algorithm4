import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_22 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;

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
            if (cmp < 0)        x.left =  put(x.left, key, val);
            else if (cmp > 0)   x.right = put(x.right, key, val);
            else                x.val = val;
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        public Node minNode(Node x) {
            if (x == null) return null;
            while (x.left != null) {
                x = x.left;
            }
            return x;
        }

        public Node maxNode(Node x) {
            if (x == null) return null;
            while (x.right != null) {
                x = x.right;
            }
            return x;
        }

        public List<Node> inOrderTraverse(Node root) {
            List<Node> result = new ArrayList<>();
            Deque<Node> stack = new ArrayDeque<>();
            Node curr = root;
            while (curr != null || !stack.isEmpty()) {
                while (curr != null) {
                    stack.push(curr);
                    curr = curr.left;
                }
                Node p = stack.pop();
                result.add(p);
                curr = p.right;
            }
            return result;
        }

        public void check() {
            // check every node that has successor and predecessor
            for (Node x : inOrderTraverse(root)) {
                if (x.left != null && x.right != null) {

                    Node succ = minNode(x.right);
                    if (succ != null && succ.left != null) {
                         throw new AssertionError("Successor has a left child! x=" + x.key +
                                             " succ=" + succ.key);
                    }

                    Node pred = maxNode(x.left);
                    if (pred != null && pred.right != null) {
                        throw new AssertionError("Predecessor has a right child! x=" + x.key +
                                                " predecessor=" + pred.key);
                    }

                }

            }
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



    public static void main(String[] args) {
        Random rnd = new Random(42);

        int[] Ns = {8, 32, 128, 512};
        for (int N : Ns) {
            int trials = 5;
            for (int t = 0; t < trials; t++) {
                BST<Integer, Integer> bst = buildRandomBST(N, rnd);
                bst.check();
            }
        }
        StdOut.println("All tests passsed: successor has no left child, predecessor has no right child");
    }
}
