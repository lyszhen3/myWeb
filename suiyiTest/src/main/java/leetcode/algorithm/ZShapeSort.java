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
		final String convert2 = convert2(input, row);
		System.out.println(convert);
		System.out.println(convert2);

	}

	/**
	 * 通过二维数组实现?
	 *
	 * @param s
	 * @param numRows
	 * @return
	 */
	private static String convert(String s, int numRows) {

		if (s == null || s.length() == 0) {
			return null;
		}
		//计算横坐标的长度
		//一个竖斜需要的长度z= row + (row-2)
		//xLength = (input.length +z -1/z) *row  因为长度只能长不能少
		int z = numRows + (numRows - 2);
		int xLength = ((s.length() + z - 1) / z) * (numRows - 1);
		char[][] xy = new char[xLength][numRows];

		//0代表竖,1,代表斜,竖的时候x轴不变,y轴变
		int type = 0;
		int x = 0, y = 0;
		for (int i = 0; i < s.length(); i++) {
			xy[x][y] = s.charAt(i);
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
				if (y == numRows - 1) {
					type = 1;
				}
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < xy[0].length; i++) {
			//纵坐标长度
			for (int j = 0; j < xy.length; j++) {
				//横坐标长度
				if (xy[j][i] != '\u0000') {
					stringBuilder.append(xy[j][i]);
				}
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * 在string中按规律寻找, 比如 row =3
	 * 那么第一行就是 0*2*(row-1) , 1*2*(row-1), 2*2*(row-1) ...
	 * 第二行    0*2*(row-2) +`1, 1+ 1*2*(row-2), 1+ 2*2(row-2)
	 *
	 * @param s
	 * @param numRows
	 * @return
	 */
	private static String convert2(String s, int numRows) {

		if (s == null || s.length() == 0) {
			return null;
		}
		if (numRows == 1) {
			return s;
		}

		//等差值
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < numRows; i++) {
			int indexF = 0;
			for (int j = 0; ; j++) {
				if (j == 0) {
					indexF = i;
				} else {
					if (i == numRows -1) {
						//如果是下面的行
						//只走偶数逻辑
						j++;
					}
					if ((j & 1) == 1) {
						//奇数
						//从下往上走

						indexF += 2 * (numRows - 1 - i);

					} else if ((j & 1) != 1) {
						//从上往下走
						indexF += 2 * (i);
					}
					if (i == 0) {
						//如果是最上面的点,只走奇数逻辑
						j++;
					}
				}

				if (indexF > s.length() - 1) {
					break;
				}
				stringBuilder.append(s.charAt(indexF));

			}
		}

		return stringBuilder.toString();
	}
}
