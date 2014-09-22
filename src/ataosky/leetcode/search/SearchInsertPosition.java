package ataosky.leetcode.search;

/**
 * Given a sorted array and a target value, return the index if the target is
 * found. If not, return the index where it would be if it were inserted in
 * order.
 * <p/>
 * You may assume no duplicates in the array.
 * <p/>
 * Here are few examples.
 * [1,3,5,6], 5 -> 2
 * [1,3,5,6], 2 -> 1
 * [1,3,5,6], 7 -> 4
 * [1,3,5,6], 0 -> 0
 */


public class SearchInsertPosition {
    //O(logn)
    public int searchInsert(int[] A, int target) {
        if (A.length == 0)
            return 0;

        int l = 0, r = A.length - 1;
        int mid = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (A[mid] == target)
                return mid;
            else if (A[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return A[l] < target ? l + 1 : l;
    }

    //O(n)
    public int searchInsertPosition(int[] A, int target) {
        int i;
        for (i = 0; i < A.length; i++) {
            if (A[i] >= target)
                return i;
        }
        return i;
    }
}
