package ataosky.leetcode.array;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Given an unsorted array of integers, find the length of the longest
 * consecutive elements sequence.
 * <p/>
 * For example, Given [100, 4, 200, 1, 3, 2], The longest consecutive elements
 * sequence is [1, 2, 3, 4]. Return its length: 4.
 * <p/>
 * Your algorithm should run in O(n) complexity.
 *
 *如果允许O(n log n) 的复杂度，那么可以先排序，可是本题要求O(n)。
 由于序列里的元素是无序的，又要求O(n)，首先要想到用哈希表。
 用一个哈希表unordered_map<int, bool> used 记录每个元素是否使用，对每个元素，以该
 元素为中心，往左右扩张，直到不连续为止，记录下最长的长度。
 * // 时间复杂度O(n)，空间复杂度O(n)
 *
 */

public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] num) {

        HashMap<Integer,Boolean> used=new HashMap<Integer,Boolean>();

        for(int i:num) {
            used.put(i, false);
        }

        int longest = 0;
        for(int i:num)
        {
            if(used.get(i))
                continue;

            int l=1;
            used.put(i,true);
            for (int j = i-1;  used.containsKey(j); j--) {
                l++;
                used.put(j,true);
            }

            for (int j = i+1; used.containsKey(j); j++) {
                l++;
                used.put(j,true);
            }

            if(l>longest)
                longest=l;
        }
        return longest;

    }

    @Test
    public void testLongest()
    {
        int[] a = new int[]{100, 4, 200, 1, 3, 2};

        System.out.println(longestConsecutive(a));

    }

}