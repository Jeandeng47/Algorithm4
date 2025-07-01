import java.util.Iterator;

public class _ArrayStack<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;

    @SuppressWarnings("unchecked")
    public _ArrayStack() {
        this.items = (Item[]) new Object[1];
        this.n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    @SuppressWarnings("unchecked")
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public void push(Item item) {
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack underflow"); 
        }
        Item item = items[--n];
        items[n] = null; 
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public class ReverseArrayIterator implements Iterator<Item> {
        private int i = n;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return items[--i];
        }
    }

}
