import edu.princeton.cs.algs4.StdOut;

public class P_1_3_14 {
    public static class ResizingArrayQueueOfString {
        private int first;
        private int last;
        private int n;
        private String[] a;


		public ResizingArrayQueueOfString(int cap) {
            this.n = 0;
            this.first = 0;
            this.last = 0;
            this.a = new String[cap];
        }

	
        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public boolean isFull()  { 
            return n == a.length; 
        }

        private void resize(int cap) {
            String[] copy = new String[cap];
            for (int i = 0; i < n; i++) {
                copy[i] = a[(first + i) % a.length];
            }
            this.a = copy;
            this.first = 0;
            this.last = n;

        }

        public void enqueue(String s) {
            if (isFull()) {
                resize(a.length * 2);
            }
            a[last] = s;
            last = (last + 1) % a.length;
            n++;
        }

        public String dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue underflow");
            }
            String s = a[first];
            a[first] = null;
            first  = (first + 1) % a.length;
            n--;
            if (n > 0 && n == a.length * 1/4) {
                resize(a.length / 2);
            }
            return s;
        }
        
    }

    public static void main(String[] args) {
        ResizingArrayQueueOfString arrQueue = new ResizingArrayQueueOfString(5);
        String[] strs = {"I", "am", "happy", "today"};

        for (String s: strs) {
            arrQueue.enqueue(s);
        }

        while (!arrQueue.isEmpty()) {
            StdOut.print(arrQueue.dequeue() + " ");
        }
        StdOut.println();
    }
}
