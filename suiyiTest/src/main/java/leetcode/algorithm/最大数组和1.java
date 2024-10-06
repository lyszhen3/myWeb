package leetcode.algorithm;

/**
 * Created by lys on 2024-10-04.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class 最大数组和1 {
	public static void main(String[] args) {
		int[] nums = {-1};

		final int max = max(nums);
		System.out.println(max);
	}


	public static int max (int[] nums) {

		if (nums.length == 0) {
			return nums[0];
		}
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			int t = 0;
			for (int j = i; j < nums.length; j++) {
				t  = t + nums[j];
				max = Math.max(max, t);
			}
		}

		return max;
	}
}
