// To run this code, run the commands:
// cd Chapter_1-3
// javac -cp .:algs4.jar _ArrayStack.java _LinkedListStack.java _TestStack.java
// java _TestStack

public class _TestStack {
    public static void main(String[] args) {
        // Test ArrayStack
        _ArrayStack<Integer> stack = new _ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        System.out.println("ArrayStack size: " + stack.size());
        System.out.println("Popped item: " + stack.pop());
        System.out.println("Is empty: " + stack.isEmpty());
        
        // Test ReverseArrayIterator
        for (Integer item : stack) {
            System.out.println("Item in stack: " + item);
        }

        // Test LinkedListStack
        _LinkedListStack<String> linkedListStack = new _LinkedListStack<>();
        linkedListStack.push("A");
        linkedListStack.push("B");
        linkedListStack.push("C");
        System.out.println("LinkedListStack size: " + linkedListStack.size());
        System.out.println("Popped item: " + linkedListStack.pop());
        System.out.println("Is empty: " + linkedListStack.isEmpty());

        // Test ListIterator
        for (String item : linkedListStack) {
            System.out.println("Item in linked list stack: " + item);
        }
    }
}
