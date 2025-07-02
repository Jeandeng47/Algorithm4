import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.StdOut;

public class P_2_4_1 {
    public static void main(String[] args) {
        // letter to insert, * to delete
        String ops = "P R I O * R * * I * T * Y * * * Q U E * * * U * E";
        String[] tokens = ops.split(" ");
        MaxPQ<Character> pq = new MaxPQ<>();

        for (String token : tokens) {
            if (token.equals("*")) {
                if (!pq.isEmpty()) {
                    StdOut.print(pq.delMax());
                    StdOut.print(' ');
                }
            } else {
                pq.insert(token.charAt(0));
            }
        }
    }
}