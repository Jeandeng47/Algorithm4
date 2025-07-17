import edu.princeton.cs.algs4.StdOut;

public class P_1_3_1 {

    public static class FixedCapacityStack<Item> {
        private Item[] a;
        private int size;

        @SuppressWarnings("unchecked")
        public FixedCapacityStack(int cap) {
            this.a = (Item[]) new Object[cap];
            this.size = 0;
        }

        public void push(Item item) {
            a[size++] = item;
        }

        public Item pop() {
            Item item = a[--size];
            return item;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return this.size;
        }

        public boolean isFull() {
            return a.length == size;
        }

    }

    public static void main(String[] args) {
        FixedCapacityStack<String> s = new FixedCapacityStack<>(2);
        s.push("Hello ");
        s.push("world!");
        StdOut.println("Stack is full: " + s.isFull());
    }
}
