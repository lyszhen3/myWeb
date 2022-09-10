package algorithm.sort;

/**
 * @author LinYuanSheng
 * @date 2022/8/30
 * 归并排序
 * 分治,排序
 * 3,4,6,7,|2,1,5,8
 */
public class MergeSort {

	private static int[] merge(int[] a, int s, int e) {

		if (s >= e) {
			final int[] ints = new int[1];
			ints[0] = a[s];
			return ints;
		}
		// 傻逼.
		//一直二分拆开,然后排序,然后合并
		int m = (s + e) / 2;
		int[] merge_s = merge(a, s, m);
		int[] merge_e = merge(a, m + 1, e);
		return sort(merge_s, merge_e);
	}

	private static int[] sort(int[] merge_s, int[] merge_e) {

		int index_i = 0;
		int index_j = 0;
		int[] f = new int[merge_s.length + merge_e.length];

		int index = 0;
		while (index < f.length) {
			if (index_i > merge_s.length - 1) {
				f[index++] = merge_e[index_j++];
				continue;
			}
			if (index_j > merge_e.length - 1) {
				f[index++] = merge_s[index_i++];
				continue;
			}
			if (merge_s[index_i] <= merge_e[index_j]) {
				f[index++] = merge_s[index_i++];
			} else {
				f[index++] = merge_e[index_j++];
			}
		}
		return f;
	}

	public static void main(String[] args) {
		int[] a = {3, 4, 6, 7, 2, 1, 5, 8};
		final int[] merge = merge(a, 0, a.length - 1);
		for (int i = 0; i < merge.length; i++) {
			System.out.print(merge[i] + ",");
		}
	}

}
