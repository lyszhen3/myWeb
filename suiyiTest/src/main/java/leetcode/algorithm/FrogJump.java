package leetcode.algorithm;

/**
 * Created by lys on 2022/9/30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 * 一只青蛙一次可以条1或2个台阶
 * 跳n阶的台阶总共多少总可能
 */
public class FrogJump {

	public static void main(String[] args) {
		System.out.println(jump(7));

		System.out.println(jump01(7));
	}

	public static int jump(int n) {

		if (n <= 0) {
			return 1;
		}

		if (n == 1) {
			return jump(n - 1);
		} else {
			return jump(n - 1) + jump(n - 2);
		}
	}

	/**
	 * 动态规划怎么做？
	 * f(n)= f(n-1) + f(n-2)
	 *
	 * @param n
	 * @return
	 */
	public static int jump01(int n) {

		if (n == 0) {
			return n;
		}

		int n_1 = 0;
		int n_2 = 0;
		int t = 0;
		for (int i = 1; i <= n; i++) {
			if (i == 1) {
				t = 1;
				n_1 = 1;
				continue;
			}
			if (i == 2) {
				n_2 = 1;
				t = n_1 + n_2;
				n_1 = t;
				continue;
			}
			t = n_1 + n_2;
			n_2 = n_1;
			n_1 = t;
		}
		return t;
	}
}
