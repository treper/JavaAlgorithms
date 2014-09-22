package ataosky.leetcode.search;


import org.junit.Test;

/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * <p/>
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 * For example,
 * <p/>
 * Consider the following matrix:
 * <p/>
 * [
 * [1,   3,  5,  7],
 * [10, 11, 16, 20],
 * [23, 30, 34, 50]
 * ]
 * Given target = 3, return true.
 */
public class Searcha2DMatrix {
    public boolean search(int[][] A, int target) {
        int h = A.length;
        int w = A[0].length;
        int l = 0, r = h * w - 1;
        int mid, x, y;
        while (l <= r) {
            mid = (l + r) / 2;
            y = mid % w;
            x = mid / w;
            if (A[x][y] == target) {
                return true;
            } else if (A[x][y] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    @Test
    public void testSearch2DMatrix() {
        int[][] multi = new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50},
        };
    }
}
