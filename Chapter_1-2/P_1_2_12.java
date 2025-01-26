import edu.princeton.cs.algs4.StdOut;

public class P_1_2_12 {
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

        public String dayOfTheWeek() {
            // Use Zeller's Congruence
            int m = (month < 3) ? month + 12 : month; // Adjust for Jan and Feb
            int y = (month < 3) ? year - 1 : year;    // Adjust year for Jan and Feb

            int q = day;         // Day of the month
            int K = y % 100;     // Year of the century
            int J = y / 100;     // Century

            int h = (q + (13 * (m + 1)) / 5 + K + (K / 4) + (J / 4) - (2 * J)) % 7;

            // h = 0 -> Saturday, 1 -> Sunday, 2 -> Monday, ..., 6 -> Friday
            String[] daysOfWeek = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            return daysOfWeek[h];

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
            SmartDate date1 = new SmartDate(10, 12, 2024); // Valid date
            StdOut.println(date1 + " is a " + date1.dayOfTheWeek());

            SmartDate date2 = new SmartDate(2, 29, 2020); // Valid leap year date
            StdOut.println(date2 + " is a " + date2.dayOfTheWeek());

            SmartDate date3 = new SmartDate(1, 1, 2000); // Start of the century
            StdOut.println(date3 + " is a " + date3.dayOfTheWeek());

        } catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
        }
    }
}

// 10/12/2024 is a Saturday
// 2/29/2020 is a Saturday
// 1/1/2000 is a Saturday
