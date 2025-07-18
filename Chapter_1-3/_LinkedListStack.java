import java.util.Iterator;
import java.util.ListIterator;

public class _LinkedListStack<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int n;

    private class Node<Item> {
        Item item;
        Node<Item> next;

        Node(Item item) {
            this.item = item;
        }
    }

    public _LinkedListStack() {
        this.first = null;
        this.n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>(item);
        first.next = oldFirst;
        n++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        Item item = first.item;
        first = first.next;
        n--;
        return item;
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
                throw new RuntimeException("No more items in stack");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    
}
