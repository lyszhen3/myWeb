package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2021/12/24
 * [0,1,2,3,4,5,9]
 * 旋转后
 * [3,4,5,9,0,1,2]
 * <p>
 * 找出其中一个目标数
 * 要求O(logn),二分啦
 */
public class FindRotateArrayTarget {

	/**
	 * 就是二分查找呀
	 *
	 * @param arrays 数组
	 * @param target 目标
	 * @return int 下标
	 */
	public static int binarySearch(int[] arrays, int target) {
		//最大下标
		final int maxIndex = arrays.length - 1;

		//最右边的数
		final int right = arrays[maxIndex];

		int leftIndex = 0;
		int rightIndex = maxIndex;
		int binaryIndex = (leftIndex + rightIndex) / 2;

		while (binaryIndex >= leftIndex && binaryIndex <= rightIndex) {
			final int binary = arrays[binaryIndex];
			if (target == binary) {
				return binaryIndex;
			}

			if (target == right) {
				return maxIndex;
			}
			if (binaryIndex == leftIndex || binaryIndex == rightIndex) {
				return -1;
			}

			//如果目标大于right 且中分数大于right 。此时目标大于中分数往右查找，目标小于中分数，往左

			//如果目标大于right 且中分数小于right。 此时目标大于中分数往左查找，

			//如果目标小于right 且中分数大于right。  目标小于中分数,往右
			//如果目标小于right 且中分数小于right  目标大于中分数，往右， 目标小于中分数往左
			if (target > right && binary > right) {
				if (target > binary) {
					leftIndex = binaryIndex;
					binaryIndex = (binaryIndex + rightIndex) / 2;
				} else {
					rightIndex = binaryIndex;
					binaryIndex = (leftIndex + binaryIndex) / 2;
				}
			} else if (target > right && binary < right) {
				rightIndex = binaryIndex;
				binaryIndex = (leftIndex + binaryIndex) / 2;
			} else if (target < right && binary > right) {
				leftIndex = binaryIndex;
				binaryIndex = (binaryIndex + rightIndex) / 2;
			} else if (target < right && binary < right) {
				if (target > binary) {
					leftIndex = binaryIndex;
					binaryIndex = (binaryIndex + rightIndex) / 2;
				} else {
					rightIndex = binaryIndex;
					binaryIndex = (leftIndex + binaryIndex) / 2;
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {

		int[] array = {5, 8, 9, 11, 1, 2, 3, 4};
		int target = 5;
		final int i = binarySearch(array, target);
		System.out.println(i);
	}
}
