package leetcode.algorithm;

/**
 * Created by lys on 4/23/2019.
 * 删除数组重复
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class RemoveDuplicates {
	public static int removeDuplicates(int[] nums) {

		if (nums.length == 0) {
			return 0;
		}
		int j = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[j]) {
				j++;
				nums[j] = nums[i];
			}

		}

		return j + 1;
	}

	public static void main(String[] args) {
		int[] array = {0, 1, 1, 1, 1, 2, 2, 2};
		int i = removeDuplicates(array);
		System.out.println(i);
	}
}
