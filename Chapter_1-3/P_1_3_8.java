import edu.princeton.cs.algs4.StdOut;

// To run this program:
// javac -cp .:algs4.jar Chapter_1-3/_ArrayStack.java Chapter_1-3/P_1_3_8.java
// java -cp .:algs4.jar:Chapter_1-3 P_1_3_8

public class P_1_3_8 {
    public static void main(String[] args) {
        String str = "it was - the best - of times - - - it was - the - -";
        String[] words = str.split("\\s+");

        _ArrayStack<String> doubleStack = new _ArrayStack<>();
        for (String w : words) {
            if (w.equals("-")) {
                StdOut.print(doubleStack.pop() + " ");
            } else {
                doubleStack.push(w);
            }
        }
        
    }
}
