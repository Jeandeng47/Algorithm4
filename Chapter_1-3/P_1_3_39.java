import java.util.Iterator;

public class P_1_3_39 {
    private static class RingBuffer<T>{
        private T[] buffer;
        private int head;
        private int tail;
        private int count;
        private final int capacity;
        
        @SuppressWarnings("unchecked")
        public RingBuffer(int capacity) {
            this.buffer = (T[]) new Object[capacity];
            this.head = 0;
            this.tail = 0;
            this.count = 0;
            this.capacity = capacity;
        }

        public synchronized void enqueue(T item) throws InterruptedException {
            while (count == capacity) {
                wait();
            }
            buffer[tail] = item;
            tail = (tail + 1) % capacity;
            count++;
            notifyAll(); // signal waiting threads
        }

        public synchronized T dequeue() throws InterruptedException {
            while (count == 0) {
                wait();
            }
            T item = buffer[head];
            buffer[head] = null;
            head = (head + 1) % capacity;
            count--;
            notifyAll(); // signal waiting threads
            return item;
        }

        public synchronized boolean isEmpty() {
            return count == 0;
        }

        public synchronized boolean isFull() {
            return count == capacity;
        }

        public synchronized int size() {
            return count;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("RingBuffer: [");
            for (int i = 0; i < count; i++) {
                sb.append(buffer[(head + i) % capacity]);
                if (i < count - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

    }

    public static void main(String[] args) {
        RingBuffer<Integer> ringBuffer = new RingBuffer<>(3);
        
        // Example usage
        try {
            ringBuffer.enqueue(1);
            ringBuffer.enqueue(2);
            ringBuffer.enqueue(3);
            

            System.out.println("Buffer full: " + ringBuffer.isFull());
            System.out.println("Buffer size: " + ringBuffer.size());
            System.out.println(ringBuffer);
            
            System.out.println("Dequeue: " + ringBuffer.dequeue());
            ringBuffer.enqueue(4); 
            System.out.println(ringBuffer);
            

            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
