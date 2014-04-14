package ataosky.leetcode.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//============================================================================
//Given a collection of candidate numbers (C) and a target number (T),
//find all unique combinations in C where the candidate numbers sums to T.
//
//Each number in C may only be used once in the combination.
//
//Note:
//All numbers (including target) will be positive integers.
//Elements in a combination (a1, a2, … ,ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
//The solution set must not contain duplicate combinations.
//For example, given candidate set 10,1,2,7,6,1,5 and target 8,
//A solution set is:
//[1, 7]
//[1, 2, 5]
//[2, 6]
//[1, 1, 6]
//============================================================================
public class CombinationSumII {
	public ArrayList<ArrayList<Integer>> combinationSum2(int[] candidates,
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
			if (target == 0) {
				ArrayList<Integer> npath = new ArrayList<Integer>(path);
				Collections.reverse(npath);
				ret.add(npath);
			}
			return;
		}

		for (int i = begin; i < candidates.length; i++) {
			if (i > begin && candidates[i - 1] == candidates[i])
				continue;
			path.add(0, candidates[i]);
			dfs(candidates, target - candidates[i], i + 1, path, ret);
			path.remove(0);

		}
	}

	public static void main(String args[]) {
		CombinationSumII solution = new CombinationSumII();
		int[] a = new int[] { 10, 1, 2, 7, 6, 1, 5 };
		int target = 8;
		ArrayList<ArrayList<Integer>> ret = solution.combinationSum2(a, target);

		System.out.println(ret.toString());

	}
}
