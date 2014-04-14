package ataosky.leetcode.dfs;

import java.util.ArrayList;
import java.util.Collections;
//============================================================================
//Given a set of candidate numbers (C) and a target number (T),
//find all unique combinations in C where the candidate numbers sums to T.
//
//The same repeated number may be chosen from C unlimited number of times.
//
//Note:
//All numbers (including target) will be positive integers.
//Elements in a combination (a1, a2, … ,ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
//The solution set must not contain duplicate combinations.
//For example, given candidate set 2,3,6,7 and target 7,
//A solution set is:
//[7]
//[2, 2, 3]
//============================================================================
import java.util.Arrays;

public class CombinationSum {
	public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates,
			int target) {
		Arrays.sort(candidates);
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		dfs(candidates, target, 0, path, ret);

		return ret;
	}

	public void dfs(int[] candidates, int target, int begin,
			ArrayList<Integer> path, ArrayList<ArrayList<Integer>> ret) {
		if (target <= 0) {
			if (target == 0)
			{
				ArrayList<Integer> npath = new ArrayList<Integer>(path);
				Collections.reverse(npath);
				ret.add(npath);
			}
			return;
		}

		for (int i = begin; i < candidates.length; i++) {
			path.add(0, candidates[i]);
			dfs(candidates, target - candidates[i], i, path, ret);
			path.remove(0);

		}
	}

	public static void main(String args[]) {
		CombinationSum solution = new CombinationSum();
		int[] a = new int[] { 2, 3, 6, 7 };
		int target = 7;
		ArrayList<ArrayList<Integer>> ret = solution.combinationSum(a, 7);
		System.out.println(ret.toString());

	}

}
