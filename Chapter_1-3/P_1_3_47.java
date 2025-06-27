import java.util.Iterator;

public class P_1_3_47 {
    public static class CatenableQueue<Item> implements Iterable<Item> {
        private Node<Item> last; // points to the most recently added node (tail)
        private int n;
        
        private static class Node<Item> {
            Item item;
            Node<Item> next;
            public Node(Item item, Node<Item> next) {
                this.item = item;
                this.next = next;
            }
        }
        public CatenableQueue() {
            this.last = null;
            this.n = 0;
        }

        public boolean isEmpty() {
            return n == 0;
        }
        
        public int size() {
            return n;
        }

        // Add an item to the end of the queue
        public void enqueue(Item item) {
           Node<Item> newNode = new Node<>(item, null);
           if (last == null) { // empty
                last = newNode;
                newNode.next = newNode; // point to self
           } else {
                newNode.next = last.next; // point to front
                last.next = newNode; // link last to new node
                last = newNode; // update last to the new node
           }
           n++;
        }

        // Remove and return the item at the front of the queue
        public Item dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue underflow");
            }
            Node<Item> front = last.next;
            Item item = front.item; 
            if (front == last) { // only one item
                last = null;
            } else {
                last.next = front.next; // link last to the next node
            }
            n--;
            return item;
        }

        // Destructively concatenate this queue with another
        // After concatenation, the other queue will be empty
        public void catenate(CatenableQueue<Item> that) {
            if (that.isEmpty()) return;
            if (this.isEmpty()) {
                this.last = that.last;
                this.n = that.n;
            } else {
                Node<Item> thisFront = this.last.next; 
                Node<Item> thatFront = that.last.next;
                this.last.next = thatFront; // link this last to that front
                that.last.next = thisFront; // link that last to this front

                this.last = that.last;
                this.n += that.n;
            }
            that.last = null; 
            that.n = 0;
        }

        @Override
        public Iterator<Item> iterator() {
            return new ListIterator();
        }

        public class ListIterator implements Iterator<Item> {
            private Node<Item> current = last == null ? null : last.next; // start from front
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < n;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more items in the queue");
                }
                Item item = current.item;
                current = current.next; // move to next node
                count++;
                return item;
            }       
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (isEmpty()) {
                return "[]";
            }
            Node<Item> current = last.next; // start from front
            sb.append("[");
            while (current != last) {
                sb.append(current.item).append(", ");
                current = current.next;
            }
            sb.append(last.item).append("]"); // append last item
            return sb.toString();
        }
    } 

    public static void main(String[] args) {
        CatenableQueue<Integer> q1 = new CatenableQueue<>();
        q1.enqueue(1);
        q1.enqueue(2);
        System.out.println("Queue 1: " + q1);

        CatenableQueue<Integer> q2 = new CatenableQueue<>();
        q2.enqueue(3);
        q2.enqueue(4);
        System.out.println("Queue 1: " + q2);

        q1.catenate(q2);
        System.out.println("After concatenation, Queue 1: " + q1);
        System.out.println("After concatenation, Queue 2: " + q2); // empty
    }
}
