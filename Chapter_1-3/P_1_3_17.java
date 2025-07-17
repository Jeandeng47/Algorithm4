import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Transaction;

public class P_1_3_17 {
    public static Transaction[] readTransactions() {
        Queue<Transaction> q = new Queue<>();

        while (!StdIn.isEmpty()) {
            String who      = StdIn.readString();
            String dateTok  = StdIn.readString();
            String[] parts  = dateTok.split("/");
            int m = Integer.parseInt(parts[0]);
            int d = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            Date when       = new Date(m, d, y);

            double amount   = StdIn.readDouble();

            q.enqueue(new Transaction(who, when, amount));
        }

        Transaction[] result = new Transaction[q.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = q.dequeue();
        return result;
    }

    public static void main(String[] args) {
        
        Transaction[] txs = readTransactions();
        StdOut.println("Transactions array: ");
        for (Transaction t : txs) {
            StdOut.println(t);
        }
    }
}

// example input:
// Alice 3/5/2020 100.00
// Bob   7/15/2021 200.50
// Carol 1/1/2022 300.75
// ctrl + D

// example output:
// Transactions array: 
// Alice        3/5/2020   100.00
// Bob         7/15/2021   200.50
// Carol        1/1/2022   300.75

