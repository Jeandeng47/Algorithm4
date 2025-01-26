import java.util.Objects;

import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.StdOut;

public class P_1_2_14 {
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
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            Transaction that = (Transaction) obj;
            return Double.compare(this.amount, that.amount) == 0 && 
            this.customer.equals(that.customer) &&
            this.date.equals(that.date);
        }

        @Override
        public String toString() {
            return String.format("%-10s %s %8.2f", customer, date, amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(customer, date, amount);
        }
    }

    public static void main(String[] args) {
        Transaction t1 = new Transaction("Alice", new Date("03/15/2023"), 100.50);
        Transaction t2 = new Transaction("Bob", new Date("04/10/2022"), 250.75);

        StdOut.println("Transaction 1: " + t1);
        StdOut.println("Transaction 2: " + t2);

        StdOut.println("Are transactions equal? " + t1.equals(t2));
    }
}


// Transaction 1: Alice      3/15/2023   100.50
// Transaction 2: Bob        4/10/2022   250.75
// Are transactions equal? false
    

