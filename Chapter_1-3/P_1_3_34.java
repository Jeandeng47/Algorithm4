import java.util.Iterator;
import java.util.Random;

public class P_1_3_34 {
    private static class RandomBag<Item> implements Iterable<Item> {
        private Item[] a;
        private int size;
        private Random rand;

        @SuppressWarnings("unchecked")
        public RandomBag() {
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
        
        public void add(Item item) {
            if (size == a.length) {
                resize(2 * a.length);
            }
            a[size] = item;
            size++;
        }

        private void resize(int capacity) {
            Item[] newArray = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = a[i];
            }
            a = newArray;
        }

        @Override
        public Iterator<Item> iterator() {
            return new BagIterator();
        }

        public class BagIterator implements Iterator<Item> {
            private Item[] shuffled;
            private int i;

            @SuppressWarnings("unchecked")
            public BagIterator() {
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
                    throw new java.util.NoSuchElementException();
                }
                Item item = shuffled[i];
                i++;
                return item;
            } 
        }

    }

    public static void main(String[] args) {
        RandomBag<Integer> bag = new RandomBag<>();
        for (int i = 0; i < 10; i++) {
            bag.add(i);
        }
        System.out.println("Random Bag Size: " + bag.size());
        System.out.print("Random Bag Elements: ");
        for (int item : bag) {
            System.out.print(item + " ");
        }
    }
}
