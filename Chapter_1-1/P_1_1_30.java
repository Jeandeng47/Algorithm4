import Util.ArrayPrint;

public class P_1_1_30 {
    
    // If two numbers are prime to each other, their greatest common divisor is 1
    private static int gcd(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("a and b must be non-negative integers");
        }
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int N = 10;
        int[][] m = new int[N][N];

        // generate matrix (1 - true, 0 - false)
        // a[i][j] = 1 if gcd(i, j) = 1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                m[i][j] = gcd(i, j) == 1 ? 1 : 0;
            }
        }
        ArrayPrint.printMatrix(m);
    }
}

// 0  1  0  0  0  0  0  0  0  0
// 1  1  1  1  1  1  1  1  1  1
// 0  1  0  1  0  1  0  1  0  1
// 0  1  1  0  1  1  0  1  1  0
// 0  1  0  1  0  1  0  1  0  1
// 0  1  1  1  1  0  1  1  1  1
// 0  1  0  0  0  1  0  1  0  0
// 0  1  1  1  1  1  1  0  1  1
// 0  1  0  1  0  1  0  1  0  1
// 0  1  1  0  1  1  0  1  1  0
