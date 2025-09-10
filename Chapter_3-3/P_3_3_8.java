import java.util.ArrayList;
import java.util.List;

public class P_3_3_8 {
    public static final boolean RED = true, BLACK = false;

    public static final class Node {
        char k;
        Node left, right;
        boolean red; // color of THIS node (link from parent to this node). Root is BLACK.
        Node(char k, boolean red) { this.k = k; this.red = red; }
    }

    // Pretty printer (sideways)
    public static void print(Node x) { print(x, "", false); }
    public static void print(Node x, String prefix, boolean isLeft) {
        if (x == null) return;
        print(x.right, prefix + (isLeft ? "│   " : "    "), false);
        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + (x.red ? "[R:" : "[B:") + x.k + "]");
        print(x.left,  prefix + (isLeft ? "    " : "│   "), true);
    }

    // Check whether the RBT violates the rules
    // Rule 1: no right red link -> allow right-leaning here
    // Rule 2: no consecutive 2 red links -> check
    public static boolean no2Red(Node r) {
        if (r == null) return true;
        if (r.red) {
            if ((r.left != null && r.left.red) || 
            (r.right != null && r.right.red)) return false;
        }
        return no2Red(r.left) && no2Red(r.right);
    }

    // Check whether the RBT is a minimal node 4 [L | M | R]
    public static boolean isNode4(Node r) {
        if (r == null) return false;
        if (r.left == null || r.right == null) return false;
        if (!r.left.red || !r.right.red) return false;
        return (r.left.left == null && r.left.right == null &&
                r.right.left == null && r.right.right == null);

    }

    // Build all the RBT with 3 nodes. Color both edge to children
    // to bind 3 node-2 into 1 node-4
    public static List<Node> rbt3Node2() {
        char A='A', B='B', C='C';

        List<Node> out = new ArrayList<>();
        // (1) Balanced:  B is root; A left; C right.  Both edges red (valid RB 4-node).
        {
            Node b = new Node(B, BLACK);
            b.left  = new Node(A, RED);
            b.right = new Node(C, RED);
            out.add(b);
        }

        // (2) Right-skew chain: A -> B -> C (both edges red)  [invalid: red-red path]
        {
            Node a = new Node(A, BLACK);
            a.right = new Node(B, RED);
            a.right.right = new Node(C, RED);
            out.add(a);
        }

        // (3) Left-skew chain: C <- B <- A (both edges red)   [invalid: red-red path]
        {
            Node c = new Node(C, BLACK);
            c.left = new Node(B, RED);
            c.left.left = new Node(A, RED);
            out.add(c);
        }

        // (4) Zig: A root; right child C; C's left is B. Both edges red. [invalid: C(red) -> B(red)]
        {
            Node a = new Node(A, BLACK);
            a.right = new Node(C, RED);
            a.right.left = new Node(B, RED);
            out.add(a);
        }

        // (5) Mirror zig: C root; left child A; A's right is B. Both edges red. [invalid: A(red) -> B(red)]
        {
            Node c = new Node(C, BLACK);
            c.left = new Node(A, RED);
            c.left.right = new Node(B, RED);
            out.add(c);
        }

        return out;

    }
    // Show all the possible ways that one might represents 4-node as
    // 3 2-nodes bound together with red links
    public static void main(String[] args) {
        List<Node> forms = rbt3Node2();
        int idx = 1;
        for (Node r : forms) {
            System.out.println("\n== Form #" + (idx++) + " ==");
            print(r);
            boolean ok = no2Red(r);
            boolean canon4 = isNode4(r);
            System.out.println("No-consecutive-2-reds: " + ok + (ok ? "" : "  <-- violates RB rule"));
            System.out.println("canonical-4-node: " + canon4 + (canon4 ? "  <-- valid 4-node" : ""));
        }
    }
}


// == Form #1 ==
//     ┌── [R:C]
// ┌── [B:B]
// │   └── [R:A]
// No-consecutive-2-reds: true
// canonical-4-node: true  <-- valid 4-node

// == Form #2 ==
//         ┌── [R:C]
//     ┌── [R:B]
// ┌── [B:A]
// No-consecutive-2-reds: false  <-- violates RB rule
// canonical-4-node: false

// == Form #3 ==
// ┌── [B:C]
// │   └── [R:B]
// │       └── [R:A]
// No-consecutive-2-reds: false  <-- violates RB rule
// canonical-4-node: false

// == Form #4 ==
//     ┌── [R:C]
//     │   └── [R:B]
// ┌── [B:A]
// No-consecutive-2-reds: false  <-- violates RB rule
// canonical-4-node: false

// == Form #5 ==
// ┌── [B:C]
// │   │   ┌── [R:B]
// │   └── [R:A]
// No-consecutive-2-reds: false  <-- violates RB rule
// canonical-4-node: false