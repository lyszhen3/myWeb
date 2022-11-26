package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2022/11/25
 * 给定行数. z字排序后,从左往右输出
 * e.g. 输入 "PAYPALISHIRING" 行数3
 * P     A     H
 * A  P  L  S  I
 * Y     I     R     ....画不动了
 * <p>
 * 输出 PAH......
 */
public class ZShapeSort {
	public static void main(String[] args) {
		String input = "PAYPALISHIRING";
		int row = 3;

		final String convert = convert(input, row);
		System.out.println(convert);

	}

	/**
	 * 通过二维数组实现?
	 *
	 * @param input
	 * @param row
	 * @return
	 */
	private static String convert(String input, int row) {

		if (input == null || input.length() == 0) {
			return null;
		}
		//计算横坐标的长度
		//一个竖斜需要的长度z= row + (row-2)
		//xLength = (input.length +z -1/z) *row  因为长度只能长不能少
		int z = row + (row - 2);
		int xLength = ((input.length() + z - 1) / z) * (row - 1);
		char[][] xy = new char[xLength][row];

		//0代表竖,1,代表斜,竖的时候x轴不变,y轴变
		int type = 0;
		int x = 0, y = 0;
		for (int i = 0; i < input.length(); i++) {
			xy[x][y] = input.charAt(i);
			if (type == 1) {
				x++;
				y--;
				if (y == 0) {
					type = 0;
					continue;
				}
			}
			if (type == 0) {
				y++;
				if (y == row - 1) {
					type = 1;
				}
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < xy[0].length; i++) {
			//纵坐标长度
			for (int j = 0; j < xy.length; j++) {
				//横坐标长度
				stringBuilder.append(xy[j][i]);
			}
		}

		return stringBuilder.toString();
	}
}
