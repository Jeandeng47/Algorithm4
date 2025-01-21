import edu.princeton.cs.algs4.StdOut;

public class P_1_1_6 {
    public static void main(String[] args) {
        // F(0) = 0; F(1) = 1; F(n) = F(n-1) + F(n-2)
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++)
        {
            StdOut.println(f); // f: current F(n)
            f = f + g; // f = F(n-1) + F(n) = F(n+1)
            g = f - g; // g = F(n+1) - F(n) = F(n-1)
        }
    }
}

// Output: 
// 0
// 1
// 1
// 2
// 3
// 5
// 8
// 13
// 21
// 34
// 55
// 89
// 144
// 233
// 377
// 610
