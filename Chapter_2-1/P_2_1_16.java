import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_16 {
    

    public static boolean check(Comparable[] a) {
        // copy original 
        Comparable[] copy = a.clone();

        Arrays.sort(copy);

        // compare
        for (int i = 0; i < a.length; i++) {
            if (a[i] != copy[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Integer[] a1 = {1, 2, 3, 4, 5}; // true
        Integer[] a2 = {3, 2, 4, 1, 5}; // false

        boolean sorted1 = check(a1);
        boolean sorted2 = check(a2);
        StdOut.println("Array 1 Sorted? " + sorted1);
        StdOut.println("Array 2 Sorted? " + sorted2);
    }
}
