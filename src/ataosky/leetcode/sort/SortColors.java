package ataosky.leetcode.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * Given an array with n objects colored red, white or blue, sort them so that
 * objects of the same color are adjacent, with the colors in the order red,
 * white and blue.
 *
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white,
 * and blue respectively.
 *
 * Note:
 *
 * You are not suppose to use the library's sort function for this problem.
 *
 * Follow up:
 * A rather straight forward solution is a two-pass algorithm using counting sort.
 *
 * First, iterate the array counting number of 0's, 1's, and 2's,
 * then overwrite array with total number of 0's, then 1's and followed by 2's.
 *
 * Could you come up with an one-pass algorithm using only constant space?
 *
 *
 * 由于0, 1, 2 非常紧凑，首先想到计数排序(counting sort)，但需要扫描两遍，不符合题目要求。
 由于只有三种颜色，可以设置两个index，一个是red 的index，一个是blue 的index，两边往中
 间走。时间复杂度O(n)，空间复杂度O(1)。
 第3 种思路，利用快速排序里partition 的思想，第一次将数组按0 分割，第二次按1 分割，排
 序完毕，可以推广到n 种颜色，每种颜色有重复元素的情况。

 */
//not submitted
public class SortColors {
    public void sortColors(int[] A) {
        int length = A.length;
        int left = -1;
        int right = length;
        int i = 0;
        while (i < right) {
            if (A[i] == 0) {
                System.out.println("A["+i+"]=0");
                swap(A, ++left, i++);
                System.out.println(Arrays.toString(A));
            } else if (A[i] == 2) {
                System.out.println("A["+i+"]=2");
                swap(A, i, --right);
                System.out.println(Arrays.toString(A));

            } else {
                System.out.println("i++");
                i++;
                System.out.println(Arrays.toString(A));
            }
        }
    }

    private void swap(int[] a, int i, int j) {
        System.out.println("swap "+ i+ " "+j);

        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    @Test
    public void testSortColors(){
        int[] A= new int[]{0,2,1,2,1,0,0,2,1,1,1};

        sortColors(A);
        System.out.println("result:");
        System.out.println(Arrays.toString(A));

    }
}