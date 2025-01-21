public class P_1_1_16 {

    private static void printRecursion(int depth, String msg) {
        for (int i = 0; i < depth; i++) {
            System.out.print("---"); // Two spaces for each level of depth
        }
        System.out.println(msg);
    }

    public static String exR1Helper(int n, int depth) {
        // Print the call with indentation
        printRecursion(depth, "exR1(" + n + ")");

        if (n <= 0) {
            printRecursion(depth, "return [ ]");
            return "";
        }

        // Recursive calls
        String result = exR1Helper(n - 3, depth + 1) + n + exR1Helper(n - 2, depth + 1) + n;

        // Print the return value with indentation
        printRecursion(depth, "return [" + result + "]");
        return result;
    }

    public static String exR1(int n)
    {
        return exR1Helper(n, 0);
    }


    public static void main(String[] args) {
        // exR1: 311361142246
        System.out.println(exR1(6));
    }
}

// Recursive calls:
// exR1(6)
// ---exR1(3)
// ------exR1(0)
// ------return [ ]
// ------exR1(1)
// ---------exR1(-2)
// ---------return [ ]
// ---------exR1(-1)
// ---------return [ ]
// ------return [11]
// ---return [3113]
// ---exR1(4)
// ------exR1(1)
// ---------exR1(-2)
// ---------return [ ]
// ---------exR1(-1)
// ---------return [ ]
// ------return [11]
// ------exR1(2)
// ---------exR1(-1)
// ---------return [ ]
// ---------exR1(0)
// ---------return [ ]
// ------return [22]
// ---return [114224]
// return [311361142246]
