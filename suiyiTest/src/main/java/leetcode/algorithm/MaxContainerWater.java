package leetcode.algorithm;

/**
 * Created by lys on 2022/11/27.
 * 盛最多的水
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class MaxContainerWater {

	public static void main(String[] args) {
		int[] height = {11, 100, 6, 2, 5, 4, 8, 99, 10};
		final int i = maxArea(height);
		System.out.println(i);
	}

	public static int maxArea(int[] height) {


		//左指针
		int indexL = 0;
		//右指针
		int indexR = height.length - 1;

		//当前体积最大值
		int currentMaxVolume = 0;
		for (int i = 0; ; i++) {
			if (indexL == indexR) {
				break;
			}
			if(currentMaxVolume < fetchMinHeight(height[indexL], height[indexR]) * (indexR - indexL)){
				currentMaxVolume = fetchMinHeight(height[indexL], height[indexR]) * (indexR - indexL);
			}

			if (height[indexL] <= height[indexR]) {
				//左指针移动
				indexL++;
			} else {
				//右指针移动
				indexR--;
			}
		}

		return currentMaxVolume;
	}

	private static int fetchMinHeight(int i, int i1) {
		if (i < i1) {
			return i;
		}
		return i1;
	}
}
