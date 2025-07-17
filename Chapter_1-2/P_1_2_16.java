public class P_1_2_16 {
    public static class Rational {
        // Immutable
        private final long n; // numerator
        private final long d; // denominator

        public Rational(long n, long d) { // n / d
            if (d == 0) {
                throw new IllegalArgumentException("Denominator cannot be zero");
            }
            // ensure sign on numerator
            if (d < 0) {
                n = -n;
                d = -d;
            }
            // find greatest common divisor
            long g = gcd(Math.abs(n), d);
            this.n = n / g;
            this.d = d / g;
        }

        private static long gcd(long a, long b) {
            while (b != 0) {
                long t = b;
                b = a % b;
                a = t;
            }
            return a;
        }

        // Sum of this number and b
        public Rational plus(Rational b) {
            // a / b + c / d = ad + cb / bd
            long newN = this.n * b.d + b.n * this.d;
            long newD = this.d * b.d;
            return new Rational(newN, newD);
        }

        // Difference of this number minus b
        public Rational minus(Rational b) {
            // a / b - c / d = ad - cb / bd
            long newN = this.n * b.d - b.n * this.d;
            long newD = this.d * b.d;
            return new Rational(newN, newD);
        }

        // Product of this number and b
        public Rational times(Rational b) {
            // a / b  *  c / d = ac / bd
            long newN = this.n * b.n;
            long newD = this.d * b.d;
            return new Rational(newN, newD);
        }

        // Quotient of this number and b
        public Rational divides(Rational b) {
            // (a / b) / (c / d) = (a / b) * (d / c) = ad / bc
            if (b.n == 0) {
                throw new ArithmeticException("Divide by zero");
            }
            long newN = this.n * b.d;
            long newD = this.d * b.n;
            return new Rational(newN, newD);
        }

        public boolean equals(Rational obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            Rational that = (Rational) obj;
            return (this.n == that.n) && (this.d == that.d);
        }

        public String toString() {
            if (this.d == 1) return this.n + "";
            return this.n + "/" + this.d;
        }
    }

    public static void main(String[] args) {
        // basic tests
        Rational r1 = new Rational(1, 2); 
        Rational r2 = new Rational(4, 6); 
        System.out.println("r1 = " + r1);  // r1 = 1/2
        System.out.println("r2 = " + r2);  // r2 = 2/3
        System.out.println("r1 + r2 = " + r1.plus(r2));     // r1 + r2 = 7/6
        System.out.println("r1 - r2 = " + r1.minus(r2));    // r1 - r2 = -1/6
        System.out.println("r1 * r2 = " + r1.times(r2));    // r1 * r2 = 1/3
        System.out.println("r1 / r2 = " + r1.divides(r2));  // r1 / r2 = 3/4

        // test reduction & sign handling
        Rational r3 = new Rational(2, 4);
        Rational r4 = new Rational(-2, -4);
        Rational r5 = new Rational(2, -4);
        System.out.println("2/4 reduced = " + r3);    // 2/4 reduced = 1/2
        System.out.println("-2/-4 reduced = " + r4);  // -2/-4 reduced = 1/2 
        System.out.println("2/-4 reduced = " + r5);   // 2/-4 reduced = -1/2

        // test equals
        System.out.println("r3.equals(r1)? " + r3.equals(r1));  // true
        System.out.println("r4.equals(r3)? " + r4.equals(r3));  // true
        System.out.println("r5.equals(new Rational(-1,2))? " + r5.equals(new Rational(-1,2)));

    }
    
}
