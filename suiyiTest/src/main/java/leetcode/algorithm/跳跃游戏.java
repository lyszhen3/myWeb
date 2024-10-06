package leetcode.algorithm;

/**
 * Created by lys on 2024-10-01.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class 跳跃游戏 {

	public static void main(String[] args) {

		int[] nums =
				{2, 0, 0};

		final boolean b = canJump(nums);

		System.out.println(b);
	}

	public static boolean canJump(int[] nums) {


		// f(i) = Math.max(f(i-1) -1, nums[i])

		if (nums.length == 1) {
			return true;
		}

		int fi = 0;
		for (int i = 0; i < nums.length -1; i++) {

			fi = Math.max(fi -1, nums[i]);

			if (fi == 0 ) {
				return false;
			}

		}

		return true;

	}

}
