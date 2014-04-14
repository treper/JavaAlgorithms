package ataosky.careercup.sort;

//different sort algorithms
public class Sort {
	public void swap(int[] array, int l, int r) {
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
				swap(array, i, j);

			}
		}
		swap(array, i + 1, r);
		return i + 1;

	}

	public void qsort(int[] array, int l, int r) {
		if (l < r) {
			int mid = partition(array, l, r);
			qsort(array, l, mid - 1);
			qsort(array, mid + 1, r);
		}
	}

	public static void main(String args[]) {
		Sort solution = new Sort();
		int[] a={3,2,6,4,1,7,0,9};
		solution.qsort(a,0,a.length-1);
		for(int i:a){
		System.out.print(i);
		}

	}
}
