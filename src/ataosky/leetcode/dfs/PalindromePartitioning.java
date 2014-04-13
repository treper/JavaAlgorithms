package ataosky.leetcode.dfs;

import java.util.ArrayList;
import java.util.Collections;

//============================================================================
//Palindrome Partitioning
//Given a string s, partition s such that every substring of the partition 
//is a palindrome.
//
//Return all possible palindrome partitioning of s.
//
//For example, given s = "aab",
//Return
//
//[
//    ["aa","b"],
//    ["a","a","b"]
//]
//
//Complexity:
//Recursion + Memoization, O(n^2) time,  O(n^3) space
//DP, O(n^2) time, O(n^3) space
//============================================================================

public class PalindromePartitioning {
	public ArrayList<ArrayList<String>> partition(String s) {
		ArrayList<ArrayList<String>> ret = partition1(s);

		return ret;

	}

	public ArrayList<ArrayList<String>> partition1(String s) {
		int len = s.length();
		boolean[][] map = new boolean[len][len];// s[i:j] is palindrome
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

		for (int j = 0; j < len; j++) {
			for (int i = 0; i <= j; i++) {
				if (s.charAt(i) == s.charAt(j)
						&& (j - i < 2 || map[i + 1][j - 1])) {
					map[i][j] = true;
				} else {
					map[i][j] = false;
				}
			}
		}
		ArrayList<String> path = new ArrayList<String>();
		dfs(s, map, 0, path, ret);
		return ret;

	}

	public void dfs(String s, boolean[][] map, int start,
			ArrayList<String> path, ArrayList<ArrayList<String>> ret) {
		if (start >= s.length()) {
			ArrayList<String> npath = new ArrayList<String>(path);
			Collections.reverse(npath);			
			ret.add(npath);
			System.out.println("ret:"+ret);
			return;
		}

		for (int i = start; i < s.length(); i++) {
			for (int j = i; j < s.length(); j++) {
				if (map[i][j]) {
					path.add(0, s.substring(i, j+1));
					System.out.println("push back path:"+path);
					dfs(s, map, start+j+1-i, path, ret);
					path.remove(0);
					System.out.println("pop back path:"+path);
				}  
			}
		}
	}

	public static void main(String args[]) {
		PalindromePartitioning solution = new PalindromePartitioning();
		String s = new String("aab");
		ArrayList<ArrayList<String>> ret = solution.partition(s);

		for (ArrayList<String> l : ret) {
			for (String i : l) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}

}
