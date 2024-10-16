package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2024/10/12
 */
public class 最小路径和 {

	public static void main(String[] args) {

		int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
		final int i = minPathSum(grid);
		System.out.println(i);
	}

	public static int minPathSum(int[][] grid) {

		int m = grid.length;
		int n = grid[0].length;
		int[][] dp = new int[m][n];


		//dp[i][j] = Math.min(dp[i][j-1] + grid[i][j], dp[i-1][j] + grid[i][j])
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {

				if (i == 0 && j == 0) {
					dp[i][j] = grid[i][j];
					continue;
				}

				if (i == 0) {
					dp[i][j] = dp[i][j - 1] + grid[i][j];
					continue;
				}

				if (j == 0) {
					dp[i][j] = dp[i - 1][j] + grid[i][j];
					continue;
				}

				int min1 = dp[i - 1][j] + grid[i][j];
				int min2 = dp[i][j - 1] + grid[i][j];
				dp[i][j] = Math.min(min1, min2);
			}
		}

		return dp[m - 1][n - 1];
	}
}
