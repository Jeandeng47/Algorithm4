import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// To run this program
// % make run ARGS="100"

public class P_1_2_1 {
    public static void main(String[] args) {
        // Check for proper input
        if (args.length != 1) {
            StdOut.println("Usage: <number_of_points>");
            return;
        }

        // Read number from stdin
        int N = Integer.parseInt(args[0]);
        if (N < 2) {
            StdOut.print("Provide as least 2 points");
        }

        // Generate N 2D points
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            // coordinate in range [0, 1)]
            double x = StdRandom.uniformDouble();
            double y = StdRandom.uniformDouble();
            points[i] = new Point2D(x, y);
        }

        // Compute the shortest distance bewteen a pair of points
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        StdOut.printf("The closest distance between %d points is %.6f%n", N, minDistance);
    }
}

// The closest distance between 100 points is 0.010932
