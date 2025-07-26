public class P_2_1_11 {
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static int[] makeIncrements(int N) {
        int h = 1;
        int cnt = 0;
        while (h < N/3) {
            h = 3 * h + 1;
            cnt++;
        }
        int[] inc = new int[cnt + 1];
        for (int i = 0; i <= cnt; i++) {
            inc[i] = h;
            h = (h - 1) / 3;
        }
        return inc;
    }

    public static void shellSort(Comparable[] a) {
        int N = a.length;
        int[] hs = makeIncrements(N);

        for (int h : hs) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
        }

    }
    public static void main(String[] args) {
        Character[] a = {
            'E','A','S','Y','S','H','E','L','L','S',
            'O','R','T','Q','U','E','S','T','I','O','N'
        };

        System.out.println("Before: ");
        for (char c : a) System.out.print(c + " ");
        System.out.println();

        shellSort(a);

        System.out.println("After: ");
        for (char c : a) System.out.print(c + " ");
        System.out.println();
    }
    
}

// Before: 
// E A S Y S H E L L S O R T Q U E S T I O N 
// After: 
// A E E E H I L L N O O Q R S S S S T T U Y 