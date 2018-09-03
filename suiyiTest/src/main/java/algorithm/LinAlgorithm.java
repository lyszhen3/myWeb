package algorithm;

/**
 * Created by lys on 2018/9/1.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class LinAlgorithm {


	public static int maxSubSum1(int[] a) {

		int maxSum = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i; j < a.length; j++) {

				int thisSum = 0;
				for (int k = i; k <= j; k++) {
					thisSum += a[k];

					if (thisSum > maxSum) {
						maxSum = thisSum;
					}
				}
			}
		}
		return maxSum;
	}

	public static int maxSubSum2(int[] a) {
		int maxSum = 0;
		for (int i = 0; i < a.length; i++) {
			int thisSum = 0;
			for (int j = i; j < a.length; j++) {
				thisSum += a[j];
				if (thisSum > maxSum) {
					maxSum = thisSum;
				}
			}

		}
		return maxSum;
	}

	private static int maxSubRec(int[] a, int left, int right) {

		if (left == right) {
			if (a[left] > 0) {
				return a[left];
			} else {
				return 0;
			}
		}
		int center = (left + right) / 2;
		System.out.println("maxleft  time:" + "[left:" + left + " right:" + center + "]");
		int maxLeftSum = maxSubRec(a, left, center);
		System.out.println("maxLeftSum:" + maxLeftSum);
		System.out.println("maxright time:" + "[left:" + (center + 1) + " right:" + right + "]");
		int maxRightSum = maxSubRec(a, center + 1, right);
		System.out.println("maxRightSum:" + maxRightSum);
		int maxLeftBordSum = 0, leftBordSum = 0;
		for (int i = center; i >= left; i--) {
			leftBordSum += a[i];
			if (leftBordSum > maxLeftBordSum) {
				maxLeftBordSum = leftBordSum;
			}
		}
		int maxRightBorderSum = 0, rightBorderSum = 0;
		for (int i = center + 1; i <= right; i++) {
			rightBorderSum += a[i];
			if (rightBorderSum > maxRightBorderSum) {
				maxRightBorderSum = rightBorderSum;
			}
		}


		System.out.println("time2:" + "left:" + left + " right:" + right + " center:" + center + " maxLeftBordSum:" + maxLeftBordSum + " maxRightBordSum:" + maxRightBorderSum);
		return max3(maxLeftSum, maxRightSum, maxLeftBordSum + maxRightBorderSum);
	}

	public static int maxSubSum3(int[] a) {
		return maxSubRec(a, 0, a.length - 1);
	}

	private static int max3(int a, int b, int c) {

		if (a < b) {
			a = b;
		}
		if (a >= c) {
			return a;
		} else {
			return c;
		}

	}

	public static void main(String[] args) {
		int[] a = {4, -3, 5, -2, -1, 2, 6, -2};
		int i = maxSubSum3(a);
		System.out.println(i);
		System.out.println(pow(2,30));
	}


	public static int pow(int r, int e) {
		if (e == 1) {
			return r;
		} else if (e == 0) {
			return 1;
		}
		if ((e & 1) == 1) {
			//e 为奇数
			return pow(r * r, e / 2)*r;
		} else {
			return pow(r * r, e / 2);
		}
	}
}
