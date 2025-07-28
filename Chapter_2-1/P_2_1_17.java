import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

public class P_2_1_17 {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void setUp(int N) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, 1);
    }

    private static void show(Comparable[] a, Color color) {
        int N = a.length;
        StdDraw.clear();
        StdDraw.setPenColor(color);
        for (int i = 0; i < N; i++) {
            double v = ((Double) a[i]).doubleValue();
            double halfW = 0.5;
            double halfH = v / 2;
            double x = i + 0.5; // center x 
            double y = halfH;
            StdDraw.filledRectangle(x, y, halfW, halfH);
        }
        StdDraw.show();
        StdDraw.pause(50);
    }

    public static void insertionSort(Comparable[] a) {
        int N = a.length;

        setUp(N);
        show(a, StdDraw.GRAY);

        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
            show(a, StdDraw.BLUE);
        }

        StdDraw.show();
    }

    public static void selectionSort(Comparable[] a) {
        int N = a.length;

        // set up canvas and show initi array
        setUp(N);
        show(a, StdDraw.GRAY);

        for (int i = 0; i < N; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[minIdx])) {
                    minIdx = j;
                }
            }
            exch(a, i, minIdx);
            show(a, StdDraw.GREEN);
        }

        StdDraw.show(); // final pic
    }


    public static void main(String[] args) {
        int N = 100;
        Double[] a = new Double[N];
        for (int i = 0; i < N; i++) {
            a[i] = Math.random();
        }
        Double[] b = a.clone();

        insertionSort(a);
        StdDraw.pause(1000);
        selectionSort(b);
    }
}
