package algorithm;

/**
 * Created by lys on 2022/4/3.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SortTest {

	public static void main(String[] args) {
		int[] a = {22, 23, 1, 2, 3, 6, 88, 9, 10, 20, 21, 22, 99,199,100,101};

		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] > a[j]) {
					a[i] ^= a[j];
					//a[j] 变成a[i];
					a[j] ^= a[i];
					a[i] ^= a[j];

				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
	}
}
