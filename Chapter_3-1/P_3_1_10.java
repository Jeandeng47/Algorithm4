
public class P_3_1_10 {
    
    public static class SequentialSearchST<Key, Value> {
        private Node first;
        private int totalCmp = 0;
        private int n;

        private class Node {
            Key key;
            Value val;
            Node next;
            public Node(Key k, Value v, Node n) {
                key = k;
                val = v;
                next = n;
            }
        }

        public SequentialSearchST() {
            first = null;
            n = 0;
        }

        public int size() { return n; }
        
        public Value get(Key key) {
            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key)) {
                    return x.val;
                }
            }
            return  null;
        }

        public int put(Key key, Value val) {
            int c = 0;
            for (Node x = first; x != null; x = x.next) {
                c++; // equality check per node
                if (key.equals(x.key)) { // hit
                    x.val = val;
                    totalCmp += c;
                    return c;
                } 
            }
            // miss
            first = new Node(key, val, first);
            n++;
            totalCmp += c;
            return c;
        }

        public String order() {
            StringBuilder sb = new StringBuilder();
            for (Node x = first; x != null; x = x.next) {
                if (sb.length() > 0) sb.append(" -> ");
                sb.append(x.key);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String[] keys = {"E","A","S","Y","Q","U","E","S","T","I","O","N"};
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();

        System.out.printf("%-4s %-8s %-8s %-10s %-10s %s%n",
                "#", "key", "count", "compares", "cumComp", "list (head -> tail)");
        System.out.println("-----------------------------------------------------------------");

        int cum = 0;
        for (int i = 0; i < keys.length; i++) {
            String k = keys[i];
            int c = st.put(k, 1);
            cum += c;
            System.out.printf("%-4d %-8s %-8s %-10d %-10d %s%n",
                    (i + 1), k, st.get(k), c, cum, st.order());
        }

        System.out.println("\nTOTAL compares = " + st.totalCmp);
    }

}

// #    key      count    compares   cumComp    list (head -> tail)
// -----------------------------------------------------------------
// 1    E        1        0          0          E
// 2    A        1        1          1          A -> E
// 3    S        1        2          3          S -> A -> E
// 4    Y        1        3          6          Y -> S -> A -> E
// 5    Q        1        4          10         Q -> Y -> S -> A -> E
// 6    U        1        5          15         U -> Q -> Y -> S -> A -> E
// 7    E        1        6          21         U -> Q -> Y -> S -> A -> E
// 8    S        1        4          25         U -> Q -> Y -> S -> A -> E
// 9    T        1        6          31         T -> U -> Q -> Y -> S -> A -> E
// 10   I        1        7          38         I -> T -> U -> Q -> Y -> S -> A -> E
// 11   O        1        8          46         O -> I -> T -> U -> Q -> Y -> S -> A -> E
// 12   N        1        9          55         N -> O -> I -> T -> U -> Q -> Y -> S -> A -> E

// TOTAL compares = 55