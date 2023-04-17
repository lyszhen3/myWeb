package leetcode.algorithm;

/**
 * Created by lys on 4/24/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class LongestPalindrome {

	public String longestPalindrome(String s) {
		if (s == null || "".equals(s)) {
			return "";
		}

		int start = 0, end = 0;
		for (int i = 0; i < s.length(); i++) {
			//奇数个回文 例如aba
			int len1 = expandAroundCenter(s, i, i);
			//偶数个会问 例如 abba
			int len2 = expandAroundCenter(s, i, i + 1);
			//比较奇数个回文和偶数个的大小
			int len = Math.max(len1, len2);
			if (len > end - start) {
				start = i - (len - 1) / 2;
				end = i + len / 2;
			}
		}
		return s.substring(start, end + 1);

	}

	private int expandAroundCenter(String s, int left, int right) {
		int L = left, R = right;
		while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
			L--;
			R++;
		}
		return R - L - 1;
	}
	//todo lys 如果用动态规划会怎么做呢?

	public static void main(String[] args) {
		LongestPalindrome longestPalindrome = new LongestPalindrome();
		final String result = longestPalindrome.longestPalindrome("aaabcbadd");
		System.out.println(result);
	}
}
