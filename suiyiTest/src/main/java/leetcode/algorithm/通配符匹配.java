package leetcode.algorithm;

/**
 * 给你一个输入字符串 ( s) 和一个字符模式 ( p) ，请你实现一个支持 '?' 和 '*' 匹配规则的通配符匹配：
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符序列（包括空字符序列）。
 * 判定匹配成功的充要条件是：字符模式必须能够 完全匹配 输入字符串（而不是部分匹配）。
 *
 * @author LinYuanSheng
 * @date 2024/11/14
 */
public class 通配符匹配 {

	public static void main(String[] args) {

		通配符匹配 p = new 通配符匹配();
		String s = "adceb";
		String ps = "*a*b";
		final boolean match = p.isMatch(s, ps);
		System.out.println(match);

	}

	/**
	 * i=字符模式下标，j=字符串下标
	 * {dp[i,j]= dp[i-1,j-1] & p[i]==s[j] | s[i] != ?,* }
	 * {dp[i,j]= dp[i,j-1]||dp[i-1,j-1] & s[j] 存在 | p[i] == ?,* }， p[i]== *时 遍历所有s
	 *
	 * @param s
	 * @param p
	 * @return
	 */
	public boolean isMatch(String s, String p) {

		if (s == null) {
			return false;
		}
		if (p == null) {
			return false;
		}

		if (s.equals(p)) {
			return true;
		}

		final int pL = p.length();

		if (pL == 0) {
			return false;
		}

		final int sL = s.length();

		boolean hasXing = false;
		//把s.length =0 加进来, 空串
		boolean[][] dp = new boolean[pL][sL];

		int si = 0;
		boolean emptyMatch = true;
		for (int i = 0; i < pL; i++) {

			final char pc = p.charAt(i);

			if (sL == 0) {
				if (pc != '*') {
					emptyMatch = false;
				}
				continue;
			}
			if (si == sL) {
				if (pc == '*') {
					dp[i][sL - 1] = dp[i - 1][sL - 1];
				}
				continue;
			}
			for (int j = si; j < sL; j++) {

				if (pc == '*') {
					hasXing = true;

					if (i == 0) {
						dp[i][j] = true;
					} else {
						dp[i][j - 1] = dp[i - 1][j-1];
						dp[i][j] = dp[i - 1][j] || dp[i - 1][j - 1];
					}
				} else if (pc == '?') {
					if (i == 0) {
						dp[i][j] = true;
					} else {
						if (j == 0) {
							dp[i][j] = dp[i - 1][j];
						} else {
							dp[i][j] = dp[i - 1][j - 1];
						}
					}
				} else {

					final char sc = s.charAt(j);
					if (i == 0) {
						dp[i][j] = (pc == sc);
					} else {
						if (j == 0) {
							dp[i][j] = dp[i - 1][j] && (pc == sc);
						} else {
							dp[i][j] = dp[i - 1][j - 1] && (pc == sc);
						}
					}
				}

				if (!hasXing) {
					break;
				}
			}
			if (pc != '*') {
				si++;
			}
		}
		if (sL == 0) {
			return emptyMatch;
		}
		return dp[pL - 1][sL - 1];
	}
}
