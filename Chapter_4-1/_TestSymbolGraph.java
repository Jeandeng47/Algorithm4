import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// % make run ARGS="movies.txt \"/\""

public class _TestSymbolGraph {
    public static void main(String[] args) {
        String fileName = args[0];
        String delim = args[1];
        _SymbolGraph sg = new _SymbolGraph(fileName, delim);

        _Graph G = sg.G();
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            // check if the source name exits
            if (!sg.contains(source)) {
                StdOut.println(source +  " not in database.");
                return;
            }
            // for all vertices adjacent to source
            for (int w : G.adj(sg.index(source))) {
                StdOut.println(" " + sg.name(w));
            }
        }
    }
}

// Bassett, William
//  Towering Inferno, The (1974)
//  Rupan sansei: Kariosutoro no shiro (1979)
//  Karate Kid, The (1984)
//  House of 1000 Corpses (2003)
//  Appurushîdo (2004)
//  Akira (1988)
//  1776 (1972)
// Frederic, Patrick
//  Big Easy, The (1987)
//  200 Cigarettes (1999)
// Alban, Carlo
//  Center Stage (2000)
//  21 Grams (2003)
// Macaulay
// Macaulay not in database.
// ...
