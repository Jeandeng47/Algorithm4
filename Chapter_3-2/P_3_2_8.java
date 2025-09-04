import edu.princeton.cs.algs4.StdOut;

public class P_3_2_8 {
    // Return the number of compares given the perfect
    // balanced BST with N nodes
    public static double optCompares(int N) {
        if (N <= 0 ) return 0.0;
        int ipl = ipl(N);
        return 1 + ipl * 1.0 / N;
    }
    
    private static int ipl(int N) {
        if (N <= 1) return 0;
        int left = (N - 1) / 2;
        int right = (N - 1) - left;
        return ipl(left) + ipl(right) + N - 1;
    }
    
    public static double lg2(int N) {
        return Math.log(N) / Math.log(2);
    }

    public static void main(String[] args) {
        for (int N = 1; N <= 30; N++) {
            StdOut.printf("N=%2d -> optCmp=%.3f, log2N=%.3f%n", N, optCompares(N), lg2(N));
        }
    }
}

// (1) When BST is perfectly balanced, the size of left subtree
// is floor((N-1)/2) and the size of right subtree is (N-1) - L
// the size of subtrees is N - 1
// (2) The recurrence relationship of internal path length is known.
// IPL(N) = (IPL(L) + L) + (IPL(R) + R) 
//        = IPL(L) + IPL(R) + (L + R)
//        = IPL(L) + IPL(R) + N - 1
