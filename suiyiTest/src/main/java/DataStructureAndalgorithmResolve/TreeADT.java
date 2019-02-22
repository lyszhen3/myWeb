package DataStructureAndalgorithmResolve;

import java.io.File;

/**
 * Created by lys on 2019/2/15.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TreeADT {

}

/**
 * 用文件夹目录作为搜索
 */
class NormalTreeTests {
	private static String path;

	public static String listAll(File file, int depth) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			builder.append(" ");
		}
		System.out.println(builder.toString() + file.getName() + "(" + file.length() / 1024 + "K)");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			++depth;
			assert files != null;
			for (File file1 : files) {
				listAll(file1, depth);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		path = "D:\\Programs";
		File file = new File(path);
		listAll(file, 0);
	}

}

