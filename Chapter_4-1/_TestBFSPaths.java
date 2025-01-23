import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="tinyCG.txt 0"

public class _TestBFSPaths {
    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        _BreadthFirstPaths search = new _BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            StdOut.print(s +  " to " + v + ": ");
            if (search.hasPathTo(v)) {
                for (int x : search.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(s);
                    } else {
                        StdOut.print("-" + x);
                    }
                } 
            }
            StdOut.println();
        }
    }   
}

// 0 to 0: 0
// 0 to 1: 0-1
// 0 to 2: 0-2
// 0 to 3: 0-2-3
// 0 to 4: 0-2-4
// 0 to 5: 0-5
