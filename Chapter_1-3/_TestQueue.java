// To run this code, use the command:
// cd Chapter_1-3
// javac -cp ../algs4.jar _TestQueue.java _LinkedListQueue.java _ArrayQueue.java
// java _TestQueue

public class _TestQueue {
    public static void main(String[] args) {
        _LinkedListQueue<String> linkedListQueue = new _LinkedListQueue<>();
        linkedListQueue.enqueue("A");
        linkedListQueue.enqueue("B");
        linkedListQueue.enqueue("C");

        System.out.println("Linked List Queue:");
        for (String item : linkedListQueue) {
            System.out.print(item + " ");
        }
        System.out.println();

        _ArrayQueue<String> arrayQueue = new _ArrayQueue<>();
        arrayQueue.enqueue("X");
        arrayQueue.enqueue("Y");
        arrayQueue.enqueue("Z");

        System.out.println("Array Queue:");
        for (String item : arrayQueue) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
