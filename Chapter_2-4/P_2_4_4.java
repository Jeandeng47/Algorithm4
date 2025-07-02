import edu.princeton.cs.algs4.StdOut;

public class P_2_4_4 {

    public static <Key extends Comparable<Key>> 
    boolean checkMaxHeap(Key[] a) {
        int n = a.length;
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1; // left child
            int right = 2 * i + 2; // right child
            if (left < n && a[i].compareTo(a[left]) < 0) return false;
            if (right < n && a[i].compareTo(a[right]) < 0) return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        int n = 15;
        // Build a decreasing array
        Integer[] descArray = new Integer[n];
        for (int i = 0; i < n; i++) {
            descArray[i] = n - i;
        }
        
        Integer[] randomArray = new Integer[n];
        for (int i = 0; i < n; i++) {
            randomArray[i] = (int) (Math.random() * 100); 
        }

        StdOut.println("Descending array:");
        Util.ArrayPrint.printArray(descArray);
        StdOut.println("Is max heap? " + checkMaxHeap(descArray));
        
        StdOut.println("Random array:");
        Util.ArrayPrint.printArray(randomArray);
        StdOut.println("Is max heap? " + checkMaxHeap(randomArray));
    }
}
