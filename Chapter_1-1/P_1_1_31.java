import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

// To run this program:
// make run ARGS="20 0.3"

public class P_1_1_31 {

    private static void drawRandomConnections(int N, double p) {
        setUpCanvas();
        drawCircle();
        double[][] points = computePoints(N);
        drawPoints(points);
        drawConnections(points, p);
    }

    private static void setUpCanvas() {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setScale(-1.2, 1.2);
    }

    private static void drawCircle() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        StdDraw.circle(0, 0, 1); 
    }

    private static double[][] computePoints(int N) {
        // compute the coordinates of N equally-spaced dots
        double[][] points = new double[N][2];
        double angle = 2 * Math.PI / N;
        for (int i = 0; i < N; i++) {
            points[i][0] = Math.cos(i * angle); // x-coordinate
            points[i][1] = Math.sin(i * angle); // y-coordinate
        }
        return points;
    }

    private static void drawPoints(double[][] points) {
        StdDraw.setPenColor(StdDraw.BLUE);
        for (double[] point : points) {
            StdDraw.filledCircle(point[0], point[1], 0.01);
        }
    }

    private static void drawConnections(double[][] points, double p) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.002);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (StdRandom.bernoulli(p)) {
                    StdDraw.line(points[i][0], points[i][1], points[j][0], points[j][1]);
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide two arguments: N and p");
            return;
        }

        // N: N equally-spaced dots on the circle
        // p: probability that a pair of dots are connected
        int N = Integer.parseInt(args[0]);
        double p = Double.parseDouble(args[1]);

        
        if (N < 2) {
            System.out.println("N must be greater than or equal to 2");
            return;
        }

        if (p < 0 || p > 1) {
            System.out.println("p must be within the range [0, 1]");
            return;
        }

        drawRandomConnections(N, p);
    }
}
