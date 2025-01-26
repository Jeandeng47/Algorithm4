import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.StdOut;

public class P_1_2_13 {
    
    static class Transaction {

        private final Date date;
        private final String customer;
        private final double amount;

        public Transaction(String customer, Date date, double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount cannot be negative");
            }
            this.customer = customer;
            this.date = date;
            this.amount = amount;
        }

        public String customer() {
            return this.customer;
        }

        public Date date() {
            return this.date;
        }

        public double amount() {
            return this.amount;
        }

        @Override
        public String toString() {
            return String.format("%-10s %s %8.2f", customer, date, amount);
        }

    }

    public static void main(String[] args) {
        Transaction t1 = new Transaction("Alice", new Date("03/15/2023"), 100.50);
        Transaction t2 = new Transaction("Bob", new Date("04/10/2022"), 250.75);

        StdOut.println("Transaction 1: " + t1);
        StdOut.println("Transaction 2: " + t2);

    }
}

// Transaction 1: Alice      3/15/2023   100.50
// Transaction 2: Bob        4/10/2022   250.75
