package leetcode.algorithm;


/**
 * Created by lys on 2024-10-06.
 * <p>
 * https://leetcode.cn/problems/unique-paths/
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class 不同路径 {

	public static void main(String[] args) {
		final int i = uniquePaths(4, 3);
		System.out.println(i);

	}

	public static int uniquePaths(int m, int n) {

		//dp[x,y] = dp[x-1,y] + dp[x,y-1]

		int[][] dp = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 && j == 0) {
					dp[i][j] = 1;
					continue;
				}

				if (i == 0) {
					dp[i][j] = dp[i][j - 1];
					continue;
				}

				if (j == 0) {
					dp[i][j] = dp[i - 1][j];
					continue;
				}
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}

		return dp[m - 1][n - 1];

	}

}
