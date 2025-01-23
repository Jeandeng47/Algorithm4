import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P_4_1_6 {
    public static void main(String[] args) {
        int edges[][] = {
            {0, 1},
            {1, 2},
            {2, 3},
            {3, 0}
        };

        // Define the adjacency list to check (valid or invalid)
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(0, Arrays.asList(1, 2)); // Invalid: 2 is not connected to 0
        adjList.put(1, Arrays.asList(0, 3));
        adjList.put(2, Arrays.asList(1));
        adjList.put(3, Arrays.asList(0, 2));

        // Check validity
        if (isValid(edges, adjList)) {
            System.out.println("The adjacency list is valid.");
        } else {
            System.out.println("The adjacency list is invalid.");
        }

    }

    private static boolean isValid(int[][] edges, Map<Integer, List<Integer>> adjList) {
        for (int[] edge : edges) {
            int v = edge[0];
            int w = edge[1];

            List<Integer> vAdjList = adjList.get(v);
            List<Integer> wAdjList = adjList.get(w);

            if (!vAdjList.contains(w) || !wAdjList.contains(v)) {
                return false;
            }
            
        }
        return true;
    }
}
