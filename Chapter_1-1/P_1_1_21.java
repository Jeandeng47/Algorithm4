import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_1_21 {
    public static void main(String[] args) {

        List<Record> records = new ArrayList<>();

        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            String[] tokens = line.split("\\s+");

            if (tokens.length != 3) {
                StdOut.println("Invalid input format. Each line should contain a name and two integers.");
                continue;
            }

            String name = tokens[0];
            try {
                int num1 = Integer.parseInt(tokens[1]);
                int num2 = Integer.parseInt(tokens[2]);
                records.add(new Record(name, num1, num2));
            } catch (NumberFormatException e) {
                StdOut.println("Invalid integers in input: " + line);
            }

        }

        // Print table header
        StdOut.printf("%-15s %-10s %-10s %-10s%n", "Name", "Num1", "Num2", "Result");
        StdOut.println();

        // Print all records
        for (Record record : records) {
            System.out.println(record); // Uses Record's toString() method
        }
    }

    static class Record {
        private String name;
        private int num1;
        private int num2;

        public Record(String name, int num1, int num2) {
            this.name = name;
            this.num1 = num1;
            this.num2 = num2;
        }

        private String calculate() {
            if (num2 == 0) {
                return "Error"; // Handle division by zero
            }
            return String.format("%.3f", (double) num1 / num2);
        }

        @Override
        public String toString() {
            return String.format("%-15s %-10d %-10d %-10s%n", name, num1, num2, calculate());
        }
    }
}

// Input: 
// Alice 10 5
// Bob 8 4
// Charlie 15 3
// David 6 4

// Output:
// Name            Num1       Num2       Result    

// Alice           10         5          2.000     

// Bob             8          4          2.000     

// Charlie         15         3          5.000     

// David           6          4          1.500 