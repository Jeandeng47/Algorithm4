import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_14 {
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
        
        // non-recursive size (BFS order)
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

        // non-recursive get()
        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            Node x = root;
            while (x != null) {
                int cmp = key.compareTo(x.key);
                if (cmp < 0)        x = x.left;
                else if (cmp > 0)   x = x.right;
                else return x.val;
            }
            return null;
        }

        // non-recursive put()
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            if (root == null) {
                root = new Node(key, val, 1); return; // insert into empty tree
            }

            Node x = root;
            Node parent = null;
            int cmp = 0;

            while (x != null) {
                parent = x;
                cmp = key.compareTo(x.key);
                if (cmp < 0)        x = x.left;
                else if (cmp > 0)   x = x.right;
                else  {
                    x.val = val;
                    return;
                }              
            }
            // last comparison: attach the x node to its parent
            cmp = (key).compareTo(parent.key);
            if (cmp < 0) {
                parent.left = new Node(key, val, 1);
            } else {
                parent.right = new Node(key, val, 1);
            }
        }

        // non-recursive min(), max()
        public Key min() {
            Node x = root;
            while (x.left != null) {
                x = x.left;
            }
            return x.key;
        }
        
        public Key max() {
            Node x = root;
            while (x.right != null) {
                x = x.right;
            }
            return x.key;
        }

        // non-recursive floor() and ceiling()
        public Key floor(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            Node x = root;
            Node candidate = null;
            while (x != null) {
                int cmp = key.compareTo(x.key);
                if (cmp == 0)       return x.key;
                else if (cmp < 0) {
                    // key < x.key, need smaller
                    x = x.left;
                }
                else {
                    // key > x.key, search right tree
                    candidate = x;
                    x = x.right;
                }
            }
            return candidate == null? null :  candidate.key;
        }

        public Key ceiling(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            Node x = root;
            Node candidate = null;
            while (x != null) {
                int cmp = key.compareTo(x.key);
                if (cmp == 0)       return x.key;
                else if (cmp > 0) {
                    // key > x.key, need larger
                    x = x.right;
                } else {
                    // key < x.key, search left tree
                    candidate = x;
                    x = x.left;
                }
            }
            return candidate == null? null :  candidate.key;
        }

        public int rank(Key key) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");

            int count = 0;
            Deque<Node> stack = new ArrayDeque<>();
            Node x = root;

            while (x != null || !stack.isEmpty()) {
                // push left
                while (x != null) {
                    stack.push(x);
                    x = x.left;
                }
                // visit
                x = stack.pop();
                int cmp = key.compareTo(x.key);
                if (cmp <= 0) { return count; }
                count++; // curr.key < key

                // go right
                x = x.right;
            }
            return count;
            
        }

        public Key select(int rank) {
            if (rank < 0) return null;
            int idx = 0;
            Deque<Node> stack = new ArrayDeque<>();
            Node x = root;

            while (x != null || !stack.isEmpty()) {
                // go left
                while (x != null) {
                    stack.push(x);
                    x = x.left;
                }
                // visit
                // Invariant: when a node is popped, all keys in x.left
                // has been visited and no key >= x.key has been visited
                // so idx == rank(x)
                x = stack.pop();
                if (idx == rank) return x.key;
                idx++;

                // go right
                x = x.right;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        BST<Integer, Integer> st = new BST<>();
        int[] keys = {5, 2, 8, 1, 3, 7, 9};
        for (int k : keys) st.put(k, k);

        StdOut.println("keys inserted: " + Arrays.toString(keys));

        StdOut.println("min=" + st.min());                // 1
        StdOut.println("max=" + st.max());                // 9

        StdOut.println("floor(6)=" + st.floor(6));        // 5
        StdOut.println("ceiling(6)=" + st.ceiling(6));    // 7
    
        StdOut.println("rank(1)=" + st.rank(1));          // 0
        StdOut.println("rank(5)=" + st.rank(5));          // 3
        StdOut.println("select(6)=" + st.select(6));      // 9
        StdOut.println("select(7)=" + st.select(7));      // null (out of range)
    }
}


//  BST keys: 5,2,8,1,3,7,9
//  
//           5
//         /   \
//        2     8
//       / \   / \
//      1  3  7  9
//  
//     In-order: [1, 2, 3, 5, 7, 8, 9] (0-based rank)
//  
//     Goal: select(3)  (expect 5)
//     - Push left from root: push(5), push(2), push(1)
//     - Pop 1: idx=0 -> not 3, increment idx=1, go right(null)
//     - Pop 2: idx=1 -> not 3, increment idx=2, go right(3), push(3)
//     - Pop 3: idx=2 -> not 3, increment idx=3, go right(null)
//     - Pop 5: idx=3 -> MATCH -> return 5
//  
//     At each pop, idx equaled rank(popped.key): 0 for 1, 1 for 2, 2 for 3, 3 for 5.