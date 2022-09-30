package leetcode;

/**
 * Created by lys on 2022/9/30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 * 一只青蛙一次可以条1或2个台阶
 * 跳n阶的台阶总共多少总可能
 */
public class  FrogJump{


	public static void main(String[] args) {
		System.out.println(jump(7));
	}

	public static int jump(int n) {

		if (n <=0) {
			return 1;
		}

		if (n == 1) {
			 return jump(n - 1);
		} else {
			 return jump(n-1) + jump(n - 2);
		}
	}
}
