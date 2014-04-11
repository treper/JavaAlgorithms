package com.ataosky.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//============================================================================
//Given a collection of numbers, return all possible permutations.
//
//For example,
//[1,2,3] have the following permutations:
//[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
//============================================================================

public class Permutations {


	public ArrayList<ArrayList<Integer>> permute(int[] num) {
		ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
		dfs(num, 0, results);
		return results;
	}
	
	public ArrayList<ArrayList<Integer>> permutations(
			ArrayList<Integer> num) {
		ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
		dfs(num, 0, results);
		return results;
	}
	
	public void dfs(ArrayList<Integer> num, int start,
			ArrayList<ArrayList<Integer>> results) {
		if (start == num.size()) {
			ArrayList<Integer> item = new ArrayList<Integer>(num);
			results.add(item);
			return;
		}

		for (int i = start; i < num.size(); i++) {
			Collections.swap(num, start, i);
			dfs(num, start + 1, results);
			Collections.swap(num, start, i);

		}

	}

	
	public void dfs(int[] num, int start,
			ArrayList<ArrayList<Integer>> results) {
		if (start == num.length) {
			ArrayList<Integer> item = new ArrayList<Integer>(num);
			results.add(item);
			return;
		}

		for (int i = start; i < num.length; i++) {
			Collections.swap(num, start, i);
			dfs(num, start + 1, results);
			Collections.swap(num, start, i);

		}

	}
	
	

	public static void main(String args[]) {
		ArrayList<Integer> num = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		Permutations solution = new Permutations();
		ArrayList<ArrayList<Integer>> results = solution.permutations(num);

		for (ArrayList<Integer> l : results) {
			for (Integer i : l) {
				System.out.print(i);

			}
			System.out.println();
		}
	}
}
