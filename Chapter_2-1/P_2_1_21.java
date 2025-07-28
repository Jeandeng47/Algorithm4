import edu.princeton.cs.algs4.Date;

public class P_2_1_21 {
    public static class Transaction implements Comparable<Transaction> {
        private final String who;
        private final Date when;
        private final double amount;

        public Transaction(String who, Date when, double amount) {
            if (who == null || when == null) {
                throw new IllegalArgumentException("Must provide name and date");
            }
            if (amount < 0.0) {
                throw new IllegalArgumentException("Amount should be non-negative");
            }
            this.who = who;
            this.when = when;
            this.amount = amount;
        }
        
        @Override
        public int compareTo(Transaction that) {
            return Double.compare(this.amount, that.amount);
        }

        @Override
        public String toString() {
            return String.format("%-10s  %10s  %8.2f", 
                                who, when, amount);
        }
    }
}
