// Generalized Queue using array implementation

public class P_1_3_38 {
    public static class ArrayGQ<Item> {
        private Item[] items;
        private int size;

        @SuppressWarnings("unchecked")
        public ArrayGQ() {
            this.items = (Item[]) new Object[10];
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // Add an item to the end of the queue
        public void insert(Item item) {
            if (size == items.length) {
                resize(2 * items.length);
            }
            items[size++] = item;
        }

        // Delete and return the kth item least recently added
        public Item delete(int k) {
            if (k < 0 || k >= size) {
                throw new IndexOutOfBoundsException("Index: " + k + ", Size: " + size);
            }
            Item item = items[k - 1];
            for (int i = k - 1; i < size - 1; i++) {
                items[i] = items[i + 1];
            }
            items[size - 1] = null; 
            size--;
            if (size > 0 && size == items.length / 4) {
                resize(items.length / 2);
            }
            return item;
        }

        private void resize(int capacity) {
            Item[] newItems = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                newItems[i] = items[i];
            }
            items = newItems;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < size; i++) {
                sb.append(items[i]);
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
            return sb.toString();
        }
        
    }

    public static class LinkedListGQ<Item> {
        private class Node {
            Item item;
            Node next;

            Node(Item item) {
                this.item = item;
            }
        }

        private Node last;
        private Node first;
        private int size;

        public LinkedListGQ() {
            this.last = null;
            this.first = null;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void insert(Item item) {
            Node newNode = new Node(item);
            if (isEmpty()) {
                first = newNode;
                last = newNode;
            } else {
                last.next = newNode;
                last = newNode;
            }
            size++;
        }

        public Item delete(int k) {
            if (k < 1 || k > size) {
                throw new IndexOutOfBoundsException("Index: " + k + ", Size: " + size);
            }
            
            // if k is 1, we remove the first item
            if (k == 1) {
                Item item = first.item;
                first = first.next;
                size--;
                if (first == null) {
                    last = null;
                }
                return item;
            }

            // find the (k-1)th node
            Node prev = first;
            for (int i = 1; i < k - 1; i++) {
                prev = prev.next;
            }
            Node toDelete = prev.next;
            Item item = toDelete.item;
            prev.next = toDelete.next;

            if (toDelete == last) {
                last = prev;
            }
            size--;
            return item;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node current = first;
            sb.append("[");
            while (current != null) {
                sb.append(current.item);
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
            sb.append("]\n");
            return sb.toString();
        }

    }

    public static void main(String[] args) {
        ArrayGQ<String> arrayGQ = new ArrayGQ<>();
        arrayGQ.insert("A");
        arrayGQ.insert("B");
        arrayGQ.insert("C");
        System.out.print("ArrayGQ: " + arrayGQ);

        System.out.println("Deleted item: " + arrayGQ.delete(2)); // Deletes "B"
        System.out.println("Is empty: " + arrayGQ.isEmpty());
        System.out.print("ArrayGQ: " + arrayGQ);

        LinkedListGQ<String> linkedListGQ = new LinkedListGQ<>();
        linkedListGQ.insert("X");
        linkedListGQ.insert("Y");
        linkedListGQ.insert("Z");
        System.out.print("LinkedListGQ: " + linkedListGQ);

        System.out.println("Deleted item: " + linkedListGQ.delete(1)); // Deletes "X"
        System.out.println("Deleted item: " + linkedListGQ.delete(2)); // Deletes "Y"
        System.out.print("LinkedListGQ: " + linkedListGQ);

        System.out.println("Deleted item: " + linkedListGQ.delete(1)); 
        System.out.println("Is empty: " + linkedListGQ.isEmpty());
        
    }
}
