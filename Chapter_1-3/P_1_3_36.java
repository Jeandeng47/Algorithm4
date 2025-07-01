import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class P_1_3_36 {

    public static class RandomQueue<Item> implements Iterable<Item> {
        private Item[] a;
        private int size;
        private Random rand;

        @SuppressWarnings("unchecked")
        public RandomQueue() {
            this.a = (Item[]) new Object[2];
            this.size = 0;
            this.rand = new Random();
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void enqueue(Item item) {
            if (size == a.length) {
                resize(2 * a.length);
            }
            a[size] = item;
            size++;
        }

        public Item dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            int r = rand.nextInt(size);
            Item item = a[r];
            a[r] = a[size - 1];
            a[size - 1] = null;
            size--;

            if (size > 0 && size == a.length / 4) {
                resize(a.length / 2);
            }
            return item;  
        }

        private void resize(int capacity) {
            Item[] newArray = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = a[i];
            }
            a = newArray;
        }

        // Return but do not remove a random item from the queue
        public Item sample() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return a[rand.nextInt(size)];
        }
        
        @Override
        public Iterator<Item> iterator() {
            return new RandomQueueIterator();
        }       

        public class RandomQueueIterator implements Iterator<Item> {
            private Item[] shuffled;
            private int i;

            public RandomQueueIterator() {
                shuffled = (Item[]) new Object[size];
                for (int j = 0; j < size; j++) {
                    shuffled[j] = a[j];
                }
                // Shuffle the array
                for (int j = size - 1; j > 0; j--) {
                    int r = rand.nextInt(j + 1);
                    Item temp = shuffled[j];
                    shuffled[j] = shuffled[r];
                    shuffled[r] = temp;
                }
                i = 0;
            }

            @Override
            public boolean hasNext() {
                return i < shuffled.length;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return shuffled[i++];
            }
        }
    }

    public static void main(String[] args) {
        RandomQueue<Integer> queue = new RandomQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println("Random Queue Size: " + queue.size());
        System.out.print("Random Queue Elements: ");
        for (int item : queue) {
            System.out.print(item + " ");
        }
        System.out.println();
        
        // Sample an item without removing it
        System.out.println("Sampled Item: " + queue.sample());
        
        // Dequeue an item
        System.out.println("Dequeued Item: " + queue.dequeue());
    }
    
}
