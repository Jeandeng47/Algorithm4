package Util;

import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

public class ArrayGenerator {

    // Generate a random integer array within a specified range [lo, hi)
    public static int[] getRandIntArr(int lo, int hi) {
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
    public static int[] getRandIntArr(int hi) {
        return getRandIntArr(0, hi);
    }

    // Generate an ascending integer array within the range [0, hi)
    public static int[] getAscIntArr(int hi) {
        return getAscIntArr(0, hi);
    }


    // Generate a random integer array with a specified size, whose
    // elements are within the range [lo, hi)
    public static int[] getRandIntArr(int size, int lo, int hi) {
        if (size < 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        if (lo >= hi) {
            throw new IllegalArgumentException("lo must be less than hi");
        }

        Random rand= new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(hi - lo) + lo;
        }
        return arr;
    }

    // Generate a random double array with a specified size, whose
    // elements are within the range [lo, hi)
    public static double[] getRandDoubleArr(int size, double lo, double hi) {
        if (size < 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        if (lo >= hi) {
            throw new IllegalArgumentException("lo must be less than hi");
        }

        Random rand= new Random();
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextDouble(hi - lo) + lo;
        }
        return arr;
    }

    // Generate a boolean matrix with random values
    public static boolean[][] getRandBooleanMatrix(int rows, int cols) {
        boolean[][] matrix = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Math.random() < 0.5;
            }
        }
        return matrix;
    }

    // Generate an integer matrix with random values
    public static int[][] getRandIntMatrix(int rows, int cols) {
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
