import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="twoColor.txt"

public class _TestTwoColor {
    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        _TwoColor color = new _TwoColor(G);

        boolean isTwoColorable = color.isBipartite();
        StdOut.println(G);
        StdOut.println("Is bipartite: " + isTwoColorable);
    }
}

// 6 vertices, 5 edges
// 0 : 3 1 
// 1 : 4 0 
// 2 : 5 3 
// 3 : 2 0 
// 4 : 1 
// 5 : 2 

// Is bipartite: true
