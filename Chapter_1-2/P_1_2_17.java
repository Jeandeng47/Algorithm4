public class P_1_2_17 {
    public static class Rational {

        private final long numerator;
        private final long denominator;
    
        public Rational(long p, long q) {
            assert q != 0 : "Denominator cannot be zero";
            if (q < 0) {
                p = -p;
                q = -q;
            }
            long g = gcd(abs(p), q);
            this.numerator = p / g;
            this.denominator = q / g;
        }

        private static long gcd(long a, long b) {
            while (b != 0) {
                long t = b;
                b = a % b;
                a = t;
            }
            return a;
        }

        // absolute value, with assertion against Long.MIN_VALUE
        private static long abs(long x) {
            assert x != Long.MIN_VALUE : "Overflow: abs(Long.MIN_VALUE)";
            return x < 0 ? -x : x;
        }

        // safe addition: assert no overflow
        private static long addExact(long x, long y) {
            long r = x + y;
            // if x and y have same sign, result must have same sign
            assert ((x ^ y) < 0) || ((x ^ r) >= 0)
                : "Overflow in addExact: " + x + " + " + y;
            return r;
        }

        // safe multiplication: assert no overflow
        private static long mulExact(long x, long y) {
            long r = x * y;
            // handle zero explicitly
            assert y == 0 || r / y == x
                : "Overflow in mulExact: " + x + " * " + y;
            return r;
        }

        public Rational plus(Rational b) {
            long p1 = mulExact(this.numerator, b.denominator);
            long p2 = mulExact(b.numerator, this.denominator);
            long p  = addExact(p1, p2);
            long q  = mulExact(this.denominator, b.denominator);
            return new Rational(p, q);
        }

        public Rational minus(Rational b) {
            long p1 = mulExact(this.numerator, b.denominator);
            long p2 = mulExact(b.numerator, this.denominator);
            long p  = addExact(p1, -p2);
            long q  = mulExact(this.denominator, b.denominator);
            return new Rational(p, q);
        }

        public Rational times(Rational b) {
            long p = mulExact(this.numerator, b.numerator);
            long q = mulExact(this.denominator, b.denominator);
            return new Rational(p, q);
        }

        public Rational divides(Rational b) {
            assert b.numerator != 0 : "Divide by zero Rational";
            long p = mulExact(this.numerator, b.denominator);
            long q = mulExact(this.denominator, b.numerator);
            return new Rational(p, q);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Rational)) return false;
            Rational that = (Rational) obj;
            return this.numerator == that.numerator
                && this.denominator == that.denominator;
        }

        @Override
        public String toString() {
            return (denominator == 1)
                ? Long.toString(numerator)
                : numerator + "/" + denominator;
        }

    }

    public static void main(String[] args) {
        // Values that do not overflow
        Rational x = new Rational(1L << 32, 1);
        Rational y = new Rational(1L << 31, 1);
        System.out.println("No overflow expected: " + x.plus(y));
        
        // Overflow assertion
        Rational big1 = new Rational(1L << 62, 1);
        Rational big2 = new Rational(1L << 62, 1);
        System.out.println("Expect overflow assertion on next line:");
        System.out.println(big1.plus(big2));  
    }

}
