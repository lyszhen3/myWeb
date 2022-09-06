package algorithm.sort;

/**
 * @author LinYuanSheng
 * @date 2022/8/30
 * 快排
 * 分区治理,递归
 *
 */
public class QuickSort {

	/**
	 * @param a 数组
	 * @param s 开始下标
	 * @param e 结束下标
	 */
	public static void quickSort(int[] a, int s, int e) {
		//先写主流程

		if (s< e) {
			int p = partition(a, s, e);
			quickSort(a, s, p - 1);
			quickSort(a, p + 1, e);
		}
	}

	private static int partition(int[] a, int s, int e) {
		//用数组的最后一位作为分区值,比该值小的在左边,比该值大的在右边

		//总共三个区域
		//e.g  3,2,1,7,6,5
		//3,2,1[i],7,6[j],4,5
		//当j到4时, 交换 a[j]=4 和 a[i+1]=7
		//3,2,1,4[i],6,7[j],5
		int x = a[e];
		int i = s - 1;
		int temp;
		for (int j = s; j < e; j++) {
			if (a[j] <= x) {
				i++;
				//交换位置
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}

		//最后一步把,x换到分区位置
		//x和i+1 交换
		temp = a[i + 1];
		a[i + 1] = x;
		a[e] = temp;
		return i +1;
	}

	public static void main(String[] args) {
		int[] a= {3,2,1,5,6,9,10};
		quickSort(a, 0, a.length-1);
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
	}

}
