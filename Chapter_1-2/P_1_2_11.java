import edu.princeton.cs.algs4.StdOut;

public class P_1_2_11 {

    static class SmartDate {
        private final int month;
        private final int day;
        private final int year;

        public SmartDate(int m, int d, int y) {
            if (!isValidDate(m, d, y)) {
                throw new IllegalArgumentException("Invalid date: " + m + "/" + d + "/" + y);
            }
            this.month = m;
            this.day = d;
            this.year = y;
        }

        public int month() {
            return this.month;
        }

        public int day() {
            return this.day;
        }

        public int year() {
            return this.year;
        }

        public String toString() {
            return month() + "/" + day() + "/" + year();
        }

        public boolean equals(Object x) {
            if (this == x) return true;
            if (x == null) return false;
            if (this.getClass() != x.getClass()) return false;
            SmartDate that = (SmartDate) x;
            return this.day == that.day && this.month == that.month && this.year == that.year;
        }

        private static boolean isValidDate(int m, int d, int y) {
            // check month
            if (m > 12 || m < 1) {
                return false;
            }

            // check day
            if (d < 1 || d > 31) {
                return false;
            }

            // check leap year and special month
            int[] daysInMonth =  {
                0,  // 1-based indexing
                31, // January
                28, // February (non-leap year)
                31, // March
                30, // April
                31, // May
                30, // June
                31, // July
                31, // August
                30, // September
                31, // October
                30, // November
                31  // December
            };
            
            if (m == 2 && isLeapYear(y)) {
                daysInMonth[2] = 29;
            }

            return d <= daysInMonth[m];
        }
  
        private static boolean isLeapYear(int year) {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        }

    }
   
    
    public static void main(String[] args) {
        try {
            SmartDate date1 = new SmartDate(2, 29, 2024); // Valid leap year
            StdOut.println("Valid date: " + date1);

            SmartDate date2 = new SmartDate(2, 29, 2023); // Invalid (not a leap year)
            StdOut.println("Valid date: " + date2);
        } catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
        }

        try {
            SmartDate date3 = new SmartDate(4, 31, 2022); // Invalid (April has 30 days)
            StdOut.println("Valid date: " + date3);
        } catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
        }

        try {
            SmartDate date4 = new SmartDate(12, 25, 2022); // Valid
            StdOut.println("Valid date: " + date4);
        } catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
        }
    }
}

// Valid date: 2/29/2024
// Invalid date: 2/29/2023
// Invalid date: 4/31/2022
// Valid date: 12/25/2022
