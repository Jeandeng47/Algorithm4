import edu.princeton.cs.algs4.StdOut;

public class P_1_1_18 {

    private static void printRecursion(int depth, String msg) {
        for (int i = 0; i < depth; i++) {
            System.out.print("---"); // Two spaces for each level of depth
        }
        System.out.println(msg);
    }

    // Multiplication: a * b
    public static int mystery(int a, int b) {
        return mysteryHelper(a, b, 0);
    }

    public static int mysteryHelper(int a, int b, int depth) {
        printRecursion(depth, "mystery(" + a + ", " + b + ")");
        if (b == 0) {
            printRecursion(depth, "return 0");
            return 0;
        }
        if (b % 2 == 0) {
            int result = mysteryHelper(a + a, b / 2, depth + 1);
            printRecursion(depth, "return " + result);
            return result;
        }
        int result = mysteryHelper(a + a, b / 2, depth + 1) + a;
        printRecursion(depth, "return " + result);
        return result;
    }
    public static int mystery2(int a, int b) {
        return mystery2Helper(a, b, 0);
    }

    // Exponentiation: a ^ b
    public static int mystery2Helper(int a, int b, int depth) {
        printRecursion(depth, "mystery2(" + a + ", " + b + ")");
        if (b == 0) {
            printRecursion(depth, "return 1");
            return 1;
        }
        if (b % 2 == 0) {
            int result = mystery2Helper(a * a, b / 2, depth + 1);
            printRecursion(depth, "return " + result);
            return result;
        }
        int result = mystery2Helper(a * a, b / 2, depth + 1) * a;
        printRecursion(depth, "return " + result);
        return result;
    }
    
    public static void main(String[] args) {
        StdOut.println(mystery(2, 25)); // 50
        StdOut.println(mystery(3, 11)); // 33
        
        // Modify + to * and replace return 0 with return 1
        StdOut.println(mystery2(2, 25)); // 96
        StdOut.println(mystery2(3, 11)); // 135
    }
}

// mystery(2, 25)
// ---mystery(4, 12)
// ------mystery(8, 6)
// ---------mystery(16, 3)
// ------------mystery(32, 1)
// ---------------mystery(64, 0)
// ---------------return 0
// ------------return 32
// ---------return 48
// ------return 48
// ---return 48
// return 50
// 50
// mystery(3, 11)
// ---mystery(6, 5)
// ------mystery(12, 2)
// ---------mystery(24, 1)
// ------------mystery(48, 0)
// ------------return 0
// ---------return 24
// ------return 24
// ---return 30
// return 33
// 33

// mystery2(2, 25)
// ---mystery2(4, 12)
// ------mystery2(16, 6)
// ---------mystery2(256, 3)
// ------------mystery2(65536, 1)
// ---------------mystery2(0, 0)
// ---------------return 1
// ------------return 65536
// ---------return 16777216
// ------return 16777216
// ---return 16777216
// return 33554432
// 33554432
// mystery2(3, 11)
// ---mystery2(9, 5)
// ------mystery2(81, 2)
// ---------mystery2(6561, 1)
// ------------mystery2(43046721, 0)
// ------------return 1
// ---------return 6561
// ------return 6561
// ---return 59049
// return 177147
// 177147
