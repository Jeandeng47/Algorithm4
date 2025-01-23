import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="tinyG.txt"

public class _TestCycle {
    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        _Cycle cycle = new _Cycle(G);

        boolean hasCycle = cycle.hasCycle();
        StdOut.println(G);
        StdOut.println("Has cycle: " + hasCycle);
    }
}

// 13 vertices, 13 edges
// 0 : 6 2 1 5 
// 1 : 0 
// 2 : 0 
// 3 : 5 4 
// 4 : 5 6 3 
// 5 : 3 4 0 
// 6 : 0 4 
// 7 : 8 
// 8 : 7 
// 9 : 11 10 12 
// 10 : 9 
// 11 : 9 12 
// 12 : 11 9 

// Has cycle: true
