package Util;

import java.util.Random;

public class ArrayGenerator {

    // Generate a random integer array within a specified range [lo, hi)
    public static int[] genRandIntArr(int lo, int hi) {
        if (lo >= hi) {
            throw new IllegalArgumentException("lo must be less than hi");
        }

        Random random = new Random();
        int[] arr = new int[hi - lo];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lo + random.nextInt(hi - lo);
        }
        return arr;
    }

    // Generate an ascending integer array within a specified range [lo, hi)
    public static int[] getAscIntArr(int lo, int hi) {
        if (lo >= hi) {
            throw new IllegalArgumentException("lo must be less than hi");
        }

        int[] arr = new int[hi - lo];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lo + i;
        }
        return arr;
    }

    // Generate a random integer array within the range [0, hi)
    public static int[] genRandIntArr(int hi) {
        return genRandIntArr(0, hi);
    }

    // Generate an ascending integer array within the range [0, hi)
    public static int[] getAscIntArr(int hi) {
        return getAscIntArr(0, hi);
    }

    // Generate a boolean matrix with random values
    public static boolean[][] genRandBooleanMatrix(int rows, int cols) {
        boolean[][] matrix = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Math.random() < 0.5;
            }
        }
        return matrix;
    }

    // Generate an integer matrix with random values
    public static int[][] genRandIntMatrix(int rows, int cols) {
        Random random = new Random();

        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }


}
