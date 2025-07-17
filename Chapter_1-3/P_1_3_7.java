import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class P_1_3_7 {
    public static class ArrayStack<Item> implements Iterable<Item> {
        private int n;
        private Item[] a;

        @SuppressWarnings("unchecked")
        public ArrayStack(int cap) {
            this.n = 0;
            this.a = (Item[]) new Object[cap];
        }

        public int size() {
            return this.n;
        }

        public boolean isEmpty() {
            return n == 0;
        }

        @SuppressWarnings("unchecked")
        private void resize(int cap) {
            Item[] temp = (Item[]) new Object[cap];
            for (int i = 0; i < n; i++) {
                temp[i] = a[i];
            }
            this.a = temp;
        }

        public void push(Item item) {
            if (n == a.length) {
                resize(2 * a.length);
            }
            a[n++] = item;
        }

        public Item pop() {
            if (isEmpty()) {
                throw new RuntimeException("Stack underflow"); 
            }
            Item item = a[--n];
            a[n] = null;
            if (n > 0 && n == a.length / 4) {
                resize(a.length / 2);
            }
            return item;
        }

        public Item peek() {
            Item item = a[n - 1];
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
                return a[--n];
            }
        }
    }

    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>(4);
        for (int i = 1; i < 5; i++) {
            stack.push(i);
        }
        StdOut.println("Top of stack: " + stack.peek());
        stack.pop();
        StdOut.println("Top of stack after pop: " + stack.peek());
    }
}
