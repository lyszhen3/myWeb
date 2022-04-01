package leetcode.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 三数之和
 * 输出数组3个元素相加为0的所有可能
 * {-1,1,0,-3,1,2}
 * 不重复..
 * 输出[{-1,1,0},{-3,1,2}]
 */
public class ThreeNumSum {

	public static void main(String[] args) {

		int[] array = {-1, 1, 0, -3, 1, 2};
		final List<int[]> solve = solve(array);

		for (int[] anInt : solve) {
			for (int i : anInt) {
				System.out.print(i);
				System.out.print("->");
			}
			System.out.println();
		}

	}

	public static List<int[]> solve(int[] array) {

		List<int[]> out = new ArrayList<>();
		for (int i = 0; i < array.length - 2; i++) {
			for (int j = i + 1; j < array.length - 1; j++) {

				for (int x = j + 1; x < array.length; x++) {
					if (array[i] + array[j] + array[x] == 0) {
						int[] insert = {array[i], array[j], array[x]};

						//遍历已有的,检验是否村子啊
						if (!exist(insert, out)) {

							out.add(insert);
						}
					}
				}
			}
		}

		return out;
	}

	private static boolean exist(int[] insert, List<int[]> out) {

		for (int[] ints : out) {
			int exist = 0;

			int[] existIndex = new int[3];
			for (int i : insert) {
				for (int i1 = 0; i1 < ints.length; i1++) {
					if (includeInExistIndex(i1, existIndex)) {
						continue;
					}
					if (i == ints[i1] ) {
						exist ++;
						if (exist >= 2) {
							return true;
						}
					}
				}
			}

		}

		return false;
	}

	private static boolean includeInExistIndex(int i1, int[] existIndex) {
		for (int index : existIndex) {
			if (i1 == index) {
				return true;
			}
		}
		return false;
	}

}
