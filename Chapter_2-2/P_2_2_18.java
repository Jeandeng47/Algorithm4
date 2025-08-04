import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_2_18 {
    private static final Random rnd = new Random();

    // Define a singly-linked-list node
    public static class Node<T> {
        T val;
        Node<T> next;

        public Node(T v) {
            this.val = v;
        }
    }

    // Randomly shuffle a linked list
    public static <T> Node<T> shuffle(Node<T> head) {
        int n = length(head);
        return shuffleRec(head, n);
    }

    // Shuffle the list recursively
    public static <T> Node<T> shuffleRec(Node<T> head, int n) {
        // base case
        if (n <= 1) return head;

        // split the list into two parts
        int mid = n / 2;
        Node<T> right = split(head, mid);
        Node<T> left = head;

        // shuffle each half
        Node<T> lShuffled = shuffleRec(left, mid);
        Node<T> rShuffled = shuffleRec(right, n - mid);

        // merge at random (shuffle happens here)
        return riffle(lShuffled, rShuffled, mid, n - mid);

    }

    // Randomly interleave 2 lists of length n1 and n2 such that each of the C(n1 + n2, n1)
    // interleavings is equally likely
    public static <T> Node<T> riffle(Node<T> l, Node<T> r, int n1, int n2) {
        Node<T> dummy = new Node<T>(null);
        Node<T> tail = dummy;

        while (l != null && r != null) {
            int p = rnd.nextInt(n1 + n2);
            if (p < n1) {
                // choose left: probability = n1 / (n1 + n2);
                tail.next = l;
                l = l.next;
                n1--;

            } else {
                // choose right: probability = n2 / (n1 + n2);
                tail.next = r;
                r = r.next;
                n2--;
            }
            tail = tail.next;
        }
        // attach the remaning elements if any
        tail.next = (l != null)? l : r;
        return dummy.next;
    }

    // Helper to split off the nodes behind index,
    // return the head of the split list
    private static <T> Node<T> split(Node<T> head, int index) {
        // 1 -> 2 -> 3 -> 4 -> 5 -> 6
        // split(N1, 3)
        // i = 1, c = 2
        // i = 2, c = 3
        // i = 3 == 3, stop
        // Split into 1 -> 2 -> 3, 4 -> 5 -> 6
        Node<T> c = head;
        for (int i = 1; i < index; i++) {
            c = c.next;
        } // now c point to the node at position index
        Node<T> splitList = c.next;
        c.next = null;
        return splitList;    
    }

    // Helper to find the length of linked list
    private static <T> int length(Node<T> head) {
        int len = 0;
        for (Node<T> c = head; c != null; c = c.next) {
            len++;
        }
        return len;
    }

    private static <T> void printList(Node<T> head) {
        for (Node<T> cur = head; cur != null; cur = cur.next) {
            StdOut.print(cur.val + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        // Build list 1→2→…→8
        Node<Integer> head = new Node<>(1);
        Node<Integer> cur  = head;
        for (int i = 2; i <= 8; i++) {
            cur.next = new Node<>(i);
            cur = cur.next;
        }

        // Shuffle and print a few times
        for (int t = 0; t < 5; t++) {
            head = shuffle(head);
            printList(head);
        }
    }
}


// 1. Time requirement: O(NlogN)
// (1) Divide: 
// -- compute the length by iterating throught the list, O(N)
// -- Each recursion split the list by half, walking O(n_i) nodes
// (2) Conquer:
// -- merge two sublist of total length n_i
// -- each recrusion level sum of subproblem size is N
// -- number of recursion level is O(NlogN)

// Level 0: N
// Level 1: N / 2, N / 2
// Level 2: N / 4, N / 4, N / 4, N / 4
// ...

// 2. Sapce requirement: O(logN)
// We only use recursion stack, there is logN calls deep
