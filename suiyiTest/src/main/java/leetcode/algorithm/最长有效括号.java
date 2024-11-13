package leetcode.algorithm;

/**
 * Created by lys on 2024-11-12.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class 最长有效括号 {

	public static void main(String[] args) {

		最长有效括号 zui = new 最长有效括号();

		String i = "())";
		final int i1 = zui.longestValidParentheses(i);
		System.out.println(i1);
	}

	/**
	 * i 表示有效括号的下标
	 * s[i] = ")" 时, 括号才有效
	 * <p>
	 * s[i-1]="(" 时 dp[i] = dp[i-2] + 2;
	 * <p>
	 * s[i-1]=")" 时 如果 s[i - dp[i-1] -1] = "(" 时 dp[i] = dp[i-1] + dp[i - dp[i-1] -2] + 2
	 *
	 * @param s
	 * @return
	 */
	public int longestValidParentheses(String s) {

		if (s == null || s.isEmpty()) {
			return 0;
		}

		final int length = s.length();

		int[] dp = new int[length];
		int index = 0;
		int max = 0;
		while (index < length) {
			final char ee = s.charAt(index);
			if (')' == ee) {
				if (index > 0) {
					final char ss = s.charAt(index - 1);
					if ('(' == ss) {
						if (index > 1) {
							dp[index] = dp[index - 2] + 2;
						} else {
							dp[index] = 2;
						}
					}
					if (')' == ss) {
						if (index > 1) {
							if (index - dp[index - 1] > 0) {

								final char pss = s.charAt(index - dp[index - 1] - 1);
								if ('(' == pss) {
									if (index - dp[index - 1] > 2) {
										dp[index] = dp[index - 1] + dp[index - dp[index - 1] - 2] + 2;
									} else {
										dp[index] = dp[index - 1] + 2;
									}
								}
							}
						}
					}
				}
			}
			max = Math.max(max, dp[index]);

			index++;
		}
		return max;
	}
}
