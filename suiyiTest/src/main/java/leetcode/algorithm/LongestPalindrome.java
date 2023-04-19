package leetcode.algorithm;

/**
 * Created by lys on 4/24/2019.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 * 最长回文串
 * aabacd  最长回文是 aba
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

	/**
	 * 存在一个回文 aba 那么 左边-1=右边 +1 那么也是个回文,例如 cabac
	 * 我们将a[l][r]=true表示回文保存起来,如此依赖a[l-1][r+1]也能轻松确定是否回文
	 *
	 * @param s
	 * @return
	 */
	public String longestPalindrome_2(String s) {

		if (s == null) {
			return null;
		}
		if (s.length() == 1) {
			return s;
		}
		boolean[][] pails = new boolean[s.length()][s.length()];

		//先把i==j的存储起来和 i,i+1存起来
		for (int i = 0; i < s.length(); i++) {
			pails[i][i] = true;
			if (i < s.length() -1) {
				if (s.charAt(i) == s.charAt(i +1)) {
					pails[i][i+1] = true;
				}
			}
		}
		//假设从2开始
		int maxL = 0;
		//最终左边坐标
		int fL = 0;
		//最终右边坐标
		int fR = 0;
		for (int i = 0; i < s.length()-2; i++) {

			for (int j = i + 2; j < s.length(); j++) {
				if (s.charAt(i) == s.charAt(j) && pails[i + 1][j - 1]) {
					pails[i][j] = true;
					if (j-i > maxL) {
						maxL = j - i;
						fL = i;
						fR = j;
					}
				}else {
					pails[i][j] =false;
				}
			}
		}

		return s.substring(fL,fR +1);
	}

	public static void main(String[] args) {
		LongestPalindrome longestPalindrome = new LongestPalindrome();
		final String result = longestPalindrome.longestPalindrome("aaabcbadd");
		final String result2 = longestPalindrome.longestPalindrome_2("aaabcbadd");
		System.out.println(result);
		System.out.println(result2);
	}
}
