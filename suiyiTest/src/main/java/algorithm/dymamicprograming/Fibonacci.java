package algorithm.dymamicprograming;

/**
 * Created by lys on 2022/12/4.
 * 一个列子,斐波那契列 的递归及动态规划实现
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Fibonacci {
	public static void main(String[] args) {
		final int fib = fib(10);
		System.out.println(fib);
		final int i = fib_2(10);
		System.out.println("fib_2:" + i);

	}

	public static int fib(int n) {
		if (n <= 1) {
			return n;
		}
		return fib(n - 1) + fib(n - 2);
	}

	public static int fib_2(int n) {
		if (n <= 1) {
			return n;
		}

		int last_1 = 0;
		int last_2 = 0;
		int temp = 0;
		for (int i = 0; i < n; i++) {
			if (i == 0) {
				last_2 = 1;
			} else if(i == 1) {
				last_1 = 1;
			} else {
				temp = last_2;
				last_2 = last_2 + last_1;
				last_1 = temp;
			}
		}
		return last_2;
	}
}
