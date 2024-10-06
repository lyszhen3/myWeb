package leetcode.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lys on 2024-10-04.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class 最大数组和 {

	public static void main(String[] args) {

		int[] nums = {8, -19, 5, -4, 20};

		final int 动态规划 = 动态规划(nums);
		System.out.println(动态规划);
		final int 分区治理 = 分区治理(nums);
		System.out.println(分区治理);
	}

	/**
	 * f(i) = Math.max(f(i-1) + nums[i], nums[i])
	 * max = Math.max(f(i), max)
	 *
	 * @param nums
	 * @return
	 */
	public static int 动态规划(int[] nums) {

		int dp = nums[0];
		int max = nums[0];
		for (int num : nums) {
			dp = Math.max(dp + num, num);
			max = Math.max(max, dp);
		}

		return max;
	}

	/**
	 * @param nums
	 * @return
	 */
	public static int 分区治理(int[] nums) {

		return getRegion(nums, 0, nums.length - 1).getmSum();

	}

	private static Region getRegion(int[] nums, int l, int r) {

		if (l == r) {
			return new Region(nums[l], nums[l], nums[l], nums[l]);
		}

		int m = (l + r) / 2;

		Region lr = getRegion(nums, l, m);
		Region rr = getRegion(nums, m + 1, r);

		return sunRegion(lr, rr);
	}

	private static Region sunRegion(Region lr, Region rr) {
		int lSum = Math.max(lr.getlSum(), lr.gettSum() + rr.getlSum());
		int rSum = Math.max(rr.getrSum(), rr.gettSum() + lr.getrSum());
		int tSum = lr.gettSum() + rr.gettSum();
		int mSum =Math.max(Math.max(lr.getmSum(), rr.getmSum()), lr.getrSum() + rr.getlSum());
		return new Region(lSum, rSum, tSum, mSum);
	}

	static class Region {
		/**
		 * 左端点最大和
		 */
		int lSum;
		/**
		 * 右端点最大和
		 */
		int rSum;
		/**
		 * 总和
		 */
		int tSum;
		/**
		 * 最大和
		 */
		int mSum;

		public Region(int lSum, int rSum, int tSum, int mSum) {
			this.lSum = lSum;
			this.rSum = rSum;
			this.tSum = tSum;
			this.mSum = mSum;
		}

		public int getlSum() {
			return lSum;
		}

		public int getrSum() {
			return rSum;
		}

		public int gettSum() {
			return tSum;
		}

		public int getmSum() {
			return mSum;
		}
	}

	public static int max(int max, int t, int lIndex, int rIndex, int[] nums, Map<String, Integer> preMap) {

		if (lIndex == rIndex) {
			return max;
		}

		int lt = t - nums[lIndex];

		preMap.put(lIndex + "," + rIndex, lt);
		int rt = t - nums[rIndex];

		preMap.put(lIndex + "," + rIndex, rt);

		if (preMap.get(lIndex + 1 + "," + rIndex) != null) {
			max = Math.max(max, preMap.get(lIndex + 1 + "," + rIndex));
		} else {
			max = max(max, lt, lIndex + 1, rIndex, nums, preMap);
		}
		max = Math.max(max, lt);
		if (preMap.get(lIndex + "," + (rIndex - 1)) != null) {
			max = Math.max(max, preMap.get(lIndex + "," + (rIndex - 1)));
		} else {
			max = max(max, rt, lIndex, rIndex - 1, nums, preMap);
		}
		max = Math.max(max, rt);
		return max;
	}

}
