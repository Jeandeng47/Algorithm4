import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// To run this program:
// % make run ARGS="100 0.1 0.3"

public class P_1_2_3 {

    private static Interval2D[] getIntervals(int n, double min, double max) {

        Interval2D[] intervals = new Interval2D[n];

        for (int i = 0; i < n; i++) {
            // Generate starting points
            double x1 = StdRandom.uniformDouble(0, 1);
            double y1 = StdRandom.uniformDouble(0, 1);

            // Generate width and height within range min, max
            double width = StdRandom.uniformDouble(min, max);
            double height = StdRandom.uniformDouble(min, max);

            double x2 = Math.min(x1 + width, 1);
            double y2 = Math.min(y1 + height, 1);

            // Generate 1D intervals
            Interval1D xInterval = new Interval1D(x1, x2);
            Interval1D yInterval = new Interval1D(y1, y2);

            intervals[i] = new Interval2D(xInterval, yInterval);

        }

        return intervals;
    }

    private static void drawIntervals(Interval2D[] intervals) {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);

        for (Interval2D interval : intervals) {
            interval.draw();
        }
    }

    private static int countIntersections(Interval2D[] intervals) {
        int count = 0;
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1;  j < intervals.length; j++) {
                if (intervals[i].intersects(intervals[j])) {
                    count++;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        // Check for proper input
        if (args.length != 3) {
            StdOut.println("Usage: <number_of_intervals> <min> <max>");
            return;
        }

        int N = Integer.parseInt(args[0]);       // Number of intervals
        double min = Double.parseDouble(args[1]); // Minimum width/height
        double max = Double.parseDouble(args[2]); // Maximum width/height

        Interval2D[] intervals = getIntervals(N, min, max);
        drawIntervals(intervals);

        int intersectCount = countIntersections(intervals);

        StdOut.printf("Number of intersecting pairs: %d%n", intersectCount);
    }

           
}
