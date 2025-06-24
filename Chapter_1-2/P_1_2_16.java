public class P_1_2_16 {
    public static class Rational {
        private int n;   
        private int d;

        // Constructor
        public Rational(int numerator, int denominator) {
            this.n = numerator;
            this.d = denominator;
            if (d == 0) {
                throw new IllegalArgumentException("Denominator cannot be zero.");
            }
        }

        public Rational plus(Rational other) {
            // a/b + c/d = (ad + bc) / bd
            int newN = this.n * other.d + other.n * this.d;
            int newD = this.d * other.d;
            return new Rational(newN, newD);
        }

        public Rational minus(Rational other) {
            // a/b - c/d = (ad - bc) / bd
            int newN = this.n *  other.d - other.n * this.d;
            int newD = this.d * other.d;
            return new Rational(newN, newD);
        }

        public Rational times(Rational other) {
            // a/b * c/d = (ac) / (bd)
            int newN = this.n * other.n;
            int newD = this.d * other.d;
            return new Rational(newN, newD);
        }

        public Rational divides(Rational other) {
            // a/b ÷ c/d = (a * d) / (b * c) (c could not be zero)
            if (other.n == 0) {
                throw new IllegalArgumentException("Cannot divide by zero.");
            }
            int newN = this.n * other.d;
            int newD = this.d * other.n;
            return new Rational(newN, newD);
        }

        public boolean equals(Rational other) {
            // a/b == c/d if and only if ad = bc
            return this.n * other.d == this.d * other.n;
        }

        public String toString() {
            return n + "/" + d;
        }
    }

    public static void main(String[] args) {
         Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(2, 3);

        System.out.println("r1 = " + r1);
        System.out.println("r2 = " + r2);
        System.out.println("r1 + r2 = " + r1.plus(r2));
        System.out.println("r1 - r2 = " + r1.minus(r2));
        System.out.println("r1 * r2 = " + r1.times(r2));
        System.out.println("r1 / r2 = " + r1.divides(r2));
        System.out.println("r1 equals 2/4? " + r1.equals(new Rational(2, 4)));
    }
    
}
