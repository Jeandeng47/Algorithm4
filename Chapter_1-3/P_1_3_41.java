import java.util.Iterator;
import java.util.NoSuchElementException;

public class P_1_3_41 {
    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first; // link to least recently added node
        private Node<Item> last;  // link to most recently added node
        private int n;      // number of items in the queue

        private class Node<Item> {
            Item item;
            Node<Item> next;

            public Node(Item item, Node<Item> next) {
                this.item = item;
                this.next = next;
            }
        }

        // Default constructor
        public Queue() {
            first = null;
            last = null;
            n = 0;
        }

        // Copyable queue (without modifying the original queue)
        public Queue(Queue<Item> q) {
            if (q == null) throw new IllegalArgumentException("Queue cannot be null");
            this.first = null;
            this.last = null;
            this.n = 0;
            for (Item item : q) {   
                this.enqueue(item);
            }
        }

        public boolean isEmpty() {
            return first == null;
        }

        public int size() {
            return n;
        }

        public void enqueue(Item item) {
            Node<Item> newNode = new Node<>(item, null);
            if (isEmpty()) {
                first = newNode;
                last = newNode;
            } else {
                last.next = newNode;
                last = newNode;
            } 
            n++;
        }

        public Item dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            Item item = first.item;
            first = first.next;
            n--;
            if (isEmpty()) {
                last = null;
            }
            return item;
        }

        @Override
        public Iterator<Item> iterator() {
            return new QueueIterator();
        }

        public class QueueIterator implements Iterator<Item> {
            private Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more items in the queue");
                }
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Node<Item> current = first;
            while (current != null) {
                sb.append(current.item);
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        // Create a copy of the queue
        Queue<String> copiedQueue = new Queue<>(queue);
        copiedQueue.enqueue("D");
        copiedQueue.enqueue("E");

        // Print original and copied queues
        System.out.println("Original Queue: " + queue);
        System.out.println("Copied Queue: " + copiedQueue);
      
        System.out.println();
    }
    
}
