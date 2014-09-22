package ataosky.leetcode.dp;


//============================================================================
//Unique Paths
//A robot is located at the top-left corner of a m x n grid (marked 'Start'
//in the diagram below).
//
//The robot can only move either down or right at any point in time. The robot
//is trying to reach the bottom-right corner of the grid (marked 'Finish' in
//the diagram below).
//
//How many possible unique paths are there?
//
//Complexity:
//DP, O(m*n) time, O(1) space
//DP, O(m*n) time, O(n) space
//Math, O(n) time, O(1) space
//============================================================================


import org.junit.Test;

public class UniquePaths {
	public int uniquePaths(int m, int n)
	{
		if(m==0 || n==0)
		{
			return 0;
		}
		int[][] path = new int[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++){
				if(i==0 ||j==0)
					path[i][j]=1;
				else
					path[i][j]=path[i-1][j] + path[i][j-1];
			}
		return path[m-1][n-1];
	}

	@Test
	public  void testUniquePahts()
	{
        int p = uniquePaths(4,3);
		
	}
	
}
