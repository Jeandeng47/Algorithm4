import java.util.ArrayDeque;
import java.util.Deque;

public class P_1_3_45 {

    // Check if intermixed sequences of push and pop operations on a stack
    // can lead to underflow.
    // Solution: There will be underflow if there k pop operations occur
    // before k push operations.
    public static boolean checkUnderflow(String[] sequence) {
        int count = 0;

        for (String op : sequence) {
            if (op.equals("-")) {
                if (count == 0) {
                    return true;
                }
                count--; // pop
            } else {
                count++; // push
            }
        }
        
        return false;
    }

    // Chek if a sequence of push and pop operations on a stack
    // is a valid permutation of the original sequence.

    // Solution: A sequence is a valid permutation if the next integer
    // in the permutation is in the top of the stack, pop it; otherwise,
    // push it onto the stack.

    // nextPush: goes from 0 to n - 1 (given 0, 1, 2, ..., n - 1 to push)
    // stack: store pushed but not yet popped integers
    // For each pop we want:
    // 1. Push everything up to it if needed
    // 2. If after pushing the top of stack is the integer we want to pop,
    //    pop it; otherwise, return false.
    // 3. If we can't get the integer we want to pop, return false.
    
    public static boolean checkPermutation(int[] permutation) {
        int n = permutation.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int nextPush = 0;

        for (int want : permutation) {
            // Push ints until we reach the one we want
            System.out.println("Want: " + want);
            while (nextPush < n && (stack.isEmpty() || stack.peek() != want)) {
                stack.push(nextPush);
                nextPush++;
            }
            printStack(stack);

            if (stack.isEmpty() || stack.peek() != want) {
                System.out.println("Can't pop " + want + " from stack.");
                return false; // Can't pop the wanted integer
            } else {
                System.out.println("Popping " + want + " from stack.");
                stack.pop(); 
            }

        }
        
        return true;
    } 

    private static void printStack(Deque<Integer> stack) {
        System.out.print("Stack: [");
        for (Integer i : stack) {
            System.out.print(i + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void main(String[] args) {
        // Test cases for underflow
        String[] s1 = {"0", "1", "-", "-", "-"};
        System.out.println("Underflow ?" + checkUnderflow(s1)); // true, underflow

        String[] s2 = {"0", "1", "-", "2", "-", "3", "-"};
        System.out.println("Underflow ?" + checkUnderflow(s2)); // false, no underflow

        // Test cases for permutations
        int[] p1 = {2, 1, 0, 3};
        System.out.println("Permutation valid ? " + checkPermutation(p1)); // true, valid permutation

        int[] p2 = {2, 0, 1, 3};
        System.out.println("Permutation valid ? " + checkPermutation(p2)); // false, invalid permutation

    }
}
