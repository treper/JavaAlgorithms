package ataosky.leetcode.dfs;

import java.util.ArrayList;
import java.util.Arrays;

//============================================================================
//Word Search
//Given a 2D board and a word, find if the word exists in the grid.
//
//The word can be constructed from letters of sequentially adjacent cell,
//where "adjacent" cells are those horizontally or vertically neighboring.
//The same letter cell may not be used more than once.
//
//For example,
//Given board =
//
//[
//["ABCE"],
//["SFCS"],
//["ADEE"]
//]
//word = "ABCCED", -> returns true,
//word = "SEE", -> returns true,
//word = "ABCB", -> returns false.
//============================================================================
public class WordSearch {
	public int[] v = { 1, -1, 0, 0 };
	public int[] h = { 0, 0, -1, 1 };

	// up down left right
	public boolean exist(char[][] board, String word) {

		boolean[][] visited = new boolean[board.length][board[0].length];
		for(boolean[] visit:visited){
		Arrays.fill(visit,false);}
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++) {
				if (dfs(board, visited,word, 0, i, j))
					return true;

			}
		return false;
	}

	public boolean dfs(char[][] board,boolean[][] visited, String word, int start, int ci, int cj) {
		if (start == word.length()) {
			return true;
		}
		int m = board.length;
		int n = board[0].length;
		boolean flag=false;
		if (!visited[ci][cj] &&board[ci][cj] == word.charAt(start)) {
			
			visited[ci][cj]=true;
			for (int k = 0; k < 4; k++) {
				int ni = ci + v[k], nj = cj + h[k];
				if (ni < m && ni >= 0 && nj < n && nj >= 0) {
					if(dfs(board,visited, word, start + 1, ni, nj))
					{
						flag=true;
						break;
					}

				}
			}
			visited[ci][cj]=false;

		}
		return flag;

	}

	public static void main(String args[]) {
		WordSearch solution = new WordSearch();
		char[][] board={{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
		ArrayList<String> words = new ArrayList<String>(Arrays.asList("ABCCED","SEE","ABCB"));
		for(String word:words){
		System.out.println(solution.exist(board,word ));
		}
	}
}
