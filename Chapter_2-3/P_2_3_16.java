import edu.princeton.cs.algs4.StdOut;

public class P_2_3_16 {
    public static void best(int[] a, int lo, int hi, int d) {
        if (lo >= hi) return;

        // when there are even numbers, take the lower median
        int mid = lo + (hi - lo) / 2;
        StdOut.print("   ");
        printArr(a, lo, hi, d);

        best(a, lo, mid - 1, d + 1);
        best(a, mid + 1, hi, d + 1);
        exch(a, lo, mid);

        StdOut.print("==>");
        printArr(a, lo, hi, d);
    }


    public static int[] bestArr(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i + 1;
        best(a, 0, n - 1, 1);
        return a;
    }

    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static String indent(int depth) {
         StringBuilder sb = new StringBuilder();
        for (int k = 1; k < depth; k++) sb.append("  "); // 2 spaces per level
        return sb.toString();
    }

    private static void printArr(int[] a, int lo, int hi, int depth) {
        int mid = lo + (hi - lo) / 2;
        StringBuilder sb = new StringBuilder();
        sb.append(indent(depth)).append("a[").append(lo).append("..").append(hi).append("] = [");
        for (int i = lo; i <= hi; i++) {
            if (i > lo) sb.append(' ');
            if (lo == mid && i == lo) {           // size 1 window case: both lo and mid
                sb.append("{").append(a[i]).append("}");
            } else if (i == lo) {
                sb.append("[").append(a[i]).append("]");
            } else if (i == mid) {
                sb.append("(").append(a[i]).append(")");
            } else {
                sb.append(a[i]);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) { 
        int[] a = bestArr(11);
                
        System.out.print("Final: ");
        for (int v : a) System.out.print(v + " ");
        System.out.println();
    }
}

// In quick-sort, we pick a partition index p at every level
// which split our array into a[lo...p-1] and a[p+1...hi]
// To evenly spilt the array, we would like a[p] to be 
// the median. Therefors, we should arrange the array s.t. 
// pivot a[p] is always median.

// Recursive arrangement: 
// Idea: if every subarray placed the median at correct pos,
// the whole array placed their median at correct pos.
// Step:
// 1. Calculate mid index
// 2. Split the array with mid index
// 3. At level level, exchange lo and mid

//    a[0..10] = [[1] 2 3 4 5 (6) 7 8 9 10 11]
//      a[0..4] = [[1] 2 (3) 4 5]
//        a[0..1] = [{1} 2]
// ==>    a[0..1] = [{1} 2]
//        a[3..4] = [{4} 5]
// ==>    a[3..4] = [{4} 5]
// ==>  a[0..4] = [[3] 2 (1) 4 5]
//      a[6..10] = [[7] 8 (9) 10 11]
//        a[6..7] = [{7} 8]
// ==>    a[6..7] = [{7} 8]
//        a[9..10] = [{10} 11]
// ==>    a[9..10] = [{10} 11]
// ==>  a[6..10] = [[9] 8 (7) 10 11]
// ==>a[0..10] = [[6] 2 1 4 5 (3) 9 8 7 10 11]
// Final: 6 2 1 4 5 3 9 8 7 10 11