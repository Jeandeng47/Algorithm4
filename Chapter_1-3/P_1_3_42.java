import java.util.Iterator;
import java.util.NoSuchElementException;

public class P_1_3_42 {
    public static class Stack<Item> implements Iterable<Item> {

        private Node<Item> first; // top of stack
        private int size; // number of items

        // Helper linked list node
        private static class Node<Item> {
            Item item;
            Node<Item> next;

            Node(Item item, Node<Item> next) {
                this.item = item;
                this.next = next;
            }
        }

        // Default constructor
        public Stack() {
            this.first = null;
            this.size = 0;
        }

        // Copy constructor
        public Stack(Stack<Item> other) {
            if (other.first == null) {
                this.first = null;
                this.size = 0;
                return;
            }
            this.first = new Node<>(other.first.item, null);
            Node<Item> currNew = this.first;
            Node<Item> currOld = other.first.next;

            while (currOld != null) {
                currNew.next = new Node<>(currOld.item, null);
                currNew = currNew.next;
                currOld = currOld.next;
            }
            this.size = other.size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        // Add item to top of the stack (front of list)
        public void push(Item item) {
            Node<Item> oldFirst = first;
            first = new Node<>(item, oldFirst);
            size++;
        }

        // Remove item from top 
        public Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack undeflow");
            }
            Item item = first.item;
            first = first.next;
            size--;
            return item;
        }

        @Override
        public Iterator<Item> iterator() {
            return new ListIterator();
        }

        private class ListIterator implements Iterator<Item> {
            private Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Stack underflow");
                }
                Item item = current.item;
                current = current.next;
                return item;
            }
            
        }
        
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Stack<Integer> copyStack = new Stack<>(stack);
        System.out.println("Original Stack:");
        for (int item : stack) {
            System.out.print(item + " ");
        }
        System.out.println("\nCopied Stack:");
        for (int item : copyStack) {
            System.out.print(item + " ");  
        }
    }
}
