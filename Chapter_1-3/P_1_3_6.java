import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdArrayIO;
import edu.princeton.cs.algs4.StdOut;

public class P_1_3_6 {
    public static void reverse(Stack<String> stack, Queue<String> queue) {
        while (!queue.isEmpty()) {
            stack.push(queue.dequeue());
        }
        while(!stack.isEmpty()) {
            queue.enqueue(stack.pop());
        }
    }
    
    public static void printStack(Stack<String> stack) {
        StdOut.print("Stack: ");
        for (String s : stack) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }

    public static void printQueue(Queue<String> queue) {
        StdOut.print("Queue: ");
        for (String s : queue) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        Queue<String> queue = new Queue<>();

        String[] words = {"I", "make", "a", "stack"};
        for (String w : words) {
            stack.push(w);
        }

        StdOut.println("After 1st operation: ");
        reverse(stack, queue);
        printStack(stack);
        printQueue(queue);
        StdOut.println();

        StdOut.println("After 2nd operation: ");
        reverse(stack, queue);
        printStack(stack);
        printQueue(queue);
    }
}


// After 1st operation: 
// Stack: 
// Queue: stack a make I 

// After 2nd operation: 
// Stack: 
// Queue: I make a stack 