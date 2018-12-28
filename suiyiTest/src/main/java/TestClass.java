import java.io.File;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Created by lys on 2018/9/6.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestClass {
	private int m;

	public void testTree() {

	}

	private static void listAll(int depth, File file) {
		printName(depth, file.getName());
		if (file.isDirectory()) {
			for (File listFile : Objects.requireNonNull(file.listFiles())) {
				listAll(depth + 1, listFile);
			}
		}
	}

	private static void printName(int depth, String name) {
		StringBuilder path = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			path.append("  ");
		}
		System.out.println(path + name);
	}

	public static void main(String[] args) {
		listAll(0, new File("D:\\storage"));
	}

}
