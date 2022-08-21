package io;

import java.io.*;

/**
 * Created by lys on 2022/8/21.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class OutPutStreamTest {
	public static void main(String[] args) {
		try {
			OutputStream out = new FileOutputStream("D://tttt.txt");
			//输出一个感叹号,int转化为二进制在计算机中是个感叹号哈
			out.write(33);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
