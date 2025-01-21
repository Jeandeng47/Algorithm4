import Util.ArrayGenerator;
import Util.ArrayPrint;

public class P_1_1_15 {
    public static int[] histogram(int[] array, int M) {
        int[] result = new int[M];
        // Result array: index -> a[i], value -> count of a[i]
        for (int i = 0; i < array.length; i++) {
            result[array[i]]++; // Increment the count for the value in array[i]
        }
        return result;
    }

    public static int sumArray(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static void main(String[] args) {

        int M = 10;

        int[] array = ArrayGenerator.genRandIntArr(M);
        ArrayPrint.printArray(array);
        
        int[] result = histogram(array, M);
        ArrayPrint.printArray(result);

        System.out.println("Array length: " + array.length);
        System.out.println("Sum of result array: " + sumArray(result));
    }
}
