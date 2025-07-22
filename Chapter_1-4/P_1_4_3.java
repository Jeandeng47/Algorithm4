import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class P_1_4_3 {
    private static void plot(List<Integer> Ns, List<Double> times, boolean isLog) {
        // Handle coordinate
        List<Double> xs = new ArrayList<>(Ns.size());
        List<Double> ys = new ArrayList<>(times.size());

        for (int i = 0; i < Ns.size(); i++) {
            double x = isLog? Math.log10(Ns.get(i)) : Ns.get(i);
            double y = isLog? Math.log10(times.get(i)) : times.get(i);
            xs.add(x);
            ys.add(y);
        }

        // determine bounds
        double minX = Collections.min(xs);
        double maxX = Collections.max(xs);
        double minY = Collections.min(ys);
        double maxY = Collections.max(ys);
        // margins
        double dx = (maxX - minX) * 0.05;
        double dy = (maxY - minY) * 0.05;

        // set up canvas
        StdDraw.clear();
        StdDraw.setPenRadius(0.015);
        StdDraw.setXscale(minX - dx, maxX + dx);
        StdDraw.setYscale(minY - dy, maxY + dy);

        // Draw points and lines
        for (int i = 0; i < xs.size(); i++) {
            // points
            double x = xs.get(i);
            double y = ys.get(i);
            StdDraw.point(x, y);

            // lines
            if (i > 0) {
                double px = xs.get(i - 1);
                double py = ys.get(i - 1);
                StdDraw.line(px, py, x, y);
            }
        }
        StdDraw.show();
        
    }

    public static double timeTrial(int N) {
        int MAX = 1000000;
        int[] a = new int[N];
        
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniformInt(-MAX, MAX);
        }
        Stopwatch timer = new Stopwatch();
        int count = _ThreeSum.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        int start = 125;
        int rounds = 5;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();

        for (int i = 0, n = start; i < rounds; i++, n *= 2) {
            double t = timeTrial(n);
            StdOut.printf("%7d %7.3f%n", n, t);
            Ns.add(n);
            times.add(t);
        }

        plot(Ns, times, false);
        StdDraw.pause(2000);
        plot(Ns, times, true);
    }
}
