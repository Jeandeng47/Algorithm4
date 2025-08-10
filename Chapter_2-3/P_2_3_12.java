import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class P_2_3_12 {
    public static void firstPartition(char[] a) {
        int N = a.length;
        sort(a, 0, N - 1);
    }

    private static void sort(char[] a, int lo, int hi) {
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        char v = a[lo];

        StdOut.println("step  lt  i  gt  action        array");
        int step = 0;
        StdOut.printf("%3d  %2d %3d %3d  %-13s %s%n",
                step, lt, i, gt, "start", new String(a));

        while (i <= gt) {
            int cmp = a[i] - v;
            if (cmp < 0) { //a[i] < v
                exch(a, lt++, i++);
                StdOut.printf("%3d  %2d %3d %3d  %-13s %s%n",
                        ++step, lt, i, gt, "exch lt,i (<)", new String(a));
            } else if (cmp > 0) {
                exch(a, i, gt--);
                StdOut.printf("%3d  %2d %3d %3d  %-13s %s%n",
                        ++step, lt, i, gt, "exch i,gt (>)", new String(a));
            } else {
                i++;
                StdOut.printf("%3d  %2d %3d %3d  %-13s %s%n",
                        ++step, lt, i, gt, "== pivot", new String(a));
            }
        }
    }

    private static void exch(char[] a, int i, int j) {
        if (i == j) return;
        char t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // Full sort
    public static void quickSort3way(Comparable[] a) {
        sort3way(a, 0, a.length - 1);
    }
    private static void sort3way(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int c = a[i].compareTo(v);
            if (c < 0)      exch(a, lt++, i++);
            else if (c > 0) exch(a, i, gt--);
            else            i++;
        }
        sort3way(a, lo, lt - 1);
        sort3way(a, gt + 1, hi);
    }

    private static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }


    public static void main(String[] args) {
        String s = "BABABABACADABRA";
        char[] arr = s.toCharArray();
        firstPartition(arr); // trace
        
        Character[] chars = s.chars().mapToObj(c -> (char) c)
        .toArray(Character[]::new);
        quickSort3way(chars);
        System.out.println("sorted: " + Arrays.toString(chars));
    }
}


// step  lt  i  gt  action        array
//   0   0   1  14  start         BABABABACADABRA
//   1   1   2  14  exch lt,i (<) ABBABABACADABRA
//   2   1   3  14  == pivot      ABBABABACADABRA
//   3   2   4  14  exch lt,i (<) AABBBABACADABRA
//   4   2   5  14  == pivot      AABBBABACADABRA
//   5   3   6  14  exch lt,i (<) AAABBBBACADABRA
//   6   3   7  14  == pivot      AAABBBBACADABRA
//   7   4   8  14  exch lt,i (<) AAAABBBBCADABRA
//   8   4   8  13  exch i,gt (>) AAAABBBBAADABRC
//   9   5   9  13  exch lt,i (<) AAAAABBBBADABRC
//  10   6  10  13  exch lt,i (<) AAAAAABBBBDABRC
//  11   6  10  12  exch i,gt (>) AAAAAABBBBRABDC
//  12   6  10  11  exch i,gt (>) AAAAAABBBBBARDC
//  13   6  11  11  == pivot      AAAAAABBBBBARDC
//  14   7  12  11  exch lt,i (<) AAAAAAABBBBBRDC
// sorted: [A, A, A, A, A, A, A, B, B, B, B, B, C, D, R]