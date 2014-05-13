package ataosky.careercup.sort;

import java.util.ArrayList;
import java.util.Arrays;

//different sort algorithms
public class Sort {
	public void swap(int[] array, int l, int r) {
		if(l==r)return;
		int tmp = array[l];
		array[l] = array[r];
		array[r] = tmp;

	}

	public int partition(int[] array, int l, int r) {
		int x = array[r];
		int i = l - 1;
		for (int j = l; j < r; j++) {
			if (array[j] < x) {
				i++;
				//System.out.println("swap "+array[i]+" "+array[j]);
				swap(array, i, j);
				
//				for(int k:array)
//				System.out.print(k+" ");
//				System.out.println();

			}
			//System.out.println();
		}
		swap(array, i + 1, r);
//		System.out.println("special swap "+array[i+1]+" "+array[r]);
		return i + 1;

	}

	public void qsort(int[] array, int l, int r) {
		if (l < r) {
			int mid = partition(array, l, r);
			System.out.println("sort "+l+" mid "+mid+" to "+r);
			qsort(array, l, mid - 1);
			qsort(array, mid + 1, r);
		}
	}

	public static void main(String args[]) {
		Sort solution = new Sort();
		int[] a={3,2,6,4,1,7,0,9,5};
		solution.qsort(a,0,a.length-1);
		for(int i:a){
		System.out.print(i);
		}

	}
}
