import edu.princeton.cs.algs4.StdOut;

public class P_2_2_17 {
    // Define a singly-linked-list node
    public static class Node<T extends Comparable<? super T>> {
        T val;
        Node<T> next;

        public Node(T v) {
            this.val = v;
        }
    }

    // Define a run structure
    public static class Run<T extends Comparable<? super T>> {
        Node<T> head;
        Node<T> tail;

        public Run(Node<T> h, Node<T> t) {
            this.head = h;
            this.tail = t;
        }
    }

    // Natural-mergesort for linked-list
    // return the head of the sorted linked-list
    public static <T extends Comparable<? super T>> Node<T> mergeSortNatural(Node<T> head) {
        if (head == null || head.next == null) return null;

        // create dummy head
        Node<T> dummy = new Node(null);
        dummy.next = head;

        // while the we have more than 1 runs, do:
        //      1. Find 2 runs in the list, record run1, run2, nextRun
        //      2. Merge the runs and put back into the list
        //      3. Find the next runs and continue to merge
        //      4. Until we reach the end of list

        boolean isMerged;
        do {
            isMerged = false;
            Node<T> prev = dummy;
            Node<T> curr = dummy.next;

            while (curr != null) {
                // Find continuous 2 runs
                Node<T> run1Start = curr;
                Node<T> run1End = findRun(run1Start);

                Node<T> run2Start = run1End.next;
                // when all sorted, break the loop, flag is false
                if (run2Start == null) break; 
                Node<T> run2End = findRun(run2Start);

                // detach runs to merge
                Node<T> nextRun = run2End.next;
                run1End.next = null;
                run2End.next = null;
                Run<T> mergedRun = mergeRuns(run1Start, run2Start);

                // put back the merged run into the list
                prev.next = mergedRun.head;
                mergedRun.tail.next = nextRun;

                // update pointers
                prev = mergedRun.tail;
                curr = nextRun;
                
                // set the flag
                isMerged = true;
            }
        } while (isMerged);

        return dummy.next;
    }

    
    private static <T extends Comparable<? super T>> Run<T> mergeRuns(Node<T> l1, Node<T> l2) {
        Node<T> head, tail;
        if (l1.val.compareTo(l2.val) <= 0) {
            head = tail =  l1;
            l1 = l1.next;
        } else {
            head = tail = l2;
            l2 = l2.next;
        }

        while (l1 != null && l2 != null) {
            // move tail pointer to attach next element
            if (l1.val.compareTo(l2.val) <= 0) {
                tail.next = l1;
                tail = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                tail = l2;
                l2 = l2.next;
            }
        }

        // Attach the remaining elements if any
        tail.next = (l1 != null? l1 : l2);
        while (tail.next != null) tail = tail.next;

        return new Run<>(head, tail);
    }

    // Find and return the end node of the non-decreasing run
    private static <T extends Comparable<? super T>> Node<T> findRun(Node<T> start) {
        Node<T> curr = start;
        while (curr.next != null && curr.val.compareTo(curr.next.val) <= 0) {
            curr = curr.next;
        }
        return curr;
    }

    // Helper to print the linked list
    private static <T extends Comparable<? super T>> void printList(Node<T> head) {
        for (Node<T> cur = head; cur != null; cur = cur.next) {
            StdOut.print(cur.val + " ");
        }
        StdOut.println();
    }
    
    public static void main(String[] args) {
        // create list: 5 -> 1 -> 4 -> 2 -> 3
        Node<Integer> h = new Node<Integer>(5);
        h.next = new Node<>(1);
        h.next.next = new Node<>(4);
        h.next.next.next = new Node<>(2);
        h.next.next.next.next = new Node<>(3);

        StdOut.print("Before: ");
        printList(h);

        Node<Integer> sorted = mergeSortNatural(h);
        StdOut.print(" After: ");
        printList(sorted);

    }
}

// Example: merging two runs
    // 1 -> 3 -> 5, 2 -> 4 
    // l1           l2

    // 1 < 2, init head + tail
    // h = t = 1, l1 = l1.next, l2 = l2.next
    // 1 -> 3 -> 5, 2 -> 4
    // ht   l1      l2

    // 2 < 3
    // t.next = l2
    // 1 -> 2 -> 4 , 3 -> 5
    // ht   l2       l1

    // t = l2, l2 = l2.next
    // 1 -> 2 -> 4,  3 -> 5
    // h    t    l2  l1

    // 3 < 4
    // t.next = l1
    // 1 -> 2 -> 3 -> 5,  4
    // h    t    l1       l2
    
    // t = l1, l1 = l1.next
    // 1 -> 2 -> 3 -> 5,  4
    // h         t    l1  l2

    // 4 < 6
    // t.next = l2
    // 1 -> 2 -> 3 -> 4,   5
    // h         t    l2   l1
    
    // t = l2, l2 = l2.next 
    // 1 -> 2 -> 3 -> 4,   5
    // h              t    l1

    // while loop is break (l2 == null)
    // Attach remaining elements from l1

    // t.next = l1
    // 1 -> 2 -> 3 -> 4 -> 5
    // h              t    l1
    // while (t.next != null) t = t.next
    // 1 -> 2 -> 3 -> 4 -> 5
    // h                   t


    

    

