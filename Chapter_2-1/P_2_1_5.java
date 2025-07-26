import edu.princeton.cs.algs4.StdOut;

public class P_2_1_5 {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void sortCheck(Comparable[] a, String description) {
        StdOut.println("===== " + description + " array =====");
        int N = a.length;
        for (int i = 1; i < N; i++) {
            int j = i;
            while (j > 0 && less(a[j], a[j-1])) {
                exch(a, j, j - 1);
                j--;
            }
            // For each round, check on termination
            String reason = (j == 0)? 
            "Stopped since j = 0" : 
            "Stopped since less(a[j],a[j-1]) is false";
            StdOut.printf("Pass %2d: %s (final j = %d)%n", i, reason, j);
        }
        StdOut.println();
    } 

    public static void main(String[] args) {        
        int N = 10;
        // ascending: 1,2,3,...,10
        Integer[] asc  = new Integer[N];
        // descending: 10,9,8,...,1
        Integer[] desc = new Integer[N];
        for (int i = 0; i < N; i++) {
            asc[i]  = i+1;
            desc[i] = N-i;
        }

        sortCheck(asc,  "sorted ascending");
        sortCheck(desc, "sorted descending");
    }
}


// Condition 1: less(a[j],a[j-1]) is always false for an
// already sorted array, we will never enter the inner loop

// ===== sorted ascending array =====
// Pass  1: Stopped since less(a[j],a[j-1]) is false (final j = 1)
// Pass  2: Stopped since less(a[j],a[j-1]) is false (final j = 2)
// Pass  3: Stopped since less(a[j],a[j-1]) is false (final j = 3)
// Pass  4: Stopped since less(a[j],a[j-1]) is false (final j = 4)
// Pass  5: Stopped since less(a[j],a[j-1]) is false (final j = 5)
// Pass  6: Stopped since less(a[j],a[j-1]) is false (final j = 6)
// Pass  7: Stopped since less(a[j],a[j-1]) is false (final j = 7)
// Pass  8: Stopped since less(a[j],a[j-1]) is false (final j = 8)
// Pass  9: Stopped since less(a[j],a[j-1]) is false (final j = 9)

// Condition 2: j > 0 is always false at loop termination for an
// reversely sorted array, the inner loop will always run until j reaches 0.

// ===== sorted descending array =====
// Pass  1: Stopped since j = 0 (final j = 0)
// Pass  2: Stopped since j = 0 (final j = 0)
// Pass  3: Stopped since j = 0 (final j = 0)
// Pass  4: Stopped since j = 0 (final j = 0)
// Pass  5: Stopped since j = 0 (final j = 0)
// Pass  6: Stopped since j = 0 (final j = 0)
// Pass  7: Stopped since j = 0 (final j = 0)
// Pass  8: Stopped since j = 0 (final j = 0)
// Pass  9: Stopped since j = 0 (final j = 0)