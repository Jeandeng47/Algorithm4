import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class P_4_1_2 {
    public static void main(String[] args) {
        Graph G = new Graph(new In("algs4-data/tinyGex2.txt"));
        StdOut.println(G);
    }
}

// 12 vertices, 16 edges
// 0 : 2 6 
// 1 : 4 8 11 
// 2 : 5 6 0 3 
// 3 : 10 10 6 2 
// 4 : 1 8 
// 5 : 10 2 
// 6 : 2 3 0 
// 7 : 8 11 
// 8 : 1 11 7 4 
// 9 : 
// 10 : 3 5 3 
// 11 : 8 7 1 