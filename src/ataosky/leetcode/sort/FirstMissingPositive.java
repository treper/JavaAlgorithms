package ataosky.leetcode.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 本质上是桶排序(bucket sort)，每当A[i]!= i+1 的时候，将A[i] 与A[A[i]-1] 交换，直到无法
 * 交换为止，终止条件是A[i]== A[A[i]-1]。
 * <p/>
 * Given an unsorted integer array, find the first missing positive integer.
 * <p/>
 * For example, Given [1,2,0] return 3, and [3,4,-1,1] return 2.
 * <p/>
 * Your algorithm should run in O(n) time and uses constant space.
 */
//not submitted

public class FirstMissingPositive {
    //原地换位,各归其位
    public int firstMissingPositive(int[] A) {
//3,4,5,7
        for (int i = 0; i < A.length; i++) {
            if (A[i] > 0 && A[i] - 1 <A.length && A[i] != i + 1 && A[i] != A[A[i] - 1]) {
                //swap A[i] and A[A[i]-1]
                int t = A[A[i] - 1];
                A[A[i] - 1] = A[i];
                A[i] = t;
                i--;
                System.out.println(i);
                System.out.println(Arrays.toString(A));
            }
        }

        for (int i = 0; i < A.length; i++) {
            if (A[i] != i + 1)
                return i + 1;

        }
        return A.length + 1;
    }

    @Test
    public void testFirstMissingPositive()
    {
        int[] A = new int[]{3,4,-1,1};
        int missing = firstMissingPositive(A);
        System.out.println(missing);
    }

}
