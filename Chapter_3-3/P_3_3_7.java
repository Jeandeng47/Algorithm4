import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class P_3_3_7 {
    // 2-3 node (structure only)
    public static final class T23 {
        final List<String> keys;   
        final List<T23> kids;      // size = keys.size()+1 
        final String ext;          // external subtree placeholder 

        private T23(List<String> keys, List<T23> kids, String ext) {
            this.keys = keys;
            this.kids = kids;
            this.ext  = ext;
        }

        public static T23 ext(String name) { return new T23(null, List.of(), name); }
        public static T23 node1(String k, T23 L, T23 R) {
            return new T23(List.of(k), List.of(L, R), null);
        }
        public static T23 node2(String k1, String k2, T23 L, T23 M, T23 R) {
            return new T23(List.of(k1, k2), List.of(L, M, R), null);
        }
        // temporary 4-node being split
        public static T23 node3tmp(String a, String b, String c, 
                                    T23 T0, T23 T1, T23 T2, T23 T3) {
            return new T23(List.of(a, b, c), List.of(T0, T1, T2, T3), null);
        }
    }

    public static void print(T23 r) { print(r, "", 0, 1); }
    public static void print(T23 x, String prefix, int idx, int siblings) {
        if (x == null) return;

        String conn;
        if (prefix.isEmpty())            conn = "";
        else if (idx == 0)               conn = "┌── ";
        else if (idx < siblings - 1)     conn = "├── ";
        else                              conn = "└── ";

        String branch = (idx == siblings - 1 ? "    " : "│   ");
        String childPrefix = prefix + branch;

        // label
        String label;
        if (x.ext != null) {
            label = "⟨" + x.ext + "⟩";
        } else if (x.keys.size() == 1) {
            label = "[" + x.keys.get(0) + "]";
        } else if (x.keys.size() == 2) {
            label = "[" + x.keys.get(0) + " | " + x.keys.get(1) + "]";
        } else { // temporary-4: 3 keys
            label = "[" + x.keys.get(0) + " | " + x.keys.get(1) + " | " + x.keys.get(2) + "]";
        }

        // print children top→bottom (reverse order for nice sideways look)
        int m = (x.kids == null) ? 0 : x.kids.size();
        if (m > 0) print(x.kids.get(m - 1), childPrefix, 0, m);
        StdOut.println(prefix + conn + label);
        for (int i = m - 2, pos = 1; i >= 0; i--, pos++) {
            print(x.kids.get(i), childPrefix, pos, m);
        }
    }

    // Case 1: ROOT is the temporary 4-node [a|b|c]
    public static void caseRoot() {
        T23 T0 = T23.ext("T0"), 
        T1 = T23.ext("T1"), 
        T2 = T23.ext("T2"), 
        T3 = T23.ext("T3");

        T23 before = T23.node3tmp("a","b","c", T0,T1,T2,T3);
        T23 after  = T23.node1("b", T23.node1("a", T0, T1), T23.node1("c", T2, T3));
        show("ROOT splits", before, after);
    }

    // Case 2: Parent is a 3-node [d|e], temporary-4 is the LEFT child [a|b|c]
    
    public static void caseP3Left() {
        T23 T0=T23.ext("T0"), T1=T23.ext("T1"), T2p=T23.ext("T2'"), T3p=T23.ext("T3'");
        T23 U2=T23.ext("T2"),  U3=T23.ext("T3");
        T23 before = T23.node2("d","e",
                        T23.node3tmp("a","b","c", T0,T1,T2p,T3p), U2, U3);
        // After: parent becomes temporary-4 [b|d|e]
        T23 after  = T23.node3tmp("b","d","e",
                        T23.node1("a", T0, T1),
                        T23.node1("c", T2p, T3p),
                        U2,
                        U3);
        show("Parent is 3-node, 4-node at LEFT", before, after);
    }

    // Case 3: Parent is a 3-node [a|e], temporary-4 is the MIDDLE child [b|c|d]
    public static void caseP3Middle() {
        T23 V0=T23.ext("T0"), V1=T23.ext("T1"), V2=T23.ext("T2"), V3=T23.ext("T3");
        T23 before = T23.node2("a","e",
                        V0,
                        T23.node3tmp("b","c","d", V1,V2,V3,T23.ext("T4")), // use four placeholders in order
                        T23.ext("T5"));
        // To make names simple and ordered as in the book, relabel children around middle:
        T23 Bleft  = T23.ext("T1"); T23 Bright = T23.ext("T2");
        T23 Dleft  = T23.ext("T3"); T23 Dright = T23.ext("T4");
        T23 L = T23.ext("T0"), R = T23.ext("T5");
        T23 after  = T23.node3tmp("a","c","e",
                        T23.node1("b", Bleft, Bright),
                        T23.node1("d", Dleft, Dright),
                        R,  // right of parent
                        L); // left of parent
        // (Note: placeholders just indicate order; the exact T-naming is illustrative.)
        show("Parent is 3-node, 4-node at MIDDLE", before, after);
    }

    // Case 4: Parent is a 3-node [a|b], temporary-4 is the RIGHT child [c|d|e]
    public static void caseP3Right() {
        T23 T0=T23.ext("T0"), T1=T23.ext("T1"),
            U0=T23.ext("U0"), U1=T23.ext("U1"), U2=T23.ext("U2"), U3=T23.ext("U3");
        T23 before = T23.node2("a","b", T0, T1,
                        T23.node3tmp("c","d","e", U0,U1,U2,U3));
        T23 after  = T23.node3tmp("a","b","d",
                        T0, T1,
                        T23.node1("c", U0, U1),
                        T23.node1("e", U2, U3));
        show("Parent is 3-node, 4-node at RIGHT", before, after);
    }

    // Case 5: Parent is a 2-node [d], temporary-4 is the LEFT child [a|b|c]
    public static void caseP2Left() {
        T23 T0=T23.ext("T0"), T1=T23.ext("T1"), T2=T23.ext("T2"), T3=T23.ext("T3"),
            R = T23.ext("TR"); // right sibling subtree of parent
        T23 before = T23.node1("d",
                        T23.node3tmp("a","b","c", T0,T1,T2,T3),
                        R);
        T23 after  = T23.node2("b","d",
                        T23.node1("a", T0, T1),
                        T23.node1("c", T2, T3),
                        R);
        show("Parent is 2-node, 4-node at LEFT", before, after);
    }

    // Case 6: Parent is a 2-node [a], temporary-4 is the RIGHT child [b|c|d]
    public static void caseP2Right() {
        T23 L=T23.ext("TL"),
            U0=T23.ext("U0"), U1=T23.ext("U1"), U2=T23.ext("U2"), U3=T23.ext("U3");
        T23 before = T23.node1("a",
                        L,
                        T23.node3tmp("b","c","d", U0,U1,U2,U3));
        T23 after  = T23.node2("a","c",
                        L,
                        T23.node1("b", U0, U1),
                        T23.node1("d", U2, U3));
        show("Parent is 2-node, 4-node at RIGHT", before, after);
    }

    // ----- Utility to print a labeled case -----
    public static void show(String title, T23 before, T23 after) {
        StdOut.println("\n============================================");
        StdOut.println(title + "  (split a temporary [a|b|c])");
        StdOut.println("\nBefore:");
        print(before);
        StdOut.println("\nAfter:");
        print(after);
    }

    public static void main(String[] args) {
        caseRoot();
        caseP3Left();
        caseP3Middle();
        caseP3Right();
        caseP2Left();
        caseP2Right();
    }
} 


