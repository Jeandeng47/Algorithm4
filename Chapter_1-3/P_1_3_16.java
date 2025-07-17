import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P_1_3_16 {
    // read dates from stdin, return array of dates
    public static Date[] readDates() {
        Queue<Date> q = new Queue<>();

        while (!StdIn.isEmpty()) {
            // input format: month/day/year
            String s = StdIn.readString();
            String[] parts = s.split("/");
            int m = Integer.parseInt(parts[0]);
            int d = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            q.enqueue(new Date(m, d, y));
        }

        int n = q.size();
        Date[] dates = new Date[n];
        for (int i = 0; i < n; i++) {
            dates[i] = q.dequeue();
        }
        return dates;
    }

    public static void main(String[] args) {
        Date[] dates = readDates();
        
        StdOut.println("Date array: ");
        for (Date d: dates) {
            StdOut.println(d.toString());
        }
    }
    
}

// example input:
// 10/15/1998
// 7/17/2025
// 8/17/1995
// ctrl + D

// example output:
// Date array: 
// 10/15/1998
// 7/17/2025
// 8/17/1995
