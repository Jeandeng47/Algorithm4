import edu.princeton.cs.algs4.StdOut;

public class P_2_1_4 {

    public static void traceSort(Comparable[] a, int currIdx, int exchIdx) {
        for (int i = 0; i < a.length; i++) {
            if (i == currIdx) StdOut.printf("*");
            if (i == exchIdx) StdOut.printf(">");
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        

        for (int i = 1; i < N; i++) {
            // if no swap, * and > are at same element
            int currIdx = i;
            int exchIdx = i;
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
                exchIdx = j - 1; // the element i is at j-1 now 
            }
            traceSort(a, currIdx, exchIdx);
        }
    }

    public static void main(String[] args) {
        Character[] a = {'E', 'A', 'S', 'Y', 'Q', 'U', 'E', 'S', 'T', 'I', 'O', 'N'};        

        StdOut.println("Initial a: ");
        traceSort(a, -1, -1);
        StdOut.println();

        StdOut.println("Sorting a: ");
        insertionSort(a);

    }
}


// Initial a: 
// E A S Y Q U E S T I O N 

// Sorting a: 
// >A *E S Y Q U E S T I O N 
// A E *>S Y Q U E S T I O N 
// A E S *>Y Q U E S T I O N 
// A E >Q S *Y U E S T I O N 
// A E Q S >U *Y E S T I O N 
// A E >E Q S U *Y S T I O N 
// A E E Q S >S U *Y T I O N 
// A E E Q S S >T U *Y I O N 
// A E E >I Q S S T U *Y O N 
// A E E I >O Q S S T U *Y N 
// A E E I >N O Q S S T U *Y 