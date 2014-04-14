package ataosky.leetcode.recursive;

import java.util.ArrayList;

//============================================================================
//Given n pairs of parentheses, write a function to generate all combinations
//of well-formed parentheses.
//
//For example, given n = 3, a solution set is:
//
//"((()))", "(()())", "(())()", "()(())", "()()()"
//
//Complexity:
//O(n!)
//============================================================================
public class GenerateParenthesis {

	public ArrayList<String> generateParenthesis(int n)
	{
		ArrayList<String> ret = new ArrayList<String>();
		if(n==0)
		{
			ret.add("");
		}
		else if(n==1)
		{
			ret.add("()");
		}
		else
		{

		
		for(int i=0;i<n;i++)
			for(String inner:generateParenthesis(i))
				for(String outer:generateParenthesis(n-i-1))
					ret.add("("+inner+")"+outer);
		}
		return ret;

	}

	public static void main(String args[])
	{
		GenerateParenthesis solution = new GenerateParenthesis();
		ArrayList<String> ret = solution.generateParenthesis(3);
		System.out.println(ret.toString());

	}

}
