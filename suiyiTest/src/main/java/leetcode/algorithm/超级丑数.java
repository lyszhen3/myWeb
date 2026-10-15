package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2026/10/13
 * <br>
 * <a href="https://leetcode.cn/problems/super-ugly-number/description/">超级丑数</a>
 */
public class 超级丑数 {

	public int nthSuperUglyNumber(int n, int[] primes) {

		int[] dp = new int[n];
		final int l = primes.length;
		//指针数组，表示primes数组里的质因数×到第几个数的指针
		int[] pointer = new int[l];
		//第一个丑数一定是0
		dp[0] = 1;

		for (int i = 1; i < n; i++) {
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < l; j++) {
				if (dp[pointer[j]] * primes[j] > 0) {
					min = Math.min(dp[pointer[j]] * primes[j], min);
				}
			}
			for (int j = 0; j < l; j++) {
				if (min == dp[pointer[j]] * primes[j]) {
					pointer[j] = pointer[j] + 1;
				}
			}
			dp[i] = min;
		}
		return dp[n - 1];
	}
}
