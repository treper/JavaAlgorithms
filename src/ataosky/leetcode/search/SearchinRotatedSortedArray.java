package ataosky.leetcode.search;

//============================================================================
// Search in Rotated Sorted Array
// Suppose a sorted array is rotated at some pivot unknown to you beforehand.
//
// (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
//
// You are given a target value to search. If found in the array return its
// index, otherwise return -1.
//
// You may assume no duplicate exists in the array.
//
// Complexity
// O(log(n)) time
//============================================================================
//not submitted
// a1<a2<a3<a4<a5<a6<a7 => a4<a5<a6<a7,a1<a2<a3 没有重复
public class SearchinRotatedSortedArray {
    public int searchinRotatedSortedArray(int[] A,int target)
    {
        int left=0;
        int right = A.length-1;
        while(left<=right)
        {
            int mid = (left+right)/2;
            if(target==A[mid])
                return mid;
            if(A[left]<=A[mid])//前半段有序
            {
                if(target > A[left] && target<A[mid])//target在前半段
                {
                    right=mid-1;
                }
                else
                {
                    left=mid+1;
                }
            }
            else//前半段无序
            {
                if(target>A[mid] && target<A[right])
                {
                    left=mid+1;
                }
                else
                {
                    right=mid-1;
                }
            }

        }
        return  -1;
    }
}
