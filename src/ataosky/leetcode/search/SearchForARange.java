package ataosky.leetcode.search;

import org.junit.Test;

import java.util.Arrays;

/**
 * Given a sorted array of integers, find the starting and ending position of a
 * given target value.
 *
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * If the target is not found in the array, return [-1, -1].
 *
 * For example, Given [5, 7, 7, 8, 8, 10] and target value 8, return [3, 4].
 */
public class SearchForARange {
    public int findLow(int[] A,int target,int left,int right)
    {
        int mid =0;
        int ret = -1;
        while(left<=right)
        {
            mid =(left+right)/2;
            if(A[mid]==target)
            {
                ret = mid;
                int next = findLow(A,target,left,mid-1);//findLow 与findHigh的不同之处
                if(next!=-1)
                    ret=next;
                break;
            }
            else if(A[mid]<target)//在右半
            {
                left=mid+1;
            }
            else//在左半
            {
                right=mid-1;
            }

        }

        return ret;
    }

    public int findHigh(int[] A,int target,int left,int right)
    {
        int mid=0;
        int ret=-1;
        while (left<=right)
        {
            mid = (left+right)/2;
            if(A[mid]==target)
            {
                ret=mid;
                int next=findHigh(A,target,mid+1,right);
                if(next!=-1)
                    ret=next;
                break;
            }
            else if(A[mid]<target)//在右半
            {
                left=mid+1;
            }
            else//在左半
            {
                right=mid-1;
            }
        }
        return ret;
    }

    public int[] search(int[] A,int target)
    {
        int low = findLow(A,target,0,A.length-1);
        int high = findHigh(A,target,0,A.length-1);

        int[] ret=new int[2];
        ret[0]=low;
        ret[1]=high;
        return ret;


    }
    @Test
    public void testSearchForRange()
    {
        int[] A=new int[]{5, 7, 7, 8, 8, 10};
        int[] result = search(A,8);
        System.out.println(Arrays.toString(result));
    }


}
