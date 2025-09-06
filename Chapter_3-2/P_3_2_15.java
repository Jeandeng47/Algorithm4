import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_2_15 {
    public static void main(String[] args) {
        BST<String, Integer> bst = new BST<>();
        // A D E J M Q S T
        String[] str = {"E", "D", "Q", "A", "J", "T", "M", "S"};
        for (String s : str) { bst.put(s, 0);   }
        
        StdOut.printf("floor(Q) = %s%n", bst.floor("Q"));
        StdOut.printf("select(5) = %s%n", bst.select(5));
        StdOut.printf("ceiling(Q) = %s%n", bst.ceiling("Q"));
        StdOut.printf("rank(J) = %d%n", bst.rank("J"));
        StdOut.printf("size(D, T) = %d%n", bst.size("D", "T"));
        StdOut.print("keys(D, T) = ");
        for (String k : bst.keys("D", "T")) {
            StdOut.print(k + " ");
        }
        StdOut.println();
    }
}


// floor(Q) = Q
// select(5) = Q
// ceiling(Q) = Q
// rank(J) = 3
// size(D, T) = 7
// keys(D, T) = D E J M Q S T 