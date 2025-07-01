import java.util.Iterator;
import java.util.NoSuchElementException;

public class _ArrayQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;
    private int first;
    private int last;

    @SuppressWarnings("unchecked")
    public _ArrayQueue() {
        this.items = (Item[]) new Object[1];
        this.n = 0;
        this.first = 0;
        this.last = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int max) {
        @SuppressWarnings("unchecked")
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = items[(first + i) % items.length]; // wrap around
        }
        items = temp;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) {
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[last] = item;
        last = (last + 1) % items.length; // wrap around
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        n--;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<Item> {
        private int current = first;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in queue");
            }
            Item item = items[current];
            current = (current + 1) % items.length; // wrap around
            count++;
            return item;
        }
    }
}
