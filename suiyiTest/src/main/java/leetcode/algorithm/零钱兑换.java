package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2026/10/15
 */
public class 零钱兑换 {
	public int coinChange(int[] coins, int amount) {

		int[] dp = new int[amount + 1];

		if (amount == 0){
			return 0;
		}
		for (int j = 0; j < coins.length; j++) {
			if (coins[j] <= amount) {
				dp[coins[j]] = 1;
			}
		}
		for (int i = 1; i < dp.length; i++) {
			if (dp[i] == 0) {
				continue;
			}
			for (int j = 0; j < coins.length; j++) {
				int add = coins[j];
				if (i + add > amount || i + add <0) {
					//如果超出总面额， 也别搞了
					continue;
				}
				if (dp[i + add] == 0) {
					dp[i + add] = dp[i] + 1;
					continue;
				}
				if (dp[i + add] > 0) {
					dp[i + add] = Math.min(dp[i + add], dp[i] + 1);
				}
			}
		}
		if (dp[amount] == 0) {
			//表示凑不出
			return -1;
		}
		return dp[amount];
	}
}
