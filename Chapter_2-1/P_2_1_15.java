import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class P_2_1_15 {
    public static class Crate implements Comparable<Crate> {
        private String id;
        private int shipTime;

        public Crate(String id, int shipTime) {
            this.id = id;
            this.shipTime = shipTime;
        }

        @Override
        public int compareTo(Crate that) {
            return Integer.compare(this.shipTime, that.shipTime);
        }

        @Override
        public String toString() {
            return id + "(" +shipTime + ")";
        }

    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    public static void selectionSort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                if (a[j].compareTo(a[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            exch(a, i, minIdx);
        }
    }

    public static void main(String[] args) {
        int N = 10;
        Crate[] crates = new Crate[N];
        Random rnd = new Random(42);
        for (int i = 0; i < N; i++) {
            // IDs C0…C9, times 0…99
            crates[i] = new Crate("C" + i, rnd.nextInt(100));
        }

        StdOut.println("Before sorting:");
        StdOut.println(Arrays.toString(crates));

        // sort by shipping time; only N–1 swaps, minimal crate moves
        selectionSort(crates);

        StdOut.println("After sorting:");
        StdOut.println(Arrays.toString(crates));
    }
}
