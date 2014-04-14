package ataosky.leetcode.string;

import java.util.ArrayList;

import ataosky.leetcode.dfs.Permutations;

//============================================================================
//Implement strStr().
//
//Returns a pointer to the first occurrence of needle in haystack,
//or null if needle is not part of haystack.
//
//Complexity:
//brute force, O(n*m) time, O(1) space
//Rabin - Karp(RK), O(n + m) average and O(n*m) worst case time, O(1) space
//Knuth-Morris-Pratt Algorithm (KMP), O(n+m) time, O(n) space
//============================================================================
public class ImplementStrStr {
	public String strStr(String haystack, String needle) {
		return strStr1(haystack,needle);
	}

	public String strStr1(String haystack,String needle)
	{
		if (null == haystack || null == needle)
			return null;
		if (haystack.isEmpty() && needle.isEmpty())
			return "";
		else if(!haystack.isEmpty() & needle.isEmpty())
			return haystack;


		int m = haystack.length();
		int n = needle.length();
		for (int i = 0; i <= m - n; i++) {
			int j = 0;
			while (j < n && haystack.charAt(i + j) == needle.charAt(j))
				j++;

			if (j == n) {
				return haystack.substring(i);
			}
		}
		return null;
	}
	public String kmp(String haystack, String needle) {
		//generate longest prefix lenth array
		
		//compare
		return "";
	}

	public static void main(String args[]) {
		ImplementStrStr solution = new ImplementStrStr();
		String p = solution.strStr("aaabaa", "b");
		//System.out.print("".isEmpty());
		System.out.print(p);

	}
}
