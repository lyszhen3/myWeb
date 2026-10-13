package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2026/10/13
 */
public class 最长递增子序列 {

	public int lengthOfLIS(int[] nums) {

		int l = nums.length;

		int[] dp = new int[l];

		dp[0] = 1;
		int max = 0;
		for (int i = 1; i < l; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {

				if (nums[i] > nums[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			max = Math.max(max, dp[i]);
		}

		return max;
	}
}
