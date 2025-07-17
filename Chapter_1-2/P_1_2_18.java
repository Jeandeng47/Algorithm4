public class P_1_2_18 {
    public static class Accumulator {
        private double m; // mean value
        private double s;
        private int N; // number of values

        public void addDataValue(double x) {
            N++;
            s = s + 1.0 * (N - 1) / N * (x - m) * (x - m);
            m = m + (x - m) / N;
        }

        public double mean() {
            return m;
        }

        public double var() {
            return s / (N - 1);
        }

        public double stddev() {
            return Math.sqrt((this.var()));
        }

    }

    public static class NaiveAccumulator {
        private double sum;
        private double sumSq;
        private int N;

        public void addDataValue(double x) {
            N++;
            sum += x;
            sumSq += x * x;
        }

        public double mean() {
            return sum / N;
        }

        public double var() {
            return (sumSq - (sum * sum) / N) / (N - 1);
        }

        public double stddev() {
            return Math.sqrt(var());
        }
    }

    public static void test(double[] data) {
        Accumulator acc = new Accumulator();
           NaiveAccumulator naive = new NaiveAccumulator();
        for (double x : data) {
            acc.addDataValue(x);
            naive.addDataValue(x);
        }
        System.out.printf("Samples: %d\n", data.length);
        System.out.printf("Mean:     Acc=%.9f, Naive=%.9f\n", acc.mean(), naive.mean());
        System.out.printf("Variance: Acc=%.9f, Naive=%.9f\n", acc.var(), naive.var());
        System.out.printf("Stddev:   Acc=%.9f, Naive=%.9f\n\n", acc.stddev(), naive.stddev());
    }

    public static void main(String[] args) {
        double[] small = {1e8 + 1, 1e8 + 2, 1e8 + 3, 1e8 + 4, 1e8 + 5};
        test(small);
    }
}

// Samples: 5
// Mean:     Acc=100000003.000000000, Naive=100000003.000000000
// Variance: Acc=2.500000000, Naive=2.000000000
// Stddev:   Acc=1.581138830, Naive=1.414213562