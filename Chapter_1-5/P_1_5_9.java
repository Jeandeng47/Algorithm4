import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class P_1_5_9 {
    private static void drawTree(int[] id) {
        Map<Integer, List<Integer>> trees = new HashMap<>();
        for (int i = 0; i < id.length; i++) {
            if (id[i] == i) {
                // parent
                trees.computeIfAbsent(i, k -> new ArrayList<>());
            } else {
                // child
                trees.computeIfAbsent(id[i], k -> new ArrayList<>()).add(i);
            }
        }
        
        for (int r = 0; r < id.length; r++) {
            if (id[r] == r) {
                printTree(r, trees, "", true);
            }
        }
    }

    private static void printTree(int node, Map<Integer, List<Integer>> trees, 
        String prefix, boolean isTail) {
            // print the current node
            if (prefix.isEmpty()) {
                StdOut.println(node);
            } else {
                StdOut.println(prefix + (isTail? "└── " : "├── ") + node);
            }

            // print recursively into the leaves
            List<Integer> children = trees.getOrDefault(node, List.of());
            for (int i = 0; i < children.size(); i++) {
                boolean last = (i == children.size() - 1);
                String childPrefix = prefix + (isTail?  "    " : "│   ");
                printTree(children.get(i), trees, childPrefix, last);
            }

    }
    
    public static void main(String[] args) {
        int[] id = {1, 1, 3, 1, 5, 6, 1, 3, 4, 5};

        StdOut.println("Tree: ");
        drawTree(id);
    }
}

// This tree could not be produce by weighted quick union,
// since the height of the tree must not exceed log2(N) = log2(10)
// But the tree in result has height 4 = log2(16) > log2(10)

// Tree: 
// 1
//     ├── 0
//     ├── 3
//     │   ├── 2
//     │   └── 7
//     └── 6
//         └── 5
//             ├── 4
//             │   └── 8
//             └── 9