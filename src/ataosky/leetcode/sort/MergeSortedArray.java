package ataosky.leetcode.sort;

/**
 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
 *
 * Note:
 *
 * You may assume that A has enough space to hold additional elements from B.
 * The number of elements initialized in A and B are m and n respectively.
 */
//not submitted
public class MergeSortedArray {
    //A has enough space
    public void mergeSortedArray(int[] A,int m,int[] B,int n)
    {
        int i = m,j=n;
        int k = m+n-1;
        while (i>=0 && j>=0 )
        {
            A[k--]=A[i]>=B[j]?A[i--]:B[j--];

        }

        if(j>=0)

        {
            A[k--]=B[j--];
        }
    }


}
