import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;

public class P_3_1_11 {
    public static class BinarySearchST<Key extends Comparable<Key>, Value> {
        private Key[] keys;
        private Value[] vals;
        private int N;

        private int totalCmp = 0;
        private int lastRankCmp = 0;

        public BinarySearchST(int cap) {
            keys = (Key[]) new Comparable[cap];
            vals = (Value[]) new Object[cap];
        }

        public int size() { return N; }
        public boolean isEmpty() { return N == 0; }
        public boolean contains(Key k)  { return get(k) != null; }

        public Value get(Key key) {
            if(isEmpty()) return null;
            int i = rank(key); // return the index of key
            if (i < N && keys[i].compareTo(key) == 0) {
                return vals[i];
            } else {
                return null;
            }
        }

        // returns the number of comparisons performed for one operation
        public int put(Key key, Value val) {
            int i = rank(key);
            int c = lastRankCmp;

            // update to new value if found
            if (i < N && keys[i].compareTo(key) == 0) {
                vals[i] = val;
            
                c++;
                totalCmp += c;
                return c;
            }

            // if not found, insert the v at i
            // move values [i+1 ... N] to the right
            if (N == keys.length) resize(2 * keys.length);
            for (int j = N; j > i; j--) {
                keys[j] = keys[j-1];
                vals[j] = vals[j-1];
            }
            keys[i] = key;
            vals[i] = val;
            N++;

            totalCmp += c;
            return c;
        }

        public Iterable<Key> keys(Key lo, Key hi) {
            Queue<Key> q = new Queue<>();
            if (lo == null || hi == null) return q;
            if (isEmpty()) return q;

            int i = rank(lo);
            int j = rank(hi);
            for (int k = i; k < j; k++) q.enqueue(keys[k]);
            if (j < N && keys[j].compareTo(hi) == 0) q.enqueue(keys[j]);
            return q;
        }

        // Resize both the keys and vals arrays
        private void resize(int cap) {
            Key[] newK = (Key[]) new Comparable[cap];
            Value[] newV = (Value[]) new Comparable[cap];

            for (int i = 0; i < N; i++) {
                newK[i] = keys[i];
                newV[i] = vals[i];
            }
            keys = newK;
            vals = newV;
        }
        

        // Iterative binary search in an ordered array
        private int rank(Key key) {
            int cmpCnt = 0;
            int lo = 0;
            int hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = key.compareTo(keys[mid]);
                cmpCnt++;
                if (cmp < 0) hi = mid - 1; // search left
                else if (cmp > 0) lo = mid + 1;
                else {
                    lastRankCmp = cmpCnt;
                    return mid;
                }
            }
            lastRankCmp = cmpCnt;
            return lo; // the number of elements that are less than key
        }

        public String order() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(keys[i]);
            }
            return sb.toString();
        }
    }
    public static void main(String[] args) {
        String[] keys = {"E","A","S","Y","Q","U","E","S","T","I","O","N"};
        BinarySearchST<String, Integer> st = new BinarySearchST<>(5);

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
// 3    S        1        2          3          A -> E -> S
// 4    Y        1        2          5          A -> E -> S -> Y
// 5    Q        1        2          7          A -> E -> Q -> S -> Y
// 6    U        1        3          10         A -> E -> Q -> S -> U -> Y
// 7    E        1        4          14         A -> E -> Q -> S -> U -> Y
// 8    S        1        4          18         A -> E -> Q -> S -> U -> Y
// 9    T        1        3          21         A -> E -> Q -> S -> T -> U -> Y
// 10   I        1        3          24         A -> E -> I -> Q -> S -> T -> U -> Y
// 11   O        1        3          27         A -> E -> I -> O -> Q -> S -> T -> U -> Y
// 12   N        1        4          31         A -> E -> I -> N -> O -> Q -> S -> T -> U -> Y

// TOTAL compares = 31
