import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// make run ARGS="3"

public class P_1_3_15 {
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: java <K> ");
        }

        int k = Integer.parseInt(args[0]);
        Queue<String> buffer = new Queue<>();

        while (!StdIn.isEmpty()) {
            buffer.enqueue(StdIn.readString());
            // we keep queue size of k, such that the head is 
            // always the kth string from last 
            if (buffer.size() > k) {
                buffer.dequeue();
            }
        }

        if (buffer.size() < k) {
            StdOut.println("Error: fewer than " + k + " strings on standard input");
        } else {
            StdOut.println(buffer.dequeue());
        }
    }
}

// example input:
// hello
// world
// i 
// am
// happy
// today

// example output:
// am