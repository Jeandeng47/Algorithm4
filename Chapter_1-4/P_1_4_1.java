// To run this program:
// make run ARGS="7"

public class P_1_4_1 {
    // Math induction:
    // To prove: C(N, 3) = N(N - 1)(N - 2) / 6
    // (1) Base case (N = 3):
    // Select 3 elements from a set of 3 elements only has one way
    // C(3, 3) = 1

    // (2) Inductive case (N >= 3):
    // Assume this is also true for N >= 3, we have:
    //  C(N, 3) = N(N - 1)(N - 2) / 6
    
    // We have to prove this is true for N + 1, that is C(N + 1, 3) = (N + 1)(N)(N - 1) / 6
    // Consider choosing 3 elements from N + 1 elements, there are 2 situation:

    // A. Not choosing (N + 1)th element, select all 3 from the previous: 
    // C(N, 3) ways

    // B. Choose (N + 1)th element, select 2 from the previous:
    // C(N, 2) * 1 ways

    //C(N + 3) = C(N, 3) + C(N, 2) * 1 = (N + 1)(N)(N - 1) / 6

    public static void main(String[] args) {
        int N;
        if (args.length == 1) {
            N = Integer.parseInt(args[0]);
        } else {
            System.out.println("Provide a positive integer <N>");
            return;
        }

        int enumCount = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    enumCount++;
                }
            }
        }

        int formulaCount = N * (N - 1) * (N - 2) / 6;

        System.out.println("N = " + N);
        System.out.println("Enumerated triples count = " + enumCount);
        System.out.println("Formula N*(N-1)*(N-2)/6 = " + formulaCount);
        System.out.println("Match: " + (enumCount == formulaCount));
    }
}

// //N = 7
// Enumerated triples count = 35
// Formula N*(N-1)*(N-2)/6 = 35
// Match: true