import java.util.Arrays;

import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.StdOut;

public class P_2_1_22 {
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

    public static void main(String[] args) {
        Transaction[] a = new Transaction[] {
            new Transaction("Alice", new Date( 5, 21, 2025),  250.00),
            new Transaction("Bob",   new Date( 5, 20, 2025), 1200.50),
            new Transaction("Carol", new Date( 5, 22, 2025),   75.25),
            new Transaction("Dave",  new Date( 5, 19, 2025),  500.00),
        };

        StdOut.println("Before sort:");
        for (Transaction t : a) StdOut.println(t);

        Arrays.sort(a);

        StdOut.println("\nAfter sort by amount:");
        for (Transaction t : a) StdOut.println(t);
    }
}
