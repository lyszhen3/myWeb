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

		String i ="()()()()";
		final int i1 = zui.longestValidParentheses(i);
		System.out.println(i1);
	}

	/**
	 * i 表示有效括号的下标
	 * s[i] = ")" 时, 括号才有效
	 *
	 * s[i-1]="(" 时 dp[i] = dp[i-2] + 2;
	 *
	 * s[i-1]=")" 时 如果 s[i - dp[i-2] -1] = "(" 时 dp[i] = dp[i-1] + dp[i - dp[i-1] -2] + 2
	 * @param s
	 * @return
	 */
	public int longestValidParentheses(String s) {

		if (s == null || s.isEmpty()) {
			return 0;
		}

		final int length = s.length();

		int index = 0;
		while (index < length) {



		}
		return 0;
	}
}
