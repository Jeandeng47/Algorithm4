import Util.ArrayUtil;

public class P_1_1_10 {
    public static void main(String[] args) {
        // int[] a; -> Incorrect
        int[] a = new int[10]; // Initialize the array 
        for (int i = 0; i < 10; i++) {
            a[i] = i * i;
        }
        ArrayUtil.printArray(a);
    }
    
}
