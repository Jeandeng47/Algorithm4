
public class P_3_2_12 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root;

        private class Node {
            private Key key;
            private Value val;
            private Node left, right;
            
            public Node(Key key, Value val) {
                this.key = key;
                this.val = val;
                // no cached size
            }
        }

        public BST() {}

        // compute size() recursively
        public int size() {
            return size(root);
        }        
        private int size(Node x) {
            if (x == null) return 0;
            return 1 + size(x.left) + size(x.right);
        }
        public boolean isEmpty() {
            return root == null;
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("Key cannot be null");
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            if (x == null) return new Node(key, val);
            int cmp = key.compareTo(x.key);
            if (cmp < 0)        x.left = put(x.left, key, val);
            else if (cmp > 0)   x.right = put(x.right, key, val);
            else                x.val = val;
            return x;
        }

    }

    public static void main(String[] args) {
        BST<Integer, String> st = new BST<>();
        st.put(5,"A"); st.put(2,"B"); st.put(8,"C");
        st.put(1,"D"); st.put(3,"E"); st.put(7,"F"); st.put(9,"G");
        System.out.println("size=" + st.size()); // expect 7
      
    }
    
}
