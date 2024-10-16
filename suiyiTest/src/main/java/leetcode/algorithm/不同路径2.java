package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2024/10/11
 */
public class 不同路径2 {
	public static void main(String[] args) {



		int[][]  a = {{1,0}};

		final int i = path2(a);
		System.out.println(i);

	}

	public static int path2(int[][] obstacleGrid) {


		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		int[][] dp = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (obstacleGrid[i][j] == 1) {
					dp[i][j] = 0;
					continue;
				}
				if (i == 0 && j == 0) {
					dp[i][j] = 1;
					continue;
				}

				if (i == 0) {
					dp[i][j] = dp[i][j - 1];
					continue;
				}

				if (j == 0) {
					dp[i][j] = dp[i -1][j];
					continue;
				}

				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}

		return dp[m - 1][n - 1];

	}
}
