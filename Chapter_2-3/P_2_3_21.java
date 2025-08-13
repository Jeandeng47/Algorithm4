import java.math.BigInteger;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class P_2_3_21 {
    private static int trials = 5;
    private static int Nmin = 20, Nmax = 50; // N: total items
    private static int Kmin = 2, Kmax = 10; // K : distinct k values

    // N! / f1 ! * f2 ! * ... * fn-1! * fn!
    private static BigInteger multiNomialCount(int[] f) {
        int N = Arrays.stream(f).sum();
        BigInteger[] fact = factorialTable(N);
        
        // denominator = f1 ! * f2 ! * ... * fn-1! * fn!
        BigInteger denom = BigInteger.ONE;
        for (int fi : f) {
            denom = denom.multiply(fact[fi]);
        }
        // Numerator: N !
        BigInteger numer = fact[N];
        return numer.divide(denom); 
    }

    // table[] : t[i] = factorial of i
    private static BigInteger[] factorialTable(int N) {
        BigInteger[] fact = new BigInteger[N + 1];
        fact[0] = BigInteger.ONE;
        for (int i = 1; i <= N; i++) {
            fact[i] = fact[i - 1].multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    //  H = - (p1lg(p1) + p2lg(p2) + p3lg(p3) + .... + pklg(pk)), pi = fi / N 
    private static double entropyBits(double[] p) {
        double H = 0.0;
        for (double pi : p) {
            if (pi > 0) {
                H -= pi * (Math.log(pi) / Math.log(2));
            }
        }
        return H;
    }

    private static double[] probsFromCount(int[] freq) {
        int sum = Arrays.stream(freq).sum();
        int N = freq.length;
        double[] p = new double[N];
        for (int i = 0; i < N; i++) {
            p[i] = (double) freq[i] / sum;
        }
        return p;
    }

    // Dirichlet(1,...,1): sample k i.i.d. Exp(1) and normalize
    private static double[] randomProb(int k) {
        double[] w = new double[k];
        double sum = 0.0;
        for (int i = 0; i < k; i++) {
            double u = Math.max(StdRandom.uniformDouble(), 1e-300); // avoid log(0)
            double e = -Math.log(u); // Exp(1)
            w[i] = e; sum += e;
        }
        for (int i = 0; i < k; i++) w[i] /= sum;
        return w;
    }

    // Draw N samples from p[] to get frequency count (inverse transform sampling)
    private static int[] counts(int N, double[] p) {
        int k = p.length;
        double[] cdf = new double[k];
        double acc = 0.0;
        
        for (int i = 0; i < k; i++) {
            acc += p[i];
            cdf[i] = acc;
        }
        cdf[k - 1] = 1.0;

        int[] f = new int[k];
        for (int t = 0; t < N; t++) {
            double u = StdRandom.uniformDouble();
            int pos = Arrays.binarySearch(cdf, u);
            int i = (pos >= 0)? pos + 1: -pos -1;  // first cdf[i] > u
            if (i >= k) i = k - 1;
            f[i]++;
        }
        return f;
    }

    // return uniform integer in [lo, hi]
    private static int randInt(int lo, int hi) {
        return lo + StdRandom.uniformInt(hi - lo + 1);
    }

    private static int ceilLog2(BigInteger x) {
        int b = x.bitLength(); // floor(log2 x) + 1
        boolean pow2 = x.and(x.subtract(BigInteger.ONE)).equals(BigInteger.ZERO);
        return pow2 ? (b - 1) : b;
    }

    private static double log2Big(BigInteger x) {
        int b = x.bitLength();
        if (b <= 62) return Math.log(x.longValue()) / Math.log(2);
        int shift = b - 53;
        long top = x.shiftRight(shift).longValue(); // keep ~53 bits
        double mant = top / (double) (1L << 52);
        return (b - 1) + Math.log(mant) / Math.log(2);
    }

    public static void main(String[] args) {
        StdRandom.setSeed(42L);

        for (int t = 1; t <= trials; t++) {
            // randomly choose N and K
            int N = randInt(Nmin, Nmax);
            int K = randInt(Kmin, Kmax);

            // generate probability -> frequency array
            double[] p = randomProb(K);
            int[] f = counts(N, p);
            double[] phat = probsFromCount(f); // f/N

            double Hbits = entropyBits(phat); // Shannon entropy
            BigInteger L = multiNomialCount(f); // N ! / ∏ f_i!
            int worst = ceilLog2(L);            // exact worst-case ≥ ceil(log2 L)
            double avg = log2Big(L);            // average-case ≥ log2 L (approx)

            StdOut.printf("Trial %d%n", t);
            StdOut.printf("  N=%d, K=%d%n", N, K);
            StdOut.print("  Probability array p: ");
            for (double pi : p) { StdOut.printf("%.3f ", pi);};
            StdOut.println();

            StdOut.print("  Frequency array f : ");
            for (double fi : f) { StdOut.printf("%.3f ", fi);};

            StdOut.printf ("  sum(f)=%d  (should equal N)%n", Arrays.stream(f).sum());
            StdOut.printf ("  Entropy H(p̂)=%.3f bits;  N·H(p̂) - N =%.3f comps (Prop M lower bound)%n",
                           Hbits, N * Hbits - N);
            StdOut.printf ("  Decision-tree bound: ceil(log2 L)=%d (worst), log2 L≈%.3f (avg)%n",
                           worst, avg);
            StdOut.println();

        } 
    }

}

// Proposition M : 
// No compare-based sorting algorithm can guarantee to sort Nitems 
// with fewer than NH - N compares, where H is the Shannon entropy, 
// defined from the frequencies of key values.
// H = - (p1lg(p1) + p2lg(p2) + p3lg(p3) + .... + pklg(pk))
