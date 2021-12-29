package leetcode.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LinYuanSheng
 * @date 2021/12/28
 * 全排序数组
 * e.g {1,2}
 * {1,2}, {2,1}
 */
public class AllSortArray {
	public static void main(String[] args) {

		int[] aa = {1,2,3,4,5};
		final List<int[]> ints = allSort(aa);
		for (int[] anInt : ints) {
			for (int i : anInt) {
				System.out.print(i);
				System.out.print("->");
			}
			System.out.println();
		}
	}

	public static List<int[]> allSort(int[] array) {
		final int length = array.length;
		List<int[]> list = new ArrayList<>();
		int[] outArray = new int[length];
		insert(length, outArray, array, list, 0);
		return list;
	}

	public static void insert(int remain, int[] array, int[] originArray, List<int[]> list, int startIdex) {
		if (remain == 0) {
			return;
		}
		for (int i = 0; i < originArray.length; i++) {
			final int[] newArray = Arrays.copyOf(array, array.length);
			newArray[startIdex] = originArray[i];
			int[] newOrigin = removeIndex(originArray, i);
			if (remain == 1) {
				list.add(newArray);
			}
			insert(remain - 1, newArray, newOrigin, list, startIdex + 1);
		}
	}

	private static int[] removeIndex(int[] originArray, int removeIndex) {
		if (originArray.length == 0) {
			return new int[0];
		}
		int[] newInt = new int[originArray.length - 1];
		for (int i = 0, j = 0; i < originArray.length; i++) {
			if (i == removeIndex) {
				continue;
			}
			newInt[j] = originArray[i];
			j++;
		}

		return newInt;
	}
}
