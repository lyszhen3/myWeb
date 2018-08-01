import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Test {
	@org.junit.Test
	public void test() {
		Test test = null;
		System.out.println("上一个test" + test);
		if (test == null && (test = new Test()) == null) {
			System.out.println("空空");
		}
		System.out.println("下一个test" + test);
	}


	public static void main(String[] args) {
		long l = TimeUnit.SECONDS.toMillis(2l);
		System.out.println(l);
	}

}
