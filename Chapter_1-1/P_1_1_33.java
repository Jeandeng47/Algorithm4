import edu.princeton.cs.algs4.StdOut;

public class P_1_1_33 {
    // vector dot product
    // a = [1, 2, 3], b = [4, 5, 6]
    // dot(a, b) = 1*4 + 2*5 + 3*6 = 32
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("The length of the two vectors must be the same");
        }
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return sum;
       
    }

    // matrix matrix product
    // A = [[1, 2], [3, 4]], B = [[5, 6], [7, 8]]
    // mult(A, B) = [[1*5 + 2*7, 1*6 + 2*8], [3*5 + 4*7, 3*6 + 4*8]]
    public static double[][] mult(double[][] a, double[][] b) {
        int aRow = a.length;
        int aCol = a[0].length;
        int bRow = b.length;
        int bCol = b[0].length;

        if (aCol != bRow) {
            throw new IllegalArgumentException("Invalid matrix dimensions for multiplication.");
        }

        // Cij = Ai1 * B1j + Ai2 * B2j + ... + Aik * Bkj
        //     = sum(Aik * Bkj)
        double[][] result = new double[aRow][bCol];
        for (int i = 0; i < aRow; i++) {
            for (int j = 0; j < bCol; j++) {
                for (int k = 0; k < aCol; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // transpose    
    // A = [[1, 2], [3, 4]]
    // transpose(A) = [[1, 3], [2, 4]]
    public static double[][] transpose(double[][] a) {
        int aRow = a.length;
        int aCol = a[0].length;
        
        // Cij = Aji
        double[][] result = new double[aCol][aRow];
        for (int i = 0; i < aRow; i++) {
            for (int j = 0; j < aCol; j++) {
                result[j][i] = a[i][j];
            }
        }
        return result;
    }

    // matrix vector product
    // A = [[1, 2], [3, 4]], x = [5, 6]
    // mult(A, x) = [1*5 + 2*6, 3*5 + 4*6]
    public static double[] mult(double[][] a, double[] x) {
        int aRow = a.length;
        int aCol = a[0].length;
        int xLen = x.length;

        if (aCol != xLen) {
            throw new IllegalArgumentException("Invalid matrix dimensions for multiplication.");
        }

        // Ci = sum(Aij * xj)
        double[] result = new double[aRow];
        for (int i = 0; i < aRow; i++) {
            for (int j = 0; j < aCol; j++) {
                result[i] += a[i][j] * x[j];
            }
        }
        return result;
    }

    // vector matrix product
    // y = [1, 2], A = [[3, 4], [5, 6]]
    // mult(y, A) = [1*3 + 2*5, 1*4 + 2*6]
    public static double[] mult(double[] y, double[][] a) {
        int yLen = y.length;
        int aRow = a.length;
        int aCol = a[0].length;

        if (yLen != aRow) {
            throw new IllegalArgumentException("Invalid matrix dimensions for multiplication.");
        }

        // Ci = sum(yj * Aji)
        double[] result = new double[aCol];
        for (int i = 0; i < aCol; i++) {
            for (int j = 0; j < aRow; j++) {
                result[i] += y[j] * a[j][i];
            }
        }
        return result;
    }


    public static void main(String[] args) {
        // dot product 
        double[] x = {1, 2, 3};
        double[] y = {4, 5, 6};
        StdOut.println("Dot product of x and y: " + dot(x, y));

        // matrix matrix product
        double[][] a = {{1, 2}, {3, 4}};
        double[][] b = {{5, 6}, {7, 8}};
        double[][] c = mult(a, b);
        StdOut.println("Matrix matrix product of a and b:");
        Util.ArrayPrint.printMatrix(c);

        // transpose
        double[][] t = transpose(a);
        StdOut.println("Transpose of a:");
        Util.ArrayPrint.printMatrix(t);

        // matrix vector product
        double[] v = {5, 6}; 
        double[] r = mult(a, v); // 2x2, 2x1 -> 2x1
        StdOut.println("Matrix vector product of a and v:");
        Util.ArrayPrint.printArray(r);

        // vector matrix product
        double[] w = {1, 2};
        double[] s = mult(w, a); // 1x2, 2x2 -> 1x2
        StdOut.println("Vector matrix product of w and a:");
        Util.ArrayPrint.printArray(s);
    }
}
