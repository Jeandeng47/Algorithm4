import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.princeton.cs.algs4.StdOut;

public class P_3_2_4 {
    public static class Result {
        boolean ok; 
        String reason;
        Result(boolean ok, String reason) { 
            this.ok = ok; 
            this.reason = reason; }

        public String toString() { 
            return ok ? "Valid" : ("Invalid (" + reason + ")"); }
    }
    public static Result isValid(int[] seq) {
        int target = 5;
        // exclusive bounds
        int lo = Integer.MIN_VALUE;
        int hi = Integer.MAX_VALUE;
        boolean[] seen = new boolean[11]; // keys 1..10
        
        for (int i = 0; i < seq.length; i++) {
            int v = seq[i];
            if (seen[v]) return new Result(false, "value " + v + " repeated"); 
            seen[v] = true;

            if (!(lo < v && v < hi)) {
                return new Result(false, "value " + v + " violates range (" + lo + ", " + hi + ")");
            }

            if (v == target) {
                if (i != seq.length - 1) {
                    return new Result(false, 
                    "found target at position " + i + " but sequence continues");
                }
                return new Result(true, "ok"); // if valid, end here
            } else if (target > v) { // go right: keys must be > v
                lo = Math.max(lo, v);
            } else { // go left: keys must be < v
                hi = Math.min(hi, v);
            }
        }
        return new Result(false, "sequence ended without target 5");
    }
    public static void main(String[] args) {
        Map<String, int[]> cases = new LinkedHashMap<>();
        cases.put("a", new int[]{10,9,8,7,6,5});
        cases.put("b", new int[]{4,10,8,7,5,3});
        cases.put("c", new int[]{1,10,2,9,3,8,4,7,6,5});
        cases.put("d", new int[]{2,7,3,8,4,5});
        cases.put("e", new int[]{1,2,10,4,8,5});

        for (Entry<String, int[]> entry : cases.entrySet()) {
            String name = entry.getKey();
            int[] seq = entry.getValue();
            Result r = isValid(seq);
            StdOut.println(name + " " + Arrays.toString(seq) + "->" + r);
        }
    }    
}

// a [10, 9, 8, 7, 6, 5]->Valid
// b [4, 10, 8, 7, 5, 3]->Invalid (found target at position 4 but sequence continues)
// c [1, 10, 2, 9, 3, 8, 4, 7, 6, 5]->Valid
// d [2, 7, 3, 8, 4, 5]->Invalid (value 8 violates range (3, 7))
// e [1, 2, 10, 4, 8, 5]->Valid

// d: [2, 7, 3, 8, 4, 5]
// step     a[i]        cmp         go          range
// 1        2           2<5         right       (2, max)
// 2        7           7>5         left        (2, 7)
// 3        3           3<5         right       (3, 7)
// 4        8           8>5         left        (3, 8) -> violation

