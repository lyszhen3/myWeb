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
		String ps = "?a";
		final boolean match = p.isMatch(s, ps);
		System.out.println(match);

	}

	public boolean isMatch(String s, String p) {


		if (s == null || s.isEmpty()) {
			return false;
		}
		if (p == null || p.isEmpty()) {
			return false;
		}

		final int pL = p.length();
		final int sL = s.length();

		int pI = 0;
		for (int i = 0; i < sL; i++) {
			final char c = p.charAt(i);

		}
		return false;
	}
}
