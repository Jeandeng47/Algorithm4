import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class P_3_3_21 {
    public static void main(String[] args) {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        RedBlackBST<String, Integer> rbt = new RedBlackBST<>();
        for (int i = 0; i < keys.length; i++)
            rbt.put(keys[i], i);

        StdOut.println("size = " + rbt.size());
        StdOut.println("min  = " + rbt.min());
        StdOut.println("max  = " + rbt.max());
        StdOut.println();

        // print keys
        StdOut.println("--------------------------------");
        for (String s : rbt.keys()) {
            StdOut.println(s + " " + rbt.get(s));
        }
        StdOut.println();

        // print keys in order using select
        StdOut.println("Testing select");
        StdOut.println("--------------------------------");
        for (int i = 0; i < rbt.size(); i++)
            StdOut.println(i + " " + rbt.select(i));
        StdOut.println();

        // test rank, floor, ceiling
        StdOut.println("key rank floor ceil");
        StdOut.println("-------------------");
        for (char i = 'A'; i <= 'X'; i++) {
            String s = i + "";
            StdOut.printf("%2s %4d %4s %4s\n", s, rbt.rank(s), rbt.floor(s), rbt.ceiling(s));
        }
        StdOut.println();

        // test range search and range count
        String[] from = { "A", "Z", "X", "0", "B", "C" };
        String[] to   = { "Z", "A", "X", "Z", "G", "L" };
        StdOut.println("range search");
        StdOut.println("-------------------");
        for (int i = 0; i < from.length; i++) {
            StdOut.printf("%s-%s (%2d) : ", from[i], to[i], rbt.size(from[i], to[i]));
            for (String s : rbt.keys(from[i], to[i]))
                StdOut.print(s + " ");
            StdOut.println();
        }
        StdOut.println();


        // delete the smallest keys
        for (int i = 0; i < rbt.size() / 2; i++) {
            rbt.deleteMin();
        }

        StdOut.println("After deleting the smallest " + rbt.size() / 2 + " keys");
        StdOut.println("--------------------------------");
        for (String s : rbt.keys())
            StdOut.println(s + " " + rbt.get(s));
        StdOut.println();

        // delete all the remaining keys
        while (!rbt.isEmpty()) {
            rbt.delete(rbt.select(rbt.size() / 2));
        }

        StdOut.println("After deleting the remaining keys");
        StdOut.println("--------------------------------");
        for (String s : rbt.keys())
            StdOut.println(s + " " + rbt.get(s));
        StdOut.println();

        StdOut.println("After adding back n keys");
        StdOut.println("--------------------------------");
        for (int i = 0; i < keys.length; i++)
            rbt.put(keys[i], i);
        for (String s : rbt.keys())
            StdOut.println(s + " " + rbt.get(s));
        StdOut.println();

        StdOut.println();
        
    }

    
}