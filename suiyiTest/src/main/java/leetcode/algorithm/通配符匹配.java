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
		String s = "aa";
		String ps = "*";
		final boolean match = p.isMatch(s, ps);
		System.out.println(match);

	}

	/**
	 * 用字符串便利字符模式, 因为字符模式比较短
	 * i=字符串下标,j=字符模式下标，
	 * {dp[i,j]= dp[i-1,j-1] & p[i]==s[j] | s[i] != ?,* }
	 * {dp[i,j]= dp[i,j-1]||dp[i,j-1] & s[j] 存在 | p[i] == * }
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

		final int sl = s.length();
		final int pl = p.length();

		//这里+1 是因为有空串的存在
		boolean[][] dp = new boolean[sl + 1][pl + 1];

		dp[0][0] = true;

		for (int i = 1; i < pl + 1; i++) {
			if (p.charAt(i -1) == '*') {
				dp[0][i] = true;
			} else {
				break;
			}
		}
		//因为字符模式比较短, 所以用字符串遍历字符模式
		for (int i = 1; i < sl + 1; i++) {

			final char sc = s.charAt(i - 1);
			for (int j = 1; j < pl + 1; j++) {

				final char pc = p.charAt(j - 1);

				if (pc == '*') {
					dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
				} else if (pc == '?' || pc == sc) {
					dp[i][j] = dp[i - 1][j - 1];
				}
			}
		}
		return dp[sl][pl];

	}
}
