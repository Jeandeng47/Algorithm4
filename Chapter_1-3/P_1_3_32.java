import java.util.Iterator;
import java.util.NoSuchElementException;

// Implement a Steque (stack-queue hybrid) that supports push, pop, 
// and enqueue operations.
public class P_1_3_32 {
    public static class Steque<Item> implements Iterable<Item> {

        private static class Node<Item> {
            Item item;
            Node<Item> next;
            Node(Item item, Node<Item> next) {
                this.item = item;
                this.next = next;
            }
        }

        private Node<Item> first; // top of the stack
        private Node<Item> last; // end of the queue
        private int size;

        public Steque() {
            this.first = null;
            this.last = null;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(Item item) {
            Node<Item> newNode = new Node<>(item, first);
            first = newNode;
            if (last == null) { // if steque empty
                last = newNode;
            }
            size++;
        }

        public Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Steque underflow");
            }
            Item item = first.item;
            first = first.next;
            if (first == null) { // if steque becomes empty
                last = null;
            }
            size--;
            return item;
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
            size++;
        }

        @Override
        public Iterator<Item> iterator() {
            return new ListIterator();
        }

        public class ListIterator implements Iterator<Item> {
            private Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in Steque");
                }
                Item item = current.item;
                current = current.next;
                return item;
            }
        } 
    }

    public static void main(String[] args) {
        Steque<Integer> steque = new Steque<>();
        steque.push(1);
        steque.push(2);
        steque.enqueue(3);
        steque.enqueue(4);

        System.out.println("Steque size: " + steque.size()); // Output: 4

        for (int item : steque) {
            System.out.print(item + " "); // Output: 2 1 3 4
        }
        System.out.println();

        System.out.println("Popped item: " + steque.pop()); // Output: 2
        System.out.println("Steque size after pop: " + steque.size()); // Output: 3

    }
}
