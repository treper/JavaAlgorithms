package ataosky.leetcode.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

	public ArrayList<ArrayList<Integer>> permutations(
			int[] num) {
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

	public void swap(int[] num,int a,int b){
		int tmp = num[a];
		num[a]=num[b];
		num[b]=tmp;
	}
	
	public void dfs(int[] num, int start,
			ArrayList<ArrayList<Integer>> results) {
		if (start == num.length) {
			ArrayList<Integer> item = new ArrayList<Integer>();
			for(int i:num)
			{
				item.add(i);
			}
			results.add(item);
			return;
		}

		for (int i = start; i < num.length; i++) {
			//swap start and current selected item, the current item has been moved to start, so the next round current item will not be visited
			swap(num,start,i);
			dfs(num, start + 1, results);
			//restore
			swap(num,start,i);

		}

	}
	
	

	public static void main(String args[]) {
//		ArrayList<Integer> num = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
//		Permutations solution = new Permutations();
//		ArrayList<ArrayList<Integer>> results = solution.permutations(num);
		int[] num={1,2,3};
		Permutations solution = new Permutations();
		ArrayList<ArrayList<Integer>> ret = solution.permutations(num);
		System.out.println(ret.toString());
	}
}
