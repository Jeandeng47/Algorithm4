import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class _TestSearch {
    public static void main(String[] args) {
        _Graph G = new _Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        _DepthFirstSearch search = new _DepthFirstSearch(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v)) {
                System.out.print(v + " ");
            }
        }
        StdOut.println();

        if (search.count() != G.V()) {
            StdOut.print("NOT ");
        }
        StdOut.println("connected");
    }
}