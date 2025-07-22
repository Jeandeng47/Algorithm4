// Even faster: further bring down time complexity to O(N^2)

// Idea:
// 1.  First sort the array: 
//     - help to remove duplicates
//     - help to move pointers towards center
// 2.  Iterate first element i:
//     - if i > 0 && a[i] == a[i-1], duplicate, continue
//     - find in the interval a[i + 1...n-1], search for pair (j, k) s.t. 
//     - a[j] + a[k] == -a[i]
// 3. Search interval a[i + 1...n-1]:
//    - left = i + 1, right = n - 1, target = -a[i]
//    - stop checking if left >= right
//    - in while loop (l < r):
//          (1) sum = a[left] + a[right]
//          (2) if sum == target, count++, left++, right--
//          (3) if sum > target, decrease sum by right--, decreases a[right]
//          (4) if sum < target, increase sum by left++, increases a[left]
//  

import java.util.Arrays;

public class _ThreeSumDoublePtrs {
    public static int threeSumDoublePtrs(int[] a) {
        Arrays.sort(a); // O(NlogN) 
        int n = a.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (i > 0 && a[i] == a[i - 1]) continue;
            int target = -a[i];
            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int sum = a[left] + a[right];
                if (sum == target) {
                    count++;

                while (left < right && a[left] == a[left+1]) left++;
                while (left < right && a[right] == a[right -1]) right--;
                
                left++;
                right--;
                    
                } else if (sum < target) {
                    // sum is too small, increase sum by left++
                    left++;
                } else if (sum > target) {
                    // sum is too large, decrease sum by right--
                    right--;
                }
            }
        }
        return count;
        
    }
}
