import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class _SymbolGraph {

    private ST<String, Integer> st; // string -> index
    private String[] keys; // index -> string
    private _Graph G;

    public _SymbolGraph(String stream, String sp) {
        // First pass: build the index by reading strings
        // to associate each distrinct string with an index
        this.st = new ST<>();
        
        In in = new In(stream);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp);
            for (int i = 0; i < a.length; i++) {
                // No duplicate String keys allowed
                if (!st.contains(a[i])) {
                    st.put(a[i], st.size());
                    // StdOut.println("Adding key: " + a[i]);
                }
                
            }
        }
        // Invert index to get string keys
        this.keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // Second pass: build the graph by connecting
        // the first vertex on each line to all the others
        this.G = new _Graph(st.size());
        in = new In(stream);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp);
            // vertex: index corresponding to the key
            int v = st.get(a[0]); 
            for (int i = 1; i < a.length; i++) {
                G.addEdge(v, st.get(a[i]));
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int index(String s) {
        return st.get(s);
    }

    public String name(int v) {
        return keys[v];
    }

    public _Graph G() {
        return G;
    }
}
