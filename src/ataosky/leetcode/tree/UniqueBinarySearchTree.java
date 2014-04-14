package ataosky.leetcode.tree;

import java.util.Arrays;

import ataosky.leetcode.string.ImplementStrStr;

//============================================================================
//Unique Binary Search Trees
//Given n, how many structurally unique BST's (binary search trees) that
//store values 1...n?
//
//For example,
//Given n = 3, there are a total of 5 unique BST's.
//
//1         3     3      2      1
// \       /     /      / \      \
//  3     2     1      1   3      2
// /     /       \                 \
//2     1         2                 3
//
//Complexity:
//Recursion, O(n^2) time, O(n) space
//DP, O(n^2) time, O(n) space
//Math, O(n) time, O(1) space
//============================================================================
public class UniqueBinarySearchTree {
	public int numTrees(int n) {
		return numTrees1(n);
	}

	public int numTrees1(int n) {
		/*

		empty set:
		count[0]=1
		set {1}:
		count[1]=1
		set {1,2}:
		count[2]=
		count[0]*count[1] + //1 as root,so left node is null(count[0]),right is must be 2,only one element so count[1]
		count[1]*count[0] //2 as root,so left node is 1(count[1]),right is null so count[0]
		set {1,2,3}:
		count[3]=
		count[0]*count[2] // 1 as root,left must be null(count[0]),right include {2,3} so count[2]
		+ count[1]*count[1] // 2 as root,left must be 1(count[1]),right must be 3 so count[1]
		count[2]*count[1] // 3 as root,left include {1,2}(count[2]),right must be null(count[2])

		transform formula
		count[n]=sum(count[i]*count[j]),0<=i,j<n
		*/

		int[] dp=new int[n+1];
		Arrays.fill(dp,0);
		dp[0]=dp[1]=1;
		for(int i =2;i<=n;i++)
		{
			for(int j =0;j<i;j++)
				dp[i]+=dp[j]*dp[i-j-1];
		}
		return dp[n];

	}

	public static void main(String args[]) {
		UniqueBinarySearchTree solution = new UniqueBinarySearchTree();
		int n = solution.numTrees(3);
		System.out.print(n);

	}
}
