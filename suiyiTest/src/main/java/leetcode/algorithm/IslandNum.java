package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2021/12/21
 * 岛屿数量
 * <p>
 * o表示陆地 x 表示水
 * 陆地相连则为一个岛屿
 * 如下 有两个岛屿
 * o o o x x x x
 * x x x o x x x
 * x x x x x x x
 */
public class IslandNum {

	public static int isLandNum(int[][] xy) {

		final int length1 = xy.length;
		//纵坐标长度
		final int length2 = xy[0].length;

		//设置一个数组，放陆地
		int[][] isLand = new int[length1][length2];
		int isLandNum = 0;
		for (int j = 0; j < length2; j++) {
			for (int i = 0; i < length1; i++) {
				final int land = xy[i][j];
				//如果是陆地呢就把它抹了，顺便加到 isLand里
				if (land == 3) {
					//如果走到为2的证明该点已经被抹除
					continue;
				}
				if (land == 1) {
					isLand[i][j] = xy[i][j];
					//设为3表示抹除
					xy[i][j] = 0;
					//清理附近的陆地，这里走右下两个路线
					attachIsland(xy, i, j, isLand, length1, length2);

					isLandNum++;

				}
			}

		}

		return isLandNum;
	}

	private static void attachIsland(int[][] xy, int i, int j, int[][] isLand, int length1, int length2) {

		if ((i + 1) <= length1 - 1 && xy[i + 1][j] == 1) {
			xy[i + 1][j] = 0;
			isLand[i + 1][j] = 1;
			attachIsland(xy, i + 1, j, isLand, length1, length2);
		}
		if ((j + 1) <= length2 - 1 && xy[i][j + 1] == 1) {
			xy[i][j + 1] = 0;
			isLand[i][j + 1] = 1;
			attachIsland(xy, i, j + 1, isLand, length1, length2);
		}

	}

	public static void main(String[] args) {

		int[][] xy = new int[3][3];
		//1表示陆地，0表示水
		xy[0][0] = 1;
		xy[2][0] = 1;
		xy[0][1] = 1;
		xy[2][1] = 1;
		xy[1][2] = 1;
		final int landNum = isLandNum(xy);
		System.out.println(landNum);
	}
}
