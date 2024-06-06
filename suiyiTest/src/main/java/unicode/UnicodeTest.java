package unicode;

/**
 * Created by lys on 2024/5/12.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class UnicodeTest {

	public static void main(String[] args) {

		//中的UTF-8 二进制
		int i = 0b111001001011100010101101;
		final String hexString = Integer.toHexString(i);
		System.out.println("utf-8 16进制:" + hexString);
		char zhong = '中';
		final String hexString1 = Integer.toHexString(zhong);
		System.out.println("unicode 16进制:" +hexString1);
	}
}
