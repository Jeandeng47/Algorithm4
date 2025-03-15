import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// make run
// 0.1 0.5 0.9 0.3 0.8 0.7 0.2 0.6 0.4 1.0
// press ctrl-d

public class P_1_1_34 {
    public static void main(String[] args) {    
        // Part 1: fixed number of variables and fixed size array

        // Print the maximum and minimum numbers.
        // Print the sum of the squares of the numbers.
        // Print the average of the N numbers.
        // Print the median of the numbers.
        double min = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        double sum = 0;
        double sumOfSquares = 0;
        int N = 0; 

        // Print the k th smallest value, for k less than 100.
        // Max heap for lower half: peek() gives max in lower half
        // Min heap for upper half: peek() gives min in upper half
        PriorityQueue<Double> lower = new PriorityQueue<Double>(Collections.reverseOrder()); 
        PriorityQueue<Double> upper = new PriorityQueue<Double>();

        List<Double> values = new ArrayList<Double>();

        while (!StdIn.isEmpty()) {
            double value = StdIn.readDouble();
            if (value < min) min = value;
            if (value > max) max = value;
            sum += value;
            sumOfSquares += value * value;
            N++;

            // Store value in heap
            if (lower.size() == 0 || value < lower.peek()) {
                lower.add(value);
            } else {
                upper.add(value);
            }
            // rebalance the heap: median is the top of the lower heap
            if (lower.size() > upper.size() + 1) { // lower could have one extra element
                upper.add(lower.poll());
            } else if (upper.size() > lower.size()) {
                lower.add(upper.poll());
            }

            // Store values for part 2
            values.add(value);
        }

        StdOut.println("Min: " + min);
        StdOut.println("Max: " + max);
        StdOut.println("Sum of squares: " + sumOfSquares);
        double average = sum / N;
        StdOut.println("Average: " + average);

        double median;
        if (lower.size() > upper.size()) {
            median = lower.peek();
        } else {
            median = (lower.peek() + upper.peek()) / 2;
        }
        StdOut.println("Median: " + median);
       
        
        // Part 2: Require storing all the values
        // Print the percentage of numbers greater than the average.
        // Print the N numbers in increasing order.
        // Print the N numbers in random order.
       
        Collections.sort(values);
        StdOut.println("Numbers in increasing order: ");
        for (double value : values) {
            StdOut.print(value + " ");
        }
        StdOut.println();

        int count = (int) values.stream().filter(v -> v > average).count();
        double percentage = (double) count / N * 100;
        StdOut.println("Percentage of numbers greater than the average: " + percentage + "%");
        
        // Shuffle the list
        Collections.shuffle(values);
        StdOut.println("Numbers in random order: ");
        for (double value : values) {
            StdOut.print(value + " ");
        }
        StdOut.println();
    }
}

// Example input: 0.1 0.5 0.9 0.3 0.8 0.7 0.2 0.6 0.4 1.0

// Min: 0.1
// Max: 1.0
// Sum of squares: 3.85
// Average: 0.55
// Median: 0.55
// Numbers in increasing order: 
// 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0 
// Percentage of numbers greater than the average: 50.0%
// Numbers in random order: 
// 0.5 0.2 0.3 0.9 0.8 0.1 0.4 0.6 0.7 1.0 