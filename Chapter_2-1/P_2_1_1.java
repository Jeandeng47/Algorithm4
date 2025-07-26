import edu.princeton.cs.algs4.StdOut;

public class P_2_1_1 {
    // print the process of sorting
    private static void traceSort(Comparable[] a, int currIdx, int exchIdx) {
        for (int i = 0; i < a.length; i++) {
            if (i == currIdx) StdOut.print("*");
            if (i == exchIdx) StdOut.print(">");
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

    public static void selectionSort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[minIdx])) {
                    minIdx = j;
                }
            }
            traceSort(a, i, minIdx);
            exch(a, i, minIdx);
        }
    }

    public static void main(String[] args) {
        Character[] a = {'E', 'A', 'S', 'Y', 'Q', 'U', 'E', 'S', 'T', 'I', 'O', 'N'};        

        StdOut.println("Initial a: ");
        traceSort(a, -1, -1);
        StdOut.println();

        StdOut.println("Sorting a: ");
        selectionSort(a);

        
    }
}

// Initial a: 
// E A S Y Q U E S T I O N 

// Sorting a: 
// *E >A S Y Q U E S T I O N 
// A *>E S Y Q U E S T I O N 
// A E *S Y Q U >E S T I O N 
// A E E *Y Q U S S T >I O N 
// A E E I *Q U S S T Y O >N 
// A E E I N *U S S T Y >O Q 
// A E E I N O *S S T Y U >Q 
// A E E I N O Q *>S T Y U S 
// A E E I N O Q S *T Y U >S 
// A E E I N O Q S S *Y U >T 
// A E E I N O Q S S T *>U Y 
// A E E I N O Q S S T U *>Y 
