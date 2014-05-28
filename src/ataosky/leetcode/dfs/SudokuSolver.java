package ataosky.leetcode.dfs;
//============================================================================
//Sudoku Solver
//Write a program to solve a Sudoku puzzle by filling the empty cells.
//
//Empty cells are indicated by the character '.'.
//
//You may assume that there will be only one unique solution.
//
//Complexity:
//O(M*N^2) time, M is # of empty cells, N is size of board
//============================================================================
public class SudokuSolver {
    public void solveSudoku(char[][] board) {
    	boolean available[][] = new boolean[board.length][board[0].length];
    	
        
    }
    
    public void dfs(char[][] board)
    {
    	
    }
    
	public static void main(String args[]) {
		SudokuSolver solution = new SudokuSolver();
		char[][] board = new char[][]{};
		solution.solveSudoku(board);
		System.out.println(board.toString());
	}
}
