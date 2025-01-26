import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class P_1_2_10 {

    static class VisualCounter {
        private final int maxOp; // max number of operations
        private final int maxVal; // max absolute value of the counter
        private int count;
        private int operations;

        public VisualCounter(int N, int max) {
            this.maxOp = N;
            this.maxVal = max;
            this.count = 0;
            this.operations = 0;
            setUp();
        }

        private void setUp() {
            StdDraw.setXscale(0, maxOp + 1);
            StdDraw.setYscale(- maxVal - 1, maxVal);
            StdDraw.setPenRadius(0.005);
        }

        private void plot() {
            StdDraw.point(operations, count);
        }

        public void increment() {
            if (operations >= maxOp) {
                throw new IllegalStateException("Maximum number of operations exceeded");
            }
            if (count + 1 > maxVal) {
                throw new IllegalStateException("Counter value exceeds maximum allowed value");
            }
            count++;
            operations++;
            plot();
        }

        public void decrement() {
            if (operations >= maxOp) {
                throw new IllegalStateException("Maximum number of operations exceeded");
            }
            if (count - 1 < (-maxVal)) {
                throw new IllegalStateException("Counter value below minimum allowed value");
            }
            count--;
            operations++;
            plot();
        }

        public int tally() {
            return this.count;
        }


    }

    public static void main(String[] args) {

        int N = 20; // Maximum 20 operations
        int max = 10; // Counter values between -10 and +10

        VisualCounter vc = new VisualCounter(N, max);

        // Perform some random operations
        for (int i = 0; i < N; i++) {
            boolean rand = StdRandom.uniformDouble() < 0.4;
            if (rand) {
                vc.decrement();
            } else {
                vc.increment();
            }
        }

        System.out.printf("Final counter value: %d%n", vc.tally());
        
    }
}
