import java.util.Iterator;
import java.util.NoSuchElementException;

// Implement a double-eneded queue Deque using doubly linked-list
public class P_1_3_33_1 {
    public static class Deque<Item> implements Iterable<Item> {
        private static class Node<Item> {
            Item item;
            Node<Item> prev;
            Node<Item> next;
            public Node(Item item) {
                this.item = item;
            }
        }

        private int size;
        private Node<Item> left; // first node
        private Node<Item> right; // last node
        
        public Deque() {
            this.size = 0;
            this.left = null;
            this.right = null;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void pushLeft(Item item) {
            Node<Item> newNode = new Node<>(item);
            if (isEmpty()) {
                left = newNode;
                right = newNode;
            } else {
                newNode.next = left;
                left.prev = newNode;
                left = newNode;
            }
            size++;
        }

        public void pushRight(Item item) {
            Node<Item> newNode = new Node<>(item);
            if (isEmpty()) {
                left = newNode;
                right = newNode;
            } else {
                newNode.prev = right;
                right.next = newNode;
                right = newNode;
            }
            size++;
        }

        public Item popLeft() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque underflow");
            }
            Item item = left.item;
            left = left.next;
            if (left != null) {
                left.prev = null;
            } else {
                right = null; // if deque becomes empty
            }
            size--;
            return item;
        }

        public Item popRight() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque underflow");
            }
            Item item = right.item;
            right = right.prev;
            if (right != null) {
                right.next = null;
            } else {
                left = null; // deque becomes empty
            }
            size--;
            return item;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node<Item> current = left;
            while (current != null) {
                sb.append(current.item).append(" ");
                current = current.next;
            }
            return sb.toString().trim();
        }


        @Override
        public Iterator<Item> iterator() {
            return new ListIterator();
        }

        public class ListIterator implements Iterator<Item> {
            private Node<Item> curr = left;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = curr.item;
                curr = curr.next;
                return item;
            }
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.pushLeft(1);
        deque.pushRight(2);
        deque.pushLeft(0);
        System.out.println("Deque: " + deque); // Deque: 0 1 2

        System.out.println("Size: " + deque.size()); // Size: 3

        System.out.println("Pop Left: " + deque.popLeft()); // Pop Left: 0
        System.out.println("Deque: " + deque);

        System.out.println("Pop Right: " + deque.popRight()); // Pop Right: 2
        System.out.println("Deque: " + deque);

        System.out.println("Size after pops: " + deque.size()); // Size after pops: 1
    }
}
