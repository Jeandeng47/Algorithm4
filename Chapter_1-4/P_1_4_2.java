import java.math.BigInteger;
import java.util.Arrays;

public class P_1_4_2 {

    public static long count(int[] a) {
        int N = a.length;
        long cnt = 0L;
        for (int i = 0; i < N; i++) {
            BigInteger bi = BigInteger.valueOf(a[i]);
            for (int j = i+1; j < N; j++) {
                BigInteger bj = BigInteger.valueOf(a[j]);
                for (int k = j+1; k < N; k++) {
                    BigInteger bk = BigInteger.valueOf(a[k]);
                    BigInteger sum = bi.add(bj).add(bk);
                    if (sum.signum() == 0) cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[][] tests = {
            {0, 0, 0},                     // 1
            {1, -1},                       // 0
            {-1, 0, 1, 2, -1, -4},         // 2
            {Integer.MAX_VALUE, 1, -1, -Integer.MAX_VALUE}  // 0
        };

        for (int[] test : tests) {
            System.out.println("Test array: " + Arrays.toString(test));
            long result = count(test);
            System.out.println("Three-sum count = " + result);
            System.out.println("-------------------------");
        }
        
    }
}

// Test array: [0, 0, 0]
// Three-sum count = 1
// -------------------------
// Test array: [1, -1]
// Three-sum count = 0
// -------------------------
// Test array: [-1, 0, 1, 2, -1, -4]
// Three-sum count = 3
// -------------------------
// Test array: [2147483647, 1, -1, -2147483647]
// Three-sum count = 0
// -------------------------


