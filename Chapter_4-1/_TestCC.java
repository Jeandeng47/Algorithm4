import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="tinyG.txt"

public class _TestCC {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        _CC cc = new _CC(G);

        int M = cc.count();
        StdOut.println(M + " components");

        // Create an array of Bags
        Bag<Integer>[] components = (Bag<Integer>[]) new Bag[M];
        for (int i = 0; i < M; i++) {
            components[i] = new Bag<>();
        }

        // Assign vertices to corresponding component
        for (int v = 0; v < G.V(); v++) {
            int id = cc.id(v);
            components[id].add(v);
        }

        // Pring out each component
        for (int i = 0; i < M; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}

// 3 components
// 6 5 4 3 2 1 0 
// 8 7 
// 12 11 10 9 