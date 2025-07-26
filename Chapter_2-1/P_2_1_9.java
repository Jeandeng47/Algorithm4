import edu.princeton.cs.algs4.StdOut;

public class P_2_1_9 {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void traceSort(Comparable[] a, int currIdx, int exchIdx, int h) {
        StdOut.printf("h=%2d | i=%2d: ", h, currIdx);
        for (int k = 0; k < a.length; k++) {
            if (k == currIdx)      StdOut.print("*");
            if (k == exchIdx)      StdOut.print(">");
            StdOut.print(a[k] + " ");
        }
        StdOut.println();
    }

    public static void shellSort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        // for each h, do a h-sort
        while (h >= 1) {
            StdOut.println("\n--- h = " + h + " pass ---");
            // outter loop: iterate through a[h]...a[N-1]
            for (int i = h; i < N; i++) {
                int currIdx = i;
                int exchIdx = i;
                // inner loop: compare a[i] with a[i - h], a[i - 2h]...
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                    exchIdx = j - h;
                }
                traceSort(a, currIdx, exchIdx, h);
            }
            
            h = h / 3;
        }
    }

    public static void main(String[] args) {
        Character[] a = {
            'E','A','S','Y','S','H','E','L','L','S',
            'O','R','T','Q','U','E','S','T','I','O','N'
        };

        StdOut.println("Initial array:");
        for (char c : a) StdOut.print(c + " ");
        StdOut.println("\n");

        StdOut.println("Shellsort trace:");
        shellSort(a);

        StdOut.println("\nSorted array:");
        for (char c : a) StdOut.print(c + " ");
        StdOut.println();
    }
}


// Initial array:
// E A S Y S H E L L S O R T Q U E S T I O N 

// Shellsort trace:

// --- h = 13 pass ---
// h=13 | i=13: E A S Y S H E L L S O R T *>Q U E S T I O N 
// h=13 | i=14: E A S Y S H E L L S O R T Q *>U E S T I O N 
// h=13 | i=15: E A >E Y S H E L L S O R T Q U *S S T I O N 
// h=13 | i=16: E A E >S S H E L L S O R T Q U S *Y T I O N 
// h=13 | i=17: E A E S S H E L L S O R T Q U S Y *>T I O N 
// h=13 | i=18: E A E S S H E L L S O R T Q U S Y T *>I O N 
// h=13 | i=19: E A E S S H E L L S O R T Q U S Y T I *>O N 
// h=13 | i=20: E A E S S H E L L S O R T Q U S Y T I O *>N 

// --- h = 4 pass ---
// h= 4 | i= 4: E A E S *>S H E L L S O R T Q U S Y T I O N 
// h= 4 | i= 5: E A E S S *>H E L L S O R T Q U S Y T I O N 
// h= 4 | i= 6: E A E S S H *>E L L S O R T Q U S Y T I O N 
// h= 4 | i= 7: E A E >L S H E *S L S O R T Q U S Y T I O N 
// h= 4 | i= 8: E A E L >L H E S *S S O R T Q U S Y T I O N 
// h= 4 | i= 9: E A E L L H E S S *>S O R T Q U S Y T I O N 
// h= 4 | i=10: E A E L L H E S S S *>O R T Q U S Y T I O N 
// h= 4 | i=11: E A E L L H E >R S S O *S T Q U S Y T I O N 
// h= 4 | i=12: E A E L L H E R S S O S *>T Q U S Y T I O N 
// h= 4 | i=13: E A E L L H E R S >Q O S T *S U S Y T I O N 
// h= 4 | i=14: E A E L L H E R S Q O S T S *>U S Y T I O N 
// h= 4 | i=15: E A E L L H E R S Q O S T S U *>S Y T I O N 
// h= 4 | i=16: E A E L L H E R S Q O S T S U S *>Y T I O N 
// h= 4 | i=17: E A E L L H E R S Q O S T S U S Y *>T I O N 
// h= 4 | i=18: E A E L L H E R S Q >I S T S O S Y T *U O N 
// h= 4 | i=19: E A E L L H E >O S Q I R T S O S Y T U *S N 
// h= 4 | i=20: E A E L L H E O >N Q I R S S O S T T U S *Y 

// --- h = 1 pass ---
// h= 1 | i= 1: >A *E E L L H E O N Q I R S S O S T T U S Y 
// h= 1 | i= 2: A E *>E L L H E O N Q I R S S O S T T U S Y 
// h= 1 | i= 3: A E E *>L L H E O N Q I R S S O S T T U S Y 
// h= 1 | i= 4: A E E L *>L H E O N Q I R S S O S T T U S Y 
// h= 1 | i= 5: A E E >H L *L E O N Q I R S S O S T T U S Y 
// h= 1 | i= 6: A E E >E H L *L O N Q I R S S O S T T U S Y 
// h= 1 | i= 7: A E E E H L L *>O N Q I R S S O S T T U S Y 
// h= 1 | i= 8: A E E E H L L >N *O Q I R S S O S T T U S Y 
// h= 1 | i= 9: A E E E H L L N O *>Q I R S S O S T T U S Y 
// h= 1 | i=10: A E E E H >I L L N O *Q R S S O S T T U S Y 
// h= 1 | i=11: A E E E H I L L N O Q *>R S S O S T T U S Y 
// h= 1 | i=12: A E E E H I L L N O Q R *>S S O S T T U S Y 
// h= 1 | i=13: A E E E H I L L N O Q R S *>S O S T T U S Y 
// h= 1 | i=14: A E E E H I L L N O >O Q R S *S S T T U S Y 
// h= 1 | i=15: A E E E H I L L N O O Q R S S *>S T T U S Y 
// h= 1 | i=16: A E E E H I L L N O O Q R S S S *>T T U S Y 
// h= 1 | i=17: A E E E H I L L N O O Q R S S S T *>T U S Y 
// h= 1 | i=18: A E E E H I L L N O O Q R S S S T T *>U S Y 
// h= 1 | i=19: A E E E H I L L N O O Q R S S S >S T T *U Y 
// h= 1 | i=20: A E E E H I L L N O O Q R S S S S T T U *>Y 

// Sorted array:
// A E E E H I L L N O O Q R S S S S T T U Y 
