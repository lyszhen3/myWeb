package serializatest;

/**
 * Created by lys on 2022/7/17.
 *序列化测试
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SerializeTest{

	public static void main(String[] args) {

		final int i = string2int("12");
		System.out.println(i);
		int z =  i >> 8;
		System.out.println(z);

	}


	public static int string2int(String str) {
		if (str == null) {
			return 0;
		}
		final byte[] bytes = str.getBytes();
		int result = 0;
		for (int i = 0; i < bytes.length; i++) {
			result |= bytes[i] << (i*8);
		}

		return result;
	}
}
