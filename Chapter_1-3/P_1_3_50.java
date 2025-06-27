import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// Fail-fast iterator for a stack
// If the client try to modify the stack while iterating,
// it will throw a ConcurrentModificationException.

public class P_1_3_50 {

    public static class Stack<Item> implements Iterable<Item> {

        private Node<Item> first;
        private int size;
        private int modCount = 0; // for fail-fast iterator

        private static class Node<Item> {
            Item item;
            Node<Item> next;
            Node(Item item, Node<Item> next) {
                this.item = item;
                this.next = next;
            }
        }

        public Stack() {
            this.first = null;
            this.size = 0;
            this.modCount = 0;
        }

        public boolean isEmpty() {
            return first == null;
        }

        public int size() {
            return size;
        }

        public void push(Item item) {
            Node<Item> oldFirst = first;
            first = new Node<>(item, oldFirst);
            size++;
            modCount++;
        }

        public Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack underflow");
            }
            Item item = first.item;
            first = first.next;
            size--;
            modCount++;
            return item;
        }

        @Override
        public Iterator<Item> iterator() {
            return new ListIterator();
        }

        public class ListIterator implements Iterator<Item> {
            private Node<Item> curr = first;
            private final int expectedCount = modCount;
            
            @Override
            public boolean hasNext() {
                if (modCount != expectedCount) {
                    throw new ConcurrentModificationException("Stack modified during iteration");
                }
                return curr != null;
            }

            @Override
            public Item next() {
                if (modCount != expectedCount) {
                    throw new ConcurrentModificationException("Stack modified during iteration");
                }
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in stack");
                }
                Item item = curr.item;
                curr = curr.next;
                return item;
            }

        }
    }

    public static void main(String[] args) {
        try {
            Stack<Integer> stack = new Stack<>();
            stack.push(1);
            stack.push(2);
            stack.push(3);
            System.out.println("Stack size: " + stack.size()); // 3

            // Normal iteration
            for (Integer item : stack) {
                System.out.println("Item: " + item);
            }

            // Modify the stack while iterating
            Iterator<Integer> iterator = stack.iterator();
            while (iterator.hasNext()) {
                Integer item = iterator.next();
                System.out.println("Iterated item: " + item);
                if (item == 2) {
                    stack.push(4); // This will cause ConcurrentModificationException
                }
            }

        } catch (ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Caught NoSuchElementException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }
}
