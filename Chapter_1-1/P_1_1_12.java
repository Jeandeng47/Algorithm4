import Util.ArrayPrint;

public class P_1_1_12 {
    public static void main(String[] args) {
        int[] a = new int[10];

        // Initialize the array
        // 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
        for (int i = 0; i < 10; i++) {
            a[i] = 9 - i;
        }
        ArrayPrint.printArray(a);
       
        // Use value as index to get the value at that index
        // 0, 1, 2, 3, 4, 4, 3, 2, 1, 0
        for (int i = 0; i < 10; i++) {
            a[i] = a[a[i]];
        }
        ArrayPrint.printArray(a);
        
    }
}
