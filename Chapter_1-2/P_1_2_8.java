import Util.ArrayGenerator;
import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdOut;

public class P_1_2_8 {
    public static void main(String[] args) {
        int[] a = ArrayGenerator.getAscIntArr(0, 11);
        int[] b = ArrayGenerator.getAscIntArr(10, 21);

        StdOut.print("a: ");
        ArrayPrint.printArray(a);

        StdOut.print("b: ");
        ArrayPrint.printArray(b);
        StdOut.println();

        // Swap by reference

        int[] t = a;
        StdOut.print("t: ");
        ArrayPrint.printArray(t);
        StdOut.println();

        a = b;
        StdOut.print("a: ");
        ArrayPrint.printArray(a);
        StdOut.println();

        b = t;
        StdOut.print("b: ");
        ArrayPrint.printArray(b);
        StdOut.println();
    }
    
}


// a: 0 1 2 3 4 5 6 7 8 9 10
// b: 10 11 12 13 14 15 16 17 18 19 20

// t: 0 1 2 3 4 5 6 7 8 9 10

// a: 10 11 12 13 14 15 16 17 18 19 20

// b: 0 1 2 3 4 5 6 7 8 9 10
