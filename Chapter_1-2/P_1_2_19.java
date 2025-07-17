import edu.princeton.cs.algs4.Date;

public class P_1_2_19 {

    public static class Date {
        private final int month;
        private final int day;
        private final int year;

        public Date(int m, int d, int y) {
            if (!isValidDate(m, d, y)) {
                throw new IllegalArgumentException("Invalid date: " + m + "/" + d + "/" + y);
            }
            this.month = m;
            this.day = d;
            this.year = y;
        }

        public Date(String date) {
            String[] fields = date.split("/");
            this.month = Integer.parseInt(fields[0]);
            this.day = Integer.parseInt(fields[1]);
            this.year = Integer.parseInt(fields[2]);
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
            Date that = (Date) x;
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
        Date strDate = new Date("5/22/1939");
        Date d = new Date(5, 22, 1939);

        System.out.println("Constructor with string: " + strDate);
        System.out.println("Constructor with int: " + d);

    }

}
