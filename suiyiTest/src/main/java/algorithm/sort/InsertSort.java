package algorithm.sort;

/**
 * Created by lys on 2022/8/28.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 * 插入排序
 * 2,3,6,5,4,1
 */
public class InsertSort {

	public static void sort(int[] a) {

		for (int i = 0; i < a.length; i++) {
			int v = a[i];

			int j = i - 1;
			for (; j >= 0; j--) {
				if (a[j] > v) {
					a[j + 1] = a[j];
				} else {
					break;
				}
			}
			if (j < i -1) {
				//位置发生过变化
				a[j +1] = v;
			}

		}
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
	}

	public static void main(String[] args) {
		int[] a = {2,3,6,5,4,1,7,9,10,18,12};
		sort(a);
	}
}
