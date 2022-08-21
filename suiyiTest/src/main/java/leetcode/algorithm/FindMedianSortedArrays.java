package leetcode.algorithm;

/**
 * Created by lys on 2019/4/21.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class FindMedianSortedArrays {

	public double findMedianSortedArrays(int[] A, int[] B) {
		int m = A.length;
		int n = B.length;
		if (m > n) { // to ensure m<=n
			int[] temp = A;
			A = B;
			B = temp;
			int tmp = m;
			m = n;
			n = tmp;
		}
		int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
		while (iMin <= iMax) {
			int i = (iMin + iMax) / 2;
			int j = halfLen - i;
			if (i < iMax && B[j - 1] > A[i]) {
				iMin = i + 1; // i is too small
			} else if (i > iMin && A[i - 1] > B[j]) {
				iMax = i - 1; // i is too big
			} else { // i is perfect
				int maxLeft = 0;
				if (i == 0) {
					maxLeft = B[j - 1];
				} else if (j == 0) {
					maxLeft = A[i - 1];
				} else {
					maxLeft = Math.max(A[i - 1], B[j - 1]);
				}
				if ((m + n) % 2 == 1) {
					return maxLeft;
				}

				int minRight = 0;
				if (i == m) {
					minRight = B[j];
				} else if (j == n) {
					minRight = A[i];
				} else {
					minRight = Math.min(B[j], A[i]);
				}

				return (maxLeft + minRight) / 2.0;
			}
		}
		return 0.0;
	}


	public static double copy(int[] A, int[] B) {
		int m = A.length;
		int n = B.length;
		//如果m长度大于n交换下
		if (m > n) {
			int[] tmp = A;
			A = B;
			B = tmp;
			m ^= n;
			n ^= m;
			m ^= n;

		}
		int imin = 0, imax = m, halfLen = (m + n + 1) / 2;
		while (imin <=imax) {
			int i = (imin + imax) / 2;
			int j = halfLen - i;
			if (i < imax && B[j - 1] > A[i]) {
				//i 太小需要+1;
				imin = i + 1;
			} else if (i > imin && A[i - 1] > B[j]) {
				// i太大需要-1;
				imax = i - 1;
			} else {
				int maxLeft = 0;
				if (i == 0) {
					maxLeft = B[j - 1];
				} else if (j == 0) {
					maxLeft = A[i - 1];
				} else {
					maxLeft = Math.max(B[j - 1], A[i - 1]);
				}
				if (((m + n) & 1) == 1) {
					return maxLeft;
				}

				int minRight = 0;
				if (i == m) {
					minRight = B[j];
				} else if (j == n) {
					minRight = A[i];
				} else {
					minRight = Math.min(B[j], A[i]);
				}
				return (minRight + maxLeft) / 2.0;

			}
		}
		return 0.0;
	}

	public static void main(String[] args) {
		int[] A = {1,3};
		int[] B = {2};
		double copy = copy(A, B);
		System.out.println(copy);
	}
}
