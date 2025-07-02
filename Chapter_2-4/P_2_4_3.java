// To run this class, use the commands:
// cd Chapter_2-4
// javac -cp ../algs4.jar _OrderedLListMaxPQ.java _UnorderedLListMaxPQ.java _OrderedArrayMaxPQ.java _UnorderedArrayMaxPQ.java P_2_4_3.java
// java P_2_4_3

// | Implementation                        | Insert    | Max      | delMax   |
// |---------------------------------------|-----------|----------|----------|
// | Unordered array                       | Θ(1)*     | Θ(n)     | Θ(n)     |
// | Ordered array (max at end)            | Θ(n)      | Θ(1)     | Θ(1)     |
// | Unordered linked list                 | Θ(1)      | Θ(n)     | Θ(n)     |
// | Ordered linked list (max at head)     | Θ(n)      | Θ(1)     | Θ(1)     |

// _* amortized Θ(1) when resizing._

public class P_2_4_3 {
    public static void main(String[] args) {
        _OrderedArrayMaxPQ<Integer> orderedArrayPQ = new _OrderedArrayMaxPQ<>();
        _UnorderedArrayMaxPQ<Integer> unorderedArrayPQ = new _UnorderedArrayMaxPQ<>();
        _OrderedLListMaxPQ<Integer> orderedListPQ = new _OrderedLListMaxPQ<>();
        _UnorderedLListMaxPQ<Integer> unorderedListPQ = new _UnorderedLListMaxPQ<>();

        int[] testData = {5, 3, 8, 1, 4, 7, 6, 2};
        for (int num : testData) {
            orderedArrayPQ.insert(num);
            unorderedArrayPQ.insert(num);
            orderedListPQ.insert(num);
            unorderedListPQ.insert(num);
        }
        System.out.println("Ordered Array MaxPQ: " + orderedArrayPQ); // [1, 2, 3, 4, 5, 6, 7, 8]
        System.out.println("Unordered Array MaxPQ: " + unorderedArrayPQ); // [5, 3, 8, 1, 4, 7, 6, 2]
        System.out.println("Ordered Linked List MaxPQ: " + orderedListPQ); // [8, 7, 6, 5, 4, 3, 2, 1]
        System.out.println("Unordered Linked List MaxPQ: " + unorderedListPQ); // [2, 6, 7, 4, 1, 8, 3, 5]
    }
    

}
