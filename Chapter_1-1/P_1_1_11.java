import Util.ArrayGenerator;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_11 {
    

    public static void printBooleanMatrix(boolean[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        StdOut.print(" "); // padding for col
        for (int i = 0; i < cols; i++) {
            StdOut.printf("%3d", i);
        }
        StdOut.println();

        for (int j = 0; j < rows; j++) {
            StdOut.print(j);
            for (int k = 0; k < cols; k++) {
                if (matrix[j][k]) {
                    StdOut.printf("%3s", "*");
                } else {
                    StdOut.printf("%3s", "-");
                }
            }
            StdOut.println();
        }

    }

    public static void main(String[] args) {
        // Example matrix
        int rows = 10;
        int cols = 10;
        boolean[][] matrix = ArrayGenerator.genRandBooleanMatrix(rows, cols);

        // Print boolean matrix
        printBooleanMatrix(matrix);
       
    }
}
