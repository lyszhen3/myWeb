package leetcode.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lys on 2023/2/19.
 * 括号的几种可能
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 *
 * 输入 3
 * 输出
 * [((())), ()()().(()()),()(())]
 *
 */
public class Brackets {

	public static void main(String[] args) {

		int n  = 4;

		final List<String> kh = kh(n);

		System.out.println("回溯算法:" + kh);

		final List<String> strings = generateBrace(n);
		System.out.println("动态规划:" + strings);

	}

	/**
	 * 回溯算法
	 * @param num
	 * @return
	 */
	public static List<String> kh( int num) {

		List<String> result = new ArrayList<>();
		tc("", 0, 0, 1, num * 2, result);

		return result;
	}

	public static void tc(String bras, int right, int rightNum, int index, int total, List<String> list) {
		if (index > total) {
			list.add(bras);
			return;
		}
		if (index == 1) {
			tc(bras + "(", ++right, ++rightNum, ++index, total, list);
			return;
		}
		if (index == total) {
			tc(bras + ")", --right, rightNum, ++index, total, list);
			return;
		}
		if (rightNum >= (total / 2)) {
			tc(bras + ")", --right, rightNum, ++index, total, list);
			return;
		}
		if (right <= 0) {
			tc(bras + "(", ++right, ++rightNum, ++index, total, list);
			return;
		}

		int rrn = rightNum + 1;
		int rr = right + 1;
		int ri = index + 1;
		tc(bras + "(", rr, rrn, ri, total, list);
		tc(bras + ")", --right, rightNum, ++index, total, list);

	}


	/**
	 * 动态规划方案
	 *
	 * @param n
	 * @return 如果要求所有合法的括号集合
	 * ({p}) {q}
	 * {p}表示n=p时括号的集合, {q} 表示n=q时括号的集合
	 * p + q = n-1
	 */
	public static List<String> generateBrace(int n) {
		//搞个集合记录之前的产生的数据
		List<List<String>> result = new ArrayList<>();

		if (n == 0) {
			return new ArrayList<>();
		}
		List<String> list0 = new ArrayList<>();
		list0.add("");
		result.add(list0);

		List<String> list1 = new ArrayList<>();
		list1.add("()");
		result.add(list1);

		for (int i = 2; i <= n; i++) {
			//生成填充result结果

			List<String> temp = new ArrayList<>();
			for (int p = 0; p < i; p++) {
				final List<String> pCollection = result.get(p);
				final List<String> qCollection = result.get(i - 1 - p);
				for (String pStr : pCollection) {
					for (String qStr : qCollection) {
						String newStr = "(" +pStr +")" +qStr;
						temp.add(newStr);
					}
				}

			}
			result.add(temp);
		}
		return result.get(n);
	}
}
