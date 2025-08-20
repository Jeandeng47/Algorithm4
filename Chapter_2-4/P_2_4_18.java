import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class P_2_4_18 {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.addAll(Arrays.asList(5, 2, 7, 6, 3, 4, 1));

        // Original heap
        List<Integer> origin = new ArrayList<>(pq);

        // insert 1 largest, then delMax
        pq.add(100);
        pq.poll();
        System.out.printf("Case 1 same? %s%n", 
                            pq.containsAll(origin) && origin.containsAll(pq)); // true

        // insert 2 largest, then delMax twice
        pq.add(1000);
        pq.add(1001);
        pq.poll();
        pq.poll();
        System.out.printf("Case 2 same? %s%n", 
                            pq.containsAll(origin) && origin.containsAll(pq)); // true
    }
}
