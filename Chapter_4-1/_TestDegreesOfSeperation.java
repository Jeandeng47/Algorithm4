import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// To run this program:
// make run ARGS="movies.txt \"/\" \"Bassett, William\""
// make run ARGS="movies.txt \"/\" \"Animal House (1978)\""

public class _TestDegreesOfSeperation {
    public static void main(String[] args) {
        _SymbolGraph sg = new _SymbolGraph(args[0], args[1]);
        _Graph G = sg.G();

        String source = args[2];
        if (!sg.contains(source)) {
            StdOut.println(source +  " not in database.");
            return;
        }

        int s = sg.index(source);
        _BreadthFirstPaths bfs = new _BreadthFirstPaths(G, s);

        while (!StdIn.isEmpty()) {
            String sink = StdIn.readLine();
            if (sg.contains(sink)) {
                int t = sg.index(sink);
                if (bfs.hasPathTo(t)) {
                    for (int v : bfs.pathTo(t)) {
                        StdOut.println(" " + sg.name(v));
                    }
                } else {
                    StdOut.println("Not connected.");
                }
            } else {
                StdOut.println("Not in database.");
            }
        }


    }
}

// Mercer, Matthew
//  Bassett, William
//  Akira (1988)
//  Mercer, Matthew
// Fimple, Dennis
//  Bassett, William
//  House of 1000 Corpses (2003)
//  Fimple, Dennis
// Garrison, Rob
//  Bassett, William
//  Karate Kid, The (1984)
//  Garrison, Rob

// Titanic (1997)
//  Animal House (1978)
//  Allen, Karen (I)
//  Raiders of the Lost Ark (1981)
//  Taylor, Rocky (I)
//  Titanic (1997)
// To Catch a Thief (1955)
//  Animal House (1978)
//  Vernon, John (I)
//  Topaz (1969)
//  Hitchcock, Alfred (I)
//  To Catch a Thief (1955)
// To Be or Not to Be (1942)
//  Animal House (1978)
//  Matheson, Tim (I)
//  1941 (1979)
//  Stack, Robert
//  To Be or Not to Be (1942)
