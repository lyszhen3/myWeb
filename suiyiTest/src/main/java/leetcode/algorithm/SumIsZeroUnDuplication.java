package leetcode.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 输出数组3个元素相加为0的所有可能
 * {-1,1,0,-3,1,2}
 * 不重复..
 * 输出[{-1,1,0},{-3,1,2}]
 */
public class SumIsZeroUnDuplication {

	public static void main(String[] args) {

		int[] array={-1,1,0,-3,1,2};
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
		for (int i = 0; i < array.length -2; i++) {
			for (int j = i + 1; j < array.length-1; j++) {

				for (int x = j +1 ; x < array.length; x++) {
					if (array[i] + array[j] + array[x] == 0) {
						int[] insert = {array[i], array[j], array[x]};
						out.add(insert);
					}
				}
			}
		}

		return out;
	}

}
