import java.util.*;

public class P_2_4_14 {
    private final static long seed = 42;
    private static int exchCount;
    
    private static int kDelMaxExch(Integer[] heap, int k) {
        Integer[] b = Arrays.copyOf(heap, heap.length);
        int n = b.length, total = 0;
        for (int t = 0; t < k && n > 0; t++) {
            total += delMaxCountExch(b, n);
            n--;
        }
        return total;
    }

    private static int delMaxCountExch(Integer[] a, int heapSize) {
        exchCount = 0;
        if (heapSize <= 1) return 0;
        exch(a, 0, heapSize - 1);
        sinkCount(a, 0, heapSize - 1);
        return exchCount;
    }

    private static void sinkCount(Integer[] a, int k, int n) {
        while (2*k + 1 < n) {
            int j = 2*k + 1;
            if (j + 1 < n && a[j] < a[j+1]) j++;
            if (a[k] >= a[j]) break;
            exch(a, k, j);
            k = j;
        }
    }
    private static void exch(Integer[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t; exchCount++;
    }


    // Build max-heap of size N that minimizes the exchanges for the first k
    // del-Max operation
    // 1. Top-3 largest at the top
    // 2. Left subtree gets the L smallest as a local max-heap
    // 3. Right subtree gets the next R values
    // 4. Search (shuffle+heapify) within the right subtree for trials times
    // 5. keep the tree structure with min exchanges
    public static Integer[] buildMinExchHeap(int N, int k, int trials) {
        Integer[] base = new Integer[N];

        // place top3 at the top
        Integer[] a = new Integer[N];
        a[0] = N;
        a[1] = N - 1;
        a[2] = N - 2;

        // collect indices of descendants
        List<Integer> leftDesc = collectDescendants(1, N);
        List<Integer> rightDesc = collectDescendants(2, N);

        int L = leftDesc.size();
        int R = rightDesc.size();

        // assign smallest (1...L) into left subtree
        
        Integer[] leftVals = rangeInc(1, L);
        heapify0(leftVals);
        for (int i = 0; i < L; i++) {
            a[leftDesc.get(i)] = leftVals[i];
        }

        // right subtree: search
        Integer[] best = null;
        int bestScore = Integer.MAX_VALUE;
        Integer[] rightBase = rangeInc(L + 1, L + R);

        Random rand = new Random(seed);
        Integer[] candidate = new Integer[R];
        for (int t = 0; t < trials; t++) {
            // shuffle
            System.arraycopy(rightBase, 0, candidate, 0, R);
            for (int i = R-1; i > 0; i--) {
                int j = rand.nextInt(i+1);
                int tmp = candidate[i]; candidate[i] = candidate[j]; candidate[j] = tmp;
            }
            // heapifly
            heapify0(candidate);

           
            // place into full heap
            Integer[] test = Arrays.copyOf(a, N);
            for (int i = 0; i < R; i++) test[rightDesc.get(i)] = candidate[i];

            int score = kDelMaxExch(test, k);
            if (score < bestScore) { 
                bestScore = score; 
                best = test; }
        }
        return best;
    }

    private static void heapify0(Integer[] a) {
        for (int k = a.length / 2 - 1; k >= 0; k--) {
            sink0(a, k, a.length);
        }
    }

    private static void sink0(Integer[] a, int k, int n) {
        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j + 1 < n && a[j] < a[j+1]) j++;
            if (a[k] > a[j]) break;
            swap(a, k, j);
            k = j;
        }
    }

    private static void swap(Integer[] a, int i, int j) {
        Integer t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // Add descendants to list except for the root
    private static List<Integer> collectDescendants(int root, int n) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();
        q.add(root);
        boolean first = true;
        while (!q.isEmpty()) {
            int i = q.poll();
            if (first) first = false; else result.add(i);
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            if (l < n) q.add(l);
            if (r < n) q.add(r);
        }
        return result;
    }

    private static Integer[] rangeInc(int lo, int hi) {
        int m = hi - lo + 1;
        Integer[] a = new Integer[m];
        for (int i = 0; i < m; i++) a[i] = lo + i;
        return a;
    }

    private static boolean isMaxHeap(Integer[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            if (l < n && a[i] < a[l]) return false;
            if (r < n && a[i] < a[r]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 15, k = 3, trials = 10_000;
        Integer[] h = buildMinExchHeap(N, k, trials); 

        System.out.println("N=15 heap   = " + Arrays.toString(h));
        System.out.println("isMaxHeap   = " + isMaxHeap(h));
        System.out.println("k=1 exch    = " + kDelMaxExch(h, 1)); // 2
        System.out.println("k=2 exch    = " + kDelMaxExch(h, 2)); // 5
        System.out.println("k=3 exch    = " + kDelMaxExch(h, 3)); // 8
    }
}

// N=15 heap   = [15, 14, 13, 6, 5, 12, 11, 3, 4, 2, 1, 9, 8, 10, 7]
// isMaxHeap   = true
// k=1 exch    = 2
// k=2 exch    = 5
// k=3 exch    = 8
