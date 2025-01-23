
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

// BFS:  
// Put the source vertex on the queue, then perform the steps until the queue is empty:
// 1. Take the next vertex v from the queue and mark it.
// 2. Put onto the queue all unmarked vertices adjacent to v.

public class _BreadthFirstPaths {
    // Is a shortest path to this vertex known?
    private boolean[] marked; 
    // last vertex on known path to this vertex
    // edgeTo[w] = v: v->w is the edge we cross to visit v
    private int[] edgeTo;
    // source vertex
    private final int s;    

    
    public _BreadthFirstPaths(_Graph G, int s) {
        this.s = s;
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        bfs(G, s);
    }

    public void bfs(_Graph G, int s) {
        // use a (FIFO) queue to help us explore 
        // vertices in order of their distance from the source.
        Queue<Integer> queue = new Queue<>();

        // Handle source
        marked[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            // Dequeue order: increasing depth
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                // Put unmarked neighbours onto queue
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        } 
        path.push(s);
        return path;
    }
}
