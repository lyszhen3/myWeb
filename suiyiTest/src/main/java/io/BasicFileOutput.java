package io;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by lys on 2017-08-29.
 * 我的天哪
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class BasicFileOutput {
	static String file = "BasicFileOutPut.out";

	/**
	 * 试试输出中文到文件
	 */
	@Test
	public void outPutChinese() {
		String path = "D:\\chinese.txt";
		String chinese = "我的天";
		try (FileWriter fileWriter = new FileWriter(path)) {

//			fileWriter.write(chinese);

			char[] dst = new char[10];
			chinese.getChars(0, chinese.length(), dst, 0);
			fileWriter.write(dst);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void outPutStreamChinese() {
		String path = "D:\\chineseBytes.txt";
		String chinese = "我的天";
		try (FileOutputStream out = new FileOutputStream(path)) {
			out.write(chinese.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws IOException {
		String path = "D:\\workspace\\self\\myWeb\\suiyiTest\\src\\main\\java\\io\\BasicFileOutput.java";
		BufferedReader in = new BufferedReader(new FileReader(path));

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		String s;
		int linCount = 1;
		while ((s = in.readLine()) != null) {
			out.println(linCount++ + ": " + s);
		}
		out.close();
		System.out.println(BufferedInputFile.read(file));

	}
}
