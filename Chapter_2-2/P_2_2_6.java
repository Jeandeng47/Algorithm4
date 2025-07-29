import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class P_2_2_6 {
    private static Comparable[] aux;
    private static int access;

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
            access += 2; // read aux[k], write a[k]
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++]; 
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
            access += 2; // read aux[], write a[k]
        }
    }

    public static int sortBU(Comparable[] a) {
        access = 0; // reset

        int N = a.length;
        aux = new Comparable[N];

        // size = 1, 2, 4, 8, ....
        for (int size = 1; size < N; size = 2 * size) {
            for (int lo = 0; lo < N - size; lo += 2 * size) {
                merge(a, lo, lo+size-1, Math.min(lo+2*size-1, N -1));
            }
        }

        return access;
    }


    public static int sortTD(Comparable[] a) {
        access = 0; // reset
        aux = new Comparable[a.length];

        sortTDHelper(a, 0, a.length - 1);
        return access;
    }

    public static void sortTDHelper(Comparable[] a, int lo, int hi) {
        // base case
        if (lo >= hi) return;
        
        // recursive case
        int mid = lo + (hi - lo) / 2;
        sortTDHelper(a, lo, mid);
        sortTDHelper(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }
    
    public static void main(String[] args) throws IOException {

        String outDir = "Chapter_2-2";
        Path outPath = Paths.get(outDir);
        if (!Files.exists(outPath)) {
            Files.createDirectories(outPath);
        }

        try (FileWriter out = 
            new FileWriter(outDir + File.separator + "mergesort_accesses.csv")) {

            out.write("N,TopDown,BottomUp,6NlgN\n");

            for (int N = 1; N <= 512; N++) {
                // use a dummy array
                Integer[] a1 = new Integer[N];
                for (int i = 0; i < N; i++) a1[i] = i;
                long td = sortTD(a1.clone());

                Integer[] a2 = a1.clone();
                long bu = sortBU(a2);

                double bound = N > 1 ? 6.0 * N * (Math.log(N) / Math.log(2)) : 0.0;


                out.write(String.format("%d,%d,%d,%.2f\n", N, td, bu, bound));
            }
        }
        
    }
}
