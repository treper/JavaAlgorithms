package ataosky.leetcode.miscellaneous;

import java.util.Arrays;


//============================================================================
//NextPermutation
//Implement next permutation, which rearranges numbers into the
//lexicographically next greater permutation of numbers.
//
//If such arrangement is not possible, it must rearrange it as the lowest
//possible order (ie, sorted in ascending order).
//
//The replacement must be in-place, do not allocate extra memory.
//
//Here are some examples. Inputs are in the left-hand column and its
//corresponding outputs are in the right-hand column.
//1,2,3 ¡ú 1,3,2
//3,2,1 ¡ú 1,2,3
//1,1,5 ¡ú 1,5,1
//
//Complexity
//O(n) time
//============================================================================


public class NextPermutation {
	
	public void nextPermutation(int[] num){
		//find first element break the descending order
		int n = num.length;
		int i;
		for(i=n-1;i>0;i--)
		{
			if(num[i]>num[i-1])
				break;//15293->15329->15392->15923->15932->19235
		}
		if(i==0){
			Arrays.sort(num);
			return;
		}
		//sort the num[i:end]
		Arrays.sort(num,i,n);
		//find first greater than num[i-1],swap them
		int t = i-1;
		for(;i<n;i++)
		{
			if(num[i]>num[t])
				break;
		}
		int tmp=num[t];
		num[t]=num[i];
		num[i]=tmp;
		
		for(int j:num)
		{
			System.out.print(j);
		}
	}

	public static void main(String args[])
	{
		NextPermutation solution = new NextPermutation();
		int[] num={1,3,2};
		solution.nextPermutation(num);
		
	}
	
}
