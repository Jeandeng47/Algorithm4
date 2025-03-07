import Util.ArrayGenerator;
import edu.princeton.cs.algs4.StdDraw;

// To run this program:
// make run ARGS="10 0.0 1.5"

public class P_1_1_32 {

    private static void drawHistogram(double[] arr, int N, double l, double r) {
        setUpCanvas();
        int[] bins = computeBins(arr, N, l, r);
        drawBars(bins, N, l, r);
    }

    private static void setUpCanvas() {
        StdDraw.setCanvasSize(800, 800);
    }

    private static int[] computeBins(double[] arr, int N, double l, double r) {

        int[] bins = new int[N];
        double interval = (r - l) / N;
        for (double num : arr) {
            // skip nums that are out of bounds
            if (num < l || num >= r) {
                continue;
            }
            // calculate the index of the bin
            int index = (int) ((num - l)/ interval);
            bins[index]++;
        }
        return bins;
    }

    private static int getMax(int[] bins) {
        int max = 0;
        for (int val : bins) {
            if (val > max) max = val;
        }
        return max;
    }

    private static void drawBars(int[] bins, int N, double l, double r) {
        // Set up scales based on the range of the data
        StdDraw.setXscale(l, r);
        StdDraw.setYscale(0, getMax(bins) * 1.5);
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(StdDraw.BLUE);

        double interval = (r - l) / N;
        double halfWidth = interval / 2;
        for (int i = 0; i < N; i++) {
            double height = bins[i];
            double center = l + i * interval + halfWidth;
            // filledRectangle(centerX, centerY, halfWidth, halfHeight)
            StdDraw.filledRectangle(center, height / 2, halfWidth, height / 2);
        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Enter: interval N, double l, double r");
            return;
        }

        int N = Integer.parseInt(args[0]);
        double l = Double.parseDouble(args[1]);
        double r = Double.parseDouble(args[2]);

        int num = 1000;
        double[] arr = ArrayGenerator.getRandDoubleArr(num, l, r);

        drawHistogram(arr, N, l, r);
        
    }
}