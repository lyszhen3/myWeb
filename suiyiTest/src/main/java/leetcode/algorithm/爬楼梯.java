package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2024/10/16
 */
public class 爬楼梯 {

	public static void main(String[] args) {
		final int climb = climb(1);
		System.out.println(climb);
	}

	/**
	 *
	 * 一次爬1阶梯或者2阶梯
	 * @param n 阶梯树
	 * @return 多少总爬法
	 */
	public static int climb(int n) {
		//dp[i] = dp[i-1] + dp[i-2]

		int pre1 = 0;
		int pre2 = 0;
		int t = 0;
		for (int i = 1; i <= n; i++) {
			if (i == 1) {
				pre1 =1;
			}
			t= pre1 + pre2;

			pre2 = pre1;
			pre1 = t;
		}

		return t;

	}
}
