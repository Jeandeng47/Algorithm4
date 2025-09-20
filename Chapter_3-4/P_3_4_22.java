import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.StdOut;

public class P_3_4_22 {
    public static class Point2D {
        private double x;
        private double y;
        public Point2D(double x, double y) {
            this.x = x; this.y = y;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point2D)) return false;
            Point2D that = (Point2D) o;
            return Double.compare(this.x, that.x) == 0 &&
                   Double.compare(this.y, that.y) == 0;
        }
        public int hashCode() {
            int h = 17;
            h = 31*h + Double.hashCode(x);
            h = 31*h + Double.hashCode(y);
            return h;
        }
    }

    public static class Interval {
        private final double lo, hi;

        public Interval(double a, double b) {
            if (Double.isNaN(a) || Double.isNaN(b)) throw new IllegalArgumentException("NaN endpoint");
            // normalize so that lo <= hi
            this.lo = Math.min(a, b);
            this.hi = Math.max(a, b);
        }
        public double lo() { return lo; }
        public double hi() { return hi; }
        public boolean contains(double v) { return v >= lo && v <= hi; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Interval)) return false;
            Interval that = (Interval) o;
            return Double.compare(this.lo, that.lo) == 0 &&
                   Double.compare(this.hi, that.hi) == 0;
        }

        @Override public int hashCode() {
            int h = 17;
            h = 31*h + Double.hashCode(lo);
            h = 31*h + Double.hashCode(hi);
            return h;
        }
    }
    public static class Interval2D {
        private final Interval x, y;

        public Interval2D(Interval x, Interval y) {
            if (x == null || y == null) throw new IllegalArgumentException("null interval");
            this.x = x; this.y = y;
        }
        public Interval x() { return x; }
        public Interval y() { return y; }
        

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Interval2D)) return false;
            Interval2D that = (Interval2D) o;
            return this.x.equals(that.x) && this.y.equals(that.y);
        }

        @Override public int hashCode() {
            int h = 17;
            h = 31*h + x.hashCode();
            h = 31*h + y.hashCode();
            return h;
        }
    }

    public static class Date {
        private final int year;   // e.g., 2025
        private final int month;  // 1..12
        private final int day;    // 1..31 (basic check here; not a full calendar lib)

        public Date(int year, int month, int day) {
            if (month < 1 || month > 12) throw new IllegalArgumentException("month out of range");
            if (day < 1 || day > 31)     throw new IllegalArgumentException("day out of range");
            this.year = year; this.month = month; this.day = day;
        }
        public int year() { return year; }
        public int month() { return month; }
        public int day() { return day; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Date)) return false;
            Date that = (Date) o;
            return this.year == that.year && this.month == that.month && this.day == that.day;
        }

        @Override public int hashCode() {
            int h = 17;
            h = 31*h + year;
            h = 31*h + month;
            h = 31*h + day;
            return h;
        }
    }

    public static void main(String[] args) {
        Point2D p1 = new Point2D(1.5, 2.5);
        Point2D p2 = new Point2D(1.5, 2.5);
        Point2D p3 = new Point2D(2.0, 3.0);

        StdOut.println("p1 = " + p1 + ", hash = " + p1.hashCode());
        StdOut.println("p2 = " + p2 + ", hash = " + p2.hashCode());
        StdOut.println("p1.equals(p2)? " + p1.equals(p2));
        StdOut.println("p1.equals(p3)? " + p1.equals(p3));

        // p1.equals(p2)? true
        // p1.equals(p3)? false


        Interval ix1 = new Interval(0.0, 5.0);
        Interval ix2 = new Interval(5.0, 0.0); // equals ix1
        Interval ix3 = new Interval(-2.0, 3.0);

        StdOut.println("ix1 = " + ix1 + ", hash = " + ix1.hashCode());
        StdOut.println("ix2 = " + ix2 + ", hash = " + ix2.hashCode());
        StdOut.println("ix1.equals(ix2)? " + ix1.equals(ix2));
        StdOut.println("ix1.equals(ix3)? " + ix1.equals(ix3));

        // ix1.equals(ix2)? true
        // ix1.equals(ix3)? false

        Interval2D box1 = new Interval2D(ix1, ix3);
        Interval2D box2 = new Interval2D(ix2, ix3);

        StdOut.println("box1 = " + box1 + ", hash = " + box1.hashCode());
        StdOut.println("box2 = " + box2 + ", hash = " + box2.hashCode());
        StdOut.println("box1.equals(box2)? " + box1.equals(box2));

        // box1.equals(box2)? true

        Date d1 = new Date(2025, 9, 20);
        Date d2 = new Date(2025, 9, 20);
        Date d3 = new Date(2024, 12, 31);

        StdOut.println("d1 = " + d1 + ", hash = " + d1.hashCode());
        StdOut.println("d2 = " + d2 + ", hash = " + d2.hashCode());
        StdOut.println("d1.equals(d2)? " + d1.equals(d2));
        StdOut.println("d1.equals(d3)? " + d1.equals(d3));

        // d1.equals(d2)? true
        // d1.equals(d3)? false
    }
}

// Hash code: if the object has k features f1, f2, ..., fk
// the ideal hash function 
// hash(k) = c0 + c1 * 31^(k-1) + c2 * 31^(k-2) + ... + ck * 31^(k-k)
// c0 = 17
// ci = h(ki)
