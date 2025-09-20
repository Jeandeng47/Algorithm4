
public class P_3_4_24 {
    // JVM assumptions 
    static final int OBJ_HEADER = 16;
    static final int REF = 4;
    static final int ARRAY_HEADER = 16;

    // Boxed size
    static final int DOUBLE_OBJ = OBJ_HEADER + 8; // 24
    static final int INTEGER_OBJ = OBJ_HEADER + 4; // 20 

    // Assume <K, V> = <Double, Intger> here
    static final int KEY_VAL = DOUBLE_OBJ + INTEGER_OBJ; // 44

    static final int SC_NODE = OBJ_HEADER + 3 * REF;     // 28 (key,val,next)
    static final int BST_NODE = OBJ_HEADER + 4 * REF + 4; // 36 (key,val,left,right,int)

    // Allocate space cost to K-V pair:
    // SCT: 4M/N = 4/a (M * ref(4) = 4M)
    // LPT: 8M/N = 8/a (M * ref(4) * 2 = 8M)
    static double scPair(double alpha) { return KEY_VAL + SC_NODE + 4.0 / alpha; } // 44 + 28 + 4/α
    static double lpPair(double alpha) { return KEY_VAL + 8.0 / alpha; }           // 44 + 8/α
    static double bstPair()            { return KEY_VAL + BST_NODE; }              // 44 + 36

    static void row(double alpha) {
        double sc = scPair(alpha);
        double lp = lpPair(alpha);
        double bst = bstPair();
        System.out.printf("a = %.2f | SeparateChaining: %.0f B | LinearProbing: %.0f B | BST: %.0f B%n",
                alpha, sc, lp, bst);
    }

    public static void main(String[] args) {
        System.out.println("Space per <K, V> (<Double, Integer>)");
        System.out.println("Formulas: SC = 44 + 28 + 4/a,  LP = 44 + 8/a,  BST = 44 + 36\n");

        row(0.50);   // α = 1/2
        row(2.0/3);  // α ≈ 0.67
        row(1.00);   // α = 1
    }
}

// Space per <K, V> (<Double, Integer>)
// Formulas: SC = 44 + 28 + 4/a,  LP = 44 + 8/a,  BST = 44 + 36

// a = 0.50 | SeparateChaining: 80 B | LinearProbing: 60 B | BST: 80 B
// a = 0.67 | SeparateChaining: 78 B | LinearProbing: 56 B | BST: 80 B
// a = 1.00 | SeparateChaining: 76 B | LinearProbing: 52 B | BST: 80 B


// 1. Seperate Chaining Table:
// - Integer N (#keys), M (#chains)
// - An array of SequentialSearchST<K,V>, each with:
//     - Integer N (#nodes)
//     - A linked-list of nodes, each with:
//        - Key, Value pair
//        - Next node

// 2. Linear Probing Table:
// - Two parallel array of length M (keys[], vals[])
// - Integer N (#keys), M (#slots)

// 3. Binary Search
// - A tree of Node, each with:
//    - Key, Value pair
//    - Node left, right
//    - Integer size  