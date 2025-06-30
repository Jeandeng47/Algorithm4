import java.util.Iterator;
import java.util.NoSuchElementException;

// Implement a double-ended queue Deque using a resizing array
public class P_1_3_33_2 {
    public static class ResizingArrayDeque<Item> implements Iterable<Item> {
        private static class Node<Item> {
            Item item;
            Node<Item> prev;
            Node<Item> next;
            public Node() {
                this.item = item;
                this.prev = prev;
                this.next = next;
            }
        }
        
        private Item[] a;
        private int size;
        private int left; // index of first element
        private int right; // index of last element
        
        @SuppressWarnings("unchecked")
        public ResizingArrayDeque() {
            this.a = (Item[]) new Object[2];
            this.size = 0;
            this.left = 0;
            this.right = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void pushLeft(Item item) {
            if (size == a.length) {
                resize(2 * a.length);
            }
            left = (left - 1 + a.length) % a.length; 
            a[left] = item;
            size++;
        }

        public void pushRight(Item item) {
            if (size == a.length) {
                resize(2 * a.length);
            }
            a[right] = item;
            right = (right + 1) % a.length; 
            size++; 
        }

        public Item popLeft() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque underflow");
            }
            Item item = a[left];
            a[left] = null; // avoid loitering
            left = (left + 1) % a.length; 
            size--;
            if (size > 0 && size == a.length / 4) {
                resize(a.length / 2);
            }
            return item;
        }

        public Item popRight() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque underflow");
            }
            right = (right - 1 + a.length) % a.length;
            Item item = a[right];
            a[right] = null; // avoid loitering
            size--;
            if (size > 0 && size == a.length / 4) {
                resize(a.length / 2);
            }
            return item;
        }

        private void resize(int capacity) {
            Item[] newArr = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                newArr[i] = a[(left + i) % a.length];
            }
            a = newArr;
            left = 0;
            right = size; // right points to the next available index
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            // print raw array
            sb.append("Raw array: [");
            for (int i = 0; i < a.length; i++) {
                sb.append(a[i]);
                if (i < a.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
            sb.append(String.format("Left: %d, Right: %d, Size: %d\n", left, right, size));

            // print elements in deque order
            sb.append("Logical deque: [");
            for (int i = 0; i < size; i++) {
                sb.append(a[(left + i) % a.length]);
                if (i < size - 1) sb.append(", ");
            }
            sb.append("]\n");
            return sb.toString();
        }

        @Override
        public Iterator<Item> iterator() {
            return new ListIterator();
        }
        
        public class ListIterator implements Iterator<Item> {
            private int current = left;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = a[current];
                current = (current + 1) % a.length;
                count++;
                return item;
            }
        }
    }

    public static void main(String[] args) {
        ResizingArrayDeque<Integer> deque = new ResizingArrayDeque<>();
        deque.pushLeft(1);
        System.out.println("After pushLeft(1): \n" + deque);

        deque.pushRight(2);
        System.out.println("After pushRight(2): \n" + deque);

        deque.pushLeft(0);
        System.out.println("After pushLeft(0): \n" + deque);

        deque.pushRight(3);
        System.out.println("After pushRight(3): \n" + deque);

        deque.popLeft();
        System.out.println("After popLeft(): \n" + deque);

        deque.popRight();
        System.out.println("After popRight(): \n" + deque);
        
        System.out.println("Deque size: " + deque.size()); // Output: 4
        System.out.println(deque);
    
    }

}
