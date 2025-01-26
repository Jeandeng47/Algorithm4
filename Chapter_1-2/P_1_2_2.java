import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// make run ARGS="4"

public class P_1_2_2 {
    public static void main(String[] args) {
         // Check for proper input
         if (args.length != 1) {
            StdOut.println("Usage: <number_of_intervals>");
            return;
        }

        // Read the number of intervals
        int N = Integer.parseInt(args[0]);
        if (N < 2) {
            StdOut.println("At least 2 intervals are required to find intersections.");
            return;
        }

        // Create an array of intervals1D
        Interval1D[] intervals = new Interval1D[N];

         // Read intervals from standard input
         StdOut.println("Enter intervals as pairs of double values:");
         for (int i = 0; i < N; i++) {
             double min = StdIn.readDouble();
             double max = StdIn.readDouble();
             intervals[i] = new Interval1D(min, max);
         }
 
         // Check for intersecting pairs
         StdOut.println("Intersecting pairs:");
         for (int i = 0; i < N; i++) {
             for (int j = i + 1; j < N; j++) {
                 if (intervals[i].intersects(intervals[j])) {
                     StdOut.printf("%s intersects %s%n", intervals[i], intervals[j]);
                 }
             }
         }

    }
}

// input:
// Enter intervals as pairs of double values:
// 0.1 0.5
// 0.4 0.8
// 0.9 1.2
// 0.3 0.7

// output:
// Intersecting pairs:
// [0.1, 0.5] intersects [0.4, 0.8]
// [0.1, 0.5] intersects [0.3, 0.7]
// [0.4, 0.8] intersects [0.3, 0.7]
