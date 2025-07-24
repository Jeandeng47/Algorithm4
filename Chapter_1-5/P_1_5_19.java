import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class P_1_5_19 {
    private static class Connection {
        int p;
        int q;
        
        public Connection(int p, int q) {
            this.p = p;
            this.q = q;
        }

        public String toString() {
            return p + " " + q;
        }
    }

    public static Connection[] generate(int N) {
        Bag<Connection> bag = new Bag<>();

        // Generate edge btw 2 adjacent sites

        // horizontal: (r, c), (r, c + 1), check col boundary!
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N - 1; c++) {
                int p = r * N + c;
                int q = p + 1;
                // randomly swap direction
                if (StdRandom.bernoulli()) {
                    int tmp = p; p = q; q = tmp;
                }
                bag.add(new Connection(p, q));
            }
        }

        // vertical: (r, c), (r + 1, c), check row boundary!
        for (int r = 0; r < N - 1; r++) {
            for (int c = 0; c < N; c++) {
                int p = r * N + c;
                int q = p + N;
                if (StdRandom.bernoulli()) {
                    int tmp = p; p = q; q = tmp;
                }
                bag.add(new Connection(p, q));
            }
        }

        Connection[] allConn = new Connection[bag.size()];
        int i = 0;
        for (Connection c : bag) {
            allConn[i++] = c;
        }
        StdRandom.shuffle(allConn);
        return allConn;
    }

    private static void setUpDrawing() {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(-0.05, 1.05);
        StdDraw.setYscale(-0.05, 1.05);
    }

    private static void drawEdges(int N, Connection[] conns) {
        // Draw coordinates
        double[] xs = new double[N * N];
        double[] ys = new double[N * N];
        
        for (int p = 0; p < N * N; p++) {
            int r = p / N;                          // row: 0, 1, ..., N - 1
            int c = p % N;                          // col: 0, 1, ..., N - 1
            xs[p] = c / (double)(N-1);              // x: 0, 1/(N-1), 2/(N-1), ..., 1
            ys[p] = 1 - r / (double)(N-1);          // y: 0, 1/(N-1), 2/(N-1), ..., 1
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.005);
            // draw N * N grid in unit square
            StdDraw.point(xs[p], ys[p]);
        }

        // draw edges
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(N * N);

        for (Connection c : conns) {
            int p = c.p;
            int q = c.q;
            // base line
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.setPenRadius(0.003);
            StdDraw.line(xs[p], ys[p], xs[q], ys[q]);

            // if we join 2 sites, draw in blue
            if (!wqu.connected(p, q)) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(xs[p], ys[p], xs[q], ys[q]);
            }

            StdDraw.show();
            StdDraw.pause(10);
        }
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: provide a positive integer");
            return;
        }

        int N = Integer.parseInt(args[0]);
        Connection[] conns = generate(N);

        setUpDrawing();
        drawEdges(N, conns);
    }
}