import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.StdOut;

public class P_2_4_9 {

    public static List<char[]> maxHeap(String s, boolean trace) {
        // 1-based heap array
        int n = s.length();
        char[] heap = new char[n + 1];

        // freq map
        Map<Character, Integer> freqMap = new TreeMap<>();
        for (char c : s.toCharArray()) {
            freqMap.merge(c, 1, Integer::sum); // key, value, function
        }

        // use backtracking to filter validate permutations
        List<char[]> result = new ArrayList<>();

        if (trace) StdOut.println("[TRACE] Start backtracking for s: " + s);

        backtrack(1, n, heap, freqMap, result, trace, ""); // 1-based

        return result;
    }


    // Backtracking: place position pos..n (1-based) recrusively 
    // -- if violates heap property, prune it
    // -- if satisfies heap property, add to result
    public static void backtrack(int pos, int n, char[] heap, 
                                    Map<Character, Integer> freqMap, List<char[]> result,
                                    boolean trace, String indent) {

        // base case: the pointer crosses the end of heap
        if (pos > n) {
            result.add(Arrays.copyOf(heap, heap.length));
            if (trace) StdOut.println(indent + "[TRACE] Completed: " + printHeapArray(heap));
            return;
        }
        
        if (trace) StdOut.println(indent + "pos=" + pos + ", freq=" + mapToStr(freqMap));


        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            char c = entry.getKey();
            int v = entry.getValue();
            if (v == 0) continue; // move to next char

            // place it at pos
            heap[pos] = c;

            // prune: check heap property
            if (pos > 1) {
                int p = pos / 2;
                if (heap[p] < heap[pos]) {
                    if (trace) {
                        StdOut.println(indent + "  try " + c + " @pos " + pos
                                + "  -> PRUNE (parent " + heap[p] + " < " + c + ")");
                    }
                    continue; // invalid choice, move to next char
                }
            }

            if (trace) {
                StdOut.println(indent + "  try " + c + " @pos " + pos + "  -> OK, go deeper");
            }

            // consume the char
            entry.setValue(v - 1);
            backtrack(pos + 1, n, heap, freqMap, result, trace, indent + "    ");

            // if we return from backtrack, we find one permutation
            // prepare another permutation -> restore
            entry.setValue(v);

            if (trace) {
                StdOut.println(indent + "  backtrack from pos " + (pos + 1) + " after placing " + c);
            }
        }
    }

    // Print the heap array
    private static String printHeapArray(char[] heap) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 1; i < heap.length; i++) {
            sb.append(heap[i]);
            if (i < heap.length - 1) sb.append(' ');
        }
        sb.append(" ]");
        return sb.toString();
    }


    // Print heap level by level
    private static String levels(char[] heap) {
        int n = heap.length - 1;
        StringBuilder sb = new StringBuilder();
        int i = 1, level = 1, cnt = 1;
        while (i <= n) {
            int end = Math.min(i + cnt - 1, n);
            sb.append("Level ").append(level).append(": ");
            for (int j = i; j <= end; j++) {
                sb.append(heap[j]);
                if (j < end) sb.append(' ');
            }
            sb.append('\n');
            i = end + 1;
            cnt <<= 1;
            level++;
        }
        return sb.toString();
    }

    // Print frequency map as string
    private static String mapToStr(Map<Character, Integer> freq) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            if (!first) sb.append(", ");
            first = false;
            sb.append(e.getKey()).append(':').append(e.getValue());
        }
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        // case 1: all distinct keys
        String s1 = "ABCDE";
        // case 2: only 2 distinct key
        String s2 = "AAABB";
        
        List<char[]> h1 = maxHeap(s1, true);
        List<char[]> h2 = maxHeap(s2, true);

        StdOut.println("All distinct max-heaps from keys: " + s1);
        StdOut.println("Count = " + h1.size());
        for (int idx = 0; idx < h1.size(); idx++) {
            char[] h = h1.get(idx);
            StdOut.println("#" + (idx + 1) + " " + printHeapArray(h));
            StdOut.print(levels(h));
        }

        StdOut.println("\nAll distinct max-heaps from keys: " + s2);
        StdOut.println("Count = " + h2.size());
        for (int idx = 0; idx < h2.size(); idx++) {
            char[] h = h2.get(idx);
            StdOut.println("#" + (idx + 1) + " " + printHeapArray(h));
            StdOut.print(levels(h));
        }
    }
}


