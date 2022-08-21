package leetcode.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lys on 2019/4/20.
 * <p>
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class LengthOfLongestSubString {

	public static void main(String[] args) {
		LengthOfLongestSubString lengthOfLongestSubString = new LengthOfLongestSubString();
		int abcabcbb = lengthOfLongestSubString.lengthOfLongestSubString("abcabcbbcdefg");
		int abcabcbbcdefg = lengthOfLongestSubString.lengthOfLongestSubString_2("abcabcbbcdefg");
		System.out.println(abcabcbb);
		System.out.println(abcabcbbcdefg);
	}

	public int lengthOfLongestSubString_2(String s) {
		int n = s.length();
		int ml = 0, i = 0, j = 0;
		Set<Character> set = new HashSet<>();
		while (i < n && j < n) {
			if(!set.contains(s.charAt(j))){
				set.add(s.charAt(j++));
				ml = Math.max(ml,j-i);
			}else{
				set.remove(s.charAt(i++));
			}
		}

		return ml;
	}

	/**
	 * 暴力破解
	 * 输入: "abcabcbbcdefg"
	 * 输出: 3
	 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
	 *
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubString(String s) {

		int ml = 0;
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 1; j <= s.length(); j++) {
				if (unique(s, i, j)) {
					ml = Math.max(ml, j - i);
				} else {
					break;
				}
			}
		}

		return ml;
	}

	public boolean unique(String s, int start, int end) {
		Set<Character> set = new HashSet<>();
		for (int i = start; i < end; i++) {
			char c = s.charAt(i);
			if (set.contains(c)) {
				return false;
			}
			set.add(c);
		}
		return true;
	}
}