// ============================================
// ROOT splits  (split a temporary [a|b|c])

// Before:
//     ┌── ⟨T3⟩
// [a | b | c]
//     ├── ⟨T2⟩
//     ├── ⟨T1⟩
//     └── ⟨T0⟩

// After:
//     │   ┌── ⟨T3⟩
//     ┌── [c]
//     │   └── ⟨T2⟩
// [b]
//         ┌── ⟨T1⟩
//     └── [a]
//         └── ⟨T0⟩

// ============================================
// Parent is 3-node, 4-node at LEFT  (split a temporary [a|b|c])

// Before:
//     ┌── ⟨T3⟩
// [d | e]
//     ├── ⟨T2⟩
//         ┌── ⟨T3'⟩
//     └── [a | b | c]
//         ├── ⟨T2'⟩
//         ├── ⟨T1⟩
//         └── ⟨T0⟩

// After:
//     ┌── ⟨T3⟩
// [b | d | e]
//     ├── ⟨T2⟩
//     │   ┌── ⟨T3'⟩
//     ├── [c]
//     │   └── ⟨T2'⟩
//         ┌── ⟨T1⟩
//     └── [a]
//         └── ⟨T0⟩

// ============================================
// Parent is 3-node, 4-node at MIDDLE  (split a temporary [a|b|c])

// Before:
//     ┌── ⟨T5⟩
// [a | e]
//     │   ┌── ⟨T4⟩
//     ├── [b | c | d]
//     │   ├── ⟨T3⟩
//     │   ├── ⟨T2⟩
//     │   └── ⟨T1⟩
//     └── ⟨T0⟩

// After:
//     ┌── ⟨T0⟩
// [a | c | e]
//     ├── ⟨T5⟩
//     │   ┌── ⟨T4⟩
//     ├── [d]
//     │   └── ⟨T3⟩
//         ┌── ⟨T2⟩
//     └── [b]
//         └── ⟨T1⟩

// ============================================
// Parent is 3-node, 4-node at RIGHT  (split a temporary [a|b|c])

// Before:
//     │   ┌── ⟨U3⟩
//     ┌── [c | d | e]
//     │   ├── ⟨U2⟩
//     │   ├── ⟨U1⟩
//     │   └── ⟨U0⟩
// [a | b]
//     ├── ⟨T1⟩
//     └── ⟨T0⟩

// After:
//     │   ┌── ⟨U3⟩
//     ┌── [e]
//     │   └── ⟨U2⟩
// [a | b | d]
//     │   ┌── ⟨U1⟩
//     ├── [c]
//     │   └── ⟨U0⟩
//     ├── ⟨T1⟩
//     └── ⟨T0⟩

// ============================================
// Parent is 2-node, 4-node at LEFT  (split a temporary [a|b|c])

// Before:
//     ┌── ⟨TR⟩
// [d]
//         ┌── ⟨T3⟩
//     └── [a | b | c]
//         ├── ⟨T2⟩
//         ├── ⟨T1⟩
//         └── ⟨T0⟩

// After:
//     ┌── ⟨TR⟩
// [b | d]
//     │   ┌── ⟨T3⟩
//     ├── [c]
//     │   └── ⟨T2⟩
//         ┌── ⟨T1⟩
//     └── [a]
//         └── ⟨T0⟩

// ============================================
// Parent is 2-node, 4-node at RIGHT  (split a temporary [a|b|c])

// Before:
//     │   ┌── ⟨U3⟩
//     ┌── [b | c | d]
//     │   ├── ⟨U2⟩
//     │   ├── ⟨U1⟩
//     │   └── ⟨U0⟩
// [a]
//     └── ⟨TL⟩

// After:
//     │   ┌── ⟨U3⟩
//     ┌── [d]
//     │   └── ⟨U2⟩
// [a | c]
//     │   ┌── ⟨U1⟩
//     ├── [b]
//     │   └── ⟨U0⟩
//     └── ⟨TL⟩