// All distinct max-heaps from keys: ABCDE
// Count = 8
// #1 [ E C D A B ]
// Level 1: E
// Level 2: C D
// Level 3: A B
// #2 [ E C D B A ]
// Level 1: E
// Level 2: C D
// Level 3: B A
// #3 [ E D A B C ]
// Level 1: E
// Level 2: D A
// Level 3: B C
// #4 [ E D A C B ]
// Level 1: E
// Level 2: D A
// Level 3: C B
// #5 [ E D B A C ]
// Level 1: E
// Level 2: D B
// Level 3: A C
// #6 [ E D B C A ]
// Level 1: E
// Level 2: D B
// Level 3: C A
// #7 [ E D C A B ]
// Level 1: E
// Level 2: D C
// Level 3: A B
// #8 [ E D C B A ]
// Level 1: E
// Level 2: D C
// Level 3: B A

// All distinct max-heaps from keys: AAABB
// Count = 2
// #1 [ B A B A A ]
// Level 1: B
// Level 2: A B
// Level 3: A A
// #2 [ B B A A A ]
// Level 1: B
// Level 2: B A
// Level 3: A A


// Backtraking trace example:

// [TRACE] Start backtracking for s: AAABB
// pos=1, freq={A:3, B:2}
//   try A @pos 1  -> OK, go deeper
//     pos=2, freq={A:2, B:2}
//       try A @pos 2  -> OK, go deeper
//         pos=3, freq={A:1, B:2}
//           try A @pos 3  -> OK, go deeper
//             pos=4, freq={A:0, B:2}
//               try B @pos 4  -> PRUNE (parent A < B)
//           backtrack from pos 4 after placing A
//           try B @pos 3  -> PRUNE (parent A < B)
//       backtrack from pos 3 after placing A
//       try B @pos 2  -> PRUNE (parent A < B)
//   backtrack from pos 2 after placing A
//   try B @pos 1  -> OK, go deeper
//     pos=2, freq={A:3, B:1}
//       try A @pos 2  -> OK, go deeper
//         pos=3, freq={A:2, B:1}
//           try A @pos 3  -> OK, go deeper
//             pos=4, freq={A:1, B:1}
//               try A @pos 4  -> OK, go deeper
//                 pos=5, freq={A:0, B:1}
//                   try B @pos 5  -> PRUNE (parent A < B)
//               backtrack from pos 5 after placing A
//               try B @pos 4  -> PRUNE (parent A < B)
//           backtrack from pos 4 after placing A
//           try B @pos 3  -> OK, go deeper
//             pos=4, freq={A:2, B:0}
//               try A @pos 4  -> OK, go deeper
//                 pos=5, freq={A:1, B:0}
//                   try A @pos 5  -> OK, go deeper
//                     [TRACE] Completed: [ B A B A A ]
//                   backtrack from pos 6 after placing A
//               backtrack from pos 5 after placing A
//           backtrack from pos 4 after placing B
//       backtrack from pos 3 after placing A
//       try B @pos 2  -> OK, go deeper
//         pos=3, freq={A:3, B:0}
//           try A @pos 3  -> OK, go deeper
//             pos=4, freq={A:2, B:0}
//               try A @pos 4  -> OK, go deeper
//                 pos=5, freq={A:1, B:0}
//                   try A @pos 5  -> OK, go deeper
//                     [TRACE] Completed: [ B B A A A ]
//                   backtrack from pos 6 after placing A
//               backtrack from pos 5 after placing A
//           backtrack from pos 4 after placing A
//       backtrack from pos 3 after placing B
//   backtrack from pos 2 after placing B