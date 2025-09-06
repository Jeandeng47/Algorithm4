import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_13 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;

        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            
            public Node(Key key, Value val, int size) {
                this.key = key;
                this.val = val;
            }
        }

        public BST() {}
        
        // non-recursive size (level-order)
        public int size() {
            if (root == null) return 0;
            int count = 0;
            Deque<Node> queue = new ArrayDeque<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node x = queue.removeFirst();
                count++;
                if (x.left != null) queue.addLast(x.left);
                if (x.right != null) queue.addLast(x.right);
            }
            return count;
        }

        // non-recursive get() & put()
        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            Node curr = root;
            while (curr != null) {
                int cmp = key.compareTo(curr.key);
                if (cmp < 0)        curr = curr.left;
                else if (cmp > 0)   curr = curr.right;
                else return curr.val;
            }
            return null;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            if (root == null) {
                root = new Node(key, val, 1); return; // insert into empty tree
            }

            Node curr = root;
            Node parent = null;
            int cmp = 0;

            while (curr != null) {
                parent = curr;
                cmp = key.compareTo(curr.key);
                if (cmp < 0)        curr = curr.left;
                else if (cmp > 0)   curr = curr.right;
                else  {
                    curr.val = val;
                    return;
                }              
            }
            // last comparison: attach the curr node to its parent
            cmp = (key).compareTo(parent.key);
            if (cmp < 0) {
                parent.left = new Node(key, val, 1);
            } else {
                parent.right = new Node(key, val, 1);
            }
        }
    }

   public static void main(String[] args) {
        BST<Integer, String> st = new BST<>();
        int[] keys = {5, 2, 8, 1, 3, 7, 9};
        for (int k : keys) st.put(k, "v" + k);

        StdOut.println("size = " + st.size());              // expect 7
        StdOut.println("get(3) = " + st.get(3));                  // v3
        StdOut.println("get(6) = " + st.get(6));                  // null

        st.put(3, "UPDATED");
        StdOut.println("get(3) after update = " + st.get(3));     // UPDATED
        StdOut.println("size after update = " + st.size()); // still 7

        BST<Integer, Integer> chain = new BST<>();
        for (int k = 1; k <= 10; k++) chain.put(k, k);
        StdOut.println("chain size = " + chain.size());     // 10
        StdOut.println("chain get(10) = " + chain.get(10));       // 10
    }
}
