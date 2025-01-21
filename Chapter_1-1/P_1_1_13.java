import java.util.Random;

import Util.ArrayGenerator;
import Util.ArrayPrint;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_13 {

    

    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transMatrix = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transMatrix[j][i] = matrix[i][j];
            }
        }
        return transMatrix;
    }

    public static void main(String[] args) {
        int rows = 5;
        int cols = 3;

        StdOut.println("Original matrix: ");
        int[][] matrix = ArrayGenerator.genRandIntMatrix(rows, cols);
        ArrayPrint.print2DArray(matrix);
        
        StdOut.println("Transposed matrix:");
        int[][] transMatrix = transposeMatrix(matrix);
        ArrayPrint.print2DArray(transMatrix);
    }
}
