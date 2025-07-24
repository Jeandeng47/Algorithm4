import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// To run this program:
// make run ARGS=5

public class P_1_5_18 {
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

    // Visualize the random grid 
    public static void visualize(int N, Connection[] conns) {
        // track existings edges
        boolean[][] horizontalE = new boolean[N][N-1];
        boolean[][] verticalE = new boolean[N - 1][N];

        // edge arrays
        for (Connection c : conns) {
            int pr = c.p / N, pc = c.p % N;
            int qr = c.q / N, qc = c.q % N;
            
            if (pr == qr && Math.abs(pc - qc) == 1) {
                // horizontal edge
                int col = Math.min(pc, qc);
                int row = pr;
                horizontalE[row][col] = true;
            } else if (pc == qc && Math.abs(pr - qr) == 1) {
                // vertical edge
                int row = Math.min(pr, qr);
                int col = pc;
                verticalE[row][col] = true;
            }
        }

        // print row by row, from left to right
        for (int r = 0; r < N; r++) {
            // horizontal edges (except last col)
            for (int c = 0; c < N; c++) {
                StdOut.print("o");
                if (c < N-1) StdOut.print(horizontalE[r][c] ? "--" : "  ");
            }
            StdOut.println();

            // vertical edges (except last row)
            if (r < N - 1) {
                for (int c = 0; c < N; c++) {
                    StdOut.print(verticalE[r][c] ? "|" : "  ");
                    if (c < N-1) StdOut.print("  ");
                }
                StdOut.println();
            }
        }

    }

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: provide a positive integer");
            return;
        }
 
        int N = Integer.parseInt(args[0]);
        Connection[] conns = generate(N);
        
        // print connection
        // for (Connection c : conns) {
        //     StdOut.println(c);
        // };
        // StdOut.println();

        // visualize
        StdOut.printf("Visualizing %d * %d grid\n", N, N);
        visualize(N, conns);
    }
}


// Visualizing 5 * 5 grid
// o--o--o--o--o
// |  |  |  |  |
// o--o--o--o--o
// |  |  |  |  |
// o--o--o--o--o
// |  |  |  |  |
// o--o--o--o--o
// |  |  |  |  |
// o--o--o--o--o