import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_1_1 {
    public static void main(String[] args) {
        String[] input = {
            "A+,4.33", "A,4.00", "A-,3.67",
            "B+,3.33", "B,3.00", "B-,2.67",
            "C+,2.33", "C,2.00", "C-,1.67",
            "D,1.00", "F,0.00" 
        };

        BinarySearchST<String, Double> st = new BinarySearchST<>();

        // read letter and scores 
        for (String s : input) {
            String[] parts = s.split(",");
            String grade = parts[0].trim();
            Double value = Double.parseDouble(parts[1].trim());
            st.put(grade, value);
        }

        // print 
        StdOut.println("Grade table: ");
        for (String k : st.keys()) {
            StdOut.printf("%-2s : %.2f\n", k, st.get(k));
        }
        StdOut.println();

        double sum = 0.0;
        int count = 0;

        for (String letter : st.keys()) {
            Double score = st.get(letter);
            sum += score;
            count++;
        }

        double avg = (count == 0) ? 0.0 : sum / count;
        StdOut.printf("avg GPA = %.2f\n", avg);

    }
}


// Grade table: 
// A  : 4.00
// A+ : 4.33
// A- : 3.67
// B  : 3.00
// B+ : 3.33
// B- : 2.67
// C  : 2.00
// C+ : 2.33
// C- : 1.67
// D  : 1.00
// F  : 0.00

// avg GPA = 2.55