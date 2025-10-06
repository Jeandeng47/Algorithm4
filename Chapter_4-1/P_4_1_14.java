import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="algs4-data/tinyGex2.txt 0"

public class P_4_1_14 {
    // True BFS (FIFO queue) 
    public static int[] bfsQueue(Graph g, int s) {
        int[] dist = new int[g.V()];
        boolean[] marked = new boolean[g.V()];
        for (int i = 0; i < g.V(); i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        

        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        dist[s] = 0;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    dist[w] = dist[v] + 1;
                    q.enqueue(w);
                }
            }
        }
        return dist;
    }

    // Stack-based traversal (LIFO) 
    static int[] bfsStack(Graph g, int s) {
        int[] dist = new int[g.V()];
        boolean[] marked = new boolean[g.V()];
        for (int i = 0; i < g.V(); i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        Stack<Integer> st = new Stack<>();
        marked[s] = true;
        dist[s] = 0;
        st.push(s);

        while (!st.isEmpty()) {
            int v = st.pop();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    dist[w] = dist[v] + 1;  // distance along *this* discovered tree
                    st.push(w);
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);

        int[] dq = bfsQueue(G, s);
        int[] ds = bfsStack(G, s);

        StdOut.println("Vertex  d_queue  d_stack  note");
        for (int v = 0; v < G.V(); v++) {
            String qv = dq[v] == Integer.MAX_VALUE ? "/" : String.valueOf(dq[v]);
            String sv = ds[v] == Integer.MAX_VALUE ? "/" : String.valueOf(ds[v]);
            String note = "";
            if (!qv.equals("/") && !sv.equals("/")) {
                int iq = dq[v], is = ds[v];
                if (is > iq) note = "<-- stack longer";
                else if (is < iq) note = "<-- stack shorted";
            } else if (qv.equals("/") && !sv.equals("/")) {
                note = "<-- stack reached a vertex queue didn't";
            }
            StdOut.printf("%6d  %10s  %10s  %s%n", v, qv, sv, note);
        }
    }
}

// Stack cannot gurantee the shortest path. In BFS, we handle vertices
// in FIFO order (queue) s.t. we always handle nearest vertex first.

// Vertex  d_queue  d_stack  note
//      0           0           0  
//      1           /           /  
//      2           1           1  
//      3           2           2  
//      4           /           /  
//      5           2           4  <-- stack longer
//      6           1           1  
//      7           /           /  
//      8           /           /  
//      9           /           /  
//     10           3           3  
//     11           /           /  