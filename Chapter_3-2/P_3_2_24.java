import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_24 {
    public static class BST {

        private Node root;
        private int cmp = 0;

        public static class Node { 
            int k; 
            Node l, r; 
            Node(int k){ this.k = k; } 
        }

        public int compare(int a, int b) { cmp++; return Integer.compare(a, b); }

        public void put(int k) { root = put(root, k); }
        public Node put(Node x, int k) {
            if (x == null) return new Node(k);
            int c = compare(k, x.k);       
            if (c < 0)      x.l = put(x.l, k);
            else if (c > 0) x.r = put(x.r, k);
            return x;
        }
    }

    // ceil(log2(N!)) 
    public static int ceilLog2Fact(int N) {
        double s = 0.0;
        for (int i = 2; i <= N; i++) s += Math.log(i);
        return (int)Math.ceil(s / Math.log(2.0));
    }

    public static int buildAndCount(int[] order) {
        BST t = new BST();
        for (int v : order) t.put(v);
        return t.cmp;
    }

    public static void main(String[] args) {
        Random rnd = new Random(42);
        int[] Ns = {8, 16, 32, 64, 128};

        StdOut.printf("%6s %18s %18s %18s%n",
                "N", "ceil(log2 N!)", "random cmp", "ascending cmp");

        for (int N : Ns) {
            // lower bound
            int lb = ceilLog2Fact(N);


            int[] asc = new int[N];
            for (int i = 0; i < N; i++) asc[i] = i;

            int[] rndArr = asc.clone();
            for (int i = N - 1; i > 0; i--) {
                int j = rnd.nextInt(i + 1);
                int tmp = rndArr[i]; rndArr[i] = rndArr[j]; rndArr[j] = tmp;
            }

            int cmpRandom   = buildAndCount(rndArr);
            int cmpAscending = buildAndCount(asc); // worst case for naive put ~ N(N-1)/2

            StdOut.printf("%6d %,18d %,18d %,18d%n",
                    N, lb, cmpRandom, cmpAscending);
        }

        StdOut.println();
    }
}


//   Any algorithm A that builds a BST using only key comparisons can be used to sort:
//     sort(input):
//       - build BST with A (count only key comparisons)
//       - output nodes by inorder traversal (no comparisons)
//   If A used fewer than ceil(log2(N!)) comparisons in the worst case, then sort() would
//   sort with fewer than ceil(log2(N!)) comparisons, contradicting the comparison-sorting
//   lower bound. Therefore, any compare-based BST builder has a worst-case cost of
//   at least ceil(log2(N!)) comparisons. Since log2(N!) = Θ(N log N), building a BST
//   is Ω(N log N) in the comparison model.
