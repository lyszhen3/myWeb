package leetcode.algorithm;

/**
 * @author LinYuanSheng
 * @date 2021/6/1
 */
public class RomanNumber {
	public static void main(String[] args) {
		// 字符          数值
		//I             1
		//V             5
		//X             10
		//L             50
		//C             100
		//D             500
		//M             1000
		//
		//来源：力扣（LeetCode）
		//链接：https://leetcode-cn.com/problems/integer-to-roman
		//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
		int[] romChrNum = {1, 5, 10, 50, 100, 500, 1000};
		String[] romChr = {"I", "V", "X", "L", "C", "D", "M"};
		int num = 555;
		//1<num<3999;

		final String convert = convert(num, romChrNum, romChr, null);
		System.out.println(convert);

	}

	private static String convert(int num, int[] romCharNum, String[] romChar, Integer index) {
		if (index == null) {
			index = romCharNum.length - 1;
		}
		//当前最大除数
		int div = romCharNum[index];
		//商
		int quo = num / div;
		//余
		int rem = num % div;
		if (quo > 3 && div == 1000) {
			return "num 不能大于 3999";
		}
		StringBuilder nodeChar = new StringBuilder();
		if (quo <= 3) {
			//小于等于3 则字符叠加
			for (int i = 0; i < quo; i++) {
				nodeChar.append(romChar[index]);
			}
		} else if (quo == 4) {
			nodeChar.append(romChar[index]);
			nodeChar.append(romChar[index + 1]);
		} else if (quo <= 8) {
			nodeChar.append(romChar[index + 1]);
			for (int i = 0; i < quo - 5; i++) {
				nodeChar.append(romChar[index]);
			}
		} else if (quo == 9) {
			nodeChar.append(romChar[index]);
			nodeChar.append(romChar[index + 2]);
		}
		if (rem != 0) {
			//如果余数不等于0
			nodeChar.append(convert(rem, romCharNum, romChar, index - 2));
		}
		return nodeChar.toString();

	}
}
